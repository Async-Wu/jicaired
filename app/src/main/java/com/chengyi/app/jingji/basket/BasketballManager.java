package com.chengyi.app.jingji.basket;

import android.util.SparseArray;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.param.BC;
import com.chengyi.app.model.param.SPKey;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.L;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BasketballManager {

	public static final int sf = 0;
	public static final int rfsf = 1;
	public static final int sfc = 2;
	public static final int bigsmall = 3;
	public static final int mix = 4;

	public static final int danguan = 100;
	public static final int guoguan = 200;

	private int lastfragment=10;

	public int getLastfragment() {
		return lastfragment;
	}

	public void setLastfragment(int lastfragment) {
		this.lastfragment = lastfragment;
	}

	private int danorguo=0;

	public int getDanorguo() {
		return danorguo;
	}

	public void setDanorguo(int danorguo) {
		this.danorguo = danorguo;
	}

	private int currentWanfaGuan = 200;// 玩法关，玩法与过关，相加而成的数字。过关200+胜负0=200

	private SparseArray<Long> lotteryGameRequestTimeArray;// key:wanfaGuan

	public String currentIssue = "";

	public SparseArray<ArrayList<BasketballOneDay>> lotteryGameArray;// 所有单过关玩法对应的比赛列表,不能包含已结束的
	// //key:wanfaguan
	public SparseArray<SparseArray<String>> lotteryGamePositionArray;

	public SparseArray<SparseArray<BasketballOneGame>> selectedLotteryGameArray;// 所有单过关玩法对应的已选比赛列表
	// key:wanfaguan
	// subkey:orderIdLoacl

	public SparseArray<OnBasketballDataCallback> dataCallbackArray;// key:wanfaguan

	public SparseArray<ArrayList<BasketballOneDay>> historyArray;

	public BasketballManager() {
		currentWanfaGuan = CaipiaoApplication.sharedPreferences.getInt(
				SPKey.basketball_hiswanfa, 200);
		L.e("" + currentWanfaGuan);

		lotteryGameArray = new SparseArray<ArrayList<BasketballOneDay>>();
		lotteryGamePositionArray=new SparseArray<SparseArray<String>>();
		lotteryGameRequestTimeArray = new SparseArray<Long>();
		selectedLotteryGameArray = new SparseArray<SparseArray<BasketballOneGame>>();
		dataCallbackArray = new SparseArray<OnBasketballDataCallback>();
		historyArray=new SparseArray<ArrayList<BasketballOneDay>>();
	}

	public int getCurrentWanfa() {
		return getWanfa(currentWanfaGuan);
	}

	public int getCurrentWanfaGuan() {
		return currentWanfaGuan;
	}

	public String getCurrentWanfaGuanName() {
		String name = "";
		switch (currentWanfaGuan) {
			case 200:
				name = "胜负";
				break;
			case 201:
				name = "让分胜负";
				break;
			case 202:
				name = "胜分差";
				break;
			case 203:
				name = "大小分";
				break;
			case 204:
				name = "混合";
				break;
			case 100:
				name = "胜负单关";
				break;
			case 101:
				name = "让分胜负单关";
				break;
			case 102:
				name = "胜分差单关";
				break;
			case 103:
				name = "大小分单关";
				break;
			case 104:
				name = "混合单关";
				break;
		}
		return name;
	}

	public void setCurrentWanfa(int currentWanfa) {
			this.currentWanfaGuan = guoguan + currentWanfa;
	}

	/**
	 * @param wanfaGuan
	 *            玩法与过关相加而成的数字
	 */
	public int requestLotteryData(int wanfaGuan) {

/*		if (!checkIfRequestDataNecessary(wanfaGuan)) {
			return 0;
		}*/

		// 重新网络请求新数据，需要先清除已选场次的记录
		if (selectedLotteryGameArray.get(wanfaGuan) != null) {
			selectedLotteryGameArray.get(wanfaGuan).clear();
		}
		RequestParams params = getRequestParams();
		int wanfa = wanfaGuan - guoguan;
		int guan = guoguan;
		if (wanfa < 0) {
			wanfa = wanfaGuan - danguan;
			guan = danguan;
		}
		if (guan == danguan) {
			params.put(URLSuffix.basketball_guoguan_type,
					URLSuffix.basketball_danguan);
		} else if (guan == guoguan) {
			params.put(URLSuffix.basketball_guoguan_type,
					URLSuffix.basketball_guoguan);
		}

		params.put("bt", wanfa + "");
		HttpBusinessAPI.post(BC.basketball + wanfaGuan, URLSuffix.basketball,
				params, new HttpRespHandler() {
					@Override
					public void onSuccess(int bc, String response) {

						super.onSuccess(bc, response);

						BasketballResponse bbresponse = new BasketballResponse();
						ArrayList<BasketballOneDay> gamelist = bbresponse .getBasketballGameList(response);

						int wanfaGuan = bc - BC.basketball;
						boolean ifGamesExist = filterLotteryGames(wanfaGuan, gamelist);


						if (ifGamesExist) {
							lotteryGameArray.put(wanfaGuan, gamelist);
							selectedLotteryGameArray.remove(wanfaGuan);

							if (dataCallbackArray.get(wanfaGuan) != null) {

								dataCallbackArray.get(wanfaGuan).onSuccess(
										gamelist);
							}
						} else {
							if (dataCallbackArray.get(wanfaGuan) != null) {
								L.e("basketball1", "basketball1");
								dataCallbackArray.get(wanfaGuan)
										.onSuccess(null);
							}
						}
					}

					@Override
					public void onFailure(int bc, Throwable error) {

						super.onFailure(bc, error);
						int wanfaGuan = bc - BC.basketball;
						if (dataCallbackArray.get(wanfaGuan) != null) {
							dataCallbackArray.get(wanfaGuan).onFailure(null);
						}
					}
				});
		return 1;

	}

	private boolean checkIfRequestDataNecessary(int wanfaGuan) {
		long currentTimeMiles = new Date().getTime();
		if(lotteryGameArray.get(wanfaGuan)==null||lotteryGameArray.get(wanfaGuan).size()==0){
			lotteryGameRequestTimeArray.put(wanfaGuan, currentTimeMiles);
			return true;
		}
		boolean ifNecessary = true;
		if (lotteryGameRequestTimeArray.indexOfKey(wanfaGuan) >= 0) {
			long time = lotteryGameRequestTimeArray.get(wanfaGuan);

			long result = (currentTimeMiles - time) / 1000 / 60;
			if (result < 5) {
				if (dataCallbackArray.get(wanfaGuan) != null) {

					dataCallbackArray.get(wanfaGuan).onSuccess(
							lotteryGameArray.get(wanfaGuan));
					ifNecessary = false;
				}
			}

		} else {
			lotteryGameRequestTimeArray.put(wanfaGuan, currentTimeMiles);
		}
		return ifNecessary;
	}

	/**
	 * 过滤篮球彩票列表，清除已经比赛的项目；过滤单关过关的比赛项目
	 *
	 * @param gamelist
	 * @return 比赛是否存在
	 */
	private boolean filterLotteryGames(int wanfaguan,ArrayList<BasketballOneDay> gamelist) {
		int existGamesNum = 0;
		SparseArray<String> position=new SparseArray<String>();
		for (int i = 0; i < gamelist.size(); i++) {
			BasketballOneDay item = gamelist.get(i);
			ArrayList<BasketballOneGame> oneGameList = item.gameListOneDay;
			ArrayList<BasketballOneGame> oneGameListNew = new ArrayList<BasketballOneGame>();
			for (int j = 0; j < oneGameList.size(); j++) {
				BasketballOneGame oneGame = oneGameList.get(j);
				int il=0;
				if (Control.getInstance().getBasketballManager().getDanorguo()==1){
					il = wanfaguan - 100;
				}else{
					il=wanfaguan;
				}
				switch (il){
					case 200:
						if (oneGame.sfPassStatus==0){
							if (!oneGame.end) {
								oneGameListNew.add(oneGame);
								existGamesNum++;
								position.put(oneGame.orderIdLocal, i+","+j);
							}
						}
						break;
					case 201:
						if (oneGame.rfsfPassStatus==0){
							if (!oneGame.end) {
								oneGameListNew.add(oneGame);
								existGamesNum++;
								position.put(oneGame.orderIdLocal, i+","+j);
							}
						}
						break;
					case 202:
						if (oneGame.sfcPassStatus==0){
							if (!oneGame.end) {
								oneGameListNew.add(oneGame);
								existGamesNum++;
								position.put(oneGame.orderIdLocal, i+","+j);
							}
						}
						break;
					case 203:
						if (oneGame.dxfPassStatus==0){
							if (!oneGame.end) {
								oneGameListNew.add(oneGame);
								existGamesNum++;
								position.put(oneGame.orderIdLocal, i+","+j);
							}
						}
						break;
					case 204:
							if (!oneGame.end) {
								oneGameListNew.add(oneGame);
								existGamesNum++;
								position.put(oneGame.orderIdLocal, i+","+j);
							}
						break;
					case 100:
						if (oneGame.sfSingleStatus==0){
							if (!oneGame.end) {
								oneGameListNew.add(oneGame);
								existGamesNum++;
								position.put(oneGame.orderIdLocal, i+","+j);
							}
						}
						break;
					case 101:
						if (oneGame.rfsfSingleStatus==0){
							if (!oneGame.end) {
								oneGameListNew.add(oneGame);
								existGamesNum++;
								position.put(oneGame.orderIdLocal, i+","+j);
							}
						}
						break;
					case 102:
						if (oneGame.sfcSingleStatus==0){
							if (!oneGame.end) {
								oneGameListNew.add(oneGame);
								existGamesNum++;
								position.put(oneGame.orderIdLocal, i+","+j);
							}
						}
						break;
					case 103:
						if (oneGame.dxfSingleStatus==0){
							if (!oneGame.end) {
								oneGameListNew.add(oneGame);
								existGamesNum++;
								position.put(oneGame.orderIdLocal, i+","+j);
							}
						}
						break;
				}

				item.gameListOneDay = oneGameListNew;
				item.totalMatch = oneGameListNew.size();
			}
		}
		lotteryGamePositionArray.put(wanfaguan, position);

		if (existGamesNum > 0) {
			return true;
		} else {
			return false;
		}

	}


	public boolean startRandomShake(int wanfaGuan){
		ArrayList<BasketballOneDay> listOneDay=lotteryGameArray.get(wanfaGuan);
		if(listOneDay==null){
			return false;
		}
		BasketballOneGame oneGame1=null;
		BasketballOneGame oneGame2=null;
		for (int i = 0; i < listOneDay.size(); i++) {
			BasketballOneDay item = listOneDay.get(i);
			ArrayList<BasketballOneGame> oneGameList = item.gameListOneDay;

			if(wanfaGuan-guoguan==sf){
				for (int j = 0; j < oneGameList.size(); j++) {
					BasketballOneGame newItem = oneGameList.get(j);
					if(oneGame1==null){
						oneGame1=newItem;
						continue;
					}
					if(oneGame2==null){
						if(oneGame1.shenfuMinSp[0]>newItem.shenfuMinSp[0]){
							oneGame2=oneGame1;
							oneGame1=newItem;
						}else{
							oneGame2=newItem;
						}

						continue;
					}

					double sp=newItem.shenfuMinSp[0];
					if(oneGame1.shenfuMinSp[0]>sp){
						oneGame2=oneGame1;
						oneGame1=newItem;

					}else if(oneGame2.shenfuMinSp[0]>sp){
						oneGame2=newItem;

					}
				}
			}else if(wanfaGuan-guoguan==rfsf){
				for (int j = 0; j < oneGameList.size(); j++) {
					BasketballOneGame newItem = oneGameList.get(j);
					if(oneGame1==null){
						oneGame1=newItem;
						continue;
					}
					if(oneGame2==null){
						if(oneGame1.rfshenfuMinSp[0]>newItem.rfshenfuMinSp[0]){
							oneGame2=oneGame1;
							oneGame1=newItem;
						}else{
							oneGame2=newItem;
						}

						continue;
					}

					double sp=newItem.rfshenfuMinSp[0];
					if(oneGame1.rfshenfuMinSp[0]>sp){
						oneGame2=oneGame1;
						oneGame1=newItem;

					}else if(oneGame2.rfshenfuMinSp[0]>sp){
						oneGame2=newItem;

					}
				}
			}
		}

		if(oneGame1==null||oneGame2==null){
			return false;
		}else{
			clearSelected(wanfaGuan);
			SparseArray<BasketballOneGame> selectedGames = new SparseArray<BasketballOneGame>();
			if(wanfaGuan-guoguan==sf){
				oneGame1.isSFSelected[(int)oneGame1.shenfuMinSp[1]]=true;
				oneGame1.SFFlag++;
				oneGame2.isSFSelected[(int)oneGame2.shenfuMinSp[1]]=true;
				oneGame2.SFFlag++;
			}else if(wanfaGuan-guoguan==rfsf){
				oneGame1.isSFSelected[(int)oneGame1.rfshenfuMinSp[1]]=true;
				oneGame2.isSFSelected[(int)oneGame2.rfshenfuMinSp[1]]=true;
				oneGame1.SFFlag++;
				oneGame2.SFFlag++;
			}
			selectedGames.put(oneGame1.orderIdLocal, oneGame1);
			selectedGames.put(oneGame2.orderIdLocal, oneGame2);
			selectedLotteryGameArray.put(wanfaGuan, selectedGames);
			return true;
		}

	}


	public void clearSelected(int wanfaGuan){
		SparseArray<BasketballOneGame> array=selectedLotteryGameArray.get(wanfaGuan);
		if(array!=null){
			int size=array.size();
			for(int i=0;i<size;i++){
//				array.valueAt(i).isSFSelected=new boolean[2];
				array.valueAt(i).reset();
			}
		}
		array.clear();
	}

	// public int getCurrentGuoguan() {
	// return currentGuoguan;
	// }
	//
	//
	//
	// public void setCurrentGuoguan(int currentGuoguan) {
	// this.currentGuoguan = currentGuoguan;
	// }

	public void onDestory() {
		CaipiaoApplication.sharedPreferences.edit()
				.putInt(SPKey.basketball_hiswanfa, currentWanfaGuan).commit();
		L.e(""+currentWanfaGuan);
		lotteryGameArray.clear();
		lotteryGameArray=null;
		lotteryGamePositionArray.clear();
		lotteryGamePositionArray=null;
		lotteryGameRequestTimeArray.clear();
		lotteryGameRequestTimeArray=null;
		selectedLotteryGameArray.clear();
		selectedLotteryGameArray=null;
		dataCallbackArray.clear();
		dataCallbackArray=null;
		historyArray.clear();
		historyArray=null;
	}

	// +++++++++++++++++++++++++++++++++++++++++TOOL

	public int getWanfa(int wanfaGuan) {
		int wanfa = 0;
		wanfa = wanfaGuan - guoguan;
		if (wanfa < 0) {
			wanfa = wanfaGuan - danguan;

		}
		return wanfa;
	}

	public int getGuan(int wanfaGuan) {
		int wanfa = wanfaGuan - guoguan;
		int guan = guoguan;
		if (wanfa < 0) {
			wanfa = wanfaGuan - danguan;
			guan = danguan;
		}
		return guan;
	}

	public RequestParams getRequestParams() {
	return new RequestParams();


	}

	public interface OnBasketballDataCallback {
		public void onSuccess(ArrayList<BasketballOneDay> list);

		public void onFailure(String info);
	}


	public interface OnBasketballHistoryDataCallback {
		public void onSuccess(ArrayList<BasketballOneDay> list);

		public void onFailure(String info);
	}
}
