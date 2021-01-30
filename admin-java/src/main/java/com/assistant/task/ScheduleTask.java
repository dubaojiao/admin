package com.assistant.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {

    // 每天 10点
    @Scheduled(cron = "0 0 10 ? * *")
    private void configureTasks1() {

    }

    //每天 12点 启动
    @Scheduled(cron = "0 0 12 ? * *")
    private void configureTasks2() {

    }


    //每天 14点
    @Scheduled(cron = "0 0 14 ? * *")
    private void configureTasks3() {

    }

    //每天 16点 启动
    @Scheduled(cron = "0 0 16 ? * *")
    private void configureTasks4() {

    }

    //每天 18点 启动
    @Scheduled(cron = "0 0 18 ? * *")
    private void configureTasks5() {

    }
}
