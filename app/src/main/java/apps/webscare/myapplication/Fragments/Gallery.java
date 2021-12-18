package apps.webscare.myapplication.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
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

import apps.webscare.myapplication.Adapters.GalleryItemsAdapter;
import apps.webscare.myapplication.R;

public class Gallery extends Fragment {

    ImageView home_unchecked,profile_unchecked,grid_unchecked;
    CardView home_checked,profile_checked,grid_checked,men_catagory,women_catagory;
    RecyclerView men_items,women_items;
    GalleryItemsAdapter galleryItemsAdapter,galleryItemsAdapterWomen;
    TextView women_text,men_text;

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
        profile_unchecked=getActivity().findViewById(R.id.imageView2);
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
        setGrid_checked();

        men_catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men_catagory.setCardBackgroundColor(getResources().getColor(R.color.purple_200));
                women_catagory.setCardBackgroundColor(getResources().getColor(R.color.white));
                men_text.setTextColor(getResources().getColor(R.color.white));
                women_text.setTextColor(getResources().getColor(R.color.black));
                men_items.setVisibility(View.VISIBLE);
                women_items.setVisibility(View.GONE);
                galleryItemsAdapter=new GalleryItemsAdapter();
                men_items.setLayoutManager(new GridLayoutManager(getActivity(),2));
                men_items.setAdapter(galleryItemsAdapter);

            }
        });


        women_catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                women_catagory.setCardBackgroundColor(getResources().getColor(R.color.purple_200));
                men_catagory.setCardBackgroundColor(getResources().getColor(R.color.white));
                women_text.setTextColor(getResources().getColor(R.color.white));
                men_text.setTextColor(getResources().getColor(R.color.black));
                women_items.setVisibility(View.VISIBLE);
                men_items.setVisibility(View.GONE);

                galleryItemsAdapterWomen=new GalleryItemsAdapter();
                women_items.setLayoutManager(new GridLayoutManager(getActivity(),2));

                women_items.setAdapter(galleryItemsAdapter);
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