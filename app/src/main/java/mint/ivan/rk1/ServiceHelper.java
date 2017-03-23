package mint.ivan.rk1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import mint.ivan.rk1.common.Storage;

/**
 * Created by ivan on 3/22/17.
 */
public class ServiceHelper {
    private static final String TAG = "ServiceHelper";
    private int counter = 1;
    private static ServiceHelper instance;
    private static Context context;
    private static IntentFilter filter;
    private static BroadcastReceiver br;

    public static synchronized ServiceHelper getInstance(final Context c) {
        Log.d(TAG, "getInstance() called with: c = [" + c + "]");
        if(instance == null) {
            instance = new ServiceHelper(c);
        }
        context = c;
        return instance;
    }

    interface ResultListener {
        void onResult(final int code, final float rate);
    }

    private ServiceHelper(final Context context) {
        Log.d(TAG, "ServiceHelper() called with: context = [" + context + "]");
        filter = new IntentFilter();
        filter.addAction(Constants.GET_RATE_RESULT);
    }

    public void askForRate(final ResultListener listener) {
        Log.d(TAG, "askForRate() called with: listener = [" + listener + "]");
        final Storage s = Storage.getInstance(context);
//        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context)
//        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
                if (listener != null) {
                    final int code = intent.getIntExtra(Constants.GET_RATE_RESULT, 1);
                    final float rate = code == 0 ? intent.getFloatExtra(Constants.RATE_FIELD, -1)
                            : s.getFloat(Constants.RATE_FIELD, -1);
                    listener.onResult(code, rate);
                }
            }
        };//, filter);
        LocalBroadcastManager.getInstance(context).registerReceiver(br, filter);
        Intent intent = new Intent(context, RK1IntentService.class);
        intent.setAction(Constants.GET_RATE_ACTION);
        final String currency = s.getString(Constants.CURRENCY_FIELD);
        intent.putExtra(Constants.CURRENCY_FIELD, !currency.equals("") ? currency : Constants.CURRENCIES[0]);
        context.startService(intent);
    }

    public float getRateFromStorage() {
        Log.d(TAG, "getRateFromStorage() called");
        float rate = Storage.getInstance(context).getFloat(Constants.RATE_FIELD);
        Log.d(TAG, "getRateFromStorage: rate = " + Float.toString(rate));
        return rate;
    }

    public void stopGettingRate() {
        Log.d(TAG, "stopGettingRate() called");
        LocalBroadcastManager.getInstance(context).unregisterReceiver(br);
    }

}
