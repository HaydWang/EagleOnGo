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
public class SettingsFragment extends Fragment {
    @BindView(R.id.button_settings)
    ImageButton btnSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent();
                    i.setComponent(new ComponentName("com.cnh.pf.android.vehicle",
                            "com.cnh.pf.android.vehicle.VehicleActivity"));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Vehicle Setup App not found. ", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
