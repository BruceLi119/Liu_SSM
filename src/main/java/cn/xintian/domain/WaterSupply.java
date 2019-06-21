package cn.xintian.domain;

import io.swagger.annotations.ApiModelProperty;

/**
 * 停水通知实体类
 */
public class WaterSupply {
    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private String style;
    /**
     * 模板ID
     */
    @ApiModelProperty("模板ID")
    private String midID;
    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String sTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String eTime;
    /**
     * 停水区域
     */
    @ApiModelProperty("停水区域")
    private String stopWaterArea;
    /**
     * 停水类型
     */
    @ApiModelProperty("停水类型")
    private String stopWaterStyle;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMidID() {
        return midID;
    }

    public void setMidID(String midID) {
        this.midID = midID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String getStopWaterArea() {
        return stopWaterArea;
    }

    public void setStopWaterArea(String stopWaterArea) {
        this.stopWaterArea = stopWaterArea;
    }

    public String getStopWaterStyle() {
        return stopWaterStyle;
    }

    public void setStopWaterStyle(String stopWaterStyle) {
        this.stopWaterStyle = stopWaterStyle;
    }
}
