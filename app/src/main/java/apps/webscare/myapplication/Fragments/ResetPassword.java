package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.webscare.myapplication.R;


public class ResetPassword extends Fragment {



    public ResetPassword() {
        // Required empty public constructor
    }

    public static ResetPassword newInstance() {
        ResetPassword fragment = new ResetPassword();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }
}