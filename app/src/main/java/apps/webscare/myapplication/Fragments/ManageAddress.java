package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.webscare.myapplication.R;


public class ManageAddress extends Fragment {



    public ManageAddress() {
        // Required empty public constructor
    }


    public static ManageAddress newInstance() {
        ManageAddress fragment = new ManageAddress();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_address, container, false);
    }
}