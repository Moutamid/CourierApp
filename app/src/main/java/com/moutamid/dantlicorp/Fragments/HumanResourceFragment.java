package com.moutamid.dantlicorp.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.dantlicorp.Adapter.VideoAdapter;
import com.moutamid.dantlicorp.Dailogues.DialogClass;
import com.moutamid.dantlicorp.Model.VideoModel;
import com.moutamid.dantlicorp.R;
import com.moutamid.dantlicorp.helper.Config;
import com.moutamid.dantlicorp.helper.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HumanResourceFragment extends Fragment {


    LinearLayout direct_deposit, verification_letter, verification_letter_request;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resources, container, false);
        direct_deposit = view.findViewById(R.id.direct_deposit);
        verification_letter = view.findViewById(R.id.verification_letter);
        verification_letter_request = view.findViewById(R.id.verification_letter_request);
        direct_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogClass cdd = new DialogClass(getActivity(), "Do you need to update your payment information?", "https://dantlicorp.com/onlineform/auth-form/");
                cdd.show();
            }
        });
        verification_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogClass cdd = new DialogClass(getActivity(), "Do you need to update your address on file with Dantli Corp?", "https://dantlicorp.com/onlineform/w-9/");
                cdd.show();
            }
        }); verification_letter_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogClass cdd = new DialogClass(getActivity(), "Do you want to Request Employment Verification?", "https://dantlicorp.com/onlineform/employment-verification/");
                cdd.show();
            }
        });
        return view;
    }

}