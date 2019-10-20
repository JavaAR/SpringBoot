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


}
