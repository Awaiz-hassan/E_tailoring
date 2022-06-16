package apps.webscare.myapplication.Fragments;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import apps.webscare.myapplication.Activities.Login;
import apps.webscare.myapplication.Adapters.GalleryItemsAdapter;
import apps.webscare.myapplication.Model.Constants;
import apps.webscare.myapplication.Model.Product;
import apps.webscare.myapplication.Model.ProductModel;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.RetrofitClient.RetrofitClient;
import apps.webscare.myapplication.SharedPreference.SharedPreference;
import apps.webscare.myapplication.Statics.StaticVar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gallery extends Fragment {

    ImageView home_unchecked, profile_unchecked, grid_unchecked;
    SharedPreference sharedPreference;
    CardView home_checked, profile_checked, grid_checked, men_catagory, women_catagory;
    RecyclerView men_items, women_items;
    GalleryItemsAdapter galleryItemsAdapter;

    TextView women_text, men_text;
    public static  List<Product> womenItems=new ArrayList<>(), menItems= new ArrayList<>();
    ConstraintLayout Loading;
    ImageButton back, cart;
    Call<ProductModel> menProductsCall;
    Call<ProductModel> womenProductsCall;

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
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        Constants.CurrentFrag = "gallery";

        // Inflate the layout for this fragment
        home_unchecked = getActivity().findViewById(R.id.imageView);
        sharedPreference = new SharedPreference(getActivity());
        profile_unchecked = getActivity().findViewById(R.id.imageView2);
        grid_unchecked = getActivity().findViewById(R.id.grid);
        home_checked = getActivity().findViewById(R.id.cardView);
        profile_checked = getActivity().findViewById(R.id.profileChecked);
        grid_checked = getActivity().findViewById(R.id.cardView2);
        men_catagory = view.findViewById(R.id.cardView11);
        women_catagory = view.findViewById(R.id.women_tab);

        men_items = view.findViewById(R.id.mens_wear);
        women_items = view.findViewById(R.id.women_wear);
        men_text = view.findViewById(R.id.mens_text);
        women_text = view.findViewById(R.id.women_text);
        Loading = view.findViewById(R.id.Loading);
        cart = view.findViewById(R.id.cart);
        setGrid_checked();
        getMenItems();

        back = view.findViewById(R.id.imageButton);
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


    void getMenItems() {

        if (menItems.size() > 0) {
            women_items.setVisibility(View.GONE);
            men_items.setVisibility(View.VISIBLE);
            galleryItemsAdapter = new GalleryItemsAdapter(menItems, getActivity());
            men_items.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            men_items.setAdapter(galleryItemsAdapter);
            Loading.setVisibility(View.GONE);
        } else {
            Loading.setVisibility(View.VISIBLE);
            String base= sharedPreference.getUserToken();
            String authHeader = "Bearer " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            RetrofitClient.connect("https://etailoring.pk/store/api/front/20/");
            menProductsCall = RetrofitClient.getInstance().getApi().getProducts(authHeader);
            menProductsCall.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            menItems.clear();
                            menItems = response.body().getData().get(0).getProducts();
                            for(int i=0;i<menItems.size();i++){
                                int price=(int)Math.round(Double.parseDouble(menItems.get(i).getCurrentPrice()))*200;
                                menItems.get(i).setCurrentPrice(String.valueOf(price));
                            }
                            women_items.setVisibility(View.GONE);
                            men_items.setVisibility(View.VISIBLE);
                            galleryItemsAdapter = new GalleryItemsAdapter(menItems, getActivity());
                            men_items.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            men_items.setAdapter(galleryItemsAdapter);
                            Loading.setVisibility(View.GONE);
                        }
                        else {
                            Toast.makeText(getActivity(), "Server Error! Try again later!", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        Toast.makeText(getActivity(), "Server Error! Try again later!", Toast.LENGTH_SHORT).show();
                    }
                    Loading.setVisibility(View.GONE);
                }


                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "Server Error! Try again later!", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }


    void getWomenItems() {

        if (womenItems.size() > 0) {
            men_items.setVisibility(View.GONE);
            women_items.setVisibility(View.VISIBLE);
            galleryItemsAdapter = new GalleryItemsAdapter(womenItems, getActivity());
            women_items.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            women_items.setAdapter(galleryItemsAdapter);
            Loading.setVisibility(View.GONE);
        } else {
            Loading.setVisibility(View.VISIBLE);
            String base= sharedPreference.getUserToken();
            String authHeader = "Bearer " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            RetrofitClient.connect("https://etailoring.pk/store/api/front/22/");
            womenProductsCall = RetrofitClient.getInstance().getApi().getProducts(authHeader);
            womenProductsCall.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            womenItems.clear();
                            womenItems = response.body().getData().get(0).getProducts();
                            women_items.setVisibility(View.VISIBLE);
                            men_items.setVisibility(View.GONE);
                            for(int i=0;i<womenItems.size();i++){
                                int price=(int)Math.round(Double.parseDouble(womenItems.get(i).getCurrentPrice()))*200;
                                womenItems.get(i).setCurrentPrice(String.valueOf(price));
                            }
                            galleryItemsAdapter = new GalleryItemsAdapter(womenItems, getActivity());
                            women_items.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            women_items.setAdapter(galleryItemsAdapter);
                            Loading.setVisibility(View.GONE);
                        }
                        else {
                            Toast.makeText(getActivity(), "Server Error! Try again later!", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        Toast.makeText(getActivity(), "Server Error! Try again later!", Toast.LENGTH_SHORT).show();
                    }
                    Loading.setVisibility(View.GONE);
                }


                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "Server Error! Try again later!", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


    void setHome_checked() {
        home_checked.setVisibility(View.VISIBLE);
        profile_checked.setVisibility(View.GONE);
        grid_checked.setVisibility(View.GONE);
        home_unchecked.setVisibility(View.GONE);
        grid_unchecked.setVisibility(View.VISIBLE);
        profile_unchecked.setVisibility(View.VISIBLE);

        Animation translateAnim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.bottom_up);
        home_checked.startAnimation(translateAnim);


    }

    void setProfile_checked() {
        home_checked.setVisibility(View.GONE);
        profile_checked.setVisibility(View.VISIBLE);
        grid_checked.setVisibility(View.GONE);
        profile_unchecked.setVisibility(View.GONE);
        grid_unchecked.setVisibility(View.VISIBLE);
        home_unchecked.setVisibility(View.VISIBLE);
        Animation translateAnim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.bottom_up);
        profile_checked.startAnimation(translateAnim);
    }

    void setGrid_checked() {
        home_checked.setVisibility(View.GONE);
        profile_checked.setVisibility(View.GONE);
        grid_checked.setVisibility(View.VISIBLE);
        grid_unchecked.setVisibility(View.GONE);
        home_unchecked.setVisibility(View.VISIBLE);
        profile_unchecked.setVisibility(View.VISIBLE);
        Animation translateAnim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.bottom_up);
        grid_checked.startAnimation(translateAnim);
    }


}