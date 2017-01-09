package richellin.revien.android.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.view.View;

import richellin.revien.android.model.Sentence;

/**
 * Created by LEESANGJUN on 2017/01/09.
 */

public class ItemRevienViewModel extends BaseObservable {
    private Sentence sentence;
    private Context context;

    public final ObservableInt enLabel = new ObservableInt(View.GONE);

    public ItemRevienViewModel(Sentence sentence, Context context) {
        this.sentence = sentence;
        this.context = context;
    }

    @Bindable
    public String getEn() {
         return sentence.getEn();
    }

    @Bindable
    public String getKo() {
        return sentence.getKo();
    }

    public void onItemClick(View view) {
        if (enLabel.get() == View.GONE) {
            enLabel.set(View.VISIBLE);
        } else {
            enLabel.set(View.GONE);
        }
        //context.startActivity(SentenceDetailActivity.launchDetail(view.getContext(), sentence));
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
        notifyChange();
    }
}
