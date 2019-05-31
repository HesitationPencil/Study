package com.lidar.lslon1_1_1;

public class TradInfo {
    //Varchar(10)  店铺号 No L88888 万象城制定并分配
    private String xf_storecode;
    //Varchar(3) No 01 固定值：01  收银号
    private String xf_tillid;
    private String xf_txdate;// Date No yyyy-mm-dd 交易发生日：2008-08-08  交易日期
    private String xf_postdate;//Date No yyyy-mm-dd 交易实际上传日期，取上传时系统时间。 例如， 2008-08-08 发生的交易因为故障第二天才上传 ，则该字段写入2008-08-09  上传日期
    private String xf_txtime; // Varchar(6) No HHMMSS 交易时间 交易时间，例如 16 点15 分 45 秒：161545
    private int xf_txserial;// 6. Integer No 1 每天从 1 开始，每天不能重复（同一销售单不同付款方式拆分的多条记录也不能重复） 流水号
    private String xf_invoiceno; //Varchar（10) Yes S000000001 单号必须提供，而且必须唯一，用于存放每年交易的流水号，在同一结算年度内应不可重复；以 S 开头，宽 10位，后 9 位为数字 ，顺序递增,即从S000000001 开始，一直累加，每日以上日尾数加一为起始，开始重新累加。单据号必须保证 10 位，不足 10 位的 S 之后补 0。  交易单号
    private double xf_salesqty;// 交易数量 Decimal(16,4)
    private String xf_tendercode;//Varchar(2)  No CH/CI/WP/AP/OT
    private double xf_tenderamt;// Decimal(16,4) 交易金额
    private String xf_updatestage;//更新状态 Varchar(1) No 0 固定值：0
    private String xf_userid;// 用户名  Varchar(10) No 登录数据库的用户名写入该字段，由万象城提供。
    private String xf_mallid;// varchar(10) Yes 01 万象城提供，固定值为  01： 项目编号
    private String xf_datatype; //varchar(2) 数据类型  数据如果计入店铺营业额上传S，否则上传  N
    private String xf_plu;//  varchar(30) 货号 万象城提供，只有一个货号的店铺可为空


    public String getXf_storecode() {
        return xf_storecode;
    }

    public void setXf_storecode(String xf_storecode) {
        this.xf_storecode = xf_storecode;
    }

    public String getXf_tillid() {
        return xf_tillid;
    }

    public void setXf_tillid(String xf_tillid) {
        this.xf_tillid = xf_tillid;
    }

    public String getXf_txdate() {
        return xf_txdate;
    }

    public void setXf_txdate(String xf_txdate) {
        this.xf_txdate = xf_txdate;
    }

    public String getXf_postdate() {
        return xf_postdate;
    }

    public void setXf_postdate(String xf_postdate) {
        this.xf_postdate = xf_postdate;
    }

    public String getXf_txtime() {
        return xf_txtime;
    }

    public void setXf_txtime(String xf_txtime) {
        this.xf_txtime = xf_txtime;
    }

    public int getXf_txserial() {
        return xf_txserial;
    }

    public void setXf_txserial(int xf_txserial) {
        this.xf_txserial = xf_txserial;
    }

    public String getXf_invoiceno() {
        return xf_invoiceno;
    }

    public void setXf_invoiceno(String xf_invoiceno) {
        this.xf_invoiceno = xf_invoiceno;
    }

    public double getXf_salesqty() {
        return xf_salesqty;
    }

    public void setXf_salesqty(double xf_salesqty) {
        this.xf_salesqty = xf_salesqty;
    }

    public String getXf_tendercode() {
        return xf_tendercode;
    }

    public void setXf_tendercode(String xf_tendercode) {
        this.xf_tendercode = xf_tendercode;
    }

    public double getXf_tenderamt() {
        return xf_tenderamt;
    }

    public void setXf_tenderamt(double xf_tenderamt) {
        this.xf_tenderamt = xf_tenderamt;
    }

    public String getXf_updatestage() {
        return xf_updatestage;
    }

    public void setXf_updatestage(String xf_updatestage) {
        this.xf_updatestage = xf_updatestage;
    }

    public String getXf_userid() {
        return xf_userid;
    }

    public void setXf_userid(String xf_userid) {
        this.xf_userid = xf_userid;
    }

    public String getXf_mallid() {
        return xf_mallid;
    }

    public void setXf_mallid(String xf_mallid) {
        this.xf_mallid = xf_mallid;
    }

    public String getXf_datatype() {
        return xf_datatype;
    }

    public void setXf_datatype(String xf_datatype) {
        this.xf_datatype = xf_datatype;
    }

    public String getXf_plu() {
        return xf_plu;
    }

    public void setXf_plu(String xf_plu) {
        this.xf_plu = xf_plu;
    }
}
