package apps.webscare.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import apps.webscare.myapplication.Activities.Login;
import apps.webscare.myapplication.Activities.MainActivity;
import apps.webscare.myapplication.R;

public class CheckoutThree extends Fragment {



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button ContinueShopping;
    public CheckoutThree() {
        // Required empty public constructor
    }
    public static CheckoutThree newInstance() {
        CheckoutThree fragment = new CheckoutThree();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_checkout_three, container, false);
        ContinueShopping=view.findViewById(R.id.continueShopping);
        ImageButton back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        ContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return view;
    }
}