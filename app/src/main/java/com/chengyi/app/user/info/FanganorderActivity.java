package com.chengyi.app.user.info;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.follow.FollowMeActivity;
import com.chengyi.app.follow.FollowMeModel;
import com.chengyi.app.jingji.MatchesResultEntity;
import com.chengyi.app.jingji.basket.FananBasketball;
import com.chengyi.app.jingji.bzjy.BeijingxqAdapter;
import com.chengyi.app.jingji.bzjy.FananFootballAdapter;
import com.chengyi.app.model.basket.FanorderBasketball;
import com.chengyi.app.model.bjdc.BeijingdcEntity;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.caipiao.SchemeJoinsEntity;
import com.chengyi.app.model.caipiao.XiangqingEntity;
import com.chengyi.app.model.footOrder.FanorderFootballEntity;
import com.chengyi.app.model.footOrder.SchemeContentEntity;
import com.chengyi.app.model.footOrder.SchemeDetailEntity;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.user.setting.ScanimageActivity;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.L;
import com.chengyi.app.web.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaqi on 2016/5/10.
 */
public class FanganorderActivity extends BaseActivity implements View.OnClickListener {
    private int schemeId;
    private XiangqingEntity detail;
    private ImageView ivLottery;
    private TextView tvIsHemai, tvIsName;


    private RelativeLayout loaddata;

    private TextView /*lotteryName,*//* issue, */initiateTime, schemeAmount, statusDesc, tvschemeId, schemefangan, schemeNumberUnit, multipe, fangan_follow_me, fangan_follow_me_name;
    private Button image, contact;

    private TextView fangan_xianshinote, fangan_get_money;
    private ListView drawnumber;
    private LinearLayout bottomlayoutbuy, gametitle;
    private String baseurl;
    private FananorderAdapter adapter;
//    private FananFootballAdapter adapter1;
    private Map<String, String> map;
    private int whereFrom = -1;
    int flag = 0;
    int tiaoflag = 0;
    private TextView tvLotteryName;
    private double zhongMoney;
    private TextView item_footmatchcode, item_foothostname, item_footguestname, item_result, item_footchoose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fanganorder);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestandload();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
