package richellin.revien.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LEESANGJUN on 2017/01/08.
 */

public class Sentence {
    @SerializedName("ko")
    @Expose
    private String ko;
    @SerializedName("en")
    @Expose
    private String en;

    public String getKo() {
        return ko;
    }

    public void setKo(String ko) {
        this.ko = ko;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "ko='" + ko + '\'' +
                ", en='" + en +
                '}';
    }
}
