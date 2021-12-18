package apps.webscare.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.webscare.myapplication.R;

public class PlacedOrdersAdapter extends RecyclerView.Adapter<PlacedOrdersAdapter.OrderViewHolder> {
    @NonNull
    @Override
    public PlacedOrdersAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prev_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacedOrdersAdapter.OrderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
