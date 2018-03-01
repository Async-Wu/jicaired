package com.chengyi.app.jingji.fourgoal;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;

import java.util.List;

/**
 * Created by xiaqi on 2016/9/12.
 */
public class FourgoalAdapter extends BaseAdapter {
    private Handler handler;
    private Context context;
    private int xiaflag = 0;
    private List<FourgoalEntity.DataEntity> list;
    private boolean flag;

    public FourgoalAdapter(Context context, List<FourgoalEntity.DataEntity> list) {
        this(context, list, false);
    }

    public FourgoalAdapter(Context context, List<FourgoalEntity.DataEntity> list, boolean flag) {
        this.context = context;
        this.list = list;
        this.flag = flag;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setModle(int xiaflag) {
        this.xiaflag = xiaflag;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FourgoalViewholder viewholder = null;
        if (convertView == null) {
            viewholder = new FourgoalViewholder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fourball, parent, false);
            viewholder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            viewholder.legeaug = (TextView) convertView.findViewById(R.id.legeaug);
            viewholder.game1 = (TextView) convertView.findViewById(R.id.game1);
            viewholder.team1 = (TextView) convertView.findViewById(R.id.team1);
            viewholder.fourball_time = (TextView) convertView.findViewById(R.id.fourball_time);
            viewholder.game2 = (TextView) convertView.findViewById(R.id.game2);
            viewholder.team2 = (TextView) convertView.findViewById(R.id.team2);
            viewholder.fourball1_zero = (TextView) convertView.findViewById(R.id.fourball1_zero);
            viewholder.fourball1_one = (TextView) convertView.findViewById(R.id.fourball1_one);
            viewholder.fourball1_two = (TextView) convertView.findViewById(R.id.fourball1_two);
            viewholder.fourball1_three = (TextView) convertView.findViewById(R.id.fourball1_three);
            viewholder.fourball2_zero = (TextView) convertView.findViewById(R.id.fourball2_zero);
            viewholder.fourbal2_one = (TextView) convertView.findViewById(R.id.fourbal2_one);
            viewholder.fourball2_two = (TextView) convertView.findViewById(R.id.fourball2_two);
            viewholder.fourball2_three = (TextView) convertView.findViewById(R.id.fourball2_three);
            viewholder.v = convertView.findViewById(R.id.v);
            convertView.setTag(viewholder);
        } else {
            viewholder = (FourgoalViewholder) convertView.getTag();
        }
        final FourgoalEntity.DataEntity dataEntity = list.get(position);
        viewholder.fourball_time.setText(dataEntity.getMatchTime());
        int order = 2 * dataEntity.getOrder();
        viewholder.legeaug.setText(dataEntity.getName());
        viewholder.game1.setText(order + 1 + "");
        viewholder.game2.setText(order + 2 + "");
        viewholder.team1.setText(dataEntity.getHostName());
        viewholder.team2.setText(dataEntity.getGuestName());
        if (dataEntity.Hoststatusnew[0] == 1) {
            viewholder.fourball1_zero.setSelected(true);
        } else {
            viewholder.fourball1_zero.setSelected(false);
        }
        if (dataEntity.Hoststatusnew[1] == 1) {
            viewholder.fourball1_one.setSelected(true);
        } else {
            viewholder.fourball1_one.setSelected(false);
        }
        if (dataEntity.Hoststatusnew[2] == 1) {
            viewholder.fourball1_two.setSelected(true);
        } else {
            viewholder.fourball1_two.setSelected(false);
        }
        if (dataEntity.Hoststatusnew[3] == 1) {
            viewholder.fourball1_three.setSelected(true);
        } else {
            viewholder.fourball1_three.setSelected(false);
        }
        if (dataEntity.Guestatusnew[0] == 1) {
            viewholder.fourball2_zero.setSelected(true);
        } else {
            viewholder.fourball2_zero.setSelected(false);
        }
        if (dataEntity.Guestatusnew[1] == 1) {
            viewholder.fourbal2_one.setSelected(true);
        } else {
            viewholder.fourbal2_one.setSelected(false);
        }
        if (dataEntity.Guestatusnew[2] == 1) {
            viewholder.fourball2_two.setSelected(true);
        } else {
            viewholder.fourball2_two.setSelected(false);
        }
        if (dataEntity.Guestatusnew[3] == 1) {
            viewholder.fourball2_three.setSelected(true);
        } else {
            viewholder.fourball2_three.setSelected(false);
        }
        if (xiaflag != 1) {
            setListner(viewholder, dataEntity);
        }

        if (flag) {
            viewholder.ll.setVisibility(View.GONE);
            viewholder.v.setVisibility(View.INVISIBLE);
        } else {
            viewholder.ll.setVisibility(View.VISIBLE);
            viewholder.v.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private void setListner(FourgoalViewholder viewholder, final FourgoalEntity.DataEntity dataEntity) {
        viewholder.fourball1_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView a = (TextView) v;
                if (dataEntity.Hoststatusnew[0] == 1) {
                    a.setSelected(false);
                    dataEntity.Hoststatusnew[0] = 0;
                } else {
                    a.setSelected(true);
                    dataEntity.Hoststatusnew[0] = 1;
                }
                handler.sendEmptyMessage(0);

            }
        });
        viewholder.fourball1_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView b = (TextView) v;
                if (dataEntity.Hoststatusnew[1] == 1) {
                    b.setSelected(false);
                    dataEntity.Hoststatusnew[1] = 0;
                } else {
                    b.setSelected(true);
                    dataEntity.Hoststatusnew[1] = 1;
                }
                handler.sendEmptyMessage(0);

            }
        });
        viewholder.fourball1_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView c = (TextView) v;
                if (dataEntity.Hoststatusnew[2] == 1) {
                    c.setSelected(false);
                    dataEntity.Hoststatusnew[2] = 0;
                } else {
                    c.setSelected(true);
                    dataEntity.Hoststatusnew[2] = 1;
                }
                handler.sendEmptyMessage(0);
            }
        });
        viewholder.fourball1_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView d = (TextView) v;
                if (dataEntity.Hoststatusnew[3] == 1) {
                    d.setSelected(false);
                    dataEntity.Hoststatusnew[3] = 0;
                } else {
                    d.setSelected(true);
                    dataEntity.Hoststatusnew[3] = 1;
                }
                handler.sendEmptyMessage(0);
            }
        });
        viewholder.fourball2_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView e = (TextView) v;
                if (dataEntity.Guestatusnew[0] == 1) {
                    e.setSelected(false);
                    dataEntity.Guestatusnew[0] = 0;
                } else {
                    e.setSelected(true);
                    dataEntity.Guestatusnew[0] = 1;
                }
                handler.sendEmptyMessage(0);
            }
        });
        viewholder.fourbal2_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView f = (TextView) v;
                if (dataEntity.Guestatusnew[1] == 1) {
                    f.setSelected(false);
                    dataEntity.Guestatusnew[1] = 0;
                } else {
                    f.setSelected(true);
                    dataEntity.Guestatusnew[1] = 1;
                }
                handler.sendEmptyMessage(0);
            }
        });
        viewholder.fourball2_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView g = (TextView) v;
                if (dataEntity.Guestatusnew[2] == 1) {
                    g.setSelected(false);
                    dataEntity.Guestatusnew[2] = 0;
                } else {
                    g.setSelected(true);
                    dataEntity.Guestatusnew[2] = 1;
                }
                handler.sendEmptyMessage(0);
            }
        });
        viewholder.fourball2_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView h = (TextView) v;
                if (dataEntity.Guestatusnew[3] == 1) {
                    h.setSelected(false);
                    dataEntity.Guestatusnew[3] = 0;
                } else {
                    h.setSelected(true);
                    dataEntity.Guestatusnew[3] = 1;
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

    class FourgoalViewholder {
        private TextView legeaug, game1, team1, fourball_time, game2, team2, fourball1_zero, fourball1_one, fourball1_two, fourball1_three,
                fourball2_zero, fourbal2_one, fourball2_two, fourball2_three;
        private LinearLayout ll;
        View v;
    }
}
