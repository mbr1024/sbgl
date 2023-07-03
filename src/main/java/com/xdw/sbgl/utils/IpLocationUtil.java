package com.xdw.sbgl.utils;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by Intellij IDEA.
 * User:  su_yl
 * Date:  2023/7/3
 * Desc:
 */
@Slf4j
public class IpLocationUtil {

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (Ipv4Util.isInnerIP(ip)) {
            return "内网IP";
        }
        if (true) {
            try {
                String rspStr = sendGet(IP_URL, "ip=" + ip + "&json=true", "GBK");
                if (StrUtil.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = new JSONObject(rspStr);
                String region = obj.getStr("pro");
                String city = obj.getStr("city");
                return String.format("%s-%s", region, city);
            } catch (Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return address;
    }

    public static String sendGet(String url, String param, String contentType) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    public static String getRealAddressByIPOffLine(String ip) {
        String region = "";
        String city = "";
        try {
            InputStream inputStream = Class.forName("com.xdw.sbgl.utils.IpLocationUtil").getResourceAsStream("/GeoLite2-City.mmdb");
            DatabaseReader reader = new DatabaseReader.Builder(inputStream).build();
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = reader.city(ipAddress);
            String country = response.getCountry().getName();   // United States
            region = response.getLeastSpecificSubdivision().getName(); // California
            city = response.getCity().getName();        // Mountain View
        } catch (Exception ex) {
            log.error("离线获取ip位置失败。");
        }

        log.info("离线获取ip:{}",String.format("%s-%s", region, city));
        return String.format("%s-%s", region, city);
    }

    public static void main(String[] args) {
        System.out.println(IpLocationUtil.getRealAddressByIP("218.94.87.158"));
        System.out.println(IpLocationUtil.getRealAddressByIPOffLine("218.94.87.158"));
    }
}
