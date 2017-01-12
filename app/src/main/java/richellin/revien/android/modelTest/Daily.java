package richellin.revien.android.modelTest;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LEESANGJUN on 2017/01/11.
 */

public class Daily extends RealmObject {
    @PrimaryKey
    private long date;
    private RealmList<Sentence> sentences; // Declare one-to-many relationships

    public RealmList<Sentence> getSentences() {
        return sentences;
    }
}
