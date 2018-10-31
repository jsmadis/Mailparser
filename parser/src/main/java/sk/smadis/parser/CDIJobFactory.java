package sk.smadis.parser;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

/**
 * Class solves a problem with injection of CDI managed beans into Quartz job.
 *
 * <a href="https://devsoap.com/injecting-cdi-managed-beans-into-quarz-jobs/">Source</a>
 *
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@ApplicationScoped
public class CDIJobFactory implements JobFactory {
    @Inject
    private BeanManager beanManager;

    @SuppressWarnings("unchecked")
    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        Class jobClass = triggerFiredBundle.getJobDetail().getJobClass();
        Bean bean = beanManager.getBeans(jobClass).iterator().next();
        CreationalContext ctx = beanManager.createCreationalContext(bean);
        return (Job) beanManager.getReference(bean, jobClass, ctx);
    }
}
