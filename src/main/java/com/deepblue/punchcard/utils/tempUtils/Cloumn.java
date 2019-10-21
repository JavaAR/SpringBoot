package com.deepblue.punchcard.utils.tempUtils;

/**
 * <p></p>
 * <p/>
 * 字段对象
 */
public class Cloumn {

    private String cloumnName;
    private String comment;
    private String cloumnType;

    public String getCloumnName() {
        return cloumnName;
    }

    public void setCloumnName(String cloumnName) {
        this.cloumnName = cloumnName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCloumnType() {
        return cloumnType;
    }

    public void setCloumnType(String cloumnType) {
        this.cloumnType = cloumnType;
    }

    public String getJavaType() {
        return TypeConstant.getJavaType(this.cloumnType);
    }

    public String getFieldName() {
        return putOffUnderline(this.cloumnName);
    }

    //去掉下划线并且按驼峰命名规则转换
    private  String putOffUnderline(String columnName) {
        StringBuffer fieldNameBuffer = null;
        String tempNameArray[] = columnName.split("_");
        for (int i = 0; i < tempNameArray.length; i++) {
            if (i == 0) {
                fieldNameBuffer = new StringBuffer(tempNameArray[i]);
            } else {
                fieldNameBuffer.append(captureName(tempNameArray[i]));
            }
        }
        return fieldNameBuffer.toString();
    }
    //首字母大写
    private  String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }

}
