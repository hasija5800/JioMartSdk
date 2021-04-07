package com.app.jiomartsdk;

import androidx.appcompat.app.AppCompatActivity;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.Theme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class JioMartActivity extends AppCompatActivity implements EventTriggered  {
    public static EventTriggered eventTriggered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        eventTriggered=this;

        enableAutoStart();
    }
    private void enableAutoStart() {
        for (Intent intent : Constants.AUTO_START_INTENTS) {
            if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                new Builder(this).title(R.string.enable_autostart)
                        .content(R.string.ask_permission)
                        .theme(Theme.LIGHT)
                        .positiveText(getString(R.string.allow))
                        .onPositive((dialog, which) -> {
                            try {
                                for (Intent intent1 : Constants.AUTO_START_INTENTS)
                                    if (getPackageManager().resolveActivity(intent1, PackageManager.MATCH_DEFAULT_ONLY)
                                            != null) {
                                        startActivity(intent1);
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

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(JioMartActivity.this, MyService.class));

    }

    @Override
    public void onTriggered() {
        //  Toast.makeText(MainActivity.this, "dddd", Toast.LENGTH_SHORT).show();
        finish();
    }
}