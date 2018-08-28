package zalora.twitsplit.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import zalora.twitsplit.R;
import zalora.twitsplit.base.BaseActivity;
import zalora.twitsplit.base.BaseFragment;

public class CommonUtils {
    private static final String TAG = "CommonUtils";

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static void hideSoftKeyboard(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
    }

    public static void resetFragmentTitle(List<Fragment> fragments, BaseActivity activity) {
        if (activity == null)
            return;
        if (fragments != null) {
            for (Fragment fragmentTemp : fragments) {
                if (fragmentTemp != null && fragmentTemp.isVisible()) {
                    if(fragmentTemp instanceof BaseFragment) {
                        activity.setActionBarTitle(((BaseFragment) fragmentTemp).title);
                    }
                }
            }
        }
    }

    public static Date getCurrent() {
        return Calendar.getInstance(Locale.US).getTime();
    }

    public static String getLongStringFrom(Date date) {
        return new SimpleDateFormat(AppConstants.FULL_TIMESTAMP_FORMAT, Locale.US).format(date);
    }

    public static String getShortStringFrom(Date date) {
        return new SimpleDateFormat(AppConstants.SHORT_TIMESTAMP_FORMAT, Locale.US).format(date);
    }
}
