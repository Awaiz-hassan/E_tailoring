package apps.webscare.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import apps.webscare.myapplication.Activities.Login;
import apps.webscare.myapplication.Activities.MainActivity;
import apps.webscare.myapplication.Model.User;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;


public class ResetPassword extends Fragment {

    EditText pass1,pass2;
    Button Save;
    SharedPreference sharedPreference;
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
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);
        sharedPreference=new SharedPreference(getActivity());
        pass1=view.findViewById(R.id.pas1);
        pass2=view.findViewById(R.id.pass2);
        Save=view.findViewById(R.id.save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass1.getText().toString().length()<5||pass2.getText().toString().length()<5){
                    pass1.setError("Please choose a strong password");
                    pass2.setError("Please choose a strong password");
                    return;
                }

                if(!pass1.getText().toString().equals(pass2.getText().toString())){
                    pass1.setError("Passwords should be same");
                    pass2.setError("Passwords should be same");
                    return;
                }

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                Query query=db.getReference().child("Users").orderByChild("phone").equalTo(sharedPreference.getPhone());
                query.addListenerForSingleValueEvent(

                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                        String key=dataSnapshot.getKey();
                                        db.getReference().child("Users").child(key).child("password").setValue(pass1.getText().toString());
                                        Toast.makeText(getActivity(),"Password changed",Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );


            }
        });
        ImageButton back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}