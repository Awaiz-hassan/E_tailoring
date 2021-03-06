package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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

import apps.webscare.myapplication.Adapters.AddressAdapter;
import apps.webscare.myapplication.Model.Address;
import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;


public class ManageAddress extends Fragment {

    ConstraintLayout addAddress;
    DatabaseReference ref;
    List<Address> addressList;
    AddressAdapter addressAdapter;
    RecyclerView rw_addresses;
    ConstraintLayout Loading;
    SharedPreference sharedPreference;
    AlertDialog alertDialog;

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
        View view= inflater.inflate(R.layout.fragment_manage_address, container, false);
        Constants.CurrentFrag="manageaddress";

        sharedPreference=new SharedPreference(getActivity());
        addAddress=view.findViewById(R.id.constraintLayout17);
        rw_addresses=view.findViewById(R.id.Address);
        Loading=view.findViewById(R.id.Loading);
        addressList=new ArrayList<>();
        ImageButton back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title,address;
                Button OK ,cancel;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.add_address, null);
                title=dialogView.findViewById(R.id.addressTitle);
                address=dialogView.findViewById(R.id.address);
                OK=dialogView.findViewById(R.id.ok);
                cancel=dialogView.findViewById(R.id.cancel);
                dialogBuilder.setView(dialogView);
                alertDialog = dialogBuilder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(title.getText().toString().length()<5){
                            title.setError("Enter a valid title!");
                            return;
                        }
                        if(address.getText().toString().length()<10){
                            address.setError("Enter a valid Address!");
                            return;
                        }
                        alertDialog.dismiss();
                        HashMap<String , String> addressMap = new HashMap<>();
                        addressMap.put("title" , title.getText().toString());
                        addressMap.put("address" , address.getText().toString());
                        addressMap.put("name" , sharedPreference.getName());
                        addressMap.put("phone" , sharedPreference.getPhone());
                        ref = FirebaseDatabase.getInstance().getReference().child("Address").child(sharedPreference.getPhone()).child("UserAddress");
                        ref.push().setValue(addressMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){


                                }
                            }
                        });

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
        getAllAddresses();
        return view;
    }



    void getAllAddresses(){
        Loading.setVisibility(View.VISIBLE);
        ref = FirebaseDatabase.getInstance().getReference().child("Address").child(sharedPreference.getPhone()).child("UserAddress");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addressList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Address model = dataSnapshot.getValue(Address.class);
                    addressList.add(model);
                }
                addressAdapter=new AddressAdapter(getActivity(),addressList);
                rw_addresses.setLayoutManager(new LinearLayoutManager(getActivity()));
                rw_addresses.setAdapter(addressAdapter);
                Loading.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Loading.setVisibility(View.GONE);

            }
        });
    }
}