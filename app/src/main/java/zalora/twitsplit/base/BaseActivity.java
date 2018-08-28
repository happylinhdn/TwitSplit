package zalora.twitsplit.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import zalora.twitsplit.R;
import zalora.twitsplit.utils.CommonUtils;

public abstract class BaseActivity extends AppCompatActivity  {
    protected Toolbar tlrMainToolBar;
    protected TextView txtTitle;
    public FragmentBackListener fragmentBackListener = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        initActionBar();

        setOrientation();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initFragment();
    }

    public abstract void initFragment();

    @Override
    public void onBackPressed() {
        if (fragmentBackListener != null) {
            if (fragmentBackListener.onFragmentBackPressed()) {
                fragmentBackListener = null;
            }
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStack();
                fragmentManager.executePendingTransactions();
                List<Fragment> fragments = fragmentManager.getFragments();
                CommonUtils.resetFragmentTitle(fragments, this);
            }
        }
    }

    protected void initActionBar() {
        tlrMainToolBar = findViewById(R.id.activity_template_toolbar);
        setSupportActionBar(tlrMainToolBar);
        tlrMainToolBar.setNavigationIcon(null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar_custom);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        txtTitle = (TextView) findViewById(R.id.actionbar_title);
    }

    public String setActionBarTitle(int titleID) {
        String title = getString(titleID);
        return setActionBarTitle(title);
    }

    public String setActionBarTitle(String title) {
        txtTitle.setText(title);
        return title;
    }

    private void setOrientation() {
        boolean isTablet = false;
        if (isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void displaySelectedScreen(Fragment fragment, int animId) {
        String customTag = "";
        if (fragment instanceof BaseFragment) {
            customTag = ((BaseFragment) fragment).customTag;
        }
        if (fragment != null) {
            fragmentBackListener = null;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (animId == 1) {
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            } else if (animId == 2) {
                ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top, R.anim.slide_out_bottom);
            }
            ft.add(R.id.activity_template_frame, fragment, customTag);
            FragmentManager fragmentManager = getSupportFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            if (fragments != null) {
                for (Fragment fragmentTemp : fragments) {
                    if (fragmentTemp != null && fragmentTemp.isVisible()) {
                        ft.hide(fragmentTemp);
                    }
                }
            }

            ft.addToBackStack(customTag);
            ft.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUI();
    }

    public interface FragmentBackListener {
        boolean onFragmentBackPressed();
    }

    private void refreshUI(){

    }

    private void updateViews(){

    }

}
