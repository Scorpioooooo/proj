package com.coocaa.pro.manage.common;

public class MessageBean {

    private String key;
    private Object value;

    private String field;//附加属性名
    private Object fieldValue;//附加属性值

    public MessageBean() {
    }

    public MessageBean(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public MessageBean(String key, Object value, String field, Object fieldValue) {
        this.key = key;
        this.value = value;
        this.field = field;
        this.fieldValue = fieldValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }
}
