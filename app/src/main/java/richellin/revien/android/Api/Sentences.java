package richellin.revien.android.Api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import richellin.revien.android.Model.Sentence;

/**
 * Created by LEESANGJUN on 2017/01/08.
 */

public class Sentences {
    @SerializedName(value="sentences")
    public List<Sentence> sentences;
    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;

    }

}

