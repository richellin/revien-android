package richellin.revien.android.di.component;

import dagger.Component;
import richellin.revien.android.di.module.ActivityModule;
import richellin.revien.android.di.module.AppModule;

/**
 * Created by sangjun_lee on 2017/01/24.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
  ActivityComponent activityComponent(ActivityModule activityModule);
}
