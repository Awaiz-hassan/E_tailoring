package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import apps.webscare.myapplication.Adapters.CartAdapter;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;


public class CheckoutTwo extends Fragment {

    SwitchCompat SelfPickup;
    ConstraintLayout SelfPickupLayout,Loading;
    DatabaseReference cartRef,orderRef;
    List<GalleryItem> galleryItemList;
    TextView grandTotal;
    String Address;
    SharedPreference sharedPreference;
    CardView confirmOrder;
    int priceTotal=0;
    public CheckoutTwo() {
        // Required empty public constructor
    }

    public static CheckoutTwo newInstance(String address) {
        CheckoutTwo fragment = new CheckoutTwo();
        Bundle args = new Bundle();
        args.putString("address", address);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_checkout__two, container, false);
        SelfPickup=view.findViewById(R.id.SelfPickup);
        galleryItemList=new ArrayList<>();
        sharedPreference=new SharedPreference(getActivity());
        grandTotal=view.findViewById(R.id.grandTotal);
        Loading=view.findViewById(R.id.Loading);
        SelfPickupLayout=view.findViewById(R.id.SelfPickupLayout);
        confirmOrder=view.findViewById(R.id.cardView13);
        Bundle bundle=getArguments();
        Address=bundle.get("address").toString();
        SelfPickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(SelfPickup.isChecked()){
                    SelfPickupLayout.setBackground(getResources().getDrawable(R.drawable.blue_border));
                }
                else{
                    SelfPickupLayout.setBackground(getResources().getDrawable(R.drawable.button_grey_background));

                }
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ConfirmOrder();
            }
        });
        getCartItems(sharedPreference.getPhone());
        ImageButton back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void ConfirmOrder() {

        if(galleryItemList.size()>0){
            Loading.setVisibility(View.VISIBLE);
            String items=" ";
        HashMap<String , String> orderMap = new HashMap<>();
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            for(int i=0;i<galleryItemList.size();i++){
                items=items+galleryItemList.get(i).getName()+" * "+ galleryItemList.get(i).getCount()+"\n";
            }
            orderMap.put("title",items);
            orderMap.put("placed_on",currentDate);
            orderMap.put("address",Address);
            orderMap.put("price_total",String.valueOf(priceTotal+200+150));
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(sharedPreference.getPhone()).child("UserOrders");
        cartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(sharedPreference.getPhone()).child("UserCart");

        orderRef.push().setValue(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Order Placed", Toast.LENGTH_SHORT).show();
                    cartRef.removeValue();
                    Fragment myFragment = CheckoutThree.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(null).commit();

                }
                else {
                    Loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Failed to place order", Toast.LENGTH_SHORT).show();
                }


            }
        });
        }
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

                Loading.setVisibility(View.GONE);
                priceTotal=0;
                if(galleryItemList.size()>0){
                    for(int i=0;i<galleryItemList.size();i++){
                        priceTotal=priceTotal+Integer.parseInt(galleryItemList.get(i).getPrice());
                    }

                }
                if(priceTotal>0){
                    int grand=priceTotal+200+150;
                    grandTotal.setText("PKR "+grand);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}