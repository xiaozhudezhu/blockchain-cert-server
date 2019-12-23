package com.swinginwind.iknowu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.swinginwind.core.utils.ApplicationPropsUtil;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 * wechat mp configuration
 *
 * @author 
 */
@Configuration
public class WechatMpConfiguration {
    
    @Bean
    public WxMpConfigStorage configStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(ApplicationPropsUtil.getPropsValue("wechat.mp.appId"));
        configStorage.setSecret(ApplicationPropsUtil.getPropsValue("wechat.mp.secret"));
        configStorage.setToken(ApplicationPropsUtil.getPropsValue("wechat.mp.token"));
        configStorage.setAesKey(ApplicationPropsUtil.getPropsValue("wechat.mp.aesKey"));
        return configStorage;
    }

    @Bean
    public WxMpService wxMpService(WxMpConfigStorage configStorage) {
        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        return wxMpService;
    }
    
   
}
