package richellin.revien.android.Api;

/**
 * Created by LEESANGJUN on 2017/01/08.
 */

public class APIUtils {

    public static final String BASE_URL = "https://h50yoomb24.execute-api.ap-northeast-1.amazonaws.com";

    //resource/20170107
    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
