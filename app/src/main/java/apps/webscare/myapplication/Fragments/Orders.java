package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.webscare.myapplication.Adapters.PlacedOrdersAdapter;
import apps.webscare.myapplication.R;


public class Orders extends Fragment {


    RecyclerView rw_orders;
    PlacedOrdersAdapter placedOrdersAdapter;
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
        rw_orders=view.findViewById(R.id.rw_orders);
        placedOrdersAdapter=new PlacedOrdersAdapter();
        rw_orders.setLayoutManager(new LinearLayoutManager(getActivity()));
        rw_orders.setAdapter(placedOrdersAdapter);
        placedOrdersAdapter.notifyDataSetChanged();
        return view;
    }
}