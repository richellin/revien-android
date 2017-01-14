package richellin.revien.android.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.view.View;

import richellin.revien.android.model.Sentence;

/**
 * Created by richellin on 2017/01/09.
 */

public class ItemRevienViewModel extends BaseObservable {
  private Sentence sentence;
  private Context context;
  private int visible;

  public ObservableInt enLabel;

  public ItemRevienViewModel(Sentence sentence, Context context) {
    this(sentence, context, 0);
  }

  public ItemRevienViewModel(Sentence sentence, Context context, int visible) {
    this.sentence = sentence;
    this.context = context;
    this.visible = visible;
    enLabel = new ObservableInt(visible == 0 ? View.GONE : View.VISIBLE);
  }

  @Bindable public String getEn() {
    return sentence.getEn();
  }

  @Bindable public String getKo() {
    return sentence.getKo();
  }

  public void fetchVisible() {
    if (visible == 0) {
      enLabel.set(View.GONE);
    } else {
      enLabel.set(View.VISIBLE);
    }
  }

  public void setSentence(Sentence sentence, int visible) {
    this.sentence = sentence;
    this.visible = visible;
    fetchVisible();
    notifyChange();
  }
}
