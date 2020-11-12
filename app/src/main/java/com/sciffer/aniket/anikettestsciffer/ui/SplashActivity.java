package com.sciffer.aniket.anikettestsciffer.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sciffer.aniket.anikettestsciffer.R;
import com.sciffer.aniket.anikettestsciffer.ui.main.MainActivity;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 123;
    private String[] permissionsArray = {
            WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        hideActionBar();
        permission();

//        gotoFunctionality();
    }

    // This snippet hides the actionbar.
    public void hideActionBar() {
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.hide();
        }
    }

    public void permission() {
        if (!checkPermission()) {
            requestPermission();
        } else {
            gotoFunctionality();
        }
    }

    private boolean checkPermission() {
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return result3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, permissionsArray, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writeAccepted) {
                        goToMain();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            showMessageOKCancel();
                        }
                    }
                } else {
                    myUpdateOperation();
                }

                break;
        }
    }

    private void showMessageOKCancel() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage("You need to allow access to write permissions. If permissions not showing go to your app setting and allow permission");
        ad.setCancelable(false);
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                permission();
            }
        });

        AlertDialog alertDialog = ad.create();
        alertDialog.show();
    }

    private void myUpdateOperation() {
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        finish();
    }

    private void gotoFunctionality() {
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2 * 1000);
                    goToMain();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();
    }

    private void goToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
