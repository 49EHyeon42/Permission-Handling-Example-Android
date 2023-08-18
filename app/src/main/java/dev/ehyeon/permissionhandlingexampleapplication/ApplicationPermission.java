package dev.ehyeon.permissionhandlingexampleapplication;

import android.Manifest;

public enum ApplicationPermission {

    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO, 1);

    final String permission;
    final int requestCode;

    ApplicationPermission(String permission, int requestCode) {
        this.permission = permission;
        this.requestCode = requestCode;
    }
}
