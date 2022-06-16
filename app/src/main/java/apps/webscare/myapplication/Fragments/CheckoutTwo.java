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
import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;
import apps.webscare.myapplication.Statics.StaticVar;


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
        Constants.CurrentFrag="checkouttwo";

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
                    StaticVar.priceTotal=StaticVar.priceTotal-200;
                    grandTotal.setText("Rs"+String.valueOf(StaticVar.priceTotal));
                }
                else{
                    SelfPickupLayout.setBackground(getResources().getDrawable(R.drawable.button_grey_background));
                    StaticVar.priceTotal=StaticVar.priceTotal+200;
                    grandTotal.setText("Rs"+String.valueOf(StaticVar.priceTotal));

                }
            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ConfirmOrder();
            }
        });
        ImageButton back=view.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        grandTotal.setText("Rs"+String.valueOf(StaticVar.priceTotal));
        return view;
    }

    private void ConfirmOrder() {


    }


}