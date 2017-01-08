package richellin.revien.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import richellin.revien.android.Api.APIService;
import richellin.revien.android.Api.APIUtils;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private TextView mResponseTv;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText dateEt = (EditText) findViewById(R.id.et_date);

        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        mResponseTv = (TextView) findViewById(R.id.tv_response);

        mAPIService = APIUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = dateEt.getText().toString().trim();
                if(!TextUtils.isEmpty(date)) {
                    getSentence(Integer.parseInt(date));
                }
            }
        });
    }

    public void getSentence(int date) {

        //Call<List<Sentence>> sentences =  mAPIService.getSentence(date);

        mAPIService.getSentence(date).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API1." + response.body().toString());
                    Log.i(TAG, "post submitted to API2." + response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i(TAG,t.toString());
                Log.e(TAG, "Unable to submit post to API.");
            }
        });

        /*
        mAPIService.getSentence(date).enqueue(new Callback<Sentences>() {
            @Override
            public void onResponse(Call<Sentences> call, Response<Sentences> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API1." + response.body().toString());
                    Log.i(TAG, "post submitted to API2." + response);
                }
            }

            @Override
            public void onFailure(Call<Sentences> call, Throwable t) {
                Log.i(TAG,t.toString());
                Log.e(TAG, "Unable to submit post to API.");
            }
        });


        mAPIService.getSentence(date).enqueue(new Callback<List<Sentence>>() {
            @Override
            public void onResponse(Call<List<Sentence>> call, Response<List<Sentence>> response) {
                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Sentence>> call, Throwable t) {
                Log.i(TAG,t.toString());
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
        */
        /*
        mAPIService.getSentence(date).enqueue(new Callback<List<Sentence>>() {
            @Override
            public void onResponse(Response<List<Sentence>> response, Response<Sentence> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Sentence> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
        */
    }

    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }
}
