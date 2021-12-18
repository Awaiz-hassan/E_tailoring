package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import apps.webscare.myapplication.R;


public class Home extends Fragment {

    ImageView home_unchecked,profile_unchecked,grid_unchecked;
    CardView home_checked,profile_checked,grid_checked;
    ImageButton cart;
    ConstraintLayout gallery,orders,settings;
    public Home() {
        // Required empty public constructor
    }


    public static Home newInstance() {
        Home fragment = new Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        gallery=view.findViewById(R.id.constraintLayout2);
        orders=view.findViewById(R.id.constraintLayout3);
        settings=view.findViewById(R.id.settings);
        cart=view.findViewById(R.id.cart);

        home_unchecked=getActivity().findViewById(R.id.imageView);
        profile_unchecked=getActivity().findViewById(R.id.imageView2);
        grid_unchecked=getActivity().findViewById(R.id.grid);
        home_checked=getActivity().findViewById(R.id.cardView);
        profile_checked=getActivity().findViewById(R.id.profileChecked);
        grid_checked=getActivity().findViewById(R.id.cardView2);



        setHome_checked();

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = Cart.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = Gallery.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = Orders.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = Profile.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });
        return view;
    }


    void setHome_checked(){
        home_checked.setVisibility(View.VISIBLE);
        profile_checked.setVisibility(View.GONE);
        grid_checked.setVisibility(View.GONE);
        home_unchecked.setVisibility(View.GONE);
        grid_unchecked.setVisibility(View.VISIBLE);
        profile_unchecked.setVisibility(View.VISIBLE);

        Animation translateAnim= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.bottom_up);
        home_checked.startAnimation(translateAnim);


    }

    void setProfile_checked(){
        home_checked.setVisibility(View.GONE);
        profile_checked.setVisibility(View.VISIBLE);
        grid_checked.setVisibility(View.GONE);
        profile_unchecked.setVisibility(View.GONE);
        grid_unchecked.setVisibility(View.VISIBLE);
        home_unchecked.setVisibility(View.VISIBLE);
        Animation translateAnim= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.bottom_up);
        profile_checked.startAnimation(translateAnim);
    }

    void setGrid_checked(){
        home_checked.setVisibility(View.GONE);
        profile_checked.setVisibility(View.GONE);
        grid_checked.setVisibility(View.VISIBLE);
        grid_unchecked.setVisibility(View.GONE);
        home_unchecked.setVisibility(View.VISIBLE);
        profile_unchecked.setVisibility(View.VISIBLE);
        Animation translateAnim= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.bottom_up);
        grid_checked.startAnimation(translateAnim);
    }

}