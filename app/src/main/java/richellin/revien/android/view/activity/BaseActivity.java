package richellin.revien.android.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import richellin.revien.android.viewmodel.base.ActivityViewModel;

/**
 * Created by sangjun_lee on 2017/01/24.
 */

public class BaseActivity extends AppCompatActivity{

  private ActivityViewModel viewModel;

  public void bindViewModel(ActivityViewModel viewModel) {
    this.viewModel = viewModel;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onStart() {
    super.onStart();
    checkViewModel();
    viewModel.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    checkViewModel();
    viewModel.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    checkViewModel();
    viewModel.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    checkViewModel();
    viewModel.onStop();
  }

  private void checkViewModel() {
    if (viewModel == null) {
      throw new IllegalStateException("Before resuming activity, bindViewModel must be called.");
    }
  }
}
