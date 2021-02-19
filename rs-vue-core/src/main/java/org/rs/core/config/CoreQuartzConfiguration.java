package org.rs.core.config;

import java.util.Properties;
import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration(proxyBeanMethods = false)
public class CoreQuartzConfiguration {

	@Bean
	public SchedulerFactoryBean quartzScheduler(ApplicationContext ctx,DataSource dataSource) {
		
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		//schedulerFactoryBean.setDataSource(dataSource);
		SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
		jobFactory.setApplicationContext(ctx);
		schedulerFactoryBean.setJobFactory(jobFactory);
		 //quartz参数
        Properties prop = new Properties();
        //prop.put("org.quartz.scheduler.instanceName", "Task");
        //prop.put("org.quartz.scheduler.instanceId", "AUTO");
        //线程池配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "5");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        //JobStore配置
        //prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        //集群配置
        //prop.put("org.quartz.jobStore.isClustered", "false");
        //prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        //prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        //prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        //prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        schedulerFactoryBean.setQuartzProperties(prop);
        schedulerFactoryBean.setSchedulerName("RsSched");
        //延时启动
        schedulerFactoryBean.setStartupDelay(5);
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        schedulerFactoryBean.setOverwriteExistingJobs(true);
		return schedulerFactoryBean;
	}
}
