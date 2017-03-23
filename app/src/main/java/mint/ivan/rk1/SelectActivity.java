package mint.ivan.rk1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import mint.ivan.rk1.common.Storage;

public class SelectActivity extends AppCompatActivity {
    private static final String TAG = "SelectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        LinearLayout ll = (LinearLayout) findViewById(R.id.btn_list);
        for (String s : Constants.CURRENCIES) {
            final Button b = new Button(this);
            b.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setText(s);
            b.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Storage s = Storage.getInstance(getApplicationContext());
                    s.save(Constants.CURRENCY_FIELD, b.getText().toString());
                    s.save(Constants.RATE_FIELD, -1);

                    finish();
                }
            });
            ll.addView(b);
        }
    }
}
