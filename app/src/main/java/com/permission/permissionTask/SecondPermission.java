package com.permission.permissionTask;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SecondPermission extends AppCompatActivity {
    private static final String TAG = "debugging on==>";
    private final String[] permissionList = {Manifest.permission.CAMERA};
    private int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_permission);
    }

    public void checkPermission1(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "checkPermission: ");
            if (state <= 1) {
                ActivityCompat.requestPermissions(SecondPermission.this, permissionList, 10);
                state++;
            }
            else {
                ActivityCompat.requestPermissions(SecondPermission.this, permissionList, 10);
            }
        }
        else {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMessageOkCancel(String s, DialogInterface.OnClickListener onClickListener) {

        new AlertDialog.Builder(this).setMessage(s)
                .setPositiveButton("Ok", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10) {
            if (grantResults[0] == -1) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Log.i(TAG, "shouldShowRequestPermissionRationale:");
                    showMessageOkCancel("camera permission is required to access camera",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                } else {
                    Toast.makeText(this, "Camera Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
