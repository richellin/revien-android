package richellin.revien.android.data;

import java.util.List;

import richellin.revien.android.model.Sentence;

/**
 * Created by richellin on 2017/01/08.
 */

public class RevienResponse {
  //@SerializedName("results")
  private List<Sentence> sentenceList;

  public List<Sentence> getSentenceList() {
    return sentenceList;
  }

  public void setSentenceList(List<Sentence> mSentenceList) {
    this.sentenceList = mSentenceList;
  }
}
