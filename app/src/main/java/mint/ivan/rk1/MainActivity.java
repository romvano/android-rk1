package mint.ivan.rk1;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mint.ivan.rk1.common.CodeSimplifier;

public class MainActivity extends AppCompatActivity implements ServiceHelper.ResultListener {
    private static final String TAG = "MainActivity";
    protected boolean canRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // goto Settings button
        CodeSimplifier.setLinkToActivity(R.id.select_button, this, SelectActivity.class);

        ((Button) findViewById(R.id.refresh_button)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRate();
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart() called");
        float rate = ServiceHelper.getInstance(this).getRateFromStorage();
        ((TextView) findViewById(R.id.value)).setText(rate != -1 ? Float.toString(rate) : "???");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop() called");
        ServiceHelper.getInstance(this).stopGettingRate();
        super.onStop();
    }

    protected void getRate() {
        Log.d(TAG, "getRate() called");
        if (canRefresh) {
            ServiceHelper.getInstance(this).askForRate(this);
            canRefresh = false;
            Toast.makeText(this, "making request", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "already pressed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResult(final int code, final float rate) {
        Log.d(TAG, "onResult() called with: code = [" + code + "], rate = [" + rate + "]");
        canRefresh = true;
        if (rate != -1) {
                ((TextView) findViewById(R.id.value)).setText(Float.toString(rate));
                Toast.makeText(this, code == Constants.SUCCESS ? "Refreshed" : "No Internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sth wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
