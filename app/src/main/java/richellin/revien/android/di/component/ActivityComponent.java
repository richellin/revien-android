package richellin.revien.android.di.component;

import dagger.Subcomponent;
import richellin.revien.android.di.PerActivity;
import richellin.revien.android.di.module.ActivityModule;
import richellin.revien.android.view.activity.RevienActivity;

/**
 * Created by sangjun_lee on 2017/01/24.
 */

@PerActivity
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
  void inject(RevienActivity activity);
}
