package com.hy.tools.enums;

/***
 *  <pre>
 * 单选框共用枚举 查询条件显示类型
 * </pre>
 * @author gu.anni
 * @date 15/11/26.
 */
public enum YesNo {

    YES(1,"是"),
    NO(0,"否"),

    UNKNOW(-100,"unknow");

    private YesNo(int code,String name){
        this.code=code;
        this.name=name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static YesNo parse(int code){
        YesNo[] values = YesNo.values();
        for(YesNo adminType:values){
            if(adminType.code==code)
                return adminType;
        }
        return UNKNOW;
    }
}
