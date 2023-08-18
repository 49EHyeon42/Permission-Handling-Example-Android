package dev.ehyeon.permissionhandlingexampleapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PermissionDeniedFragment extends Fragment {

    private final ActivityResultLauncher<String> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
                MainActivity mainActivity = (MainActivity) getActivity();

                if (result) {
                    mainActivity.showFragment(mainActivity.permissionGrantedFragment);
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                        Toast.makeText(getContext(), "Permission is required.", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                .setTitle("Request permission")
                                .setMessage("Permissions are required to use this application.")
                                .setNegativeButton("CANCEL", (dialog, which) -> {
                                })
                                .setPositiveButton("OK", (dialog, which) -> {
                                    Intent intent = new Intent(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", mainActivity.getPackageName(), null));

                                    startActivity(intent); // Re-request to "intent"
                                });

                        AlertDialog alertDialog = builder.create();

                        alertDialog.show();
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.permission_denied_fragment, container, false);

        Button button = view.findViewById(R.id.retry_button);

        button.setOnClickListener(ignored -> activityResultLauncher.launch(Manifest.permission.RECORD_AUDIO));

        activityResultLauncher.launch(Manifest.permission.RECORD_AUDIO);

        return view;
    }
}
