package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.webscare.myapplication.Adapters.CartAdapter;
import apps.webscare.myapplication.R;


public class Cart extends Fragment {


    RecyclerView cartItems;
    CartAdapter cartAdapter;

    public Cart() {
        // Required empty public constructor
    }


    public static Cart newInstance() {
        Cart fragment = new Cart();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        cartItems=view.findViewById(R.id.cartItems);
        cartAdapter=new CartAdapter();
        cartItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartItems.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        return view;
    }
}