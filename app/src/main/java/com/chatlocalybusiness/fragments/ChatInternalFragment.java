package com.chatlocalybusiness.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatlocalybusiness.R;
import com.chatlocalybusiness.adapter.AdapterForChatThreads;
import com.chatlocalybusiness.adapter.AdapterForInternalChats;

/**
 * Created by windows on 12/19/2017.
 */
public class ChatInternalFragment extends Fragment{


    RecyclerView rv_internal;
    LinearLayoutManager layoutManager;
    AdapterForInternalChats adapterForInternalChats;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chatinternal,container,false);
        setRecyclerView(view);
        return view;
    }
    public void setRecyclerView(View view)
    {
        rv_internal=(RecyclerView)view.findViewById(R.id.rv_internal);
        layoutManager=new LinearLayoutManager(getActivity());
        adapterForInternalChats=new AdapterForInternalChats(getActivity());
        rv_internal.setAdapter(adapterForInternalChats);
        rv_internal.setLayoutManager(layoutManager);
    }

}
