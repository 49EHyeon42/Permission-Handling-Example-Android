package dev.ehyeon.permissionhandlingexampleapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button permissionButton = findViewById(R.id.permission_button);

        permissionButton.setOnClickListener(view -> {
            if (checkSelfPermission(ApplicationPermission.RECORD_AUDIO.permission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{ApplicationPermission.RECORD_AUDIO.permission}, ApplicationPermission.RECORD_AUDIO.requestCode);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ApplicationPermission.RECORD_AUDIO.requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions have been set.", Toast.LENGTH_SHORT).show();
            } else {
                if (shouldShowRequestPermissionRationale(ApplicationPermission.RECORD_AUDIO.permission)) {
                    Toast.makeText(this, "Permission is required.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Request permission")
                            .setMessage("Permissions are required to use this application.")
                            .setNegativeButton("CANCEL", (dialog, which) -> {
                            })
                            .setPositiveButton("OK", (dialog, which) -> {
                                Intent intent = new Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getPackageName(), null));

                                startActivity(intent); // Re-request to "intent"
                            });

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }
            }
        }
    }
}
