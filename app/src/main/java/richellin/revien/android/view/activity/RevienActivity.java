package richellin.revien.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import io.realm.Realm;
import java.util.List;

import javax.inject.Inject;
import richellin.revien.android.R;
import richellin.revien.android.RevienApplication;
import richellin.revien.android.databinding.RevienActivityBinding;
import richellin.revien.android.di.HasComponent;
import richellin.revien.android.di.component.ActivityComponent;
import richellin.revien.android.di.module.ActivityModule;
import richellin.revien.android.model.Sentence;
import richellin.revien.android.view.adapter.SentenceAdapter;
import richellin.revien.android.viewmodel.RevienViewModelContract;
import richellin.revien.android.viewmodel.RevienViewModel;

public class RevienActivity extends BaseActivity implements RevienViewModelContract.MainView,
    HasComponent<ActivityComponent> {

  private RevienActivityBinding revienActivityBinding;

  @Inject RevienViewModel viewModel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getComponent().inject(this);

    initDataBinding();

    bindViewModel(viewModel);

    setSupportActionBar(revienActivityBinding.toolbar);
    setupListSentenceView(revienActivityBinding.listSentence);

    initialize();
  }

  public void initialize() {
    viewModel.initialize();
  }

  private void initDataBinding() {
    revienActivityBinding = DataBindingUtil.setContentView(this, R.layout.revien_activity);
    revienActivityBinding.setViewModel(viewModel);
  }

  private void setupListSentenceView(RecyclerView listSentence) {
    SentenceAdapter adapter = new SentenceAdapter();
    listSentence.setAdapter(adapter);
    listSentence.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override public ActivityComponent getComponent() {
    return ((RevienApplication) getApplication()).getComponent().activityComponent(new ActivityModule(this, this));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    viewModel.destroy();
  }

  @Override public Context getContext() {
    return RevienActivity.this;
  }

  @Override public void loadData(List<Sentence> sentences) {
    SentenceAdapter sentenceAdapter =
        (SentenceAdapter) revienActivityBinding.listSentence.getAdapter();
    sentenceAdapter.setSentenceList(sentences);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_github) {
      startActivityActionView();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void startActivityActionView() {
    startActivity(
        new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/richellin/revien-android")));
  }
}
