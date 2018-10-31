package sk.smadis.parser;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import sk.smadis.parser.jobs.ProcessEmails;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Class that constructs Quartz Job
 *
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Startup
@Singleton
public class JobScheduler {

    private Logger logger = Logger.getLogger(this.getClass());

    @Inject
    private CDIJobFactory jobFactory;

    private Scheduler scheduler;

    @PreDestroy
    public void destroyJob() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error("Error while destroying job.", e);
        }
    }

    @PostConstruct
    public void init() {
        try {

            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.setJobFactory(jobFactory);

            JobDetail job = JobBuilder.newJob(ProcessEmails.class)
                    .withIdentity("Parser", "sk.smadis.mailparser")
                    .build();

            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("Mailparser", "sk.smadis.mailparser")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

            scheduler.start();

        } catch (Throwable t) {
            logger.error("Init of scheduler.", t);
        }
    }
}
