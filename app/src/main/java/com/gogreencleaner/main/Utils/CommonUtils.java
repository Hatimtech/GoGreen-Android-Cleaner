package com.gogreencleaner.main.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gogreencleaner.main.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    /**
     * Uses static final constants to detect if the device's platform version is
     * Gingerbread or later.
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean isShowNetworkAlert = true;

    /**
     * Uses static final constants to detect if the device's platform version is
     * Honeycomb or later.
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }



    /**
     * Uses static final constants to detect if the device's platform version is
     * Honeycomb MR1 or later.
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Uses static final constants to detect idiaf the device's platform version is
     * ICS or later.
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * This Method is Checked that network is Available or Not If available
     * Result Will be True If not available Result Will be False
     */
    public static boolean isNetworkAvailable(Context mContext) {

        /* getting systems Service connectivity manager */
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mConnectivityManager != null) {
            NetworkInfo[] mNetworkInfos = mConnectivityManager.getAllNetworkInfo();
            if (mNetworkInfos != null) {
                for (NetworkInfo mNetworkInfo : mNetworkInfos) {
                    if (mNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    public static void log(Context c, String tag, String msg) {

        if (Constants.isDebug) {
            Log.d(tag, msg);
        }
    }


    public static String getParamsToUrl(String Url,
                                        LinkedHashMap<String, String> ParamsLinked) {
        // Using StringBuffer append for better performance.
        StringBuilder combinedParams = new StringBuilder();
        if (!ParamsLinked.isEmpty()) {

            for (Map.Entry<String, String> entry : ParamsLinked.entrySet()) {
                try {
                    combinedParams.append(combinedParams.length() > 1 ? "&" : "").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return Url + combinedParams.toString();
    }

    public static boolean isInternetReachable() {
        try {
            // make a URL to a known source
            URL url = new URL("http://www.google.com");

            // open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection) url
                    .openConnection();

            // trying to retrieve data from the source. If there
            // is no connection, this line will fail
            urlConnect.setConnectTimeout(5 * 1000);
            urlConnect.getContent();

        } catch (UnknownHostException e) {

            e.printStackTrace();
            return false;
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }



    // getHeight of status bar
    public static int getStatusBarHeight(Context Con) {
        int result = 0;
        int resourceId = Con.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = Con.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // Calculate ActionBar height

    public static int getActionBarBarHeight(Context Con) {
        int result = 0;
        TypedValue tv = new TypedValue();
        if (Con.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv,
                true)) {
            result = TypedValue.complexToDimensionPixelSize(tv.data, Con
                    .getResources().getDisplayMetrics());
        }

        return result;
    }

    // getHeight of NavigationBar bar
    public static int getNavigationBarHeight(Context Con) {
        int result = 0;

        Resources resources = Con.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;

    }

    public static Bitmap makeBitmap(String path, Context mContext, int width,
                                    int height) {
        int orient = 0;

        /*
         * Display dispDefault = ((WindowManager)
         * mContext.getSystemService(Context.WINDOW_SERVICE))
         * .getDefaultDisplay(); DisplayMetrics dispMetrics = new
         * DisplayMetrics(); dispDefault.getMetrics(dispMetrics);
         *
         * Point mPoint = CommonUtils.getDisplaySize(dispDefault);
         */
        Resources resource = mContext.getResources();
        // int screenWidth = resource.getDisplayMetrics().widthPixels;
        // int screenHeight = resource.getDisplayMetrics().heightPixels;

        try {
            ExifInterface ei = new ExifInterface(path);
            orient = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Bitmap image = decodeSampledBitmapFromResource(path, resource, 1,
                width, height);

        Matrix matrix = new Matrix();
        switch (orient) {
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            default:
                matrix.postRotate(0);
        }
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(),
                image.getHeight(), matrix, true);
        matrix.reset();

        float scaleWidth = ((float) width) / image.getWidth();
        float scaleHeight = ((float) height) / image.getHeight();
        float scale = Math.min(scaleWidth, scaleHeight);
        matrix.postScale(scale, scale);

        return Bitmap.createBitmap(image, 0, 0, image.getWidth(),
                image.getHeight(), matrix, true);
    }



    // url = file path or whatever suitable URL you want.
    public static String getMimeType(Context context, Uri uri) {
        String type = "";
        if (uri != null) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.MediaColumns.MIME_TYPE}, null,
                    null, null);

            if (cursor != null && cursor.moveToNext()) {
                type = cursor.getString(0);
            }

            return type;
        }
        return type;
    }

    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * this method is for getting Display Dimensions Weather this method is
     * Deprecated or not
     **/
    @SuppressLint("NewApi")
    public static Point getDisplaySize(final Display display) {
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }

    public static String getDateDifference(Date thenDate) {
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        now.setTime(new Date());
        then.setTime(thenDate);

        // Get the represented date in milliseconds
        long nowMs = now.getTimeInMillis();
        long thenMs = then.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = nowMs - thenMs;

        // Calculate difference in seconds
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);


        if (diffMinutes < 60) {
            if (diffMinutes == 1)
                return diffMinutes + " minute ago";
            else
                return diffMinutes + " minutes ago";
        } else if (diffHours < 24) {
            if (diffHours == 1)
                return diffHours + " hour ago";
            else
                return diffHours + " hours ago";
        } else if (diffDays < 30) {
            if (diffDays == 1)
                return diffDays + " day ago";
            else
                return diffDays + " days ago";
        } else if ((diffDays / 30) >= 2 && (diffDays / 30) < 12) {
            return String.format("%d months ago", (diffDays / 30));
        } else if ((diffDays / 365) > 1) {
            return String.format("%d years ago", (diffDays / 365));
        } else {
            return "a long time ago..";
        }
    }

    public static long CalculateTimeDifferenceInTermsofMinute(String StartDate,
                                                              String StopDate) {
        long diffMinutes = 0;

        // HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(StartDate);
            d2 = format.parse(StopDate);

            // in milliseconds
            long diff = d2.getTime() - d1.getTime();

            // long diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            // long diffHours = diff / (60 * 60 * 1000) % 24;
            // long diffDays = diff / (24 * 60 * 60 * 1000);

            // System.out.println(diffDays + " days, ");
            // System.out.println(diffHours + " hours, ");
            System.out.println(diffMinutes + " minutes, ");
            // System.out.println(diffSeconds + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diffMinutes;
    }

    /**
     * @param view EditText
     * @return true/false
     * @category EditText Validation
     */
    public static boolean isEditValid(EditText view) {
        boolean isValid = false;

        EditText mEditText = view;

        if (mEditText.getText().toString().trim().length() > 0) {
            isValid = true;
        }

        return isValid;
    }

    /*
     * isNameValid function is check wheather given text contains any numeric
     * value or special character if it contains numeric value or special
     * character it will return false
     */

    public static boolean isNameValid(String name) {
        boolean isValid = true;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!(Character.isSpaceChar(c) || Character.isLetter(c))) {
                isValid = false;
            }

        }
        return isValid;
    }

    /**
     * @param email as a String
     * @return true/false
     * @category Email Validation
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "[A-Z0-9a-z]+([._%+-]{1}[A-Z0-9a-z]+)*@[A-Z0-9a-z]+([.-]{1}[A-Z0-9a-z]+)*(\\\\.[A-Za-z]{2,4}){0,1}";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isValidMobile(EditText phoneEditText) {
        boolean check;
        if (phoneEditText.getText().toString().trim().length() < 6 || phoneEditText.getText().toString().trim().length() > 13) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }

    public static boolean isValidMobNumber(String phoneNumber) {
        boolean check;
        if (phoneNumber.trim().length() == 10) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }


    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String MillisecondsToDate(long millis) {
        String returnTime;
        // SimpleDateFormat formatter = new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df = new SimpleDateFormat("hh:mm a");
        // formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        returnTime = df.format(c.getTime());
        return returnTime;
    }

    public static void CopyRAWtoSDCard(Context mContext, int id, String path)
            throws IOException {
        InputStream in = mContext.getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    /**
     * Compare start Date is before start Date
     *
     * @param startDate
     * @param endDate
     * @return
     */

    public static boolean isDateBefore(String startDate, String endDate) {
        try {
            String myFormatString = "yyyy-MM-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (startingDate.before(date1))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return date in specified format.
     *
     * @param milliSeconds Date in milliseconds
     * @param dateFormat   Date format
     * @return String representing date in specified format
     */
    public static String MillisecondsToHeaderDate(long milliSeconds,
                                                  String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    public static String Base64Decoder(String base64string) {
        byte[] data = Base64.decode(base64string, Base64.DEFAULT);
        String text = "";
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text.trim();

    }




    public static void displayNetworkAlert(final Context context, final boolean isFinish) {

        if (isShowNetworkAlert) {
            new AlertDialog.Builder(context)
                    .setMessage("Please Check Your Internet Connection and Try Again")
                    .setTitle("Network Error")
                    .setCancelable(false)
                    .setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (isFinish) {
                                        dialog.dismiss();
                                        ((Activity) context).finish();
                                    } else {


                                        dialog.dismiss();
                                    }
                                }
                            }).show();

        }
    }

    public static String getDate(String timestamp) {
        Date oneWayTripDate;
        String formatedDate = "";
        if (!TextUtils.isEmpty(timestamp)) {
            try {
                String time[] = timestamp.split(" ");
                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
                oneWayTripDate = input.parse(time[0]);
                formatedDate = output.format(oneWayTripDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return formatedDate;
    }

    public static int getExpireDays(String date) {
        SimpleDateFormat dfDate = new SimpleDateFormat("MMM dd, yyyy");
        Date d = null;
        Date d1 = null;
        int diffInDays;

        Calendar cal = Calendar.getInstance();
        try {
            d = dfDate.parse(date);
            d1 = dfDate.parse(dfDate.format(cal.getTime()));// Returns
            // 15/10/2012
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));

        return diffInDays;

    }

    public static Bitmap getBitmapFromPath(String FilePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(FilePath, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        if (imageWidth > imageHeight) {
            options.inSampleSize = calculateInSampleSize(options, 512, 256);// if
            // landscape
        } else {
            options.inSampleSize = calculateInSampleSize(options, 256, 512);// if
            // portrait
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(FilePath, options);
    }

    public static Bitmap getBitmapThumbnailsFromPath(String FilePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(FilePath, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        if (imageWidth > imageHeight) {
            options.inSampleSize = calculateInSampleSize(options, 512, 256);// if
            // landscape
        } else {
            options.inSampleSize = calculateInSampleSize(options, 256, 512);// if
            // portrait
        }
        options.inJustDecodeBounds = false;
        // return BitmapFactory.decodeFile(FilePath, options);
        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeFile(FilePath, options), 64, 64, false);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static String removeZeroFromDate(String date) {
//		Log.e("Original Date---", "" + date);
        String[] separated = date.split(" ");
        String s = separated[1];
        String remove = "";
        if (s.startsWith("0")) {
//			Log.e("Comp", "Comp---" + s.substring(1, s.length() - 1));
            remove = s.substring(1, s.length() - 1);
//			Log.e("Seprated[0]", "" + separated[0]);
//			Log.e("s", "" + remove);
//			Log.e("Final String---", "" + separated[0] + " " + remove + ","
//					+ " " + separated[2]);
            return separated[0] + " " + remove + "," + " " + separated[2];
        } else {

            return date;
        }

    }

    public static void AlertDialogDefault(Context mContext, String Title,
                                          String Message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setTitle(Title);
        alertDialogBuilder.setMessage(Message).setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    public static int timeDiff(String time) {
        int min = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            int HH = calendar.get(Calendar.HOUR_OF_DAY);
            int mm = calendar.get(Calendar.MINUTE);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date date1 = simpleDateFormat.parse(HH + ":" + mm);
            Date date2 = simpleDateFormat.parse(time);

            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return min;
    }


    public static String encodeString(String string) {
        String s = "";
        if (!string.equals("")) {
            //encoding  byte array into base 64
            byte[] encoded = Base64.encode(string.getBytes(), Base64.DEFAULT);
            s = new String(encoded);
        }
        return s.trim();
    }

    public static String twoTimeDifference(String startTime, String endTime) {
        int min = 0;
        String returnTime = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date date1 = simpleDateFormat.parse(startTime);
            Date date2 = simpleDateFormat.parse(endTime);

            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);

            if (hours > 0) {
                returnTime = "" + hours + " hrs";
            } else if (min > 0) {
                returnTime = "" + min + " mins";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnTime;
    }

    public static long dateDifferent(String dateStart, String dateStop) {
//		String dateStart = "01/14/2012 09:29:58";
//		String dateStop = "01/15/2012 10:31:48";

        //HH converts hour in 24 hours format (0-23), day calculation
        long diffDays = 0;
        if (!dateStart.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            Date d1 = null;
            Date d2 = null;

            try {
                d1 = format.parse(dateStart);
                d2 = format.parse(dateStop);

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                diffDays = diff / (24 * 60 * 60 * 1000);

                System.out.print(diffDays + " days, ");
                System.out.print(diffHours + " hours, ");
                System.out.print(diffMinutes + " minutes, ");
                System.out.print(diffSeconds + " seconds.");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return diffDays;
    }


//	public static String getAddress(LatLng latLng, Context context)
//	{
//		Geocoder geocoder = new Geocoder(context);
//		double latitude = latLng.latitude;
//		double longitude = latLng.longitude;
//
//		String address = "";
//
//		try
//		{
//			Log.i("Address Info","Address based opn geocoder");
//			DrawerList<Address> addresses = geocoder.getFromLocation(latitude,
//					longitude, 1);
//
//			if (addresses != null && !addresses.isEmpty())
//			{
//				Address returnedAddress = addresses.get(0);
//				StringBuilder strReturnedAddress = new StringBuilder();
//				int addressLineIndex = returnedAddress.getMaxAddressLineIndex();
//
//				int addressLinesToShow = 2;
//				//              To get address in limited lines
//				if (addressLineIndex < 2)
//				{
//					addressLinesToShow = addressLineIndex;
//				}
//				for (int p = 0; p < addressLinesToShow; p++)
//				{
//					strReturnedAddress
//							.append(returnedAddress.getAddressLine(p)).append(
//							"\n");
//				}
//				address = strReturnedAddress.toString();
//			}
//			else
//			{
//				address = "Address not available";
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//			address = "Address not available";
//			Log.e("Address not found","Unable to get Address in info window");
//		}
//		return address;
//	}

//	public GeoPoint getLocationFromAddress(String strAddress,Context mContext)
//	{
//
//		Geocoder coder = new Geocoder(mContext);
//		DrawerList<Address> address;
//		GeoPoint p1 = null;
//
//		try {
//			address = coder.getFromLocationName(strAddress, 5);
//			if (address == null) {
//				return null;
//			}
//			Address location = address.get(0);
//			location.getLatitude();
//			location.getLongitude();
//
//			p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
//					(int) (location.getLongitude() * 1E6));
//
//
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return p1;
//	}

//	public static void getGCMToken(final Activity activity)
//	{
//		String PROJECT_NUMBER = "342255497947";
//		GCMClientManager pushClientManager;
//		pushClientManager = new GCMClientManager(activity, PROJECT_NUMBER);
//		pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
//			@Override
//			public void onSuccess(String registrationId, boolean isNewRegistration) {
//				if(Consts.isDebug) {
//					Log.e("GCM Token----", "" + registrationId);
//				}
//				Preferences.setPreference(activity,PrefEntity.DEVICETOKEN,registrationId);
//			}
//
//			@Override
//			public void onFailure(String ex) {
//				super.onFailure(ex);
//				// If there is an error registering, don't just keep trying to register.
//				// Require the user to click a button again, or perform
//				// exponential back-off when retrying.
//			}
//		});
//	}
//
//	public static void getUniqueDeviceID(Context mContext)
//	{
//		try
//		{
//			String deviceId = Settings.Secure.getString(mContext.getContentResolver(),
//					Settings.Secure.ANDROID_ID);
//			if(Consts.isDebug) {
//				Log.e("deviceId---",""+deviceId);
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//	}

//	public static String getCityName(Context mContext)
//	{
//		String cityName="";
//		try
//		{
//			//if(CustomerHomeActivity.mAutoSearchLocation.getText().length() > 0 && !CustomerHomeActivity.mAutoSearchLocation.getText().toString().equals(mContext.getResources().getString(R.string.no_address_found))) {
//				Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
//				DrawerList<Address> addresses = gcd.getFromLocation(Double.parseDouble(Global.latitude), Double.parseDouble(Global.longitude), 1);
//				if (addresses.size() > 0) {
//					System.out.println(addresses.get(0).getLocality());
//					cityName = addresses.get(0).getLocality();
//				}
//			//}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//		return cityName;
//	}

    public static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 5) {
            return true;
        }
        return false;
    }

    public static boolean isValidUserName(String username) {
        if (username != null && username.length() >= 3) {
            return true;
        }
        return false;
    }

//	public static boolean idValidUsername(String username){
//		Pattern pattern;
//		Matcher matcher;
//		String USERNAME_PATTERN = "^[a-z0-9A-Z]{3,15}$";
//		pattern = Pattern.compile(USERNAME_PATTERN);
//		matcher = pattern.matcher(username);
//		return matcher.matches();
//
//	}

//	public static boolean idValidUsername(String s)
//	{
//		Pattern letter = Pattern.compile("[a-zA-z]");
//		Pattern digit = Pattern.compile("[0-9]");
//		Matcher hasLetter = letter.matcher(s);
//		Matcher hasDigit = digit.matcher(s);
//
//		if(hasLetter.find() && hasDigit.find())
//		{
//			return true;
//		}
//		else
//		{
//			return false;
//		}
//	}

    public static String getDateFormate(String timestamp) {
        String date = "";
        if (!timestamp.equals("")) {
            try {
                String time[] = timestamp.split(" ");
                date = time[0];
//				if(getDate(time[0]).equals(getCurrentDate()))
//				{
//					date=getCurrentTime(timestamp);
//				}
//				else {
//					date=getDate(time[0])+" "+getCurrentTime(timestamp);
//				}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    private static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(c.getTime());
    }

    private static String getCurrentTime(String dateString) {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date parsed = null;
        try {
            try {
                parsed = sourceFormat.parse(dateString);
            } catch (java.text.ParseException e) {

                e.printStackTrace();
                return "";
            }
        } catch (ParseException e1) {

            e1.printStackTrace();
            return "";
        }

        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat destFormat = new SimpleDateFormat("HH:mm");
        destFormat.setTimeZone(tz);
        String result = destFormat.format(parsed);
        return result;
    }

    public static boolean checkNumber(String s) {
        if (s.matches("[0-9]+")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getRelativeDate(String date) {

        Calendar future = Calendar.getInstance();
        String relativeDate = "";

//		Log.e("days---",""+getDateDifference(new Date(date)));

        //19 May 2015 10:13:28
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MMM yyyy HH:MM:SS");
        String inputString1 = "23 01 1997";
        String inputString2 = "27 04 1997";

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }

        long days = getDateDiff(future.getTime(), Calendar.getInstance().getTime(), TimeUnit.DAYS);

//		Log.e("days---",""+date.substring(5));

        if (days < 7) {
            CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(future.getTimeInMillis(), System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_ALL);

//      Timber.d("relativeTime - " + relativeTime);

            if (relativeTime.toString().equals("0 minutes ago")
                    || relativeTime.toString().equals("in 0 minutes")) {
                relativeDate = "Just now";
            } else if (relativeTime.toString().contains("hr. ")) {
                if (relativeTime.toString().equals("1 hr. ago")) {
                    relativeDate = "1 hour ago";
                } else {
                    relativeDate = relativeTime.toString().replace("hr. ", "hours ");
                }
            } else {
                relativeDate = relativeTime.toString();
            }
        } else if (days >= 7 && days < 14) {
            relativeDate = "A week ago";
        } else if (days >= 14 && days < 21) {
            relativeDate = "2 weeks ago";
        } else if (days >= 21 && days < 28) {
            relativeDate = "3 weeks ago";
        } else if ((days / 30) == 1) {
            relativeDate = "1 month ago";
        } else if ((days / 30) >= 2 && (days / 30) < 12) {
            relativeDate = String.format("%d months ago", (days / 30));
        } else if ((days / 365) > 1) {
            relativeDate = String.format("%d years ago", (days / 365));
        }

//        Timber.d("getRelativeDate() : days - " + days);
//        Timber.d("getRelativeDate() : relativeDate - " + relativeDate);

        return relativeDate;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static void copyDatabaseToSdCard(Context mContext) {
        if (Constants.isDebug) {
            try {

                String DB_PATH = "";
                String DATABASE_NAME = "mindworks.db";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    DB_PATH = mContext.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;
                } else {
                    DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/" + DATABASE_NAME;
                }

                File f1 = new File(DB_PATH);
                if (f1.exists()) {
                    File f2 = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/mindworks.sqlite");
                    f2.createNewFile();
                    InputStream in = new FileInputStream(f1);
                    OutputStream out = new FileOutputStream(f2);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

//	public static void showSnackBar(View view,String message)
//	{
//		Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
//	}


    public static void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

    public static void getListViewSize(GridView mGridView) {
        ListAdapter myListAdapter = mGridView.getAdapter();
        if (myListAdapter == null) {
            // do nothing return null
            return;
        }
        // set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, mGridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // setting listview item in adapter
        ViewGroup.LayoutParams params = mGridView.getLayoutParams();
        params.height = totalHeight - (myListAdapter.getCount() - 1);//(totalHeight+ (1 * (myListAdapter.getCount() - 1)))/2;
        mGridView.setLayoutParams(params);
        // print height of adapter on log
    }

    public static void cancelToastMessage(final Toast toast) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);
    }

    public static void customToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String getDeviceType(Context context) {
        if ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return "Tablet";
        } else {
            return "Mobile";
        }

    }

    public static String getNetworkTypeName(int type) {
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "CDMA - EvDo rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "CDMA - EvDo rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "CDMA - EvDo rev. B";
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "CDMA - 1xRTT";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "CDMA - eHRPD";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDEN";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            default:
                return "UNKNOWN";
        }
    }


    public static String convertTimestampToDate(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String localTime = sdf.format(new Date(Long.parseLong(timeStamp) * 1000));
        Date date = new Date();
        try {
            date = sdf.parse(localTime);//get local date

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("DATE : ", date.toString());
        return "" + sdf.format(date);
    }

    public static String convertDateToTimestamp(String date) {

        String timestamp[] = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Integer.parseInt(timestamp[0]), Integer.parseInt(timestamp[1]) - 1, Integer.parseInt(timestamp[2]));
        return String.valueOf(calendar.getTimeInMillis() / 1000);
    }

    public static void alert(Context context, String msg) {

        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }


    public static void log(String volleyRequest, String s) {
        if (Constants.isDebug) {
            Log.d(volleyRequest, s);
        }
    }
}