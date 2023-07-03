package com.xdw.sbgl.controller;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.xdw.sbgl.utils.IpLocationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

/**
 * Description:
 * Author: suyl
 * Create Data: 2023/6/9
 */
@Controller
@Slf4j
public class IndexController {
    @RequestMapping("/admin")
    public String index(HttpServletRequest request) throws IOException, GeoIp2Exception {
        try {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip.contains(":")) {
                throw new RuntimeException("ipv6地址");
            }
            String realAddressByIP = IpLocationUtil.getRealAddressByIP(ip);
            log.info("有人访问：(#{},#{})", ip, realAddressByIP);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "/admin.html";
    }

    @RequestMapping("/detail")
    public String detail(HttpServletRequest request) throws IOException, GeoIp2Exception {
        return "/detail.html";
    }
}
