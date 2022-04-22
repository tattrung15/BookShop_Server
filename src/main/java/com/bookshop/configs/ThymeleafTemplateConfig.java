package com.bookshop.configs;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafTemplateConfig {

    /*
     *   Spring Boot auto-configuration by default
     *
     *   @Bean
     *   public SpringTemplateEngine springTemplateEngine() {
     *       SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
     *       springTemplateEngine.addTemplateResolver(emailTemplateResolver());
     *       return springTemplateEngine;
     *   }
     */

    /*
     *   Spring Boot auto-configuration by default
     *
     *   public ClassLoaderTemplateResolver emailTemplateResolver() {
     *       ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
     *       emailTemplateResolver.setPrefix("/templates/");
     *       emailTemplateResolver.setSuffix(".html");
     *       emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
     *       emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
     *       emailTemplateResolver.setCacheable(false);
     *       return emailTemplateResolver;
     *   }
     */
}
