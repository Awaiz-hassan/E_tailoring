package apps.webscare.myapplication.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import apps.webscare.myapplication.Fragments.Cart;
import apps.webscare.myapplication.Fragments.Gallery;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;

public class GalleryItemsAdapter extends RecyclerView.Adapter<GalleryItemsAdapter.GalleryViewHolder> {

    List<GalleryItem> itemList;
    Context context;
    DatabaseReference cartRef;
    String phone;

    public GalleryItemsAdapter(List<GalleryItem> itemList, Context context, String phone) {
        this.itemList = itemList;
        this.context = context;
        this.phone = phone;
    }

    @NonNull
    @Override
    public GalleryItemsAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryItemsAdapter.GalleryViewHolder holder, int position) {
        holder.itemName.setText(itemList.get(position).getName());
        holder.itemPrice.setText("PKR "+itemList.get(position).getPrice());
        Glide.with(context).load(itemList.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.itemImage);
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemsToCart(itemList.get(position).getName(),itemList.get(position).getId(),itemList.get(position).getPrice(),itemList.get(position).getImage(),phone);
            }
        });
        holder.orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemsToCart(itemList.get(position).getName(),itemList.get(position).getId(),itemList.get(position).getPrice(),itemList.get(position).getImage(),phone);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemList.get(position).isVisible()){
                    holder.orderNowLayout.setVisibility(View.GONE);
                    itemList.get(position).setVisible(false);

                }
                else {
                    holder.orderNowLayout.setVisibility(View.VISIBLE);
                    itemList.get(position).setVisible(true);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        TextView itemName,itemPrice;
        ImageView itemImage;
        CardView cart;
        ConstraintLayout orderNowLayout;
        Button orderNow;
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.textView28);
            itemPrice=itemView.findViewById(R.id.textView27);
            itemImage=itemView.findViewById(R.id.itemImage);
            cart=itemView.findViewById(R.id.addToCart);
            orderNowLayout= itemView.findViewById(R.id.orderNowLayout);
            orderNow=itemView.findViewById(R.id.buttonOrder);
        }
    }


    void AddItemsToCart(String name,String id,String price,String image,String phone){
        cartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(phone).child("UserCart");

        HashMap<String , String> cartMap = new HashMap<>();

        cartMap.put("name" , name);
        cartMap.put("id" , id);
        cartMap.put("price" , price);
        cartMap.put("image" , image);
        cartMap.put("phone" , phone);
        cartMap.put("count" , "1");



        Query query=cartRef.orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                GalleryItem item=dataSnapshot.getValue(GalleryItem.class);
                                if(item.getId().equals(id)){
                                    String key= dataSnapshot.getKey();
                                    String setCount=String.valueOf(Integer.parseInt(item.getCount())+1);
                                    String setPrice=String.valueOf(Integer.parseInt(item.getPrice())+Integer.parseInt(price));
                                    cartRef.child(key).child("count").setValue(setCount);
                                    cartRef.child(key).child("price").setValue(setPrice);
                                    Toast.makeText(context,"Added to cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else{
                            cartRef.push().setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context,"Added to cart", Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        Toast.makeText(context,"Failed to add in cart", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                }
        );

    }
}
