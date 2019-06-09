package com.sample.simpsonviewer.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.simpsonviewer.DetailActivity;
import com.sample.simpsonviewer.R;
import com.sample.simpsonviewer.model.RelatedTopic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sample.simpsonviewer.Constants.KEY_DESCRIPTION;
import static com.sample.simpsonviewer.Constants.KEY_IMAGE_URL;
import static com.sample.simpsonviewer.Constants.KEY_NAME;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.Item> implements Filterable {

    private List<RelatedTopic> list;
    private List<RelatedTopic> filteredList;
    private boolean isTablet;

    public CharactersAdapter(List<RelatedTopic> list, boolean isTablet) {
        this.list = list;
        this.filteredList = list;
        this.isTablet = isTablet;
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Item(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_character, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Item item, int position) {
        RelatedTopic relatedTopic = filteredList.get(position);
        String name = relatedTopic.getCharacterName();
        item.name.setText(name == null ? "Unknown" : name);
        if (!isTablet) {
            item.name.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(KEY_NAME, name);
                String description = relatedTopic.getDescription();
                intent.putExtra(KEY_DESCRIPTION, description);
                intent.putExtra(KEY_IMAGE_URL, relatedTopic.getIcon().getURL());
                view.getContext().startActivity(intent);
            });
        } else if (item.description != null && item.imageView != null && item.imageProgress != null) {
            item.description.setText(relatedTopic.getDescription());
            String imageURL = relatedTopic.getIcon().getURL();
            if (imageURL != null && !imageURL.isEmpty()) {
                Picasso.get()
                        .load(imageURL)
                        .error(R.drawable.placeholder_thumb)
                        .into(item.imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                item.imageProgress.setVisibility(View.GONE);
                                item.imageView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {
                                item.imageProgress.setVisibility(View.GONE);
                                item.imageView.setVisibility(View.VISIBLE);
                            }
                        });
            } else {
                item.imageProgress.setVisibility(View.GONE);
                item.imageView.setVisibility(View.VISIBLE);
                item.imageView.setImageResource(R.drawable.placeholder_thumb);
            }
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = list;
                } else {
                    ArrayList<RelatedTopic> tempList = new ArrayList<>();
                    for (RelatedTopic relatedTopic : list) {
                        if (relatedTopic.getCharacterName().toLowerCase().contains(charString) || relatedTopic.getDescription().toLowerCase().contains(charString)) {
                            tempList.add(relatedTopic);
                        }
                    }
                    filteredList = tempList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<RelatedTopic>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class Item extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @Nullable
        @BindView(R.id.description)
        TextView description;
        @Nullable
        @BindView(R.id.imageView)
        ImageView imageView;
        @Nullable
        @BindView(R.id.imageProgress)
        ProgressBar imageProgress;

        Item(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
