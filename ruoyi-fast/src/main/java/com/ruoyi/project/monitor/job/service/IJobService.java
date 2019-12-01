package com.ruoyi.project.monitor.job.service;

import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.project.monitor.job.domain.Job;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 定时任务调度信息信息 服务层
 *
 * @author ruoyi
 */
public interface IJobService {
    /**
     * 获取quartz调度器的计划任务
     *
     * @param job 调度信息
     * @return 调度任务集合
     */
    List<Job> selectJobList(Job job);

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    Job selectJobById(Long jobId);

    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     */
    int pauseJob(Job job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     */
    int resumeJob(Job job) throws SchedulerException;

    /**
     * 删除任务后，所对应的trigger也将被删除
     *
     * @param job 调度信息
     * @return 结果
     */
    int deleteJob(Job job) throws SchedulerException;

    /**
     * 批量删除调度信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    void deleteJobByIds(String ids) throws SchedulerException;

    /**
     * 任务调度状态修改
     *
     * @param job 调度信息
     * @return 结果
     */
    int changeStatus(Job job) throws SchedulerException;

    /**
     * 立即运行任务
     *
     * @param job 调度信息
     * @return 结果
     */
    void run(Job job) throws SchedulerException;

    /**
     * 新增任务
     *
     * @param job 调度信息
     * @return 结果
     */
    int insertJob(Job job) throws SchedulerException, TaskException;

    /**
     * 更新任务
     *
     * @param job 调度信息
     * @return 结果
     */
    int updateJob(Job job) throws SchedulerException, TaskException;

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    boolean checkCronExpressionIsValid(String cronExpression);
}