package com.chengyi.app.home.actives;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.listener.IInit;
import com.chengyi.app.listener.INet;

import java.util.ArrayList;
import java.util.List;

public class ActivesActivity extends BaseActivity implements INet, IInit {
    private List<ActivesMode> mMode = new ArrayList<>();
    private RecyclerView rvActies;
    private boolean isEnd;
    private SwipeRefreshLayout srl_actives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actives);

       setCusTomeTitle("活动");
        setBack();
        init();
    }

    @Override
    public void init() {
        initView();
        loadDate();
    }

    @Override
    public void initView() {
        rvActies = (RecyclerView) findViewById(R.id.rv_acties);
        rvActies.setLayoutManager(new LinearLayoutManager(this));
        srl_actives = (SwipeRefreshLayout) findViewById(R.id.srl_actives);

        rvActies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (recyclerView.getAdapter()!=null&&!isEnd && ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == recyclerView.getAdapter().getItemCount()) {
                            loadDatemore();
                        }

                        break;
                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        srl_actives.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadDate();
            }
        });

    }

    private void loadDatemore() {
        loadDate();
    }

    @Override
    public void loadDate() {
        isEnd = false;
        fetch(FIRST);

    }

    @Override
    public void response(int tag, Object o) {
        if (tag == FIRST) {
            List<ActivesMode> m = (List<ActivesMode>) o;

            if (m == null || m.size() < 10) {
                isEnd = true;
            }

            mMode.addAll(m);

            initAdapter();

        }
    }

    private ActivesAdapter adapter;

    private void initAdapter() {

        if (mMode == null || mMode.isEmpty()) return;

        if (adapter == null) {
            adapter = new ActivesAdapter(mMode, this);
            rvActies.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }


    }

    @Override
    public void fetch(int tag) {
        if (FIRST == tag) {

        }

    }
}
