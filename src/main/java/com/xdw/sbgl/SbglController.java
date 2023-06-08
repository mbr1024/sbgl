package com.xdw.sbgl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.sql.Condition;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
public class SbglController {

    @GetMapping({"/list/{search}", "/list"})
    public ModelAndView list(@PathVariable(required = false) String search) throws SQLException {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());    //返回json
        List<Entity> sbs;
        if (StrUtil.isNotEmpty(search)) {
            sbs = Db.use().query("select * from sb where no like '%?%' order by update_time desc", search);
        }else {
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
        Db.use().insert(
                Entity.create("sb")
                        .set("name", sbInfo.getName())
                        .set("no", sbInfo.getNo())
                        .set("update_time", DateUtil.format(new Date(), "yyyyMMdd"))
        );
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

    @PostMapping("/update")
    public ModelAndView update(@RequestBody SbInfo sbInfo) throws SQLException {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());    //返回json
        List<Entity> sbs = Db.use().findAll(Entity.create("sb").set("no", sbInfo.getNo()));
        if (CollectionUtil.isEmpty(sbs)) {
            throw new RuntimeException("该数据不存在");
        }
        Db.use().update(
                Entity.create().set("name", sbInfo.getName()), //修改的数据
                Entity.create("sb").set("no", sbInfo.getNo()) //where条件
        );
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
        if (CollectionUtil.isNotEmpty(sbs)){
            mv.addObject("data", sbs.get(0));
        }
        return mv;
    }

    @GetMapping("qrcode/{no}")
    public ModelAndView qrcode(@PathVariable String no) throws SQLException {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());    //返回json
        List<Entity> sbs = Db.use().findAll(Entity.create("sb").set("no", no));

        if (CollectionUtil.isNotEmpty(sbs)){
            QrCodeUtil.generate(//
                    "最喜欢可爱的小鱼鱼了", //二维码内容
                    QrConfig.create().setImg("D:/yuyu.jpg"), //附带logo
                    FileUtil.file("D:/yuyuqrcode.jpg")//写出到的文件
            );
            mv.addObject("data", sbs.get(0));
        }
        mv.addObject("message", "操作成功");
        mv.addObject("code", 0);
        return mv;
    }


}
