package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;


public class Gender extends Fragment {


    ConstraintLayout Male, Female;
    Button Save;
    Boolean male;
    ImageButton back;

    public Gender() {
        // Required empty public constructor
    }

    DatabaseReference ref;
    SharedPreference sharedPreference;

    public static Gender newInstance() {
        Gender fragment = new Gender();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gender, container, false);
        Male = view.findViewById(R.id.constraintLayout11);
        Female = view.findViewById(R.id.constraintLayout12);
        Save = view.findViewById(R.id.save);
        back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        sharedPreference = new SharedPreference(getActivity());

        if (sharedPreference.getGender() != null) {
            if (sharedPreference.getGender().equals("Male")) {
                Male.setBackgroundColor(getResources().getColor(R.color.purple_500));
                Female.setBackgroundColor(getResources().getColor(R.color.background_color));
                male = true;
            } else {
                Female.setBackgroundColor(getResources().getColor(R.color.purple_500));
                Male.setBackgroundColor(getResources().getColor(R.color.background_color));
                male = false;
            }
        }


        Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Male.setBackgroundColor(getResources().getColor(R.color.purple_500));
                Female.setBackgroundColor(getResources().getColor(R.color.background_color));
                male = true;

            }
        });

        Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Female.setBackgroundColor(getResources().getColor(R.color.purple_500));
                Male.setBackgroundColor(getResources().getColor(R.color.background_color));
                male = false;

            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = FirebaseDatabase.getInstance().getReference().child("Users");
                ref.orderByChild("phone").equalTo(sharedPreference.getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                String key = data.getKey();
                                if (male) {
                                    ref.child(key).child("gender").setValue("Male");
                                    sharedPreference.removeGender();
                                    sharedPreference.setGender("Male");
                                } else {
                                    ref.child(key).child("gender").setValue("Female");
                                    sharedPreference.removeGender();
                                    sharedPreference.setGender("Female");
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return view;
    }
}