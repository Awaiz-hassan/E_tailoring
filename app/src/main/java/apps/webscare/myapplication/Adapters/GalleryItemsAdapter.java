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
import apps.webscare.myapplication.Fragments.SingleCheckoutOne;
import apps.webscare.myapplication.Model.CartModel;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.Model.Measurement;
import apps.webscare.myapplication.Model.Product;
import apps.webscare.myapplication.Model.ProductModel;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.Statics.StaticVar;

public class GalleryItemsAdapter extends RecyclerView.Adapter<GalleryItemsAdapter.GalleryViewHolder> {

    List<Product> itemList;
    Context context;


    public GalleryItemsAdapter(List<Product> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }

    @NonNull
    @Override
    public GalleryItemsAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryItemsAdapter.GalleryViewHolder holder, int position) {
        holder.itemName.setText(itemList.get(position).getTitle());
        holder.itemPrice.setText("PKR "+itemList.get(position).getCurrentPrice());
        Product product=itemList.get(holder.getAdapterPosition());
        Glide.with(context).load(itemList.get(position).getThumbnail()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.itemImage);
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartModel cartModel=new CartModel(product.getTitle(),product.getCurrentPrice(),new Measurement("0","0","0","0","0","0","0","0","0"),"1",product.getThumbnail());
                boolean alreadyExist=false;
                if(StaticVar.cart.size()>=holder.getAdapterPosition())
                for(int i=0;i<StaticVar.cart.size();i++){
                       if(StaticVar.cart.get(i).getTitle().equals(itemList.get(holder.getAdapterPosition()).getTitle())){
                           int quantity= Integer.parseInt(StaticVar.cart.get(i).getQuantity())+1;
                           StaticVar.cart.get(i).setQuantity(String.valueOf(quantity));
                           alreadyExist=true;
                       }
                   }

                if(!alreadyExist){
                    StaticVar.cart.add(cartModel);
                }
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
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



}
