package com.vincent78.jvm.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author ivan
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ImportAutoConfiguration({KeycloakAdminConfig.class, WebHttpConfig.class})
@Slf4j
@EnableScheduling
@EnableAsync
public class JVMApplication {
  public static void main(String[] args) {
    SpringApplication.run(JVMApplication.class, args);
    log.info("{}", " ------啓動完成-------");
  }

//  @Scheduled(cron = "0 0 0 */10 *  *")
//  private void testMaster() {
//    BackUpService.testMaster();
//  }
//
//  @Scheduled(cron = "0 0 0 */10 *  *")
//  private void testslave() {
//    BackUpService.testSlave();
//  }
//
//  @Scheduled(cron = "0 0 0 */10 *  *")
//  private void deleteMaster() throws ParseException {
//    BackUpService.deleteMaster();
//    BackUpService.deleteSlave();
//  }
//
//  @Scheduled(cron = "0 */1 * * *  *")
//  private void resourceLog() {
//    ResourceLogsService.resourceLog();
//  }
//  @Scheduled(cron = "0 */30 * * *  *")
//  private void ldapUserSecret() {
//    LdapUserSecretService.ldapUserSecret();
//  }
//
//  @Scheduled(cron = "0 0 1 * * ?")
////  @Scheduled(cron = "*/5 * * * * ?")
//  private void authorityRevoke() {
//    AuthorityRevokeService.synchronize();
//  }
//  @Scheduled(cron = "5 0 1 * * ?")
//  private void authorityRevokeHistory() {
//    AuthorityRevokeService.autoRevoke();
//  }
}
