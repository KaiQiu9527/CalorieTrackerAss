package com.example.calorietrackerass;

import android.os.Bundle;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DisplayAccountFragment extends Fragment {
    View vDisplayAccount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayAccount = inflater.inflate(R.layout.fragment_account, container, false);
        return vDisplayAccount;
    }
}