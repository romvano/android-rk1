package mint.ivan.rk1;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

import mint.ivan.rk1.common.Storage;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RK1IntentService extends IntentService {
    private static final String TAG = "IntentService";
    public RK1IntentService() {
        super("RK1IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent() called with: intent = [" + intent + "]");
        if (intent != null) {
            final String action = intent.getAction();
            if (Constants.GET_RATE_ACTION.equals(action)) {
                final String currency = intent.getStringExtra(Constants.CURRENCY_FIELD);
                handleActionGetRate(currency);
            }
        }
    }

    private void handleActionGetRate(final String currency) {
        Log.d(TAG, "handleActionGetRate() called with: currency = [" + currency + "]");
        try {
            float rate = (new RateLoader()).loadRate(currency);
            Storage.getInstance(getApplicationContext()).save(Constants.RATE_FIELD, rate);
            Intent intent = new Intent(Constants.GET_RATE_RESULT);
            intent.putExtra(Constants.GET_RATE_RESULT, Constants.SUCCESS);
            intent.putExtra(Constants.RATE_FIELD, rate);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            Log.d(TAG, "handleActionGetRate: success");
        } catch (IOException e) {
            e.printStackTrace();
            sendBroadcast((new Intent(Constants.GET_RATE_RESULT))
                    .putExtra(Constants.GET_RATE_RESULT, Constants.FAILURE));
        }
    }
}
