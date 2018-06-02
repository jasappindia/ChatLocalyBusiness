package com.chatlocalybusiness.ui;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.chatlocalybusiness.R;

public class Toolbar_ActionMode_Callback implements ActionMode.Callback {
 
    private Context context;
//    private RecyclerView_Adapter recyclerView_adapter;
//    private ListView_Adapter listView_adapter;
//    private ArrayList<Item_Model> message_models;
    private boolean isListViewFragment;
 
 
    public Toolbar_ActionMode_Callback(Context context, boolean isListViewFragment) {
        this.context = context;
//        this.recyclerView_adapter = recyclerView_adapter;
//        this.listView_adapter = listView_adapter;
//        this.message_models = message_models;
        this.isListViewFragment = isListViewFragment;
    }
 
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_main, menu);//Inflate the menu over action mode
        return true;
    }
 
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
 
        //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels
        if (Build.VERSION.SDK_INT < 11) {
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_info), MenuItemCompat.SHOW_AS_ACTION_NEVER);
        } else {
            menu.findItem(R.id.action_info).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        }
 
        return true;
    }
 
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
 

                break;
        }
        return false;
    }
 
 
    @Override
    public void onDestroyActionMode(ActionMode mode) {
 
       /* //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
        if (isListViewFragment) {
            listView_adapter.removeSelection();  // remove selection
            Fragment listFragment = new MainActivity().getFragment(0);//Get list fragment
            if (listFragment != null)
                ((ListView_Fragment) listFragment).setNullToActionMode();//Set action mode null
        } else {
            recyclerView_adapter.removeSelection();  // remove selection
            Fragment recyclerFragment = new MainActivity().getFragment(1);//Get recycler fragment
            if (recyclerFragment != null)
                ((RecyclerView_Fragment) recyclerFragment).setNullToActionMode();//Set action mode null
        }*/
    }
}