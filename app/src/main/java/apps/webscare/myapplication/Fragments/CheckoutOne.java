package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.webscare.myapplication.R;


public class CheckoutOne extends Fragment {


    public CheckoutOne() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CheckoutOne newInstance() {
        CheckoutOne fragment = new CheckoutOne();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_checkout_one, container, false);
       return view;

    }
}