package com.xdw.sbgl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/list")
    public String list() {
        // 1、建表 DDL
        String createUser = "create table user(" +
                "id integer primary key autoincrement," +
                "name varchar(20)," +
                "age integer" +
                ")";
        jdbcTemplate.update(createUser);
        // 2、插入数据
        String insertUserData = "insert into user(name,age) values ('张三',18),('李四',20)";
        jdbcTemplate.update(insertUserData);
        // 3、查询语句
        String selectUserData = "select * from user";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(selectUserData);
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
        // 5、删除整张表
        String dropTable = "drop table user";
        jdbcTemplate.update(dropTable);
        return "list";
    }

}
