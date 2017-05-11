package com.cnh.android.eagleongo.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cnh.android.eagleongo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by f10210c on 5/11/2017.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.button_home)
    ImageButton btnHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),
                        "Home Screen is not integrated yet.", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
