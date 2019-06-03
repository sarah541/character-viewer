package com.sample.simpsonviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageProgress)
    ProgressBar imageProgress;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.description)
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String name = intent.getExtras().getString("name");
            String description = intent.getExtras().getString("description");
            String imageURL = intent.getExtras().getString("imageURL");
            this.name.setText(name);
            this.description.setText(description);
            if (imageURL != null && !imageURL.isEmpty()) {
                Picasso.get()
                        .load(imageURL)
                        .error(R.drawable.placeholder_thumb)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                imageProgress.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {
                                imageProgress.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                            }
                        });
            } else {
                imageProgress.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                this.imageView.setImageResource(R.drawable.placeholder_thumb);
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
