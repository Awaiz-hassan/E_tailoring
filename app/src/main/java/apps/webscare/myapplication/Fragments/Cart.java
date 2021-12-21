package apps.webscare.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apps.webscare.myapplication.Activities.Login;
import apps.webscare.myapplication.Activities.Signup;
import apps.webscare.myapplication.Adapters.CartAdapter;
import apps.webscare.myapplication.Adapters.GalleryItemsAdapter;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;


public class Cart extends Fragment {


    RecyclerView cartItems;
    DatabaseReference cartRef;
    CartAdapter cartAdapter;
    SharedPreference sharedPreference;
    List<GalleryItem> galleryItemList;
    ConstraintLayout Loading;
    TextView subtotal,taX,deliveryCharges,total,totalItems,grandTotal;
    Button secureCheckout;
    int priceTotal=0;

    public Cart() {
        // Required empty public constructor
    }


    public static Cart newInstance() {
        return new Cart();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        sharedPreference=new SharedPreference(getActivity());
        cartItems=view.findViewById(R.id.cartItems);
        Loading=view.findViewById(R.id.Loading);
        subtotal=view.findViewById(R.id.subTotal);
        taX=view.findViewById(R.id.tax);
        secureCheckout=view.findViewById(R.id.button3);
        deliveryCharges=view.findViewById(R.id.textView26);
        galleryItemList=new ArrayList<>();
        total=view.findViewById(R.id.total);
        totalItems=view.findViewById(R.id.textView20);
        grandTotal=view.findViewById(R.id.textView21);
        getCartItems(sharedPreference.getPhone());

        secureCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(priceTotal>0){
                    Fragment myFragment = CheckoutOne.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(null).commit();
                }
            }
        });
        return view;
    }




    void getCartItems(String phone){
        Loading.setVisibility(View.VISIBLE);
        cartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(phone).child("UserCart");
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                galleryItemList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    GalleryItem model = dataSnapshot.getValue(GalleryItem.class);
                    galleryItemList.add(model);
                }
                cartItems.setLayoutManager(new LinearLayoutManager(getActivity()));
                cartAdapter=new CartAdapter(galleryItemList,getActivity(),sharedPreference.getPhone());
                cartItems.setAdapter(cartAdapter);
                Loading.setVisibility(View.GONE);
                priceTotal=0;
                if(galleryItemList.size()>0){
                    for(int i=0;i<galleryItemList.size();i++){
                        priceTotal=priceTotal+Integer.parseInt(galleryItemList.get(i).getPrice());
                    }

                }
                if(priceTotal>0){

                subtotal.setText("PKR "+priceTotal);
                deliveryCharges.setText("PKR 200");
                taX.setText("PKR 180");
                int grand=priceTotal+200+150;
                grandTotal.setText("PKR "+grand);
                total.setText("PKR "+grand);
                totalItems.setText(String.valueOf(galleryItemList.size()));
                }

                else{
                    subtotal.setText("PKR "+0);
                    deliveryCharges.setText("PKR 0");
                    taX.setText("PKR 0");
                    grandTotal.setText("PKR "+0);
                    total.setText("PKR "+0);
                    totalItems.setText(String.valueOf(galleryItemList.size()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}