package richellin.revien.android;

import android.app.Application;
import android.content.Context;

import richellin.revien.android.data.RevienFactory;
import richellin.revien.android.data.RevienService;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by LEESANGJUN on 2017/01/09.
 */

public class RevienApplication extends Application {
    private RevienService revienService;
    private Scheduler scheduler;

    private static RevienApplication get(Context context) {
        return (RevienApplication) context.getApplicationContext();
    }

    public static RevienApplication create(Context context) {
        return RevienApplication.get(context);
    }

    public RevienService getRevienService() {
        if (revienService == null) revienService = RevienFactory.create();

        return revienService;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) scheduler = Schedulers.io();

        return scheduler;
    }

    public void setRevienService(RevienService revienService) {
        this.revienService = revienService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
