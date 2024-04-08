package com.lopez.julz.inspectionv2.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.lopez.julz.inspectionv2.R;

public class AlertHelpers {

    public static void infoDialog(Context context, String title, String message) {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        builder.setTitle(title);

        builder.setMessage(message);

        builder.setCancelable(false);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.black);
            }
        });

        alertDialog.show();
    }

    public static AlertDialog progressDialog(Context context, String title, String message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_dialog)
                    .setTitle(title)
                    .setMessage(message);

            return builder.create();
        } catch (Exception e) {
            AlertHelpers.infoDialog(context, "Error Showing Dialog", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
