package richellin.revien.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import richellin.revien.android.model.Sentence;
import richellin.revien.android.viewmodel.ItemRevienViewModel;

import static org.junit.Assert.assertEquals;

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

@RunWith(RobolectricGradleTestRunner.class) @Config(constants = BuildConfig.class, sdk = 21)
public class ItemRevienViewModelTest {
  private static final String SENTENCE_KO_TEST = "안녕하세요, 반갑습니다.";
  private static final String SENTENCE_EN_TEST = "Hello, Nice to meet you.";

  private RevienApplication revienpplication;

  @Before public void setUpItemRevienModelTest() {
    revienpplication = (RevienApplication) RuntimeEnvironment.application;
  }

  @Test public void shouldGetSentenceKo() throws Exception {
    Sentence sentence = new Sentence();
    sentence.setKo(SENTENCE_KO_TEST);
    ItemRevienViewModel itemRevienViewModel = new ItemRevienViewModel(sentence, revienpplication);
    assertEquals(sentence.getKo(), itemRevienViewModel.getKo());
  }

  @Test public void shouldGetSentenceEn() throws Exception {
    Sentence sentence = new Sentence();
    sentence.setEn(SENTENCE_EN_TEST);
    ItemRevienViewModel itemRevienViewModel = new ItemRevienViewModel(sentence, revienpplication);
    assertEquals(sentence.getEn(), itemRevienViewModel.getEn());
  }
}
