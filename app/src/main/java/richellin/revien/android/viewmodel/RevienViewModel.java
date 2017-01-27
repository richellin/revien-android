package richellin.revien.android.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmResults;
import richellin.revien.android.R;
import richellin.revien.android.RevienApplication;
import richellin.revien.android.data.RevienService;
import richellin.revien.android.model.Daily;
import richellin.revien.android.model.Sentence;
import richellin.revien.android.util.Constant;
import richellin.revien.android.util.SharedPrefUtil;
import richellin.revien.android.view.activity.BaseActivity;
import richellin.revien.android.viewmodel.base.ActivityViewModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by richellin on 2017/01/09.
 */

public class RevienViewModel extends ActivityViewModel implements RevienViewModelContract.ViewModel {
  final private int REVEN_DAY = -7;
  public ObservableInt revienProgress;
  public ObservableInt revienList;
  public ObservableInt revienLabel;
  public ObservableField<String> messageLabel;

  private RevienViewModelContract.MainView mainView;
  private Context context;
  private Subscription subscription;

  private List<Sentence> sentences;

  private Realm realm;

  private int isRevers;

  public RevienViewModel(BaseActivity activity, @NonNull RevienViewModelContract.MainView mainView) {
    super(activity);
    this.mainView = mainView;
    this.context = getContext();
    revienProgress = new ObservableInt(View.GONE);
    revienList = new ObservableInt(View.GONE);
    revienLabel = new ObservableInt(View.VISIBLE);
    messageLabel = new ObservableField<>(context.getString(R.string.default_loading_sentence));
    sentences = new ArrayList<>();
  }

  public void initialize() {
    realm = getRealm();
    isRevers = getRevers();
    delDaily(getDiffDate(REVEN_DAY));
    initializeViews();
    getSentenceList();
  }

  public void onClickFabLoad(View view) {
    initializeViews();
    getSentenceList();
  }

  public void onClickRevers(View view) {
    updateRevers();

    realm.beginTransaction();
    for (Sentence sentence : sentences) {
      sentence.revers();
    }
    realm.commitTransaction();
    mainView.loadData(sentences);
  }

  private void updateRevers() {
    if (isRevers == 0) {
      isRevers = 1;
    } else {
      isRevers = 0;
    }
    SharedPrefUtil.setInt(context, Constant.REVERS, isRevers);
  }

  private int getRevers() {
    return SharedPrefUtil.getInt(context, Constant.REVERS);
  }

  private Realm getRealm() {
    return Realm.getDefaultInstance();
  }

  public void initializeViews() {
    revienLabel.set(View.GONE);
    revienList.set(View.GONE);
    revienProgress.set(View.VISIBLE);
  }

  private void endedViews() {
    revienLabel.set(View.GONE);
    revienList.set(View.VISIBLE);
    revienProgress.set(View.GONE);
  }

  private void emptyViews() {
    messageLabel.set(context.getString(R.string.error_loading_sentence));
    revienLabel.set(View.VISIBLE);
    revienList.set(View.GONE);
    revienProgress.set(View.GONE);
  }

  private void getSentenceList() {
    unSubscribeFromObservable();
    RevienApplication revienApplication = RevienApplication.create(context);

    RevienService revienService = revienApplication.getRevienService();

    int endDate = getDiffDate(-1);
    int week = getDayOfWeek(endDate);

    final RealmResults<Daily> dailies = getDailies(endDate);
    final Daily checkDaily = realm.where(Daily.class).equalTo("date", endDate).findFirst();

    if (checkDaily != null) {
      for (Daily daily : dailies) {
        List<Sentence> tmp = daily.getSentences();
        for (Sentence sentence : tmp) {
          sentences.add(sentence);
        }
      }
      endedViews();
      mainView.loadData(sentences);
    } else {
      // Don't get one Sunday
      if (week != 1) {
        subscription = revienService.getSentence(endDate)
            .subscribeOn(revienApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((jsonObject) -> {
              endedViews();
              if (mainView != null) {
                // Persist your data in a transaction
                realm.beginTransaction();
                Daily daily =
                    realm.createObject(Daily.class, endDate); // Create managed objects directly
                for (int i = 0; i < jsonObject.size(); i++) {
                  Sentence sentence =
                      new Gson().fromJson(jsonObject.get(String.valueOf(i)).toString(),
                          Sentence.class);
                  if (isRevers == 1) {
                    sentence.revers();
                  }
                  sentences.add(sentence);
                  daily.getSentences().add(realm.copyToRealm(sentence));// Persist unmanaged objects
                }
                realm.commitTransaction();
                mainView.loadData(sentences);
              }
            }, (throwable) -> {
              throwable.printStackTrace();
              emptyViews();
            });
      }
    }
  }

  private void unSubscribeFromObservable() {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }

  @Override public void destroy() {
    reset();
  }

  private void reset() {
    unSubscribeFromObservable();
    subscription = null;
    context = null;
    mainView = null;
  }

  private int getDayOfWeek(int input) {
    int week = 0;
    DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);
    df.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));

    Calendar cal = Calendar.getInstance();

    try {
      cal.setTime(df.parse(String.valueOf(input)));
      week = cal.get(Calendar.DAY_OF_WEEK);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return week;
  }

  private int getDiffDate(int diff) {
    DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);
    df.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));

    Calendar cal = Calendar.getInstance();

    try {
      cal.setTime(df.parse(df.format(new Date())));
      cal.add(Calendar.DATE, diff);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    String endDate = df.format(cal.getTime());

    return Integer.parseInt(endDate);
  }

  private RealmResults<Daily> getDailies(int date) {
    return realm.where(Daily.class).lessThanOrEqualTo("date", date).findAll();
  }

  private void delDaily(int date) {
    final RealmResults<Daily> dailies =
        realm.where(Daily.class).lessThanOrEqualTo("date", date).findAll();
    realm.executeTransaction((r) -> {
      dailies.deleteAllFromRealm();
    });
  }

  @Override public void onStart() {

  }

  @Override public void onResume() {

  }

  @Override public void onPause() {

  }

  @Override public void onStop() {

  }
}
