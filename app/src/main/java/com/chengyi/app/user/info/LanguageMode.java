package com.chengyi.app.user.info;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

import java.util.Locale;

/**
 * Created by lishangfan on 2016/12/5.
 */
public class LanguageMode extends BaseMode {

    private String laungename;
    private boolean select;
    private String Language;

    public String getLanguage() {
        if (getLaungename().equals("中文")) {
            return "zh";
        } else if (getLaungename().equals("English")) {
            return "en";
        }
        else if (getLaungename().equals("柬埔寨")) {
            return "km";
        } else {
            return Locale.getDefault().getLanguage();
        }


    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getLaungename() {
        return laungename;
    }

    public void setLaungename(String laungename) {
        this.laungename = laungename;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.laungename);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeString(this.Language);
    }

    public LanguageMode() {
    }

    protected LanguageMode(Parcel in) {
        this.laungename = in.readString();
        this.select = in.readByte() != 0;
        this.Language = in.readString();
    }

    public static final Creator<LanguageMode> CREATOR = new Creator<LanguageMode>() {
        @Override
        public LanguageMode createFromParcel(Parcel source) {
            return new LanguageMode(source);
        }

        @Override
        public LanguageMode[] newArray(int size) {
            return new LanguageMode[size];
        }
    };
}
