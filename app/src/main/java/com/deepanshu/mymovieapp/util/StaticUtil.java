package com.deepanshu.mymovieapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.deepanshu.mymovieapp.R;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StaticUtil {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String covertTimeToText(String dataDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);
            Date nowTime = new Date();
           // long time = nowTime.getTime() - pasTime.getTime();
            long time = pasTime.getTime();
            if (time < 1000000000000L) {
                time *= 1000;
            }
            //long now = System.currentTimeMillis();
            long now = currentDate().getTime();

            if (time > now || time <= 0) {
                return null;
            }
            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " minutes ago";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " hours ago";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " days ago";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static String bytesIntoHumanReadable(long bytes) {
        long kilobyte = 1024;
        long megabyte = kilobyte * 1024;
        long gigabyte = megabyte * 1024;
        long terabyte = gigabyte * 1024;

        if ((bytes >= 0) && (bytes < kilobyte)) {
            return bytes + " B";

        } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
            return (bytes / kilobyte) + " KB";

        } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
            return (bytes / megabyte) + " MB";

        } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
            return (bytes / gigabyte) + " GB";

        } else if (bytes >= terabyte) {
            return (bytes / terabyte) + " TB";

        } else {
            return bytes + " Bytes";
        }
    }

    public static String removeChar(String text) {
        if (text != null && !text.equalsIgnoreCase("")) {
            if (text.contains("&#039;"))
                text = text.replaceAll("&#039;", "'");
            if (text.contains("&quot;"))
                text = text.replaceAll("&quot;", "\"");
            if (text.contains("&amp;"))
                text = text.replaceAll("&amp;", "&");
            if (text.contains("&lt;"))
                text = text.replaceAll("&lt;", "<");
            if (text.contains("&gt;"))
                text = text.replaceAll("&gt;", ">");
            if (text.contains("&#039;"))
                text = text.replaceAll("&#039;", "'");
            if (text.contains("&quot;"))
                text = text.replaceAll("&quot;", "\"");
            if (text.contains("&gt;"))
                text = text.replaceAll("&gt;", ">");

//       text = text.replaceAll("&quot;", "");
            text = text.replaceAll("&amp;/", "");
            text = text.replaceAll("&amp;", "");
            text = text.replaceAll("\\<.*?\\>", "");
            return text;
        } else return "";
    }

    public static void showSettingsDialog(final Activity activity,Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Messagedialog));
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings(activity,context);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }


    // navigating user to app settings
    private static void openSettings(Activity activity,Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + context.getPackageName()));
            activity.finish();
            activity.startActivity(intent);
            return;
        }

    }

    public static String base64String(String string) {
        String base64 = "";
        if (string != null) {
            base64 = Base64.encodeToString(string
                    .getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
        }
        return base64;
    }

    public static String base64toString(String encodedString) {
        String decodedString = "";
        if (encodedString != null) {
            try {
                byte[] decodedBytes = Base64.decode(encodedString, Base64.NO_WRAP);
                decodedString = new String(decodedBytes);

            } catch (Exception e) {

            }
        }
        return decodedString;
    }

}

