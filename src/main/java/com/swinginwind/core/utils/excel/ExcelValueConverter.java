package com.swinginwind.core.utils.excel;

public interface ExcelValueConverter <K, T>
{
    
    K expConvert(T obj);
    
    T impConvert(K obj);

}
