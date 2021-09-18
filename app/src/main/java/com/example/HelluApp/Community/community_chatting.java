package com.example.HelluApp.Community;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.HelluApp.DailyStamp.daily_stamp_write;
import com.example.HelluApp.R;

public class community_chatting extends Fragment {

    //채팅.java
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Intent intent = new Intent(getActivity(), ExChat.class);
        //startActivity(intent);
        return inflater.inflate(R.layout.fragment_community_chatting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}