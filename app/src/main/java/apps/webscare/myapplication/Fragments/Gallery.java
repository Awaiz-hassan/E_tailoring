package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import apps.webscare.myapplication.Adapters.GalleryItemsAdapter;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.SharedPreference.SharedPreference;

public class Gallery extends Fragment {

    ImageView home_unchecked,profile_unchecked,grid_unchecked;
    SharedPreference sharedPreference;
    CardView home_checked,profile_checked,grid_checked,men_catagory,women_catagory;
    RecyclerView men_items,women_items;
    GalleryItemsAdapter galleryItemsAdapter,galleryItemsAdapterWomen;
    TextView women_text,men_text;
    DatabaseReference ref;
    List<GalleryItem> womenItems,menItems;
    ConstraintLayout Loading;

    public Gallery() {
        // Required empty public constructor
    }

    public static Gallery newInstance() {
        Gallery fragment = new Gallery();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_gallery, container, false);

        // Inflate the layout for this fragment
        home_unchecked=getActivity().findViewById(R.id.imageView);
        sharedPreference=new SharedPreference(getActivity());
        profile_unchecked=getActivity().findViewById(R.id.imageView2);
        womenItems=new ArrayList<>();
        menItems=new ArrayList<>();
        grid_unchecked=getActivity().findViewById(R.id.grid);
        home_checked=getActivity().findViewById(R.id.cardView);
        profile_checked=getActivity().findViewById(R.id.profileChecked);
        grid_checked=getActivity().findViewById(R.id.cardView2);
        men_catagory=view.findViewById(R.id.cardView11);
        women_catagory=view.findViewById(R.id.women_tab);
        men_items=view.findViewById(R.id.mens_wear);
        women_items=view.findViewById(R.id.women_wear);
        men_text=view.findViewById(R.id.mens_text);
        women_text=view.findViewById(R.id.women_text);
        Loading=view.findViewById(R.id.Loading);
        setGrid_checked();
        getMenItems();
        men_catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men_catagory.setCardBackgroundColor(getResources().getColor(R.color.purple_200));
                women_catagory.setCardBackgroundColor(getResources().getColor(R.color.white));
                men_text.setTextColor(getResources().getColor(R.color.white));
                women_text.setTextColor(getResources().getColor(R.color.black));
                getMenItems();
            }
        });


        women_catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                women_catagory.setCardBackgroundColor(getResources().getColor(R.color.purple_200));
                men_catagory.setCardBackgroundColor(getResources().getColor(R.color.white));
                women_text.setTextColor(getResources().getColor(R.color.white));
                men_text.setTextColor(getResources().getColor(R.color.black));
                getWomenItems();

            }
        });
        return view;
    }


    void getMenItems(){
        Loading.setVisibility(View.VISIBLE);

        ref = FirebaseDatabase.getInstance().getReference().child("MenVariety");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    GalleryItem model = dataSnapshot.getValue(GalleryItem.class);
                    String key= dataSnapshot.getKey();
                    model.setId(key);
                    menItems.add(model);
                }
                women_items.setVisibility(View.GONE);
                men_items.setVisibility(View.VISIBLE);
                galleryItemsAdapter=new GalleryItemsAdapter(menItems,getActivity(),sharedPreference.getPhone());
                men_items.setLayoutManager(new GridLayoutManager(getActivity(),2));
                men_items.setAdapter(galleryItemsAdapter);
                Loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Loading.setVisibility(View.GONE);
            }
        });
    }


    void getWomenItems(){
        Loading.setVisibility(View.VISIBLE);
        ref = FirebaseDatabase.getInstance().getReference().child("WomenVariety");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                womenItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    GalleryItem model = dataSnapshot.getValue(GalleryItem.class);
                    model.setId(dataSnapshot.getKey());
                    womenItems.add(model);
                }
                Loading.setVisibility(View.GONE);
                women_items.setVisibility(View.VISIBLE);
                men_items.setVisibility(View.GONE);
                galleryItemsAdapterWomen=new GalleryItemsAdapter(womenItems,getActivity(),sharedPreference.getPhone());
                women_items.setLayoutManager(new GridLayoutManager(getActivity(),2));
                women_items.setAdapter(galleryItemsAdapterWomen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Loading.setVisibility(View.GONE);
            }
        });
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