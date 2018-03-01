package com.chengyi.app.home.discover;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.chengyi.R;
import com.chengyi.app.base.BaseFragment;
import com.chengyi.app.listener.IInit;
import com.chengyi.app.listener.INet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishangfan on 2016/10/10.
 */
public class DisFragment extends BaseFragment implements IInit, INet, PageAdapter.IPageSelect {
    private RecyclerView rvDis;
    private DisRvAdapter disRvAdapter;
    private List<DisMode> mModes = new ArrayList<>();
    private SwipeRefreshLayout srlDis;
    private DisListP disListP = new DisListP();
    private boolean isEnd;
    private TextView tv_cur, tv_pre, tv_next;
    private PageMode m;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dis, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDis = (RecyclerView) view.findViewById(R.id.rv_dis);
        m = new PageMode();


        rvDis.setLayoutManager(new LinearLayoutManager(getContext()));
        srlDis = (SwipeRefreshLayout) view.findViewById(R.id.srl_dis);


        tv_cur = (TextView) view.findViewById(R.id.tv_cur);
        tv_pre = (TextView) view.findViewById(R.id.tv_pre);
        tv_next = (TextView) view.findViewById(R.id.tv_next);
        tv_cur.setOnClickListener(this);
        tv_pre.setOnClickListener(this);
        tv_next.setOnClickListener(this);

        srlDis.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        initAdapter();

        rvDis.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:

                        if (!isEnd && recyclerView.getAdapter() != null && ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == recyclerView.getAdapter().getItemCount()) {
                            loadDateMore();
                        }

                        break;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        loadDate();


    }

    private void refresh() {
        disListP.refresh();
        isEnd = false;
        loadDate();
    }

    private void initAdapter() {
        if (mModes == null) return;

        if (disRvAdapter == null) {
            disRvAdapter = new DisRvAdapter(mModes, getContext());
            rvDis.setAdapter(disRvAdapter);
        } else {
            disRvAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void response(int tag, Object o) {


        if (srlDis != null && srlDis.isRefreshing()) srlDis.setRefreshing(false);


        if (FIRST == tag) {

            List<DisMode> disModes = (List<DisMode>) o;
            if (disModes == null || disModes.size() < disListP.getSize()) {
                isEnd = true;
            }
            mModes.addAll(disModes);
            initAdapter();
        }


    }

    @Override
    public void fetch(int tag) {
        if (FIRST == tag) {


        }

    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void loadDate() {
        fetch(FIRST);
    }


    private void loadDateMore() {
        if (isEnd) {
            Toast.makeText(getActivity(), "暂无更多数据", Toast.LENGTH_SHORT).show();
        } else {
            disListP.loadMore();
            loadDate();
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.tv_cur:
                selectPage();


                break;
            case R.id.tv_pre:
                if (Integer.valueOf(tv_cur.getText().toString().split("/")[0]) == 1) {
                    Toast.makeText(getContext(), "当前为第一页", Toast.LENGTH_SHORT).show();
                    break;
                }

                disListP.setPage(Integer.valueOf(tv_cur.getText().toString().split("/")[0]) - 1);
                tv_cur.setText(disListP.getPage() + "/" + pagesize);
                loadDate();
                break;
            case R.id.tv_next:

                if (Integer.valueOf(tv_cur.getText().toString().split("/")[0]) == Integer.valueOf(tv_cur.getText().toString().split("/")[1])) {
                    Toast.makeText(getContext(), "当前为最后一页", Toast.LENGTH_SHORT).show();
                    break;
                }

                disListP.setPage(Integer.valueOf(tv_cur.getText().toString().split("/")[0]) + 1);

                tv_cur.setText(disListP.getPage() + "/" + pagesize);
                loadDate();
                break;


        }
    }

    private int pagesize=100;
    private PopupWindow popupPage;
    private View llPage;
    private RecyclerView rvPage;
    private PageAdapter pageAdapter;


    private void selectPage() {

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();


        if (llPage == null)
            llPage = LayoutInflater.from(getActivity()).inflate(R.layout.ll_page, null,false);
        if (popupPage == null)
            popupPage = new PopupWindow(llPage, (int) (width*.7), (int) (height*.7));
        if (rvPage == null)
            rvPage = (RecyclerView) llPage.findViewById(R.id.rv_page);
        m.setIndex(Integer.parseInt(tv_cur.getText().toString().split("/")[0]));
        m.setCount(Integer.parseInt(tv_cur.getText().toString().split("/")[1]));
        rvPage.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (pageAdapter != null) {
            rvPage.scrollToPosition(Integer.parseInt(tv_cur.getText().toString().split("/")[0])-1);
            pageAdapter.notifyDataSetChanged();
        } else {
            pageAdapter = new PageAdapter(getActivity(), m, this);
            rvPage.setAdapter(pageAdapter);
        }


        popupPage.setFocusable(true);
        popupPage.setOutsideTouchable(true);


        if (!popupPage.isShowing())
            popupPage.showAtLocation(tv_cur, Gravity.CENTER, 0, 0);

    }


    private void disPopPage() {
        if (popupPage != null && popupPage.isShowing()) {
            popupPage.dismiss();
        }
    }



    @Override
    public void getPageselect(int index) {
        disPopPage();
        tv_cur.setText(index + "/" + pagesize);
        loadCurDate();

    }

    private void loadCurDate() {
        disListP.setPage(Integer.parseInt(tv_cur.getText().toString().split("/")[0]));
        loadDate();
    }
}
