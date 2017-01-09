package richellin.revien.android.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import richellin.revien.android.R;
import richellin.revien.android.databinding.ItemSentenceBinding;
import richellin.revien.android.model.Sentence;
import richellin.revien.android.viewmodel.ItemRevienViewModel;

/**
 * Created by LEESANGJUN on 2017/01/09.
 */

public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.SentenceAdapterViewHolder> {

    private List<Sentence> sentenceList;

    public SentenceAdapter() {
        this.sentenceList = Collections.emptyList();
    }

    @Override public SentenceAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSentenceBinding itemSentenceBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sentence,
                        parent, false);
        return new SentenceAdapterViewHolder(itemSentenceBinding);
    }

    @Override public void onBindViewHolder(SentenceAdapterViewHolder holder, int position) {
        holder.bindSentence(sentenceList.get(position));
    }

    @Override public int getItemCount() {
        return sentenceList.size();
    }

    public void setSentenceList(List<Sentence> sentenceList) {
        this.sentenceList = sentenceList;
        notifyDataSetChanged();
    }

    public static class SentenceAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemSentenceBinding mItemSentenceBinding;

        public SentenceAdapterViewHolder(ItemSentenceBinding itemSentenceBinding) {
            super(itemSentenceBinding.itemSentence);
            this.mItemSentenceBinding = itemSentenceBinding;
        }

        void bindSentence(Sentence sentence) {
            if (mItemSentenceBinding.getSentenceViewModel() == null) {
                mItemSentenceBinding.setSentenceViewModel(
                        new ItemRevienViewModel(sentence, itemView.getContext()));
            } else {
                mItemSentenceBinding.getSentenceViewModel().setSentence(sentence);
            }
        }
    }
}
