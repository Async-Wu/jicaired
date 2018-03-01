package com.chengyi.app.jingji.football;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.util.L;

import java.util.ArrayList;
import java.util.List;

public class MixSubActivity extends BaseActivity {
    private Button cancel, yesbtn;
    private TextView zhudui, kedui;

    private  TextView title011;
    private ArrayList<String> selectStatus = new ArrayList<>();

    private  boolean isClick=true;

    private String[][] biffArr = new String[][]{
            {
                    "1:0", "2:0", "2:1", "3:0", "3:1", "3:2", "4:0"
            }, {
            "4:1", "4:2", "5:0", "5:1", "5:2", "胜其他"
    }, {
            "0:0", "1:1", "2:2", "3:3", "平其他"
    },
            {

                    "0:1", "0:2", "1:2", "0:3", "1:3", "2:3", "0:4"

            }, {
            "1:4", "2:4", "0:5", "1:5", "2:5", "负其他"
    }
    };

    private String[][] zjqArr = {{
            "0球", "1球", "2球", "3球"
    },
            {
                    "4球", "5球", "6球", "7+球"
            }
    };


    private String[][] bqcArr = {{

            "胜胜", "胜平", "胜负",


    }
            ,
            {

                    "平胜", "平平", "平负",


            }
            ,
            {

                    "负胜", "负平", "负负",


            }
            ,

    };


    private LinearLayout llBiff, llZjq, llBqc, llSpf;


    private JingcaizuqiuOneGame gameData;
    private int wfindex;
    private String location;
    ArrayList<Integer> btnSelectedList;

    private View lineV, line;
    private List<View> views = new ArrayList<>();
    private List<View> vs = new ArrayList<>();

    private String[][] zjqNumArr = new String[2][];
    private String[][] bqcNumArr = new String[3][];
    private String[][] bfNumArr = new String[5][];

