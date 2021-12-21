package apps.webscare.myapplication.Fragments;

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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import apps.webscare.myapplication.Adapters.GalleryItemsAdapter;
import apps.webscare.myapplication.Adapters.PlacedOrdersAdapter;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;


public class Orders extends Fragment {


    RecyclerView rw_orders;
    PlacedOrdersAdapter placedOrdersAdapter;
    DatabaseReference orderRef;
    SharedPreference sharedPreference;
    ImageView back;
    ImageButton cart;
    ConstraintLayout Loading;
    List<apps.webscare.myapplication.Model.Orders> ordersList;
    public Orders() {
        // Required empty public constructor
    }


    public static Orders newInstance() {
        Orders fragment = new Orders();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_orders, container, false);
        sharedPreference=new SharedPreference(getActivity());
        rw_orders=view.findViewById(R.id.rw_orders);
        cart=view.findViewById(R.id.cart);
        Loading=view.findViewById(R.id.Loading);
        ordersList=new ArrayList<>();

        back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = Cart.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });
        getALlOrders();
        return view;
    }

    void getALlOrders(){
        Loading.setVisibility(View.VISIBLE);

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(sharedPreference.getPhone()).child("UserOrders");
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ordersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    apps.webscare.myapplication.Model.Orders model = dataSnapshot.getValue(apps.webscare.myapplication.Model.Orders.class);

                    ordersList.add(model);
                }

                placedOrdersAdapter=new PlacedOrdersAdapter(getActivity(),ordersList);
                rw_orders.setLayoutManager(new LinearLayoutManager(getActivity()));
                rw_orders.setAdapter(placedOrdersAdapter);
                Loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Loading.setVisibility(View.GONE);
            }
        });

    }
}