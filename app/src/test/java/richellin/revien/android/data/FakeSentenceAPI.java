package richellin.revien.android.data;

import java.util.ArrayList;
import java.util.List;

import richellin.revien.android.model.Sentence;

/**
 * Created by richellin on 2017/01/14.
 */

public class FakeSentenceAPI {
  private static final String SENTENCE_KO_TEST = "안녕하세요, 반갑습니다.";
  private static final String SENTENCE_EN_TEST = "Hello, Nice to meet you.";

  public static List<Sentence> getSentencesList() {
    List<Sentence> sentences = new ArrayList<>();
    for (int i = 0; i < 10; i++)
      sentences.add(getSentence());

    return sentences;
  }

  public static Sentence getSentence() {
    Sentence sentence = new Sentence();
    sentence.setKo(SENTENCE_KO_TEST);
    sentence.setEn(SENTENCE_EN_TEST);
    return sentence;
  }
}
