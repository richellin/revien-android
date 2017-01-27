package richellin.revien.android;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import io.realm.Realm;
import javax.inject.Inject;
import richellin.revien.android.data.RevienService;
import richellin.revien.android.di.HasComponent;
import richellin.revien.android.di.component.AppComponent;
import richellin.revien.android.di.component.DaggerAppComponent;
import richellin.revien.android.di.module.AppModule;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by richellin on 2017/01/09.
 */

public class RevienApplication extends Application implements HasComponent<AppComponent> {
  private AppComponent appComponent;
  @Inject RevienService revienService;
  private Scheduler scheduler;

  @Override public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      if (LeakCanary.isInAnalyzerProcess(this)) {
        return;
      }
      LeakCanary.install(this);

      Stetho.initializeWithDefaults(this);
    }

    // build component
    appComponent = buildAppComponent();

    // Initialize Realm
    initRealm();
  }

  protected AppComponent buildAppComponent() {
    return DaggerAppComponent.builder()
        .build();
  }

  private static RevienApplication get(Context context) {
    return (RevienApplication) context.getApplicationContext();
  }

  public static RevienApplication create(Context context) {
    return RevienApplication.get(context);
  }

  public RevienService getRevienService() {
    return revienService;
  }

  @Override public AppComponent getComponent() {
    return appComponent;
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

  public void initRealm() {
    Realm.init(getApplicationContext());
  }
}
