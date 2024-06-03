package com.tasks.university.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tasks.domain.university.data.UniversityListItem;
import com.tasks.university.R;

import java.util.List;
import android.util.Pair;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder> {
    private final List<Pair<UniversityListItem, String>> universityList;
    private final OnItemClicked onItemClicked;

    public UniversityAdapter(
            List<Pair<UniversityListItem, String>> universityList,
            OnItemClicked onItemClicked
    ) {
        this.universityList = universityList;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new UniversityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityViewHolder holder, int position) {
        Pair<UniversityListItem, String> item = universityList.get(position);
        holder.container.setOnClickListener(v -> onItemClicked.onClick(universityList.get(position)));
        UniversityListItem university = item.first;
        String picture_url = item.second;

        String name = university.getCells().getShortName();
        if (name.length() > 45) {
            name = name.substring(0, 44) + "...";
        }
        holder.title.setText(name);

        String id = "pic_id_" + university.getGlobalId();
        @SuppressLint("DiscouragedApi") int imageResource = holder.itemView.getContext().getResources().getIdentifier(id, "drawable", holder.itemView.getContext().getPackageName());

        // Проверка наличия ресурса
        if (imageResource != 0) {
            Picasso.get()
                    .load(imageResource)
                    .placeholder(com.tasks.core.R.drawable.vuz_default) // Placeholder image
                    .into(holder.vuzIco);
        } else if (picture_url != null && !picture_url.isEmpty()) {
            Picasso.get()
                    .load(picture_url)
                    .placeholder(com.tasks.core.R.drawable.vuz_default) // Placeholder image
                    .into(holder.vuzIco);
        } else {
            holder.vuzIco.setImageResource(com.tasks.core.R.drawable.vuz_default); // Placeholder image
        }
    }

    @Override
    public int getItemCount() {
        return universityList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Pair<UniversityListItem, String>> newData) {
        universityList.clear();
        universityList.addAll(newData);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearData() {
        universityList.clear();
        notifyDataSetChanged();
    }

    static class UniversityViewHolder extends RecyclerView.ViewHolder {
        ImageView vuzIco;
        TextView title;
        RelativeLayout container;

        public UniversityViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            vuzIco = itemView.findViewById(R.id.vuzIco);
            title = itemView.findViewById(R.id.title);
        }
    }

    public interface OnItemClicked {
        public void onClick(Pair<UniversityListItem, String> item);
    }
}