//        if (adapter1 != null) {
//            adapter1.notifyDataSetChanged();
//        }
    }

    private void init() {
        Intent intent = getIntent();
        setCusTomeTitle("订单详情");
        zhongMoney = intent.getDoubleExtra("zhongMoney", 0d);
        fangan_get_money = (TextView) findViewById(R.id.fangan_get_money);
        L.d(zhongMoney + "");
        if (zhongMoney > 0)
            fangan_get_money.setText(zhongMoney + "");
        else {
            fangan_get_money.setText("0.00");
        }

        loaddata = (RelativeLayout) findViewById(R.id.loaddata);
        loaddata.setVisibility(View.VISIBLE);


        tvIsName = (TextView) findViewById(R.id.tv_is_name);
        tvIsHemai = (TextView) findViewById(R.id.tv_is_hemai);
        schemeId = intent.getIntExtra(URLSuffix.schemeId, -1);
        whereFrom = intent.getIntExtra("whereFrom", -1);
        bottomlayoutbuy = (LinearLayout) findViewById(R.id.bottomlayoutbuy);


        item_footmatchcode = (TextView) findViewById(R.id.item_footmatchcode);

        item_foothostname = (TextView) findViewById(R.id.item_foothostname);
        item_footguestname = (TextView) findViewById(R.id.item_footguestname);
        item_result = (TextView) findViewById(R.id.item_result);
        item_footchoose = (TextView) findViewById(R.id.item_footchoose);


        fangan_xianshinote = (TextView) findViewById(R.id.fangan_xianshinote);
        fangan_follow_me = (TextView) findViewById(R.id.fangan_follow_me);
        fangan_follow_me_name = (TextView) findViewById(R.id.fangan_follow_me_name);
        fangan_follow_me.setOnClickListener(this);
        ivLottery = (ImageView) findViewById(R.id.iv_lottery);
        tvLotteryName = (TextView) findViewById(R.id.tv_lottery_name);

        gametitle = (LinearLayout) findViewById(R.id.games_title);
        if (fangan_xianshinote != null) {
            fangan_xianshinote.setVisibility(View.GONE);
        }
        if (whereFrom == 0) {
            tiaoflag = 100;
            bottomlayoutbuy.setVisibility(View.VISIBLE);
            bottomlayoutbuy.setOnClickListener(this);
            tvIsHemai.setText("发起合买人:");

        } else {
            tvIsHemai.setText("购买人:");
        }

        setBack();
        image = (Button) findViewById(R.id.xiangqing_image);
        contact = (Button) findViewById(R.id.xiangqing_contact);


//        issue = (TextView) findViewById(R.id.fangan_issue);
        initiateTime = (TextView) findViewById(R.id.fangan_initiateTime);
        schemeAmount = (TextView) findViewById(R.id.fangan_schemeAmount);
        statusDesc = (TextView) findViewById(R.id.fangan_statusDesc);
        tvschemeId = (TextView) findViewById(R.id.fangan_schemeId);
        tvschemeId.setText("订单号: " + schemeId + "");
        schemefangan = (TextView) findViewById(R.id.fangan_schemefangan);
        schemeNumberUnit = (TextView) findViewById(R.id.fangan_schemeNumberUnit);
        multipe = (TextView) findViewById(R.id.fangan_multipe);
        drawnumber = (ListView) findViewById(R.id.fangan_drawnumber);

        image.setOnClickListener(this);
        contact.setOnClickListener(this);

        baseurl = CaipiaoConst.BASE_URL + URLSuffix.SCHEME_JOIN_DETAIL;
        map = new HashMap<>();
        map.put("clientUserSession", getSession());
        map.put("schemeId", schemeId + "");

        requestandload();

    }

    private void requestandload() {
        loaddata.setVisibility(View.VISIBLE);

        OkHttpUtil.postSubmitForm(baseurl, map, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                loaddata.setVisibility(View.GONE);
                //0负 3胜 1平choose
                if (json != null) {

                    try {
                        JSONObject object = new JSONObject(json);

                        int yes = object.getInt("flag");
                        if (yes != 1) {
                            Toast.makeText(FanganorderActivity.this, object.getString("errorMessage"), Toast.LENGTH_LONG).show();
                            finish();
                            RelativeLayout failedLayout = (RelativeLayout) findViewById(R.id.failed);
                            failedLayout.setVisibility(View.VISIBLE);
                        } else {
                            XiangqingEntity xiangqingEntity = com.alibaba.fastjson.JSONObject.parseObject(json, XiangqingEntity.class);
                            detail = xiangqingEntity;
                            tvIsName.setText(detail.getUserName());

                       try {
                           tvLotteryName.setText(CaipiaoFactory.getInstance(getBaseContext()).getCaipiaoById(detail.getLotteryId()).getName());
                           ivLottery.setImageResource(CaipiaoFactory.getInstance(getBaseContext()).getCaipiaoById(detail.getLotteryId()).getIconResId());
                       }catch (Exception e){
                           e.printStackTrace();
                       }

                            if (detail.getUserId() != detail.getCurrentUserId() && (detail.getLotteryId() == CaipiaoConst.ID_BASKETBALL || detail.getLotteryId() == CaipiaoConst.ID_JINGCAIZUQIU)) {
                                findViewById(R.id.ll_follow).setVisibility(View.VISIBLE);
                            } else {
                                findViewById(R.id.ll_follow).setVisibility(View.GONE);
                            }


                            fangan_follow_me_name.setText("发起人:" + xiangqingEntity.getUserName());
                            List<SchemeDetailEntity> schemeDetail = xiangqingEntity.getSchemeDetail();
                            String issue = schemeDetail.get(0).getIssue();
//                            FanganorderActivity.this.issue.setText("第" + issue + "期");
                            initiateTime.setText(xiangqingEntity.getInitiateTime());

//                            if (null!= xiangqingEntity&&null!=xiangqingEntity.getPrizeDetail()){
//                                fangan_get_money.setText(xiangqingEntity.getPrizeDetail());
//                            }else {
//                                fangan_get_money.setText("暂无结果");
//                            }

                            schemeAmount.setText("" + xiangqingEntity.getSchemeAmount() + "元");
                            statusDesc.setText("" + xiangqingEntity.getStatusDesc());
                            schemeNumberUnit.setText("共" + xiangqingEntity.getMultiple() + " 注");
                            multipe.setText(xiangqingEntity.getSchemeNumberUnit() / xiangqingEntity.getMultiple() + "倍");
                            int open = xiangqingEntity.getOpen();
                            List<SchemeContentEntity> schemeContent = xiangqingEntity.getSchemeContent();
                            String fangan = xiangqingEntity.getNumberType();
                            List<String> list = new ArrayList<>();
                            for (int i = 0; i < schemeContent.size(); i++) {
                                String neinum = schemeContent.get(i).getNumber();
                                list.add(neinum);
                                L.d(neinum);
                            }

                            schemefangan.setText(fangan);
                            fangan_xianshinote.setVisibility(View.GONE);
                            drawnumber.setVisibility(View.VISIBLE);
                            View footview = LayoutInflater.from(FanganorderActivity.this).inflate(R.layout.activity_fanganfootview, null);

                            if (drawnumber.getFooterViewsCount() == 0)
                                drawnumber.addFooterView(footview);
                            if (open == 0) {
                                list.clear();
                                list.add("该方案跟单或截止后可见");
                            }
                            gametitle.setVisibility(View.VISIBLE);

                            if (xiangqingEntity.getLotteryName().equals("竞彩足球") || xiangqingEntity.getLotteryName().equals("竞彩篮球") || xiangqingEntity.getLotteryName().equals("北京单场")) {

                            } else {
                                item_footguestname.setVisibility(View.GONE);
                                item_result.setVisibility(View.GONE);
                                item_footchoose.setVisibility(View.GONE);
                                item_footmatchcode.setText("\t开奖号码:");

                                item_foothostname.setText(R.string.no_match_result);
                                try {
                                    item_foothostname.setText(xiangqingEntity.getSchemeDetail().get(0).getDrawNumber());
                                    L.d("11111111111");
                                } catch (Exception e) {
                                    L.d("222222222222");
                                    e.printStackTrace();
                                    item_foothostname.setText(R.string.no_match_result);
                                }
                                item_foothostname.setGravity(Gravity.LEFT);
                                item_foothostname.setTextColor(getResources().getColor(R.color.red));
                            }
                            List<SchemeJoinsEntity> schemJoins = xiangqingEntity.getSchemeJoins();
                            adapter = new FananorderAdapter(FanganorderActivity.this, schemeContent, schemJoins, tiaoflag, open, xiangqingEntity.getSchemeDetail());
                            drawnumber.setAdapter(adapter);
                            if (xiangqingEntity.getLotteryName().equals("竞彩足球")) {
                                gametitle.setVisibility(View.VISIBLE);
                                item_footguestname.setVisibility(View.VISIBLE);
                                item_result.setVisibility(View.VISIBLE);
                                item_footchoose.setVisibility(View.VISIBLE);
                                FanorderFootballEntity fanorderFootballEntity = com.alibaba.fastjson.JSONObject.parseObject(json, FanorderFootballEntity.class);

                                L.d(fanorderFootballEntity.toString()+"22222222222222");

                                List<MatchesResultEntity> matches = fanorderFootballEntity.getSchemeContent().get(0).getMatches();

                                if (open == 0) {
                                    matches.clear();
                                    MatchesResultEntity matchesEntity = new MatchesResultEntity();
                                    matchesEntity.setHostName("该方案跟单或截止后可见");
                                    matches.add(matchesEntity);
                                }
                                List<SchemeContentEntity> schemeContent1 = fanorderFootballEntity.getSchemeContent();





                                int betTypefoot = schemeContent1.get(0).getBetType();
                                schemefangan.setText(fanorderFootballEntity.getNumberType() + "");
                                if (betTypefoot == 289) {
//                                        bettype="289";"总进球复式"
                                    flag = 1;
                                } else if (betTypefoot == 426) {
                                    //bettype="426";"让球胜平负复式"
                                    flag = 2;
                                } else if (betTypefoot == 291) {
//                                        bettype="291";半全场复式
                                    flag = 4;
                                } else if (betTypefoot == 290) {
//                                         bettype="290";比分复式
                                    flag = 5;
                                } else if (betTypefoot == 424) {
                                    //   竞彩足球混合投注    bettype="424";
                                    flag = 6;
                                }
                                List<SchemeJoinsEntity> schemeJoins = xiangqingEntity.getSchemeJoins();
                                if (open == 0) {
                                    matches.clear();
                                    MatchesResultEntity matchesEntity = new MatchesResultEntity();
                                    matchesEntity.setHostName("该方案跟单或截止后可见");
                                    matches.add(matchesEntity);
                                }

                                L.d(matches.toString()+"");

                                FananFootballAdapter adapter1 = new FananFootballAdapter(FanganorderActivity.this, matches, flag, schemeJoins, tiaoflag, open);
                                   /* if (drawnumber.getHeaderViewsCount()==0){drawnumber.addHeaderView(inflate);}*/
                                drawnumber.setAdapter(adapter1);
                            }
                            if (xiangqingEntity.getLotteryName().equals("竞彩篮球")) {
                                gametitle.setVisibility(View.VISIBLE);
                                item_footguestname.setVisibility(View.VISIBLE);
                                item_result.setVisibility(View.VISIBLE);
                                item_footchoose.setVisibility(View.VISIBLE);

                                FanorderBasketball fanorderBasketball = com.alibaba.fastjson.JSONObject.parseObject(json, FanorderBasketball.class);
                                schemefangan.setText(fanorderBasketball.getNumberType() + "");
                                List<SchemeContentEntity> schemeContent2 = fanorderBasketball.getSchemeContent();
                                List<MatchesResultEntity> basketballmathes = schemeContent2.get(0).getMatches();
                                int betType = schemeContent2.get(0).getBetType();
                                if (betType == 284) {
                                    //胜负
                                    // params.put(URLSuffix.betType, 284 + "");
                                    flag = 10;
                                } else if (betType == 285) {
                                    //让分胜负
                                    //params.put(URLSuffix.betType, 285 + "");
                                    flag = 11;
                                } else if (betType == 286) {
                                    //胜负差
                                    flag = 12;
                                    // params.put(URLSuffix.betType, 286 + "");e
                                } else if (betType == 287) {
                                    //大小分
                                    flag = 13;
                                    // params.put(URLSuffix.betType, 287 + "");
                                } else if (betType == 425) {
                                    //混合投注
                                    flag = 14;
                                    //params.put(URLSuffix.betType, 425 + "");
                                }
                                List<SchemeJoinsEntity> schemeJoins = xiangqingEntity.getSchemeJoins();
                                if (open == 0) {
                                    basketballmathes.clear();
                                    MatchesResultEntity matchesEntity = new MatchesResultEntity();
                                    matchesEntity.setHostName("该方案跟单或截至后可见");
                                    basketballmathes.add(matchesEntity);
                                }
                                FananBasketball basketballadapter = new FananBasketball(FanganorderActivity.this, basketballmathes, flag, schemeJoins, tiaoflag, open);
                                drawnumber.setAdapter(basketballadapter);

                            }


                            if (xiangqingEntity.getLotteryName().equals("北京单场")) {
                                gametitle.setVisibility(View.VISIBLE);
                                item_footguestname.setVisibility(View.VISIBLE);
                                item_result.setVisibility(View.VISIBLE);
                                item_footchoose.setVisibility(View.VISIBLE);
                                BeijingdcEntity beijingdcEntity = com.alibaba.fastjson.JSONObject.parseObject(json, BeijingdcEntity.class);
                                List<SchemeContentEntity> schemeContentBJ = beijingdcEntity.getSchemeContent();
                                int betTypBJ = schemeContentBJ.get(0).getBetType();
                                List<MatchesResultEntity> BJmatches = schemeContentBJ.get(0).getMatches();
                                switch (betTypBJ) {
                                    case 274:
                                        //被单让球胜平负；
                                        flag = 30;
                                        break;
                                    case 276:
                                        //总进球
                                        flag = 31;
                                        break;
                                    case 278:
                                        //上下单双
                                        flag = 32;
                                        break;
                                    case 280:
                                        //比分
                                        flag = 33;
                                        break;
                                    case 282:
                                        //半全场
                                        flag = 34;
                                        break;
                                }
                                BeijingxqAdapter beijingxqAdapter = new BeijingxqAdapter(FanganorderActivity.this, BJmatches, flag);
                             /*       if (drawnumber.getHeaderViewsCount()==0){
                                        drawnumber.addHeaderView(inflate);
                                    }*/
                                drawnumber.setAdapter(beijingxqAdapter);
                                FanorderFootballEntity fanorderFootballEntity = com.alibaba.fastjson.JSONObject.parseObject(json, FanorderFootballEntity.class);
                                List<MatchesResultEntity> matches = fanorderFootballEntity.getSchemeContent().get(0).getMatches();
                                if (open == 0) {
                                    matches.clear();
                                    MatchesResultEntity matchesEntity = new MatchesResultEntity();
                                    matchesEntity.setHostName("该方案跟单或截止后可见");
                                    matches.add(matchesEntity);
                                }


                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                loaddata.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        //图片浏览
        switch (v.getId()) {
            case R.id.fangan_follow_me:
                FollowMeModel followMeModel = new FollowMeModel();

                if (detail != null) {
                    followMeModel.setLottery_id(String.valueOf(detail.getLotteryId()));
                    followMeModel.setUser_id(String.valueOf(detail.getUserId()));
                    followMeModel.setCur_user_id(String.valueOf(detail.getCurrentUserId()));
                    followMeModel.setUser_name(detail.getUserName());
                } else {
                    Toast.makeText(this, "暂时不能跟单", Toast.LENGTH_SHORT).show();
                    break;
                }
                followMeModel.setLottery_name(detail.getLotteryName());
                followMeModel.setObject_user_name(getUserName());

                startActivity(new Intent(FanganorderActivity.this, FollowMeActivity.class).putExtra("follow", followMeModel));
                break;
            case R.id.xiangqing_image:
                Intent intent = new Intent(FanganorderActivity.this, ScanimageActivity.class);
                intent.putExtra("lotteryName", detail.getLotteryName());
                intent.putExtra("clientUserSession", getSession());
                intent.putExtra("schemeId", schemeId);
                startActivity(intent);
                break;
            //留言
            case R.id.xiangqing_contact:
                Intent intent1 = new Intent(FanganorderActivity.this, OrderliuyanActivity.class);
                intent1.putExtra("lotteryName", detail.getLotteryName());
                intent1.putExtra("clientUserSession", getSession());
                intent1.putExtra("schemeId", schemeId);
                startActivity(intent1);
                break;
            //合买
            case R.id.bottomlayoutbuy:
                if (TextUtils.isEmpty(getSession())) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                } else {
                    String jonhemai = String.format(CaipiaoConst.JOINHEMAI, getSession(), schemeId);
                    jonhemai = jonhemai + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION + "&requestType=" + CaipiaoConst.REQUESTTYPE;
                    Intent intent2 = new Intent(FanganorderActivity.this, WebViewActivity.class);
                    intent2.putExtra("url", jonhemai);
                    startActivity(intent2);
                }
        }
    }
}
