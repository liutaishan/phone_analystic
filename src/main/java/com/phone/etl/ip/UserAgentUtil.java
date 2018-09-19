package com.phone.etl.ip;



import cz.mallat.uasparser.UASparser;
import org.apache.log4j.Logger;

import java.io.IOException;


public class UserAgentUtil {
    public static final Logger logger= Logger.getLogger(UserAgentUtil.class);

    static UASparser uaSparser =null;





    /**
     * 解析浏览器的user agent字符串,返回UserAgentInfo对象。

     * 如果user agent为空,返回null。如果解析失败,也直接返回null。
     *
     * @param userAgent
     * 要解析的user agent字符串
     * @return 返回具体的值
     */
    public static UserAgentInfo parseUserAgeent(String userAgent) {
        UserAgentInfo result = null;// 初始化一个浏览器信息类的对象
        if (!(userAgent == null || userAgent.trim().isEmpty())) {
// 此时userAgent不为null,而且不是由全部空格组成的
            try {
                cz.mallat.uasparser.UserAgentInfo info = null;// jar文件 UserAgentInfo对象
                info = uaSparser.parse(userAgent); // 使用parse方法解析
                result = new UserAgentInfo(); // 创建自定义 UserAgentInfo对象
                result.setBrowserName(info.getUaFamily());// 将解析出对应的4种数据设置到对象属性中去
                result.setBrowserVersion(info.getBrowserVersionInfo());
                result.setOsName(info.getOsFamily());
                result.setOsVersion(info.getOsName());
            } catch (IOException e) {
// 出现异常,将返回值设置为null
                result = null;
            }
        }
        return result;
    }

    /**
     * 用于封装useragent解析后的信息
     */
    public static class UserAgentInfo{

        private String browserName ;  //浏览器名称
        private String browserVersion ; //浏览器版本号
        private String osName ; //操作系统名称
        private String osVersion ; //操作系统版本号


        @Override
        public String toString() {
            return "UserAgentInfo{" +
                    "browserName='" + browserName + '\'' +
                    ", browserVersion='" + browserVersion + '\'' +
                    ", osName='" + osName + '\'' +
                    ", osVersion='" + osVersion + '\'' +
                    '}';
        }

        public String getBrowserName() {
            return browserName;
        }

        public void setBrowserName(String browserName) {
            this.browserName = browserName;
        }

        public String getBrowserVersion() {
            return browserVersion;
        }

        public void setBrowserVersion(String browserVersion) {
            this.browserVersion = browserVersion;
        }

        public String getOsName() {
            return osName;
        }

        public void setOsName(String osName) {
            this.osName = osName;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }



        public UserAgentInfo() {

        }

        public UserAgentInfo(String browserName, String browserVersion, String osName, String osVersion, String getBrowserName) {
            this.browserName = browserName;
            this.browserVersion = browserVersion;
            this.osName = osName;
            this.osVersion = osVersion;
        }
    }
}

