package com.thatday.user.service;

import com.thatday.common.exception.TDExceptionHandler;
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
        String targetClass = "com.thatday.user.task.TestTask";
        addJob(jobName, jobGroup, targetClass, data, new Date());
    }

    private void deleteJob(String jobName, String jobGroup) {
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            throw TDExceptionHandler.throwGlobalException("deleteJob", e);
        }
    }

    //固定时间的
    private void addJob(String jobName, String jobGroup, String targetClass, Object data, Date fixedTime) {
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
    private void addJob(String jobName, String jobGroup, String targetClass, Object data, int afterSecondStart, String cron) {
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

    private JobDetail getJobDetail(String jobName, String jobGroup, String targetClass) throws Exception {
        Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(targetClass).newInstance().getClass());
        return JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
    }
}
