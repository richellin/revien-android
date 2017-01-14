package richellin.revien.android.viewmodel;

import android.content.Context;

import java.util.List;

import richellin.revien.android.model.Sentence;

/**
 * Created by richellin on 2017/01/09.
 */

public class RevienViewModelContract {
  public interface MainView {

    Context getContext();

    void loadData(List<Sentence> sentences);
  }

  public interface ViewModel {

    void destroy();
  }
}
