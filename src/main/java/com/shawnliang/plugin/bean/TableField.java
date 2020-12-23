package com.shawnliang.plugin.bean;

import com.shawnliang.plugin.util.StringUtils;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2020/12/24
 */

public class TableField {

    private boolean keyFlag;

    private String name;

    private String type;

    private String propertyName;

    private String propertyType;

    private String propertyClassName;

    private String comment;

    private String extra;

    private Boolean isEnum;

    public boolean getIsEnum() {
        return (this.isEnum == null) ? false : this.isEnum.booleanValue();
    }

    public void setEnum(boolean anEnum) {
        this.isEnum = Boolean.valueOf(anEnum);
    }

    public boolean isKeyFlag() {
        return this.keyFlag;
    }

    public void setKeyFlag(boolean keyFlag) {
        this.keyFlag = keyFlag;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyClassName = StringUtils.toFirstUpperCase(propertyName);
        this.propertyName = propertyName;
    }

    public String getPropertyClassName() {
        return this.propertyClassName;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isConvert() {
        return !this.name.equals(this.propertyName);
    }

    public String getCapitalName() {
        return this.propertyName.substring(0, 1).toUpperCase() + this.propertyName.substring(1);
    }

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}

