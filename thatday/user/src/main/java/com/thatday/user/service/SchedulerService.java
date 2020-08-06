package com.thatday.user.service;

import com.thatday.common.exception.TDExceptionHandler;
import com.thatday.user.task.TestTask;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@SuppressWarnings("unchecked")
public class SchedulerService {

    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addXXXJob(Object data) {
        String jobName = "XXX";
        String jobGroup = "XXX";
        addFixedTimeJob(jobName, jobGroup, TestTask.class, data, new Date());
    }

    //固定时间的
    private void addFixedTimeJob(String jobName, String jobGroup, Class<? extends Job> targetClass, Object data, Date fixedTime) {
        try {
            deleteJob(jobName, jobGroup);

            JobDataMap dataMap = new JobDataMap();
            dataMap.put(data.getClass().getSimpleName(), data);

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                    .startAt(fixedTime)
                    .usingJobData(dataMap).build();

            scheduler.scheduleJob(getJobDetail(jobName, jobGroup, targetClass), trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //周期性的
    private void addCronJob(String jobName, String jobGroup, Class<? extends Job> targetClass, Object data, int afterSecondStart, String cron) {
        try {
            deleteJob(jobName, jobGroup);

            JobDataMap dataMap = new JobDataMap();
            dataMap.put(data.getClass().getSimpleName(), data);

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                    .startAt(DateBuilder.futureDate(afterSecondStart, DateBuilder.IntervalUnit.SECOND))
                    .usingJobData(dataMap)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

            scheduler.scheduleJob(getJobDetail(jobName, jobGroup, targetClass), trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JobDetail getJobDetail(String jobName, String jobGroup, Class<? extends Job> targetClass) throws Exception {
        Class<? extends Job> jobClass = targetClass.newInstance().getClass();
        return JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
    }

    private void deleteJob(String jobName, String jobGroup) {
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            throw TDExceptionHandler.throwGlobalException("cancelJob", e);
        }
    }
}
