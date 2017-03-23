package mint.ivan.rk1;

/**
 * Created by ivan on 3/21/17.
 */

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Request.Builder;

public class RateLoader {
    private static final String TAG = "RateLoader";
    private final OkHttpClient httpClient = new OkHttpClient();

    class ResponseObject {
        @SerializedName("currency")
        private String currency;
        @SerializedName("value")
        private Float value;
        private ResponseObject() {}
        public Float getValue() {
            return value;
        }
    }

    public RateLoader() {
        Log.d(TAG, "RateLoader() called");
    }

    public float loadRate(String currency) throws IOException {
        Log.d(TAG, "loadRate() called with: currency = [" + currency + "]");
        Request request = (new Builder())
                .url("https://community-bitcointy.p.mashape.com/convert/1/" + currency)
                .header("X-Mashape-Key", "0g92zpy6jrmshe8KpuatYLGTMlCqp1T3iBUjsnAU5WQbclJskC")
                .build();
        Log.d(TAG, "loadRate: " + request.toString());
        Response response = this.httpClient.newCall(request).execute();
        Log.d(TAG, "loadRate: executed");
        Float val;
        try {
            if(!response.isSuccessful()) {
                throw new IOException(
                        "Wrong status: " + response.code() + "; body: " + response.body().string());
            }

            Gson gson = new Gson();
            Log.d(TAG, "loadRate: response = " + response.body().toString());
            final String json = response.body().string();
            ResponseObject ro = gson.fromJson(json, ResponseObject.class);
            Log.d(TAG, "loadRate: ro = " + ro.toString());
            val = ro.getValue();
            Log.d(TAG, "loadRate: val = " + val.toString());
        } finally {
            response.body().close();
        }
        return val.floatValue();
    }
}
