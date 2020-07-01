package com.thatday.user.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
//@DisallowConcurrentExecution //作业不并发
public class TestTask implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        //后面的key需要与addJob的时候的对应
        Object data = context.getMergedJobDataMap().get(Object.class.getSimpleName());
    }
}
