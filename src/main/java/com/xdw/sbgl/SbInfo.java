package com.xdw.sbgl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: suyl
 * Create Data: 2023/6/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SbInfo {
    private String no;
    private String type;
    // 名称
    private String name;
    // 购自商店
    private String store;
    // 出厂时间
    private String factoryTime;
    // 型号规格
    private String model;
    // 价格
    private String price;
    // 购买时间
    private String buyTime;
    // 主要用途
    private String purpose;
    // 安装单位
    private String installCompany;
    // 保修期限
    private String warranty;
    // 生产厂家
    private String factory;
    // 安装地点
    private String installLocation;
    // 启用时间
    private String startTime;
    //说明书 和量表
    private String machineDoc;
    // 附属设备
    private String accessoryDevice;
    // 随机附件,专用工具
    private String machineAtt;
    // 重大变更记录(维修,借调)
    private String repairRecord;
    // 负责人
    private String responsiblePerson;
    private String updateTime;

}
