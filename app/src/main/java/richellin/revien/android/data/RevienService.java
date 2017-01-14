package richellin.revien.android.data;

import com.google.gson.JsonObject;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by richellin on 2017/01/08.
 */

public interface RevienService {
    @Headers({
            "Accept: application/json"
    })


    @GET("Prod/resource/{date}")
    Observable<JsonObject> getSentence(
            @Path("date") int date
    );

    /*
    @GET("Prod/resource/{date}")
    Observable<RevienResponse> getSentence(
            @Path("date") int date
    );
    */

    /*
    @GET("Prod/resource/{date}")
    Call<List<Sentence>> getSentence(
            @Path("date") int date
    );

    @GET("Prod/resource/{date}")
    Call<Sentences> getSentence(
            @Path("date") int date
    );
    */
}
