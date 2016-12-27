package com.arplus.android;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arplus.android.common.Constant;
import com.arplus.android.fragment.BaseFragment;
import com.arplus.android.fragment.HomeFragment;
import com.arplus.android.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.R.attr.button;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.bHome)
    ImageButton bHome;
    //    @InjectView(R.id.bCommunity)
//    ImageButton bCommunity;
//    @InjectView(R.id.bActivity)
//    ImageButton bActivity;
    @InjectView(R.id.bProfile)
    ImageButton bProfile;
//    @InjectView(R.id.rl_tab)
//    RelativeLayout rlTab;
//    @InjectView(R.id.rl_main)
//    RelativeLayout rlMain;

    private List<BaseFragment> globalFragments = new ArrayList<>();
    private List<BaseFragment> homeFragments = new ArrayList<>();
    private List<BaseFragment> profileFragments = new ArrayList<>();

    public static final int TAB_HOME = 0;
    public static final int TAB_PROFILE = 1;
    public ActionBar actionBar;
    private HomeFragment homeFragment;
    private ImageButton[] tabButtons = new ImageButton[2];
    private int currentTabIndex = TAB_HOME;
    private long spaceAvailable;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        initializeTab();


    }

    private void initializeTab() {
        tabButtons = new ImageButton[]{bHome, bProfile};
        homeFragment = new HomeFragment();

        unselectAllButton();
        tabButtons[TAB_HOME].setSelected(true);

    }

    public void addFragment(BaseFragment fragment) {
        addFragment(fragment, true);
    }

    public void addFragment(BaseFragment fragment, boolean withTransition) {
        List<BaseFragment> tabFragments = getCurrentTab(currentTabIndex);
        fragment.setParentTab(currentTabIndex);

        if (spaceAvailable == 0) {
            spaceAvailable = getAvailableSpaceInMB();
        }

        if (spaceAvailable < Constant.LIMIT_STORAGE_IN_MEGA_FOR_FRAGMENT_STACK && tabFragments.size() > Constant.LIMIT_FRAGMENT_FOR_LOW_SPACE) {
            BaseFragment fragmentToRemove = tabFragments.get(1);
            removeFragment(fragmentToRemove);
            tabFragments.remove(fragmentToRemove);
            globalFragments.remove(fragmentToRemove);
            spaceAvailable = getAvailableSpaceInMB();
        }

        tabFragments.add(fragment);
        globalFragments.add(fragment);


        if (withTransition)
            addFragment(R.id.fl_main, fragment, fragment.getTag(), true);
        else
            addFragment(R.id.fl_main, fragment, fragment.getTag(), false);

        //Remove last fragment to prevent OOM
        if (tabFragments.size() > 1) {
            removeFragment(tabFragments.get(tabFragments.size() - 2));
        }

    }

    private List<BaseFragment> getCurrentTab(int tabIndex) {
        List<BaseFragment> tabFragments;
        switch (tabIndex) {
            case TAB_HOME:
                tabFragments = homeFragments;
                break;
            case TAB_PROFILE:
                tabFragments = profileFragments;
                break;
            default:
                tabFragments = homeFragments;
        }
        return tabFragments;
    }

    @SuppressWarnings("deprecation")
    public long getAvailableSpaceInMB() {
        final long SIZE_KB = 1024L;
        final long SIZE_MB = SIZE_KB * SIZE_KB;
        long availableSpace;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_MB;
    }

    private void setTab(int tabIndex) {
        setTab(tabIndex, false);
    }

    public void setTab(int tabIndex, boolean clearFragmentStack) {
        currentTabIndex = tabIndex;

        unselectAllButton();
        tabButtons[tabIndex].setSelected(true);

        List<BaseFragment> tabFragments = getCurrentTab(tabIndex);

        if (clearFragmentStack && tabFragments.size() > 0) {
            BaseFragment firstTabFragment = tabFragments.get(0);

            for (BaseFragment baseFragment : tabFragments) {
                globalFragments.remove(baseFragment);
                removeFragment(baseFragment);
            }
            tabFragments.add(firstTabFragment);
            globalFragments.add(firstTabFragment);
            addFragment(R.id.fl_main, firstTabFragment, firstTabFragment.getTag(), true);
        }

        if (tabFragments.size() == 0) {
            switch (tabIndex) {
                case TAB_HOME:
                    HomeFragment homeFragment = new HomeFragment();
                    addFragment(homeFragment);
                    break;
                case TAB_PROFILE:
                    ProfileFragment profileFragment = new ProfileFragment();
                    addFragment(profileFragment);
            }
        } else {
            for (BaseFragment baseFragment : tabFragments) {
                removeFragment(baseFragment);
                addFragment(R.id.fl_main, baseFragment, baseFragment.getTag(), true);
            }
        }
    }

    private void unselectAllButton() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        for (ImageButton button : tabButtons) {
            button.setSelected(false);
        }

    }

    @Override
    public void onBackPressed() {

        List<BaseFragment> tabFragments = getCurrentTab(currentTabIndex);

        //Remove single fragment from current tab if stack > 0
        if (tabFragments.size() > 0) {
            //Get fragment to remove
            BaseFragment currentFragment = tabFragments.get(tabFragments.size() - 1);
            removeFragment(currentFragment);

            //Remove fragment from variables
            globalFragments.remove(currentFragment);
            tabFragments.remove(currentFragment);
        }
        //Move to another tab if current tab is empty
        if (tabFragments.size() == 0 && globalFragments.size() > 0) {
            BaseFragment currentFragment = globalFragments.get(globalFragments.size() - 1);
            setTab(currentFragment.getParentTab());
        }
        //Global fragment has no fragment in 1 activity, exit
        else if (tabFragments.size() == 0 && globalFragments.size() == 0) {
            finish();
        } else {
            //Last fragment not automatically show (popBackStack with addFragment method
            //(keep activity with single fragment)
            BaseFragment fragment = tabFragments.get(tabFragments.size() - 1);
            addFragment(R.id.fl_main, fragment, fragment.getTag(),true);
        }
    }

    @OnClick(R.id.bHome)
    void showHome() {

        setTab(TAB_HOME);
    }

    @OnClick(R.id.bProfile)
    void showProfile() {
        setTab(TAB_PROFILE);

    }



    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
