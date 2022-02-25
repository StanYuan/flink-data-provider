package data.provider;

import data.provider.mongo.service.DataProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/21 11:23
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = "data.provider.mongo.repository")
@Slf4j
public class AppStarter {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AppStarter.class);
        DataProviderService dataProvider = context.getBean(DataProviderService.class);
        if(StringUtils.isBlank(System.getProperty("date"))){
            log.info("date系统参数不可为空! 系统退出!");
            return;
        }
        dataProvider.provideDataStream(System.getProperty("date"));
    }
}
