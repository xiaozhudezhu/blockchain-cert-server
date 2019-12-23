package com.swinginwind.core.pager;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONResponse extends HashMap<String, Object> implements Serializable
{

    public enum JSONResult
    {
        FAILED("failed"), SUCCESS("success"), WARNING("warning");
        private String value = null;

        private JSONResult(String value)
        {
            setValue(value);
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }
    }

    /**
     * 
     */
    private static final long         serialVersionUID = -1986982315094947073L;

    private JSONResult                status           = JSONResult.SUCCESS;
    /**
     * 返回错误代码
     */
    private String                    errorCode;
    private String                    msg              = "";

    public JSONResponse()
    {
    	this.put("status", status.value);
    	this.put("msg", msg);
    	this.put("errorCode", errorCode);
    }

    public JSONResponse(boolean result, String msg)
    {
        if (result)
        {
            if (StringUtils.isBlank(msg))
            {
                setStatus(JSONResult.SUCCESS);
            }
            else
            {
                setStatus(JSONResult.WARNING);
            }
        }
        else
        {

            setStatus(JSONResult.FAILED);
        }
        this.setMsg(msg);
    }

    public String getMsg()
    {
        return msg;
    }

    public String getStatus()
    {
        return status.getValue();
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
        this.put("msg", msg);
    }

    public void setStatus(JSONResult status)
    {
        this.status = status;
        this.put("status", status.value);
    }

    public void setStatusAndMsg(JSONResult result, String msg)
    {
        setStatus(result);
        setMsg(msg);
    }

    public void setStatusAndMsg(boolean status, String msg)
    {
        if (status)
        {
            setStatus(JSONResult.SUCCESS);
        }
        else
        {
            setStatus(JSONResult.FAILED);
        }
        setMsg(msg);
    }

    @Override
    public String toString()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            return objectMapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e)
        {
            // TODO Auto-generated catch block
            ExceptionUtils.getStackTrace(e);
            return "";
        }
    }

    /**
     * 获取errorCode
     */
    public String getErrorCode()
    {
        return errorCode;
    }

    /**
     * 设置errorCode
     */
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
        this.put("errorCode", errorCode);
    }

}
