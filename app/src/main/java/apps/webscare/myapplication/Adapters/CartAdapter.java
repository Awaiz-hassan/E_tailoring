package apps.webscare.myapplication.Adapters;

import android.content.Context;
import android.icu.text.CaseMap;
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

import java.util.List;

import apps.webscare.myapplication.Fragments.Cart;
import apps.webscare.myapplication.Fragments.Measurements;
import apps.webscare.myapplication.Fragments.SingleCheckoutOne;
import apps.webscare.myapplication.Interfaces.updateCart;
import apps.webscare.myapplication.Model.CartModel;
import apps.webscare.myapplication.Model.GalleryItem;
import apps.webscare.myapplication.R;
import apps.webscare.myapplication.Statics.StaticVar;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    Context context;
    int row_index=-1;
    updateCart updateCart;

    public CartAdapter(Context context, apps.webscare.myapplication.Interfaces.updateCart updateCart) {
        this.context = context;
        this.updateCart = updateCart;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        CartModel cartModel=StaticVar.cart.get(holder.getAdapterPosition());
        holder.title.setText(cartModel.getTitle());
        holder.quantity.setText(cartModel.getQuantity());
        holder.price.setText("PKR "+cartModel.getPrice());
        Glide.with(context).load(cartModel.getImage()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.image);




        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticVar.cart.remove(holder.getAdapterPosition());
                updateCart.onItemClick("cleciked");
                notifyDataSetChanged();
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

            if(!StaticVar.cart.get(holder.getAdapterPosition()).isVisible()){
            holder.details.setVisibility(View.VISIBLE);
                StaticVar.cart.get(holder.getAdapterPosition()).setVisible(true);
            holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            }
            else{
                holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

                holder.details.setVisibility(View.GONE);
                StaticVar.cart.get(holder.getAdapterPosition()).setVisible(false);
            }
        }
        else{
            holder.details.setVisibility(View.GONE);
            holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

        }

    }

    @Override
    public int getItemCount() {
        return StaticVar.cart.size();
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
