package apps.webscare.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.webscare.myapplication.Fragments.Gallery;
import apps.webscare.myapplication.R;

public class GalleryItemsAdapter extends RecyclerView.Adapter<GalleryItemsAdapter.GalleryViewHolder> {
    @NonNull
    @Override
    public GalleryItemsAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryItemsAdapter.GalleryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
