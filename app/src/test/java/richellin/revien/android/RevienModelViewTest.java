package richellin.revien.android;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import richellin.revien.android.data.FakeSentenceAPI;
import richellin.revien.android.data.RevienService;
import richellin.revien.android.databinding.RevienActivityBinding;
import richellin.revien.android.model.Sentence;
import richellin.revien.android.viewmodel.RevienViewModel;
import richellin.revien.android.viewmodel.RevienViewModelContract;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

/**
 * Notes for Mac!!
 * <p/>
 * If you are on a Mac, you will probably need to configure the
 * default JUnit test runner configuration in order to work around a bug where IntelliJ / Android
 * Studio
 * does not set the working directory to the module being tested. This can be accomplished by
 * editing
 * the run configurations, Defaults -> JUnit and changing the working directory value to
 * $MODULE_DIR$
 * <p/>
 * You have to specify  sdk < 23 (Robolectric does not support API level 23.)
 * <p/>
 * https://github.com/robolectric/robolectric/issues/1648
 **/

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = Config.NONE)
public class RevienModelViewTest {

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
    RevienApplication revienApplication = (RevienApplication) RuntimeEnvironment.application;
    revienApplication.setRevienService(revienService);
    revienApplication.setScheduler(Schedulers.immediate());

    revienViewModel = new RevienViewModel(mainView, revienApplication);
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
