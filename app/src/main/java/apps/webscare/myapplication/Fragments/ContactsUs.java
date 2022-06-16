package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.R;


public class ContactsUs extends Fragment {



    public ContactsUs() {
        // Required empty public constructor
    }


    public static ContactsUs newInstance() {
        ContactsUs fragment = new ContactsUs();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contacts_us, container, false);
        Constants.CurrentFrag="contactus";

        ImageButton back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return  view;
    }
}