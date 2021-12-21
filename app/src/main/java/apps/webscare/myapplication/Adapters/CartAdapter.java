package apps.webscare.myapplication.Adapters;

import android.content.Context;
import android.icu.text.CaseMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import java.util.List;

import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<GalleryItem> cartItems;
    Context context;
    String phone;


    int row_index=-1;

    public CartAdapter(List<GalleryItem> cartItems, Context context, String phone) {
        this.cartItems = cartItems;
        this.context = context;
        this.phone = phone;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {


        holder.title.setText(cartItems.get(position).getName());
        holder.quantity.setText(cartItems.get(position).getCount());
        holder.price.setText("PKR "+cartItems.get(position).getPrice());
        Glide.with(context).load(cartItems.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.image);



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(phone).child("UserCart");

                Query query=cartRef.orderByChild("id").equalTo(cartItems.get(position).getId());
                query.addListenerForSingleValueEvent(

                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                        cartRef.child(dataSnapshot.getKey()).removeValue();
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        }
                );



            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
            }
        });
        if(row_index== holder.getAdapterPosition()){

            if(!cartItems.get(position).isVisible()){
            holder.details.setVisibility(View.VISIBLE);
            cartItems.get(position).setVisible(true);
            holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            }
            else{
                holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

                holder.details.setVisibility(View.GONE);
                cartItems.get(position).setVisible(false);
            }
        }
        else{
            holder.details.setVisibility(View.GONE);
            holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

        }

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout details,delete;
        TextView title,price,quantity;
        ImageView image,arrow;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            details=itemView.findViewById(R.id.details);
            title=itemView.findViewById(R.id.textView16);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.textView18);
            image=itemView.findViewById(R.id.imageView8);
            arrow=itemView.findViewById(R.id.imageView7);
            delete=itemView.findViewById(R.id.constraintLayout20);
        }
    }
}
