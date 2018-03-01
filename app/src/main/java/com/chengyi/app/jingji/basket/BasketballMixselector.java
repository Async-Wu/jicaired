package com.chengyi.app.jingji.basket;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.net.control.Control;

import java.util.ArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BasketballMixselector extends BaseActivity {

    private TextView visitNameTextView;
    private TextView hostNameTextView;
    private GridView visitGridView;
    private GridView hostGridView;
    private Button cancelBtn;
    private Button ensureBtn;


    BasketballSFCSelectorAdapter adapterHost;
    BasketballSFCSelectorAdapter adapterVisit;

    BasketballManager mManager;
    private BasketballOneGame oneGame;
    private SparseArray<BasketballOneGame> selectedArray;
    private  boolean isClick=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_basketball_sfc_selector);
        initData();
        setupView();
        setupListsner();
    }

    private void initData() {
        mManager = Control.getInstance().getBasketballManager();
        ArrayList<BasketballOneDay> onedayList = mManager.lotteryGameArray
                .get(204);
        isClick=getIntent().getBooleanExtra("isClick",true);
        int groupPosition = getIntent().getIntExtra("group", 0);
        int childPosition = getIntent().getIntExtra("child", 0);
        oneGame = onedayList.get(groupPosition).gameListOneDay
                .get(childPosition);

        selectedArray = mManager.selectedLotteryGameArray.get(204);
    }

    private void setupView() {
        visitNameTextView = (TextView) findViewById(R.id.basket_selector_visit);
        hostNameTextView = (TextView) findViewById(R.id.basket_selector_host);
        visitNameTextView.setText(oneGame.guestName);
        hostNameTextView.setText(oneGame.hostName);

        adapterHost = new BasketballSFCSelectorAdapter(this, true, oneGame);
        adapterVisit = new BasketballSFCSelectorAdapter(this, false, oneGame);

        visitGridView = (GridView) findViewById(R.id.basket_selector_grid_lose);
        visitGridView.setAdapter(adapterVisit);
        hostGridView = (GridView) findViewById(R.id.basket_selector_grid_win);
        hostGridView.setAdapter(adapterHost);
        cancelBtn = (Button) findViewById(R.id.cancel);
        ensureBtn = (Button) findViewById(R.id.yesbtn);
    }

    private void setupListsner() {
        visitGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
if (isClick){
                adapterVisit.setClick(position);
                adapterVisit.notifyDataSetChanged();}
            }

        });

        hostGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

if (isClick){
                adapterHost.setClick(position);
                adapterHost.notifyDataSetChanged();}

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                oneGame.isDanPressed=false;
                finish();
            }

        });

        ensureBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                BasketballOneGame itemVisit = adapterVisit
                        .getBasketballOneGameData();
                BasketballOneGame itemhost = adapterHost
                        .getBasketballOneGameData();

                int count = 0;
                for (int i = 0; i < 6; i++) {
                    boolean visitSelected = itemVisit.isSFCGuestSelected[i];
                    if (visitSelected) {
                        count++;
                    }

                    boolean hostSelected = itemhost.isSFCHostSelected[i];
                    if (hostSelected) {
                        count++;
                    }

                }
                oneGame.isSFCGuestSelected=itemVisit.isSFCGuestSelected;
                oneGame.isSFCHostSelected=itemhost.isSFCHostSelected;
                oneGame.SFFlag=itemVisit.SFCGuestFlag+itemhost.SFCHostFlag-1;
                oneGame.SFCGuestFlag=itemVisit.SFCGuestFlag;
                oneGame.SFCHostFlag=itemhost.SFCHostFlag;


/*                if(count==0){
                    oneGame.reset();
                    mManager.selectedLotteryGameArray.get(204).remove(oneGame.orderIdLocal);
                }else{*/

                    oneGame.isDanPressed=false;
                    mManager.selectedLotteryGameArray.get(204).put(oneGame.orderIdLocal, oneGame);
         /*       }*/
                finish();
            }
        });
    }
}
