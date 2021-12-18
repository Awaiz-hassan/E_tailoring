package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.webscare.myapplication.R;


public class Measurements extends Fragment {


    public Measurements() {
        // Required empty public constructor
    }

    public static Measurements newInstance(String param1, String param2) {
        Measurements fragment = new Measurements();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_measurements, container, false);
        return view;
    }
}