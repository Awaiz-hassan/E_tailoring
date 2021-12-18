package apps.webscare.myapplication.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import apps.webscare.myapplication.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    int row_index=-1;

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                Log.d("tag123","Clicked");
                notifyDataSetChanged();
            }
        });
        if (row_index == holder.getAdapterPosition()) {
            holder.details.setVisibility(View.VISIBLE);
        }
        holder.details.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return 10;
    }
    public class CartViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout details;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            details=itemView.findViewById(R.id.details);
        }
    }
}
