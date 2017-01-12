package richellin.revien.android.modelTest;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by LEESANGJUN on 2017/01/08.
 */

public class Sentence extends RealmObject {
    @SerializedName("ko")
    //@Expose
    private String ko;
    @SerializedName("en")
    //@Expose
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
