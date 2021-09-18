package com.example.HelluApp.Walking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.HelluApp.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class walking_ranking extends Fragment {

    private RecyclerView mRecyclerView;
    private walking_ranking_adapter mRecyclerAdapter;
    private ArrayList<walking_ranking_item> mfriendItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =   inflater.inflate(R.layout.fragment_walking_ranking,container,false);

        mRecyclerView = view.findViewById(R.id.recyclerView);

        /* initiate adapter */
        mRecyclerAdapter = new walking_ranking_adapter();

        /* initiate recyclerview */
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /* adapt data */
        mfriendItems = new ArrayList<>();

        // 임의로 이다영, 이예린, 이은서, 최서연, 주호은 이름 찍어주기
        mfriendItems.add(new walking_ranking_item(1, R.drawable.no_image, "이다영"));
        mfriendItems.add(new walking_ranking_item(2, R.drawable.no_image, "이예린"));
        mfriendItems.add(new walking_ranking_item(3, R.drawable.no_image, "이은서"));
        mfriendItems.add(new walking_ranking_item(4, R.drawable.no_image, "최서연"));
        mfriendItems.add(new walking_ranking_item(5, R.drawable.no_image, "주호은"));


        mRecyclerAdapter.setFriendList(mfriendItems);
        mRecyclerView.setAdapter(mRecyclerAdapter);


        // Inflate the layout for this fragment
        return view;
    }

    //fragment는 액티비티랑 다르게 view설정을 해줘야해서 너무 짜증나
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}