package apps.webscare.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apps.webscare.myapplication.Activities.Login;
import apps.webscare.myapplication.Activities.Signup;
import apps.webscare.myapplication.Adapters.AddressAdapter;
import apps.webscare.myapplication.Adapters.GalleryItemsAdapter;
import apps.webscare.myapplication.Model.Address;
import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;


public class CheckoutOne extends Fragment {

    SharedPreference sharedPreference;
    Button proceed;
    TextView address;
    public CheckoutOne() {
        // Required empty public constructor
    }

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
        Constants.CurrentFrag="checkoutone";
        sharedPreference=new SharedPreference(getActivity());
       proceed=view.findViewById(R.id.button5);
       address=view.findViewById(R.id.Address);
       address.setText(sharedPreference.getAddress());

        ImageButton back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
       proceed.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(address.getText().toString().trim().isEmpty()){
                   address.setError("Please provide delivery address");
                   address.requestFocus();
                   return;
               }
               Fragment myFragment = CheckoutTwo.newInstance(address.getText().toString().trim());
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(null).commit();
           }
       });

       return view;

    }

}