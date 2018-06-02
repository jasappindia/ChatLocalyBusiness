package com.chatlocalybusiness.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chatlocalybusiness.activity.HomeActivity;
import com.chatlocalybusiness.fragments.ChatInternalFragment;
import com.chatlocalybusiness.fragments.ChatTagsFragment;
import com.chatlocalybusiness.fragments.ChatThreadFragment;

/**
 * Created by windows on 12/19/2017.
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public HomePagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
      this.tabCount=tabCount;

    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                ChatThreadFragment thread=new ChatThreadFragment();
                return thread;
          /*  case 1:
                ChatInternalFragment chatinternal=new ChatInternalFragment();
                return chatinternal;*/
            case 1:
                ChatTagsFragment chatTags=new ChatTagsFragment();
                return chatTags;


        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
