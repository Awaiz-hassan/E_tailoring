package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import apps.webscare.myapplication.R;


public class Profile extends Fragment {

    ImageView home_unchecked,profile_unchecked,grid_unchecked;
    CardView home_checked,profile_checked,grid_checked;

    ConstraintLayout gender, myOrders,resetPassword,manageAddress,aboutUs,contactUs,logout ;
    public Profile() {
        // Required empty public constructor
    }


    public static Profile newInstance() {
        Profile fragment = new Profile();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        home_unchecked=getActivity().findViewById(R.id.imageView);
        profile_unchecked=getActivity().findViewById(R.id.imageView2);
        grid_unchecked=getActivity().findViewById(R.id.grid);
        home_checked=getActivity().findViewById(R.id.cardView);
        profile_checked=getActivity().findViewById(R.id.profileChecked);
        grid_checked=getActivity().findViewById(R.id.cardView2);



        gender=view.findViewById(R.id.constraintLayout4);
        myOrders=view.findViewById(R.id.constraintLayout5);
        resetPassword=view.findViewById(R.id.constraintLayout6);
        manageAddress=view.findViewById(R.id.constraintLayout7);
        aboutUs=view.findViewById(R.id.constraintLayout9);
        contactUs=view.findViewById(R.id.constraintLayout10);
        logout=view.findViewById(R.id.logOut);
        setProfile_checked();




        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = Gender.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });

        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment myFragment = Orders.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = ResetPassword.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });
        manageAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = ManageAddress.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = AboutUs.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment myFragment = ContactsUs.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, myFragment).addToBackStack(myFragment.getClass().getName()).commit();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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