package com.swinginwind.core.utils;

import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>
 * 描 述：定制ObjectMapper，主要用于定义Date和Clob类型的序列化
 * </p>
 * <p>
 * 创 建 人：hadoop
 * </p>
 * <p>
 * 创建时间：2015年12月23日上午9:15:21
 * </p>
 */
public class CustomObjectMapper extends ObjectMapper
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomObjectMapper()
    {
		//目标类中找不到json字符串中属性时直接忽略
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        changeSerializer("yyyy-MM-dd HH:mm:ss");
    }

    public CustomObjectMapper(String dateTimePattern)
    {
        changeSerializer(dateTimePattern);
    }

    public void changeSerializer(final String dateTimePattern)
    {
    	SimpleDateFormat smt = new SimpleDateFormat(dateTimePattern);  
        this.setDateFormat(smt);  
        setSerializationInclusion(Include.NON_NULL);// null不参加序列化
    }

}
