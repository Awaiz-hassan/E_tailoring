package apps.webscare.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import apps.webscare.myapplication.Fragments.ContactsUs;
import apps.webscare.myapplication.Model.Orders;
import apps.webscare.myapplication.R;

public class PlacedOrdersAdapter extends RecyclerView.Adapter<PlacedOrdersAdapter.OrderViewHolder> {

    Context context;
    List<Orders> ordersList;
    int rw=-1;

    public PlacedOrdersAdapter(Context context, List<Orders> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public PlacedOrdersAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prev_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacedOrdersAdapter.OrderViewHolder holder, int position) {
        holder.title.setText(ordersList.get(position).getTitle());
        holder.destination.setText(ordersList.get(position).getAddress());
        holder.date.setText(ordersList.get(position).getPlaced_on());
        holder.payment.setText("PKR "+ordersList.get(position).getPrice_total());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rw=position;
                notifyDataSetChanged();
            }
        });

        if(rw==position){
            holder.expanded.setVisibility(View.VISIBLE);
        }
        else{
            holder.expanded.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView date,title,destination,payment;
        ConstraintLayout expanded;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.textView15);
            destination=itemView.findViewById(R.id.destinationText);
            payment=itemView.findViewById(R.id.costText);
            expanded=itemView.findViewById(R.id.expanded);
        }
    }
}
