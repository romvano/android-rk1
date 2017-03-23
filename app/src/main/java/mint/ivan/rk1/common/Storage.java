package mint.ivan.rk1.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ivan on 3/22/17.
 */
public class Storage {
    private static final String TAG = "Storage";
    private static Storage instance;

    private SharedPreferences preferences;

    public static synchronized Storage getInstance(Context context) {
        Log.d(TAG, "getInstance() called with: context = [" + context + "]");
        if (instance == null) {
            instance = new Storage(context);
        }
        return instance;
    }

    private Storage(Context context) {
        Log.d(TAG, "Storage() called with: context = [" + context + "]");
        this.preferences = context.getSharedPreferences("News", 0);
    }

    public void save(String fieldName, String data) {
        Log.d(TAG, "save() called with: fieldName = [" + fieldName + "], data = [" + data + "]");
        this.preferences.edit().putString(fieldName, data).apply();
    }

    public void save(String fieldName, float data) {
        Log.d(TAG, "save() called with: fieldName = [" + fieldName + "], data = [" + data + "]");
        this.preferences.edit().putFloat(fieldName, data).apply();
    }

    public String getString(String fieldName) {
        Log.d(TAG, "getString() called with: fieldName = [" + fieldName + "]");
        return this.preferences.getString(fieldName, "");
    }

    public float getFloat(String fieldName, float ifno) {
        Log.d(TAG, "getFloat() called with: fieldName = [" + fieldName + "], ifno = [" + ifno + "]");
        return this.preferences.getFloat(fieldName, ifno);
    }

    public float getFloat(String fieldName) {
        Log.d(TAG, "getFloat() called with: fieldName = [" + fieldName + "]");
        return this.preferences.getFloat(fieldName, 0);
    }
}
