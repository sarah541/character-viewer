package com.sample.simpsonviewer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sample.simpsonviewer.adapter.CharactersAdapter;
import com.sample.simpsonviewer.data.remote.ApiService;
import com.sample.simpsonviewer.model.BaseModel;
import com.sample.simpsonviewer.model.RelatedTopic;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Inject
    ApiService apiService;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MaterialDialog dialog;
    private List<RelatedTopic> relatedTopicList = new ArrayList<>();
    private CharactersAdapter charactersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        charactersAdapter = new CharactersAdapter(relatedTopicList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(charactersAdapter);


        apiService.getCharacterViewer(getString(R.string.query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> charactersAdapter.notifyDataSetChanged())
                .subscribe(new SingleObserver<BaseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(MainActivity.this)
                                .title(getResources().getString(R.string.loading))
                                .content(getResources().getString(R.string.please_wait))
                                .cancelable(false)
                                .progress(true, 0);
                        dialog = builder.build();
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(BaseModel baseModel) {
                        dialog.dismiss();
                        MainActivity.this.relatedTopicList.addAll(baseModel.getRelatedTopics());
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        e.printStackTrace();
                    }
                });
    }
}
