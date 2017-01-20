package richellin.revien.android.viewmodel;

import org.junit.Test;

import richellin.revien.android.AbstractRobolectricTestCase;
import richellin.revien.android.model.Sentence;

import static org.junit.Assert.assertEquals;

public class ItemRevienViewModelTest extends AbstractRobolectricTestCase {
  private static final String SENTENCE_KO_TEST = "안녕하세요, 반갑습니다.";
  private static final String SENTENCE_EN_TEST = "Hello, Nice to meet you.";

  @Test public void shouldGetSentenceKo() throws Exception {
    Sentence sentence = new Sentence();
    sentence.setKo(SENTENCE_KO_TEST);
    ItemRevienViewModel itemRevienViewModel = new ItemRevienViewModel(sentence, application);
    assertEquals(sentence.getKo(), itemRevienViewModel.getKo());
  }

  @Test public void shouldGetSentenceEn() throws Exception {
    Sentence sentence = new Sentence();
    sentence.setEn(SENTENCE_EN_TEST);
    ItemRevienViewModel itemRevienViewModel = new ItemRevienViewModel(sentence, application);
    assertEquals(sentence.getEn(), itemRevienViewModel.getEn());
  }
}
