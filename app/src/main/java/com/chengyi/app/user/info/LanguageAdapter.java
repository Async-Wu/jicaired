package com.chengyi.app.user.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BasicAdapter;

import java.util.List;
import java.util.Locale;

/**
 * Created by lishangfan on 2016/12/5.
 */
public class LanguageAdapter extends BasicAdapter<LanguageMode> {
    public LanguageAdapter(List<LanguageMode> mMode, Context mCtx) {
        super(mMode, mCtx);
    }

    @Override
    protected View createView(final int position, View convertView, ViewGroup parent, LayoutInflater inflater) {
        Holde h;
        if (convertView == null) {
            h = new Holde();
            convertView = inflater.inflate(R.layout.item_select_l, parent, false);
            h.cbSelect = (CheckBox) convertView.findViewById(R.id.cb_select);
            h.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(h);
        } else {
            h = (Holde) convertView.getTag();
        }
        h.name.setText(mMode.get(position).getLaungename());
        h.cbSelect.setChecked(mMode.get(position).isSelect());

        if (!TextUtils.isEmpty(CaipiaoApplication.sharedPreferences.getString("local", ""))) {
            if (mMode.get(position).getLanguage().equals(CaipiaoApplication.sharedPreferences.getString("local", ""))) {
                h.cbSelect.setChecked(true);
            } else {
                h.cbSelect.setChecked(false);
            }
        }else {
            if (mMode.get(position).getLanguage().equals(Locale.getDefault().getLanguage())) {
                h.cbSelect.setChecked(true);
            } else {
                h.cbSelect.setChecked(false);
            }
        }




        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCtx instanceof ISetting) {
                    SharedPreferences.Editor  editor=CaipiaoApplication.sharedPreferences.edit();

                    editor.putString("local",mMode.get(position).getLanguage());

                    editor.commit();

                    ((ISetting) mCtx).seting();
                    notifyDataSetChanged();


                }
            }
        });

        return convertView;
    }

    class Holde {
        CheckBox cbSelect;
        TextView name;

    }

    public interface ISetting {
        void seting();
    }

}
