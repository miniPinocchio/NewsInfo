package com.liuhui.newsinfo;

/**
 * Created by wangyingying on 2017/1/16.
 */

public class ApiConfig {
    /**
     * Toast的状态
     */
    public static final int SUCCESS=1;
    public static final int ERROR=2;
    public static final int WARNING=3;
    public static final int INFO=4;
    public static final int DEFAULT=5;

    /**
     * SharedPreference
     */
    public static final String IS_FIRST_LOGIN = "isFirstLogin";
    public static final String USER_NAME = "username";
    public static final String NEWS_IP = "news";
    //是否是debug模式
    public static final String KLOG_TAG = "jwt";
    /**
     * 延迟时间
     */
    public static final int DELAY_TIME = 2000;

    public static final String BUSCODE = "busCode";

    /**
     * busCode接口编号
     */
    public static class busCode{
        public static final String SIGN_IN = "0000",//用户登录
                SING_OUT = "0001",//用户退出
                SEARCH_QUERY = "0004",//搜索查询接口
                CATEGORY_QUERY = "0005",//分类查询
                APP_DOWNLOAD = "0006",//下载接口
                APP_UPGRADE = "0007",//app升级检查接口
                SHOPPING_UPGRADE = "0008",//应用商店升级检查接口
                VERSION_MESSAGE = "0010",//版本信息
                APP_LOG = "0012",//app安装卸载上报日志接口
                APP_QUERY = "0014";//应用查询接口
    }

    /**
     * 返回码
     * @author cxy
     *
     */
    public static class RetCode{
        /**成功*/
        public static final String SUCCESS = "0000";
        /**参数错误*/
        public static final String PARAM_ERROR = "0001";
        /**业务处理错误,具体看信息描述*/
        public static final String BUSINESS_ERROR = "0002";
        /**登录超时,重新登录*/
        public static final String TIMEOUT_ERROR = "2222";
        /**后台服务异常*/
        public static final String SERVICE_ERROR = "9999";
    }

    /**
     * 获取手机基本信息
     */
    public static class BaseInfo {
        public static final String APP_TYPE = "Android";//应用类型
        public static String APP_VERSION = "";//当前应用版本
        public static String APP_OS = "";//应用系统信息
        public static String IMEI = "";//设备IMEI
        public static String IMSI = "";//设备IMSI
        public static String DEVICE_SN = "";//设备序列号
        public static String DEVICE_TYPE = "";//设备类型
        public static String MAC = "";//网卡mac
        public static String IP = "";//应用的v4 IP地址
        public static String UUID = "";//UUID
        public static String LONGITUDE = "";//GPS经度
    }
}
