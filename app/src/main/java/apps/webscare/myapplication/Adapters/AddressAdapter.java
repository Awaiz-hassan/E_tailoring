package apps.webscare.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import apps.webscare.myapplication.Model.Address;
import apps.webscare.myapplication.R;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    Context context;
    List<Address> addressesList;
    int row_index=-1;

    public AddressAdapter(Context context, List<Address> addressesList) {
        this.context = context;
        this.addressesList = addressesList;
    }

    @NonNull
    @Override
    public AddressAdapter.AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.AddressViewHolder holder, int position) {
        holder.name.setText(addressesList.get(position).getName());
        holder.title.setText(addressesList.get(position).getTitle());
        holder.phone.setText(addressesList.get(position).getPhone());
        holder.address.setText(addressesList.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
            }
        });

        if(position==row_index){
            holder.background.setBackground(context.getResources().getDrawable(R.drawable.blue_border));
        }
        else {
            holder.background.setBackground(context.getResources().getDrawable(R.drawable.button_grey_background));
        }

    }

    public int getItemSelected(){
        return row_index;
    }

    @Override
    public int getItemCount() {
        return addressesList.size();
    }
    public class AddressViewHolder extends RecyclerView.ViewHolder{
        TextView title,name,phone,address;
        ConstraintLayout background;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            address=itemView.findViewById(R.id.Address);
            background=itemView.findViewById(R.id.main_layout);
        }
    }
}
