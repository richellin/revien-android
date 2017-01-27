package richellin.revien.android.di.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import richellin.revien.android.RevienApplication;
import richellin.revien.android.data.RevienFactory;
import richellin.revien.android.data.RevienService;

/**
 * Created by sangjun_lee on 2017/01/24.
 */

@Module public class AppModule {
  private final RevienApplication application;

  public AppModule(RevienApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton RevienService provideRevienService() {
    return RevienFactory.create();
  }

}
