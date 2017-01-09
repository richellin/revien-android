package richellin.revien.android.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import richellin.revien.android.R;
import richellin.revien.android.RevienApplication;
import richellin.revien.android.data.RevienService;
import richellin.revien.android.model.Sentence;
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

    public RevienViewModel(@NonNull RevianViewModelContract.MainView mainView,
                           @NonNull Context context) {

        this.mainView = mainView;
        this.context = context;
        revienProgress = new ObservableInt(View.GONE);
        revienList = new ObservableInt(View.GONE);
        revienLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>(context.getString(R.string.default_loading_sentence));
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

        String currentTime = df.format(new Date());

        subscription = revienService.getSentence(Integer.parseInt(currentTime))
                .subscribeOn(revienApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((jsonObject) -> {
                    revienProgress.set(View.GONE);
                    revienLabel.set(View.GONE);
                    revienList.set(View.VISIBLE);
                    if (mainView != null) {
                        List<Sentence> sentences = new ArrayList<>();
                        for (int i = 0; i < jsonObject.size(); i++) {
                            sentences.add(new Gson().fromJson(jsonObject.get(String.valueOf(i)).toString(), Sentence.class));
                        }
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
