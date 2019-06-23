package com.smlnskgmail.jaman.hashchecker.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.smlnskgmail.jaman.hashchecker.MainActivity;
import com.smlnskgmail.jaman.hashchecker.R;
import com.smlnskgmail.jaman.hashchecker.components.fileexplorer.explorer.FileExplorerActivity;
import com.smlnskgmail.jaman.hashchecker.support.logs.L;
import com.smlnskgmail.jaman.hashchecker.support.values.Requests;

public class AppUtils {

    private static final int VIBRATION_LENGTH = 30;

    public static void openSystemFileManager(@NonNull Fragment fragment, @NonNull View view) {
        try {
            Intent openExplorerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            openExplorerIntent.addCategory(Intent.CATEGORY_OPENABLE);
            openExplorerIntent.setType("*/*");
            fragment.startActivityForResult(openExplorerIntent, Requests.FILE_SELECT_REQUEST);
        } catch (ActivityNotFoundException e) {
            UIUtils.showSnackbar(view.getContext(), view,
                    fragment.getString(R.string.message_error_start_file_selector),
                    Snackbar.LENGTH_LONG);
        }
    }

    public static void openInnerFileManager(@NonNull Fragment fragment) {
        Intent openExplorerIntent = new Intent(fragment.getContext(), FileExplorerActivity.class);
        fragment.startActivityForResult(openExplorerIntent,
                Requests.FILE_SELECT_REQUEST_FROM_APP_FILE_MANAGER);
    }

    public static void openAppSettings(@NonNull Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        }
    }

    public static void openWebLink(@NonNull Context context, @NonNull String link) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        } catch (ActivityNotFoundException e) {
            L.error(e);
        }
    }

    public static void openGooglePlay(@NonNull Context context, @NonNull View view) {
        final String appPackageName = context.getPackageName();
        Uri link;
        try {
            link = Uri.parse("market://details?id=" + appPackageName);
            context.startActivity(new Intent(Intent.ACTION_VIEW, link));
        } catch (ActivityNotFoundException e) {
            try {
                link = Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName);
                context.startActivity(new Intent(Intent.ACTION_VIEW, link));
            } catch (ActivityNotFoundException e2) {
                UIUtils.showSnackbar(context, view,
                        context.getString(R.string.message_error_start_google_play),
                        Snackbar.LENGTH_LONG);
            }
        }
    }

    public static void sendFeedback(@NonNull Context context, @NonNull String text, @NonNull String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(emailIntent,
                context.getString(R.string.message_email_app_chooser)));
    }

    static void vibrate(@NonNull Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(VIBRATION_LENGTH, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            v.vibrate(VIBRATION_LENGTH);
        }
    }

    public static void copyTextToClipboard(@NonNull Context context, @NonNull String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(context.getString(R.string.app_name), text);
        clipboard.setPrimaryClip(clip);
    }

    public static void closeApp(@NonNull Activity activity) {
        activity.finish();
    }

    public static void restartApp(@NonNull Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        closeApp(activity);
    }

}