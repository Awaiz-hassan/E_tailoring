package apps.webscare.myapplication.Fragments;

import static apps.webscare.myapplication.Statics.StaticVar.priceTotal;

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
import android.widget.ImageButton;
import android.widget.TextView;
import apps.webscare.myapplication.Statics.StaticVar.*;
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
import apps.webscare.myapplication.Interfaces.updateCart;
import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;
import apps.webscare.myapplication.Statics.StaticVar;


public class Cart extends Fragment {


    RecyclerView cartItems;
    CartAdapter cartAdapter;
    SharedPreference sharedPreference;
    ConstraintLayout Loading;
    TextView subtotal,taX,deliveryCharges,total,totalItems,grandTotal;
    Button secureCheckout;

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
        Constants.CurrentFrag="cart";

        sharedPreference=new SharedPreference(getActivity());
        cartItems=view.findViewById(R.id.cartItems);
        Loading=view.findViewById(R.id.Loading);
        subtotal=view.findViewById(R.id.subTotal);
        taX=view.findViewById(R.id.tax);
        secureCheckout=view.findViewById(R.id.button3);
        deliveryCharges=view.findViewById(R.id.textView26);
        total=view.findViewById(R.id.total);
        totalItems=view.findViewById(R.id.textView20);
        grandTotal=view.findViewById(R.id.textView21);
        cartItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        updatePrice();
        cartAdapter=new CartAdapter(getActivity(), new updateCart() {
            @Override
            public void onItemClick(String text) {
                updatePrice();
            }
        });
        cartItems.setAdapter(cartAdapter);
        secureCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(priceTotal>0){
                    Fragment myFragment = CheckoutOne.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(null).commit();
                }
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




    void updatePrice(){
        priceTotal=0;
        if(StaticVar.cart.size()>0){
            for(int i=0;i<StaticVar.cart.size();i++){
                priceTotal=priceTotal+(Integer.parseInt(StaticVar.cart.get(i).getPrice())*Integer.parseInt(StaticVar.cart.get(i).getQuantity()) );
            }

        }

        if(priceTotal>0){

            subtotal.setText("PKR "+priceTotal);
            deliveryCharges.setText("PKR 200");
            taX.setText("PKR 150");
            int grand=priceTotal+200+150;
            grandTotal.setText("PKR "+grand);
            total.setText("PKR "+grand);
        }

        else{
            subtotal.setText("PKR "+0);
            deliveryCharges.setText("PKR 0");
            taX.setText("PKR 0");
            grandTotal.setText("PKR "+0);
            total.setText("PKR "+0);
        }
        totalItems.setText(String.valueOf(StaticVar.cart.size()));
    }


}