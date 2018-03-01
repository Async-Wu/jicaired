package com.chengyi.app.util;

/**
 * @comment 友盟事件
 * @author
 * @version v1.0 2012-5-31
 * 
 */
public interface YOUMENG_EVENT {
	String suijinzhuxuanhao = "suijinzhuxuanhao";// 随机N注选号
	
	String wanchengxuanhao = "wanchengxuanhao";// 购彩大厅点击完成选号。（非投注确认界面过来修改或者手选投注）
	String shoudongtouzhu = "shoudongtouzhu";// 点击手动投注
	String jixuantouzhu = "jixuantouzhu";// 点击机选投注
	String zhuihaobeitou = "zhuihaobeitou";// 点击追号倍投
	String goumai = "goumai";// 投注确认界面点击购买按钮
	String faqihemaiintouzhuqueren = "faqihemaiintouzhuqueren";// 投注确认界面点击发起合买

	String faqihemai = "faqihemai";// 发起合买界面点击发起合买

	String querentouzhuofdaigou = "querentouzhuofdaigou";// 购买确认界面点击确认投注按钮(代购)
	String querentouzhuoffaqihemai = "querentouzhuoffaqihemai";// 购买确认界面点击确认投注按钮(合买)
	String caizhongqiehuan = "caizhongqiehuan";// 彩种切换
	String className = "className";// 类名字,(不是事件名)
	String incanyuhemai = "incanyuhemai";// 进入参与合买界面
	String canyuhemai = "canyuhemai";// 参与合买
	String canyuhemaifanhui = "canyuhemaifanhui";// 参与合买返回

	String zhucein = "zhucein";// 进入注册页面
	String zhucesubmit = "zhucesubmit";// 注册提交
	String zhucechenggong = "zhucechenggong";// 注册成功

	String chongzhifangshi = "chongzhifangshi";// 充值方式

	String daigoufanhui = "daigoufanhui";// 代购返回
	String faqihemaifanhui = "faqihemaifanhui";// 发起合买返回

	

	

	String zhuceshibai = "zhuceshibai";// 注册失败

	String goucaidatingqiehuancaizhong = "goucaidatingqiehuancaizhong";// 购彩大厅切换彩种

	String hemaizhongxinqiehuancaizhong = "hemaizhongxinqiehuancaizhong";// 合买中心切换彩种

	String jinruwanfashidecaipiao = "jinruwanfashidecaipiao";// 进入玩法页面时，当前的彩票

	String yaodonglidu = "yaodonglidu";// 摇动力度

	String hemaizhongxintabdianji = "hemaizhongxintabdianji";// 合买中心

	String bianjianniudianji = "bianjianniudianji";// 编辑按钮点击

	String gerenzhanghuzhongdianjicaozuo = "gerenzhanghuzhongdianjicaozuo";// 个人账户中点击操作

	String zhucewanchenghouquxiao = "zhucewanchenghouquxiao";// 注册完成后去向

	String gengduodianji = "gengduodianji";// 更多中去向
	
	String tubiaofenxi = "tubiaofenxi";// 图表分析
	
	
	//newversion  统计事件
	String   new_indexpage="new_indexpage";    ///首页
	String   new_aboutus="new_aboutus";       ///关于我们
	String   new_luckytouzhu="new_luckytouzhu";   ///幸运一注  
	String   new_indexpage_caipiao="new_indexpage_caipiao";    //首页彩票列表
	String   new_xuanhao="new_xuanhao";                        //选号
	String   new_yaodongxuanhao = "new_yaodongxuanhao";// 摇一摇选号
    String   new_xuanhao_wanfa="new_xuanhao_wanfa";    //选号区域彩种玩法
    String   new_touzhuqueren="new_touzhuqueren";      ///投注确认
    String   new_goumaichenggong="new_goumaichenggong";  ///购买成功
    String   new_hemaicenter="new_hemaicenter";          ///合买中心
    String   new_fanganxiangqing="new_fanganxiangqing";   ///方案详情页面
    String   new_goumaifanhuiflag = "参入合买返回标志";// 购买返回标志，成功，失败，金额不足等(不是事件名)
    String   new_kaijianggonggao="new_kaijianggonggao";   //开奖公告
    String   new_kaijianggonggao_wangqi="new_kaijianggonggao_wangqi";     ///开奖公告往期
    String   new_register="new_register";                                 ///注册登录
    String   new_zhanghushouye="new_zhanghushouye";                       ///账户首页
    String   new_setting_center="new_setting_center";                     ///设置中心
    String   new_zhanghuchongzhi="new_zhanghuchongzhi";                   ///账户充值
    String   new_goucairecord="new_goucairecord";                         ///购彩记录
    String   new_jingcaizuqiu="new_jingcaizuqiu";                         ///竞彩足球
    String   new_jingcaizuqiu_touzhuqueren="new_jingcaizuqiu_touzhuqueren";   ///竞彩足球投注确认
    String   new_basketball="new_jingcailanqiu";                         ///竞彩篮球
    String   new_basketball_cart="new_jingcailanqiu_touzhuqueren";   ///竞彩篮球投注确认

}
