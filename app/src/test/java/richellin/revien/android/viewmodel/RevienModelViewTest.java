package richellin.revien.android.viewmodel;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import richellin.revien.android.AbstractRobolectricTestCase;
import richellin.revien.android.data.FakeSentenceAPI;
import richellin.revien.android.data.RevienService;
import richellin.revien.android.databinding.RevienActivityBinding;
import richellin.revien.android.model.Sentence;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

public class RevienModelViewTest extends AbstractRobolectricTestCase {

  private static final int DATE_TEST = 20170114;

  @Mock private RevienService revienService;

  @Mock private RevienViewModelContract.MainView mainView;

  private RevienViewModel revienViewModel;

  @Mock private RevienActivityBinding revienActivityBinding;

  @Before public void setUpMainViewModelTest() {
    // inject the mocks
    MockitoAnnotations.initMocks(this);

    // Mock the RevienService so we don't call the Daily API (we are simulating only a call to the api)
    // and all observables will now run on the same thread

    application.setRevienService(revienService);
    application.setScheduler(Schedulers.immediate());

    revienViewModel = new RevienViewModel(mainView, application);
  }

  @Test public void simulateGivenTheDailyCallListFromApi() throws Exception {
    List<Sentence> sentences = FakeSentenceAPI.getSentencesList();

    doReturn(rx.Observable.just(sentences)).when(revienService).getSentence(DATE_TEST);
  }

  @Test public void ensureTheViewsAreInitializedCorrectly() throws Exception {
    revienViewModel.initializeViews();
    assertEquals(View.GONE, revienViewModel.revienLabel.get());
    assertEquals(View.GONE, revienViewModel.revienList.get());
    assertEquals(View.VISIBLE, revienViewModel.revienProgress.get());
  }
}
