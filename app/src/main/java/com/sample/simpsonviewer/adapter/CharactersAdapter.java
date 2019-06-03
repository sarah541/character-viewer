package com.sample.simpsonviewer.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.simpsonviewer.DetailActivity;
import com.sample.simpsonviewer.R;
import com.sample.simpsonviewer.model.RelatedTopic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sample.simpsonviewer.Constants.KEY_DESCRIPTION;
import static com.sample.simpsonviewer.Constants.KEY_IMAGE_URL;
import static com.sample.simpsonviewer.Constants.KEY_NAME;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.Item> {

    private final List<RelatedTopic> list;

    public CharactersAdapter(List<RelatedTopic> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Item(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_character, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Item item, int position) {
        RelatedTopic relatedTopic = list.get(position);
        String name = relatedTopic.getText().split(" - ")[0];
        item.name.setText(name == null ? "Unknown" : name);
        item.name.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra(KEY_NAME, name);
            if (relatedTopic.getText().split(" - ").length > 1) {
                String description = relatedTopic.getText().split(" - ")[1];
                intent.putExtra(KEY_DESCRIPTION, description == null ? "Unknown" : description);
            }
            intent.putExtra(KEY_IMAGE_URL, relatedTopic.getIcon().getURL());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Item extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;

        Item(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
