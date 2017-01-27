package richellin.revien.android.di.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import java.util.List;
import richellin.revien.android.model.Sentence;
import richellin.revien.android.view.activity.BaseActivity;
import richellin.revien.android.viewmodel.RevienViewModel;
import richellin.revien.android.viewmodel.RevienViewModelContract;

/**
 * Created by sangjun_lee on 2017/01/24.
 */

@Module public class ActivityModule {

  private final BaseActivity activity;
  private final RevienViewModelContract.MainView mainView;

  public ActivityModule(BaseActivity activity, RevienViewModelContract.MainView mainView) {
    this.activity = activity;
    this.mainView = mainView;
  }

  @Provides
  public RevienViewModel provideRevienViewModel() {
    return new RevienViewModel(activity, mainView);
  }
}
