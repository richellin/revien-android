package richellin.revien.android.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import richellin.revien.android.R;
import richellin.revien.android.databinding.RevienActivityBinding;
import richellin.revien.android.model.Sentence;
import richellin.revien.android.viewmodel.RevianViewModelContract;
import richellin.revien.android.viewmodel.RevienViewModel;

public class RevienActivity extends AppCompatActivity implements RevianViewModelContract.MainView {
    private RevienActivityBinding revienActivityBinding;
    private RevienViewModel revienViewModel;
    private RevianViewModelContract.MainView mainView = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();
        setSupportActionBar(revienActivityBinding.toolbar);
        setupListSentenceView(revienActivityBinding.listSentence);

        initialize();
    }

    private void initialize() {
        revienViewModel.initialize();
    }

    private void initDataBinding() {
        revienActivityBinding = DataBindingUtil.setContentView(this, R.layout.revien_activity);
        revienViewModel = new RevienViewModel(mainView, getContext());

        revienActivityBinding.setRevienViewModel(revienViewModel);
    }

    private void setupListSentenceView(RecyclerView listSentence) {
        SentenceAdapter adapter = new SentenceAdapter();
        listSentence.setAdapter(adapter);
        listSentence.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        revienViewModel.destroy();
    }

    @Override
    public Context getContext() {
        return RevienActivity.this;
    }

    @Override
    public void loadData(List<Sentence> sentences) {
        SentenceAdapter peopleAdapter = (SentenceAdapter) revienActivityBinding.listSentence.getAdapter();
        peopleAdapter.setSentenceList(sentences);
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
