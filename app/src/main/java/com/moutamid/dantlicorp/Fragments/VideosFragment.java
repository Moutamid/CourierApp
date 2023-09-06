package com.moutamid.dantlicorp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Adapter.VideoAdapter;
import com.moutamid.dantlicorp.Model.VideoModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends Fragment {

    RecyclerView content_rcv;
    public List<VideoModel> productModelList = new ArrayList<>();
    VideoAdapter videoAdapter;

    EditText search;
    TextView no_text;
    ImageView mic;
    String lcode = "en-US";
    ImageView map;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        content_rcv = view.findViewById(R.id.content_rcv);
        content_rcv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        videoAdapter = new VideoAdapter(getContext(), productModelList);
        content_rcv.setAdapter(videoAdapter);

        if (Config.isNetworkAvailable(getContext())) {
            getProducts();
        } else {
            Toast.makeText(getContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    private void getProducts() {
        Config.showProgressDialog(getContext());
        Constants.VideosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productModelList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    VideoModel herbsModel = ds.getValue(VideoModel.class);
                    productModelList.add(herbsModel);
                }
                videoAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}