package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;


public class SingleCheckoutTwo extends Fragment {


    SharedPreference sharedPreference;
    DatabaseReference orderRef, cartRef;
    ConstraintLayout Loading;
    CardView placeOrder;
    TextView totalPrice;
    String title, quantity, price, address, id;

    public SingleCheckoutTwo() {
        // Required empty public constructor
    }

    public static SingleCheckoutTwo newInstance(String title, String quantity, String price, String address, String id) {
        SingleCheckoutTwo fragment = new SingleCheckoutTwo();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("quantity", quantity);
        args.putString("price", price);
        args.putString("address", address);
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_checkout_two, container, false);
        Constants.CurrentFrag="singlecheckouttwo";

        sharedPreference = new SharedPreference(getActivity());
        Loading = view.findViewById(R.id.Loading);
        placeOrder=view.findViewById(R.id.cardView13);
        totalPrice=view.findViewById(R.id.grandTotal);
        Bundle bundle = getArguments();
        title = bundle.get("title").toString();
        quantity = bundle.get("quantity").toString();
        price = bundle.get("price").toString();
        address = bundle.get("address").toString();
        id = bundle.get("id").toString();
        int grandtotal=Integer.parseInt(price)+200+180;
        totalPrice.setText(String.valueOf(grandtotal));

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmOrder();
            }
        });
        return view;

    }

    private void ConfirmOrder() {

        Loading.setVisibility(View.VISIBLE);
        HashMap<String, String> orderMap = new HashMap<>();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        orderMap.put("title", title + "*" + quantity);
        orderMap.put("placed_on", currentDate);
        orderMap.put("address", address);
        orderMap.put("price_total", price);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(sharedPreference.getPhone()).child("UserOrders");
        cartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(sharedPreference.getPhone()).child("UserCart");

        orderRef.push().setValue(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Order Placed", Toast.LENGTH_SHORT).show();
                    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(sharedPreference.getPhone()).child("UserCart");

                    Query query = cartRef.orderByChild("id").equalTo(id);
                    query.addListenerForSingleValueEvent(

                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            cartRef.child(dataSnapshot.getKey()).removeValue();
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            }
                    );
                    Toast.makeText(getActivity(), "Order Placed", Toast.LENGTH_SHORT).show();
                    Fragment myFragment = CheckoutThree.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(null).commit();

                } else {
                    Loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Failed to place order", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}


