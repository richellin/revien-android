package richellin.revien.android.viewmodel.base;

import android.content.Context;
import android.support.annotation.NonNull;
import richellin.revien.android.view.activity.BaseActivity;

/**
 * Created by sangjun_lee on 2017/01/24.
 */

public abstract class ActivityViewModel {
  private final BaseActivity activity;

  public ActivityViewModel(BaseActivity activity) {
    this.activity = activity;
  }

  @NonNull
  public BaseActivity getActivity() {
    if (activity != null) {
      return activity;
    }
    throw new IllegalStateException("No view attached");
  }

  @NonNull
  public Context getContext() {
    if (activity != null) {
      return activity;
    }
    throw new IllegalStateException("No view attached");
  }


  public abstract void onStart();

  public abstract void onResume();

  public abstract void onPause();

  public abstract void onStop();
}
