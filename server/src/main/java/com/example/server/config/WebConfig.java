package com.example.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 图片保存路径，自动从yml文件中获取数据
     *   示例： E:/images/
     */
    private final String avatarPath=System.getProperty("user.dir")+"/File/avatar/";
    private final String firstPicturePath=System.getProperty("user.dir")+"/File/firstPicture/";
    private final String paperPath=System.getProperty("user.dir")+"/File/paper/";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 配置资源映射
         * 意思是：如果访问的资源路径是以“/images/”开头的，
         * 就给我映射到本机的“E:/images/”这个文件夹内，去找你要的资源
         * 注意：E:/images/ 后面的 “/”一定要带上
         */
        registry.addResourceHandler("/avatar/**")
                .addResourceLocations("file:"+avatarPath);

        registry.addResourceHandler("/firstPicture/**")
                .addResourceLocations("file:"+firstPicturePath);

        registry.addResourceHandler("/paper/**")
                .addResourceLocations("file:"+paperPath);
    }

}

