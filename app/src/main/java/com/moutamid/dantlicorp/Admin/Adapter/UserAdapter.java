package com.moutamid.dantlicorp.Admin.Adapter;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moutamid.dantlicorp.Admin.Fragments.CheckinFragment;
import com.moutamid.dantlicorp.Admin.Fragments.CheckoutFragment;
import com.moutamid.dantlicorp.Admin.Fragments.ProfileFragment;
import com.moutamid.dantlicorp.Admin.Fragments.SocialFragment;

public class UserAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public UserAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 1:
                SocialFragment socialFragment = new SocialFragment();
                return socialFragment;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}