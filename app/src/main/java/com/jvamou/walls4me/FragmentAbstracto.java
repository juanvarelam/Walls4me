package com.jvamou.walls4me;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAbstracto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAbstracto extends Fragment {



    public FragmentAbstracto() {
        // Required empty public constructor
    }


    public static FragmentAbstracto newInstance(String param1, String param2) {
        FragmentAbstracto fragment = new FragmentAbstracto();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_abstracto, container, false);
    }
}