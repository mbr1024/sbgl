package com.xdw.sbgl.po;

/**
 * Description:
 * Author: suyl
 * Create Data: 2023/6/8
 */
public class SbInfo {
    private String no;
    private String type;
    // 名称
    private String name;
    // 购自商店
    private String store;
    // 出厂时间
    private String factory_time;
    // 型号规格
    private String model;
    // 价格
    private String price;
    // 购买时间
    private String buy_time;
    // 主要用途
    private String purpose;
    // 安装单位
    private String install_company;
    // 保修期限
    private String warranty;
    // 生产厂家
    private String factory;
    // 安装地点
    private String install_location;
    // 启用时间
    private String start_time;
    //说明书 和量表
    private String machine_doc;
    // 附属设备
    private String accessory_device;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getFactory_time() {
        return factory_time;
    }

    public void setFactory_time(String factory_time) {
        this.factory_time = factory_time;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_Time(String buy_Time) {
        this.buy_time = buy_Time;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getInstall_company() {
        return install_company;
    }

    public void setInstall_company(String install_company) {
        this.install_company = install_company;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getInstall_location() {
        return install_location;
    }

    public void setInstall_location(String install_location) {
        this.install_location = install_location;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getMachine_doc() {
        return machine_doc;
    }

    public void setMachine_doc(String machine_doc) {
        this.machine_doc = machine_doc;
    }

    public String getAccessory_device() {
        return accessory_device;
    }

    public void setAccessory_device(String accessory_device) {
        this.accessory_device = accessory_device;
    }

    public String getMachine_att() {
        return machine_att;
    }

    public void setMachine_att(String machine_att) {
        this.machine_att = machine_att;
    }

    public String getRepair_record() {
        return repair_record;
    }

    public void setRepair_record(String repair_record) {
        this.repair_record = repair_record;
    }

    public String getResponsible_person() {
        return responsible_person;
    }

    public void setResponsible_person(String responsible_person) {
        this.responsible_person = responsible_person;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    // 随机附件,专用工具
    private String machine_att;
    // 重大变更记录(维修,借调)
    private String repair_record;
    // 负责人
    private String responsible_person;
    private String update_time;

}
