package com.bookshop.configs;

import com.bookshop.constants.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String productImagePath = "file:" + storageProperties.getLocation() + Common.PRODUCT_IMAGE_UPLOAD_PATH + "/";
        String productImagePattern = Common.PRODUCT_IMAGE_PATTERN_PATH + "/**";
        String bannerImagePath = "file:" + storageProperties.getLocation() + Common.BANNER_IMAGE_UPLOAD_PATH + "/";
        String bannerImagePattern = Common.BANNER_IMAGE_PATTERN_PATH + "/**";
        registry.addResourceHandler(productImagePattern).addResourceLocations(productImagePath);
        registry.addResourceHandler(bannerImagePattern).addResourceLocations(bannerImagePath);
    }

    /*
     *
     *   @Override
     *   public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
     *       converters.add(mappingJackson2HttpMessageConverter());
     *   }
     */

    /*
     *   @Bean
     *   public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
     *       MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
     *       ObjectMapper mapper = new ObjectMapper();
     *       mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
     *       mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
     *
     *       SimpleModule module = new SimpleModule();
     *       module.addDeserializer(String.class, new StringWithoutSpaceDeserializer(String.class));
     *       mapper.registerModule(module);
     *
     *       converter.setObjectMapper(mapper);
     *
     *       return converter;
     *   }
     */
}
