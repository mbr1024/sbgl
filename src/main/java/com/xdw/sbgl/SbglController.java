package com.xdw.sbgl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.sql.Condition;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Intellij IDEA.
 * User:  su_yl
 * Date:  2023/6/8
 * Desc:
 */
@RestController
@Slf4j
public class SbglController {

    @GetMapping({"/list/{search}", "/list"})
    public ModelAndView list(@PathVariable(required = false) String search) throws SQLException {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());    //返回json
        List<Entity> sbs;
        if (StrUtil.isNotEmpty(search)) {
            sbs = Db.use().query("select * from sb where no like '%?%' order by update_time desc", search);
        } else {
            sbs = Db.use().query("select * from sb  order by update_time desc");
        }
        mv.addObject("message", "操作成功");
        mv.addObject("code", 0);
        mv.addObject("data", sbs);
        return mv;
    }

    @PostMapping("/add")
    public ModelAndView add(@RequestBody SbInfo sbInfo) throws SQLException {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());    //返回json
        List<Entity> sbs = Db.use().findAll(Entity.create("sb").set("no", sbInfo.getNo()));
        if (CollectionUtil.isNotEmpty(sbs)) {
            throw new RuntimeException("插入失败，已存在该编码");
        }

        mv.addObject("message", "操作成功");
        mv.addObject("code", 0);
        return mv;
    }

    @PostMapping("/remove/{no}")
    public ModelAndView remove(@PathVariable String no) throws SQLException {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        // 返回json
        Db.use().del(
                Entity.create("sb").set("no", no)//where条件
        );
        mv.addObject("message", "操作成功");
        mv.addObject("code", 0);
        return mv;
    }

    @PostMapping("/merge")
    public ModelAndView update(@RequestBody SbInfo sbInfo) throws SQLException {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());    //返回json
        List<Entity> sbs = Db.use().findAll(Entity.create("sb").set("no", sbInfo.getNo()));
        if (CollectionUtil.isEmpty(sbs)) {
            Db.use().insert(
                    Entity.create("sb")
                            .set("no", sbInfo.getNo())
                            .set("type", sbInfo.getType())
                            .set("name", sbInfo.getName())
                            .set("store", sbInfo.getStore())
                            .set("factory_time", sbInfo.getFactory_time())
                            .set("model", sbInfo.getModel())
                            .set("price", sbInfo.getPrice())
                            .set("buy_time", sbInfo.getBuy_time())
                            .set("purpose", sbInfo.getPurpose())
                            .set("install_company", sbInfo.getInstall_company())
                            .set("warranty", sbInfo.getWarranty())
                            .set("factory", sbInfo.getFactory())
                            .set("install_location", sbInfo.getInstall_location())
                            .set("start_time", sbInfo.getStart_time())
                            .set("machine_doc", sbInfo.getMachine_doc())
                            .set("accessory_device", sbInfo.getAccessory_device())
                            .set("machine_att", sbInfo.getMachine_att())
                            .set("repair_record", sbInfo.getRepair_record())
                            .set("responsible_person", sbInfo.getResponsible_person())
                            .set("update_time", DateUtil.format(new Date(), "yyyyMMdd"))
            );
        } else {
            Db.use().update(
                    Entity.create()
                            .set("no", sbInfo.getNo())
                            .set("type", sbInfo.getType())
                            .set("name", sbInfo.getName())
                            .set("store", sbInfo.getStore())
                            .set("factory_time", sbInfo.getFactory_time())
                            .set("model", sbInfo.getModel())
                            .set("price", sbInfo.getPrice())
                            .set("buy_time", sbInfo.getBuy_time())
                            .set("purpose", sbInfo.getPurpose())
                            .set("install_company", sbInfo.getInstall_company())
                            .set("warranty", sbInfo.getWarranty())
                            .set("factory", sbInfo.getFactory())
                            .set("install_location", sbInfo.getInstall_location())
                            .set("start_time", sbInfo.getStart_time())
                            .set("machine_doc", sbInfo.getMachine_doc())
                            .set("accessory_device", sbInfo.getAccessory_device())
                            .set("machine_att", sbInfo.getMachine_att())
                            .set("repair_record", sbInfo.getRepair_record())
                            .set("responsible_person", sbInfo.getResponsible_person())
                            .set("update_time", DateUtil.format(new Date(), "yyyyMMdd")),
                    Entity.create("sb").set("no", sbInfo.getNo()) //where条件
            );
        }

        mv.addObject("message", "操作成功");
        mv.addObject("code", 0);
        return mv;
    }

    @GetMapping("/detail/{no}")
    public ModelAndView detail(@PathVariable String no) throws SQLException {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());    //返回json
        List<Entity> sbs = Db.use().findAll(Entity.create("sb").set("no", no));
        mv.addObject("message", "操作成功");
        mv.addObject("code", 0);
        if (CollectionUtil.isNotEmpty(sbs)) {
            mv.addObject("data", sbs.get(0));
        }
        return mv;
    }

    @GetMapping("qrcode/{no}")
    public void qrcode(@PathVariable String no, HttpServletResponse response) throws SQLException, IOException {
        List<Entity> sbs = Db.use().findAll(Entity.create("sb").set("no", no));
        QrConfig config = new QrConfig();
        // 高纠错级别
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        if (CollectionUtil.isNotEmpty(sbs)) {
            BufferedImage bufferedImage = QrCodeUtil.generate(//
                    "http://124.222.81.180:8080/detail?no=" + no, config
            );
            response.setContentType("image/png");
            ImageIO.write(bufferedImage, "png", response.getOutputStream());
        }
    }

    @GetMapping("ipAddress")
    public ModelAndView ipAddrress(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());    //返回json
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
            InputStream inputStream = this.getClass().getResourceAsStream("/GeoLite2-City.mmdb");
            DatabaseReader reader = new DatabaseReader.Builder(inputStream).build();
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = reader.city(ipAddress);
            String country = response.getCountry().getName();   // United States
            String region = response.getLeastSpecificSubdivision().getName(); // California
            String city = response.getCity().getName();        // Mountain View
            log.info("有人访问：(#{},#{}-#{}-#{})", ip, country, region, city);
            mv.addObject("data", country + "-" + region + "-" + city);
        } catch (Exception e) {
            log.error(e.getMessage());
            mv.addObject("data", "未知");
        }
        mv.addObject("message", "操作成功");
        mv.addObject("code", 0);
        return mv;
    }


}
