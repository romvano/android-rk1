package mint.ivan.rk1.common;

import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by ivan on 3/15/17.
 */

public class CodeSimplifier {
    public static void setLinkToActivity(@IdRes int id,
                                         final AppCompatActivity currentActivity,
                                         final Class<?> destination) {
        View linkingView = currentActivity.findViewById(id);
        linkingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentActivity, destination);
                currentActivity.startActivity(intent);
            }
        });
    }

}
