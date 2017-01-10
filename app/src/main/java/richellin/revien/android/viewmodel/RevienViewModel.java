package richellin.revien.android.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
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
import richellin.revien.android.R;
import richellin.revien.android.RevienApplication;
import richellin.revien.android.data.RevienService;
import richellin.revien.android.model.Sentence;
import richellin.revien.android.model.Today;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by LEESANGJUN on 2017/01/09.
 */

public class RevienViewModel implements RevianViewModelContract.ViewModel  {
    public ObservableInt revienProgress;
    public ObservableInt revienList;
    public ObservableInt revienLabel;
    public ObservableField<String> messageLabel;

    private RevianViewModelContract.MainView mainView;
    private Context context;
    private Subscription subscription;

    private List<Sentence> sentences = new ArrayList<>();

    public RevienViewModel(@NonNull RevianViewModelContract.MainView mainView,
                           @NonNull Context context) {

        this.mainView = mainView;
        this.context = context;
        revienProgress = new ObservableInt(View.GONE);
        revienList = new ObservableInt(View.GONE);
        revienLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>(context.getString(R.string.default_loading_sentence));
    }

    public void initialize() {
        initializeViews();
        getSentenceList();
    }

    public void onClickFabLoad(View view) {
        initializeViews();
        getSentenceList();
    }


    //It is "public" to show an example of test
    public void initializeViews() {
        revienLabel.set(View.GONE);
        revienList.set(View.GONE);
        revienProgress.set(View.VISIBLE);
    }
    
    private void getSentenceList() {
        unSubscribeFromObservable();
        RevienApplication revienApplication = RevienApplication.create(context);

        RevienService revienService = revienApplication.getRevienService();

        DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));

        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(df.parse(df.format(new Date())));
            cal.add(Calendar.DATE, -1);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String currentTime = df.format(cal.getTime());

        Realm realm = Realm.getDefaultInstance();

        // Get a Realm instance for this thread
        Today todaySentences = realm.where(Today.class).equalTo("date",Integer.parseInt(currentTime)).findFirst();

        if (todaySentences != null) {
            sentences = todaySentences.getSentences();

            Log.d("main1!!", "size : "+sentences.size()); // => 0 because no dogs have been added to the Realm yet

            revienProgress.set(View.GONE);
            revienLabel.set(View.GONE);
            revienList.set(View.VISIBLE);
            mainView.loadData(sentences);
        } else {
            subscription = revienService.getSentence(Integer.parseInt(currentTime))
                .subscribeOn(revienApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((jsonObject) -> {
                    revienProgress.set(View.GONE);
                    revienLabel.set(View.GONE);
                    revienList.set(View.VISIBLE);
                    if (mainView != null) {
                        // Persist your data in a transaction
                        realm.beginTransaction();
                        Today today = realm.createObject(Today.class, currentTime); // Create managed objects directly
                        for (int i = 0; i < jsonObject.size(); i++) {
                            Sentence sentence = new Gson().fromJson(jsonObject.get(String.valueOf(i)).toString(), Sentence.class);
                            sentences.add(sentence);
                            today.getSentences().add(realm.copyToRealm(sentence));// Persist unmanaged objects
                        }
                        realm.commitTransaction();
                        mainView.loadData(sentences);
                    }
                }, (throwable) -> {
                        throwable.printStackTrace();
                        messageLabel.set(context.getString(R.string.error_loading_sentence));
                        revienProgress.set(View.GONE);
                        revienLabel.set(View.VISIBLE);
                        revienList.set(View.GONE);
                    }
                );
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
}
