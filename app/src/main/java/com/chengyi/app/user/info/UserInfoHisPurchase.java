package com.chengyi.app.user.info;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  UserInfoHisPurchase extends BaseMode  {
	
		public long initiateTime;
		public String issue;
		public int lotteryId;
		public String lotteryName;
		public double prize;
		public double prizeSubjoin;
		public double schemeAmount;
		public int schemeId;
		public int schemeNumberType;
		public int sort;
		public int status;
		public String statusDesc;
		public int userId;

		public UserInfoHisPurchase(){
			initiateTime=0;
			issue="";
			lotteryId=0;
			lotteryName="";
			prize=0.00;
			prizeSubjoin=0.00;
			schemeAmount=0.00;
			schemeId=0;
			schemeNumberType=0;
			sort=0;
			status=0;
			statusDesc="";
			userId=0;

		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.initiateTime);
		dest.writeString(this.issue);
		dest.writeInt(this.lotteryId);
		dest.writeString(this.lotteryName);
		dest.writeDouble(this.prize);
		dest.writeDouble(this.prizeSubjoin);
		dest.writeDouble(this.schemeAmount);
		dest.writeInt(this.schemeId);
		dest.writeInt(this.schemeNumberType);
		dest.writeInt(this.sort);
		dest.writeInt(this.status);
		dest.writeString(this.statusDesc);
		dest.writeInt(this.userId);
	}

	protected UserInfoHisPurchase(Parcel in) {
		this.initiateTime = in.readLong();
		this.issue = in.readString();
		this.lotteryId = in.readInt();
		this.lotteryName = in.readString();
		this.prize = in.readDouble();
		this.prizeSubjoin = in.readDouble();
		this.schemeAmount = in.readDouble();
		this.schemeId = in.readInt();
		this.schemeNumberType = in.readInt();
		this.sort = in.readInt();
		this.status = in.readInt();
		this.statusDesc = in.readString();
		this.userId = in.readInt();
	}

	public static final Creator<UserInfoHisPurchase> CREATOR = new Creator<UserInfoHisPurchase>() {
		@Override
		public UserInfoHisPurchase createFromParcel(Parcel source) {
			return new UserInfoHisPurchase(source);
		}

		@Override
		public UserInfoHisPurchase[] newArray(int size) {
			return new UserInfoHisPurchase[size];
		}
	};
}
