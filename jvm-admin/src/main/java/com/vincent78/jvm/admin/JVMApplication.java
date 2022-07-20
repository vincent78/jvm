package com.vincent78.jvm.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * 启动程序
 *
 * @author ivan
 */
@SpringBootApplication(scanBasePackages = {"com.vincent78.jvm.framework","com.vincent78.jvm.admin"})
@Slf4j
public class JVMApplication {
  public static void main(String[] args) {
    ApplicationContext context =
            new SpringApplicationBuilder(JVMApplication.class)
                    .web(WebApplicationType.SERVLET)
                    .build()
                    .run(args);
    for (String n : context.getBeanDefinitionNames()) {
      log.info("Instance bean is {}", n);
    }

    log.info("{}", " ------啓動完成-------");
  }

}
