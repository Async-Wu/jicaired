package com.chengyi.app.model.param;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public interface URLSuffix {
	String requestType = "requestType";
	// 操作成功标示,0:失败；1：成功
	String flag = "flag";
	// 用户登录session,登录成功时不可空
	String clientUserSession = "clientUserSession";
	// 操作失败时才有的提示信息
	String errorMessage = "errorMessage";

	/** 登录 */
//	String LOGIN = "user/login";
	String LOGIN = "/user/login.htm";
	/*String LOGIN = "http://IOCuser/login.htm";*/

	String username = "username";
	String password = "password";
	
	/** 查询余额 */
	String QUERY_BALANC = "user/query_balance.htm";
	String balance = "balance";// 用户可用余额double,不可空
	String score = "score";// 用户积分 int ,不可空

	/** 提交购彩 */
	String lotteryId = "lotteryId";// 彩票ID int N
	String issue = "issue";// 奖期 String 多个奖期用,符号隔开 N
	String issueId = "issueId";
	String issueCount = "issueCount";// 购买奖期数，N
	String multiple = "multiple";// 倍数 String 与奖期对应，多个奖期是由每个奖期的整数倍数用，符号隔开 N
	String schemeNumber = "schemeNumber";// 方案号码 N

	String schemeAmount = "schemeAmount";// 方案金额 int N
	String buyAmount = "buyAmount";// 购买金额 int N
	String buyType = "buyType";// 方案购买类型 int 0：合买；1：代购 N
	String additional = "additional";// 追加投注 int 0：不追加；1：追加。（大乐透才有）,Y
	String safeguardMoney = "safeguardMoney";// 保底金额 int 合买方案设置发起人设置的保底 Y

	String minParticipant = "minParticipant";// 合买参与最少金额 int 合买方案不可空
	String remuneration = "remuneration";// 合买发起方案佣金比例 int 佣金比例值范围：0-10 Y
	String open = "open";// 合买方案公开情况 1：公开；2：保密；3：参与后公开；4：截止后公开 int 合买方案不可空
	String burstIntoStop = "burstIntoStop";// 开出停止追号 int
											// 0：不限；其他：指定的中奖金额，大于等于此金额停止追号。
											// 快开类型彩种才有此设置 Y
	String prizeStop = "prizeStop";// 中奖停止追号
	String schemeDesc = "schemeDesc";// 方案描述 String Y
	String isHemai="isHemai";//activity跳转判断所用
	String describe="describe";
	String safeguardTimes="safeguardTimes";
	String participantTimes="participantTimes";
	String matchCount="matchCount";//比赛场次
	String pass="pass";//过关方式
	String matches="matches";
	String hostName="hostName";//主队
	String guestName="guestName";//客队
	String canRemove="canRemove";
	String sheng="sheng";
	String ping="ping";
	String fu="fu";
	String rate="rate";
	String bingo="bingo";
	String dan="dan";
	String choose="choose";
	String betType="betType";
	String t0="t0";
	String t1="t1";
	String t2="t2";
	String t3="t3";
	String t4="t4";
	String t5="t5";
	String t6="t6";
	String t7="t7";
	String cutRepeat="cutRepeat";
	String sels="sels";
	String totalPrize="totalPrize";
	
	// 响应:
	String schemeId = "schemeId";// 方案ID
	String single = "single";// 单式
	String poly = "poly";// 复式
	String groupSingle = "groupSingle";// 组选单式
	String group3Poly = "group3Poly";// 组3复式
	String group6Poly = "group6Poly";// 组6复式
	String dxds = "dxds";// 大小单双
	String fiveStarAllSingle = "fiveStarAllSingle";// 五星通选单式
	String fiveStarAllPoly = "fiveStarAllPoly";// 五星通选复式
	String fiveStarSingle = "fiveStarSingle";// 五星单式
	String fiveStarPoly = "fiveStarPoly";// 五星复式
	String fourStarSingle = "fourStarSingle";// 四星单式
	String fourStarPoly = "fourStarPoly";// 四星复式
	String threeStarDirectSingle = "threeStarDirectSingle";// 三星直选单式
	String threeStarDirectPoly = "threeStarDirectPoly";// 三星直选复式
	String threeStarPoly="threeStarPoly";// 三星直选复式
	String threeStarGroupSingle = "threeStarGroupSingle";// 三星组选单式
	String threeStarGroup3Poly = "threeStarGroup3Poly";// 三星组3复式
	String threeStarGroup6Poly = "threeStarGroup6Poly";// 三星组6复式
	String twoStarDirectSingle = "twoStarDirectSingle";// 二星直选单式
	String twoStarDirectPoly = "twoStarDirectPoly";// 二星直选复式
	String twoStarGroupSingle = "twoStarGroupSingle";// 二星组选单式
	String twoStarGroupPoly = "twoStarGroupPoly";// 二星组选复式
	String renOne = "renOne";// 任选1
	String renTwo = "renTwo";// 任选2
	String direct = "direct";// 单选定位
	String wzSingle="wzSingle";  //位置单式
	String wzPoly="wzPoly";   //位置复式
	String oneStar = "oneStarPoly";// 一星
	String oneRedSingle = "oneRedSingle";// 前一红投
	String oneSingle = "oneSingle";// 任选前一单式
	String onePoly = "onePoly";// 任选前一复式
	String oneDirectSingle = "oneDirectSingle";// 任选前一单式
	String oneDirectPoly = "oneDirectPoly";// 任选前一复式
	String twoSingle = "twoSingle";// 任选二单式
	String twoPoly = "twoPoly";// 任选二复式
	String twoDirectSingle = "twoDirectSingle";// 前二直选单式
	String twoDirectPoly = "twoDirectPoly";// 前二直选复式
	String twoDirect = "twoDirect";// 前二直选定位
    String sumSingle="sumSingle";
    String sumPoly="sumPoly";
    String twoSame="twoSame";
    String threeSameAlone="threeSameAlone";
    String threeSameAll="threeSameAll";
    String twoSameAlonePoly="twoSameAlonePoly";
	String twoGroupSingle = "twoGroupSingle";// 前二组选单式
	String twoDifferentPoly="twoDifferentPoly";//二不同号复式
	String twoDifferentSingle="twoDifferentSingle";//二不同号单式
	String twoGroupPoly = "twoGroupPoly";// 前二组选复式
	String threeSingle = "threeSingle";// 任选三单式
	String threePoly = "threePoly";// 任选三复式
	String threeDirectSingle="threeDirectSingle";//前三直选单式
	String threeDifferentSingle="threeDifferentSingle";   //三不同号单式
	String threeDifferentPoly="threeDifferentPoly";   //三不同号复式
	// String threeDirectPoly="threeDirectPoly";//前三直选复式
	String threeDirect = "threeDirect";// 前三直选定位
	String threeGroupSingle = "threeGroupSingle";// 前三组选单式
	String threeGroupPoly = "threeGroupPoly";// 前三组选复式
	String fourSingle = "fourSingle";// 任选四单式
	String fourPoly = "fourPoly";// 任选四复式
	String fiveSingle = "fiveSingle";// 任选五单式
	String fivePoly = "fivePoly";// 任选五复式
	String sixSingle = "sixSingle";// 任选六单式
	String sixPoly = "sixPoly";// 任选六复式
	String sixDraw = "sixDraw";// 任选六胆拖
	String sevenDraw="sevenDraw";
	String eightDraw="eightDraw";
	String sevenSingle = "sevenSingle";// 任选七单式
	String sevenPoly = "sevenPoly";// 任选七复式
	String eightSingle = "eightSingle";// 任选八单式
	String eightPoly = "eightPoly";// 任选八复式

	String lottoSingle = "lottoSingle";// 大乐透单式
	String lottoPoly = "lottoPoly";// 大乐透复式

	String pl5Single = "pl5Single";// 排列5单式(直选定位单式,比较特殊)
	String pl5Poly = "pl5Poly";// 排列5复式(直选定位复式,比较特殊)

	String draw = "draw";// 胆拖
	String directDraw="directDraw";
	String group3Draw = "group3Draw";// 组三胆拖
	String group6Draw = "group6Draw";// 组六胆拖
	String fiveDraw = "fiveDraw";// ren5胆拖
	String threeDraw = "threeDraw";// ren3胆拖
	String twoDraw = "twoDraw";// ren2胆拖
	String fourDraw = "fourDraw";// ren4胆拖
	String threeDirectDraw = "threeDirectDraw";// 前三直选胆拖
	String threeGroupDraw="threeGroupDraw";
	String twoDirectDraw="twoDirectDraw";
	String twoGroupDraw="twoGroupDraw";
	String sxPoly = "sxPoly";// 生肖



	/** 注册 */
	String REGISTER = "user/register.htm";//
	String mobile = "mobile";// 手机号


	/** 购彩记录 */
	// 查询类型,0:购买记录；1：追号记录；3：合买全部记录；4 ：合买发起记录；5：合买参与记录 。默认购买记录.Y
	String type = "type";
	String firstRow = "firstRow";// 开始行
	String fetchSize = "fetchSize";// 查询记录数
	String status = "status";// 状态 0:全部, 1:中奖, 2:未中奖, 3:追号中, 4:撤单

	String lotteryName = "lotteryName";// 彩票名称
	String statusDesc = "statusDesc";// 方案状态

	/** 我的关注 */
	String CARE_SCHEME = "lottery/care_scheme.htm";
	String percent = "percent";// 进度(百分比) int N

	/** 开奖公告 */

	String ISSUE_NOTIFY = "lottery/issue_notify.htm";

	String items = "items";

	String number = "number";// 注数

	String drawNumber = "drawNumber";// 开奖号码
	/** 资金明细 */
	String in = "in";// 收入 Y
	String out = "out";// 支出 Y
	String createTime = "createTime";// 操作时间

	/** 个人信息 */
	String name = "name";
	String identify = "identify";
	String email = "email";
	String mobileBinded = "mobileBinded";// 0 未绑定；1 绑定；

	/** 合买列表 */
	String SCHEME_HM = "lottery/scheme_hm.htm";
	String sort = "sort";
	String keyWord = "keyWord";// 方案发起人 模糊查找
	String userName = "userName";
	String level = "level";
	String progress = "progress";
	String personCount = "personCount";
	String remainAmount = "remainAmount";// 剩余金额
	String moeyProgress = "moeyProgress";// 实际进度
	String safeguardProgress = "safeguardProgress";// 保底进度

	/** 现金提款请求 */
	String WITHDRAW_VIEW = "user/withdraw_view.htm";
	String bindingIdentify = "bindingIdentify";
	String bindingBanked = "bindingBanked";
	String bank = "bank";
	String province = "province";
	String city = "city";
	String bankAccount = "bankAccount";

	/** 保存现金提款请求 */
	String WITHDRAW = "user/withdraw.htm";
	String money = "money";// 提款金额，最少5元

	/** 合买确认查询 */
//	String SCHEME_JOIN_DETAIL = "lottery/scheme_join_detail.htm";
	String initiateTime = "initiateTime";// 发起时间
	String safeguard = "safeguard";// 保底金额
	String schemeType = "schemeType";// 方案类型
										// 0：合买；1：追号合买(合买确认查询和购彩记录详细查询不大同，前者直接返回string)
	String schemeContent = "schemeContent";


	String joinAmount = "joinAmount";
	String numberType = "numberType";
	String schemeNumberUnit = "schemeNumberUnit";
	String schemeDetail = "schemeDetail";
	String canCancel = "canCancel";
	String ownPrize = "ownPrize";// 参与中奖金额 合买时用这个
	String prizeDetail = "prizeDetail";// 中奖金额明细

	/** 意见反馈 */
	String LEAVE_WORD = "user/leave_word.htm";
	String title = "title";
	String content = "content";



    /**版本号*/
	String VERSION="version";


	String identityNumber = "identityNumber";


	/** 遗漏 */

	String max = "max";

	String JINGCAI_ZUQIU_SPF="lottery/spf.htm";
	
	//竞彩篮球
	String basketball="lottery/basketball.htm";
	String basketball_guoguan_type="type";
	String basketball_danguan="0";
	String basketball_guoguan="1";

	String LINGHAOQUERY="core/activity/lhcyResult.htm";
	//胜负14场和任9场的接口
	String SHENGFU14CHANG="lottery/toto14.htm";
	String SHENGFU9CHANG="lottery/toto9.htm";
	//冠军竞猜
	String SHIJIEBEI="lottery/worldcupgj.htm";

	String SHIJIEBEIGYJ="lottery/worldcupgyj.htm";
	/** 合买确认查询 */
	String SCHEME_JOIN_DETAIL = "/lottery/scheme_detail.htm";

	String BUY_LOTTERY = "lottery/buy_lottery.htm";
	String PHONE_YANZHENG_test="user/bind_mobile.htm";
	String SUBMIT_VALIDATECODE="/user/bind_mobile_validate.htm";
	String MY_SCHEME = "lottery/my_scheme.htm";
	String MY_INFO = "/user/my_info.htm"; ///user/my_info.htm
	/** 绑定身份证与真实姓名 */
	String BIND_IDENTIFY_NAME_test = "/user/bind_identify_name.htm";

	/** 忘记密码 */
	String GET_FORGET_PASSWORD_test = "/user/get_forget_password.htm";
	/** 绑定银行卡 */
	String BIND_BANK_CARD_test = "/user/bind_bank_card.htm";
	/** 支付宝充值 */
	String ALIPAY_APP_test = "/user/alipay_app.htm";
	/** 现金提款请求 */
	/** 保存现金提款请求 */
	/**竞彩足球往期开奖结果*/
	String FOOTBALL_HISTORY="lottery/football_his.htm";
	/** 银联 */
	/** 连连银通 */
	/***意见反馈 */
	String FEEDBACK_QUERY="user/queryLeaveWord.htm";
	String FEEDBACK_SEND="user/saveLeaveWord.htm";
	/**积分商城*/
	String JIFENSHANGCHENG="user/giftList.htm";
	String JIFEN_DUIHUAN="core/user/scoreExchange.htm";
	String YOUHUI="user/favorityList.htm";

	
	/**
	 * 使用手机号注册账号时，发送验证码
	 */
	String REGISTER_SEND_MOBILE = "user/sendMobile.htm";

    String CHECK_MOBILE_VERFIY_CODE = "user/checkMobileVerifyCode.htm";
    /**
     * 手机号注册的账号修改账号，只有一次修改机会
     */
    String USER_SET_ACCOUNT = "core/user/setAccount.htm";

	String LOTTERY_QUERY_CUR_ISSUE = "lottery/query_cur_issue.htm";

    String FOLLOW_ME = "/lottery/doFollow.htm";
    String SICHANGJINQIU ="/lottery/goal4.htm";
}