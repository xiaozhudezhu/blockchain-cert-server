package com.swinginwind.core.utils.excel;

public enum FieldTypeEnum
{
    String("0", "字符串"),
    Number("1", "数字"),
    Date("2", "时间"),
    Boolean("3", "布尔"),
    Clob("4", "大字数据");
    
    private String code;
    
    private String name;
    
    FieldTypeEnum(String code, String name)
    {
        this.setCode(code);
        this.setName(name);
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