    private String[][] spfArr = new String[2][];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_sub);
        gameData = (JingcaizuqiuOneGame) getIntent().getSerializableExtra("gameData");
        line = getLayoutInflater().inflate(R.layout.item_line, null, false);
        lineV = getLayoutInflater().inflate(R.layout.item_line_v, null, false);

        isClick=getIntent().getBooleanExtra("isClick",true);
        zhudui = (TextView) findViewById(R.id.zhudui);
        kedui = (TextView) findViewById(R.id.kedui);
        zhudui.setText(gameData.getTeam1());
        kedui.setText(gameData.getTeam2());
        title011= (TextView) findViewById(R.id.title011);
        if (gameData.getRangNumber()>0){
            title011.setText("+"+gameData.getRangNumber()+"");
        }else {
            title011.setText(gameData.getRangNumber()+"");
        }


        location = getIntent().getStringExtra("location");
        views.clear();
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llBiff = (LinearLayout) findViewById(R.id.layout1);
        llZjq = (LinearLayout) findViewById(R.id.layout2);
        llBqc = (LinearLayout) findViewById(R.id.layout3);
        llSpf = (LinearLayout) findViewById(R.id.layout0);
        btnSelectedList = new ArrayList<>();
        zjqNumArr[0] = new String[]{gameData.getZjqpeilv()[0] + "", gameData.getZjqpeilv()[1] + "", gameData.getZjqpeilv()[2] + "", gameData.getZjqpeilv()[3] + ""};
        zjqNumArr[1] = new String[]{gameData.getZjqpeilv()[4] + "", gameData.getZjqpeilv()[5] + "", gameData.getZjqpeilv()[6] + "", gameData.getZjqpeilv()[7] + ""};


        bqcNumArr[0] = new String[]{gameData.getBqcList().get(0), gameData.getBqcList().get(1), gameData.getBqcList().get(2)};
        bqcNumArr[1] = new String[]{gameData.getBqcList().get(3), gameData.getBqcList().get(4), gameData.getBqcList().get(5)};
        bqcNumArr[2] = new String[]{gameData.getBqcList().get(6), gameData.getBqcList().get(7), gameData.getBqcList().get(8)};

        bfNumArr[0] = new String[]{gameData.getBqcList().get(9), gameData.getBqcList().get(10), gameData.getBqcList().get(11), gameData.getBqcList().get(12), gameData.getBqcList().get(13), gameData.getBqcList().get(14), gameData.getBqcList().get(15)};
        bfNumArr[1] = new String[]{gameData.getBqcList().get(16), gameData.getBqcList().get(17), gameData.getBqcList().get(18), gameData.getBqcList().get(19), gameData.getBqcList().get(20), gameData.getBqcList().get(21)};
        bfNumArr[2] = new String[]{gameData.getBqcList().get(22), gameData.getBqcList().get(23), gameData.getBqcList().get(24), gameData.getBqcList().get(25), gameData.getBqcList().get(26)};
        bfNumArr[3] = new String[]{gameData.getBqcList().get(27), gameData.getBqcList().get(28), gameData.getBqcList().get(29), gameData.getBqcList().get(30), gameData.getBqcList().get(31), gameData.getBqcList().get(32), gameData.getBqcList().get(33)};
        bfNumArr[4] = new String[]{gameData.getBqcList().get(34), gameData.getBqcList().get(35), gameData.getBqcList().get(36), gameData.getBqcList().get(37), gameData.getBqcList().get(38), gameData.getBqcList().get(39)};


        cancel = (Button) findViewById(R.id.cancel);
        yesbtn = (Button) findViewById(R.id.yesbtn);

        cancel.setOnClickListener(this);
        yesbtn.setOnClickListener(this);


        spfArr[0] = new String[]{"主胜" + gameData.getSpfpeilv()[0], "平" + gameData.getSpfpeilv()[1], "客胜" + gameData.getSpfpeilv()[2]};
        spfArr[1] = new String[]{"主胜" + gameData.getRqSpfPeilv()[0], "平" + gameData.getRqSpfPeilv()[1], "客胜" + gameData.getRqSpfPeilv()[2]};


        addView2LL(llBiff, new int[]{7, 6, 5, 7, 6}, biffArr, bfNumArr, true);
        addView2LL(llZjq, new int[]{4, 4}, zjqArr, zjqNumArr, true);

        addView2LL(llBqc, new int[]{3, 3,3}, bqcArr, bqcNumArr, true);

        addView2LL4Line(llSpf, spfArr);


        resetSelect();


    }

    private void addView2LL4Line(LinearLayout ll, String[][] arr) {
        ll.removeAllViews();
        for (int i = 0; i < arr.length; i++) {

            LinearLayout linearLayout = new LinearLayout(this);
            for (int j = 0; j < arr[i].length; j++) {

                final View view = getLayoutInflater().inflate(R.layout.item_ver_mix, null, false);

                final TextView numtext = (TextView) view.findViewById(R.id.numtext);




                TextView peilv = (TextView) view.findViewById(R.id.peilv);
                try {
                    if (Double.parseDouble(arr[i][j].replaceAll("主胜","").replaceAll("平","").replaceAll("客胜",""))==0){
                        numtext.setText("-");
                    }else {
                        numtext.setText(arr[i][j]);
                    }

                }catch (Exception e){
                    numtext.setText(arr[i][j]);
                }

                numtext.setText(arr[i][j]);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isClick||!numtext.getText().toString().trim().equals("-"))
                        view.setSelected(!view.isSelected());
                    }
                });

                vs.add(view);

                ViewGroup.LayoutParams tempP = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                linearLayout.addView(view, tempP);
            }


            ll.addView(linearLayout, layoutParams);
        }


    }

    private void resetSelect() {

        for (int j = 0; j < views.size(); j++) {
            if (gameData.getSelects() != null && gameData.getSelects().size() == views.size())
                views.get(j).setSelected(gameData.getSelects().get(j));

        }

        for (int i = 0; i < vs.size(); i++) {
            if (gameData.isSelected != null && gameData.isSelected.length > 0) {
                vs.get(i).setSelected(gameData.isSelected[i]);

            }
        }


    }


    /**
     * @param ll
     * @param col 列
     */
    LinearLayout.LayoutParams layoutParams;

    //
    private void addView2LL(LinearLayout ll, int[] col, String[][] title, String[][] num, boolean flag) {
        ll.removeAllViews();
        for (int i = 0; i < col.length; i++) {

            LinearLayout linearLayout = new LinearLayout(this);
            for (int j = 0; j < col[i]; j++) {



                final View tempView = getLayoutInflater().inflate(R.layout.item_mix, null, false);

                TextView numtext = (TextView) tempView.findViewById(R.id.numtext);
                TextView peilv = (TextView) tempView.findViewById(R.id.peilv);
                numtext.setText(title[i][j]);
                tempView.setTag(title[i][j]);
                peilv.setText("" + num[i][j]);
                final LinearLayout.LayoutParams tempP;
                if (getMax(col) > col[i] && j == col[i] - 1) {
                    tempP = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, getMax(col) - col[i] + 1);

                } else {
                    tempP = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                }
                tempView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isClick)
                        tempView.setSelected(!tempView.isSelected());

                    }
                });
                if (flag)
                    views.add(tempView);
                else
                    vs.add(tempView);

                linearLayout.addView(tempView, tempP);

            }

            ll.addView(linearLayout, layoutParams);
        }


    }


    private int getMax(int[] m) {
        int max = 0;
        for (int i = 0; i < m.length; i++) {
            if (max <= m[i]) {
                max = m[i];
            }

        }

        return max;
    }

    private List<Boolean> selects = new ArrayList<>();

    private String getSelect() {
        String content = "";
        selects.clear();
        for (int i = 0; i < views.size(); i++) {

            selects.add(views.get(i).isSelected());


            if (views.get(i).isSelected())

                if (i <= 30) {
                    content += "2" + views.get(i).getTag().toString().replaceAll(":", "").replace("胜其他", "90").replace("平其他", "99").replace("负其他", "09") + ",";
                } else if (i > 30 && i <= 38) {
                    content += "10" + views.get(i).getTag().toString().replace("+", "").replace("球", "") + ",";
                } else if (i > 38) {
                    content += "3" + views.get(i).getTag().toString().replace("胜", "3").replace("平", "1").replace("负", "0") + ",";
                }


        }

        if (content.endsWith(",")) {
            content = content.substring(0, content.length()-1);
        }
        L.d("" + content);
        return content;


    }


    private void getSpfSelect() {

        for (int i = 0; i < vs.size(); i++) {
            gameData.isSelected[i] = vs.get(i).isSelected();
        }


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.yesbtn:
                Intent in = new Intent();
                String content = getSelect();
                gameData.setSelects(selects);
                L.d(content);
                getSpfSelect();
                in.putExtra("gameData", gameData);
                in.putExtra("content", content);
                in.putExtra("location", location);
                setResult(RESULT_OK, in);
                finish();

                break;
        }
    }
}
