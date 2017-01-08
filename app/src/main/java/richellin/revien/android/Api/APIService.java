package richellin.revien.android.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by LEESANGJUN on 2017/01/08.
 */

public interface APIService {
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
    @GET("Prod/resource/{date}")
    Call<JsonObject> getSentence(
            @Path("date") int date
    );
}
