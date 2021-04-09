package com.app.jiomartsdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

public class JioService {
    public static EventTriggered eventTriggeredd;
    public void startSdk(Context context, EventTriggered eventTriggered){
        eventTriggeredd=eventTriggered;
        context.startService(new Intent(context, MyService.class));

        enableAutoStart(context);


    }

    private void enableAutoStart(Context context) {
        for (Intent intent : Constants.AUTO_START_INTENTS) {
            if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                new MaterialDialog.Builder(context).title(R.string.enable_autostart)
                        .content(R.string.ask_permission)
                        .theme(Theme.LIGHT)
                        .positiveText(context.getString(R.string.allow))
                        .onPositive((dialog, which) -> {
                            try {
                                for (Intent intent1 : Constants.AUTO_START_INTENTS)
                                    if (context.getPackageManager().resolveActivity(intent1, PackageManager.MATCH_DEFAULT_ONLY)
                                            != null) {
                                        context.startActivity(intent1);
                                        break;
                                    }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        })
                        .show();
                break;
            }
        }
    }

}
