package com.example.android_final_project.Utilities;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DialogUtils {

    public DialogUtils() {
    }

    public void showDialog(Activity activity, String title, String message, DialogInterface.OnClickListener onOkClickListener) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, onOkClickListener)
                .show();
    }
}
