package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.dao.JobDao;
import ro.teamnet.zth.appl.domain.Employee;
import ro.teamnet.zth.appl.domain.Job;

import java.util.List;

/**
 * Created by Robert.Dumitrescu on 7/24/2017.
 */
public class JobServiceImpl implements JobService {
    private final JobDao jobDao = new JobDao();

    public List<Job> findAll() {
        return jobDao.getAllJobs();
    }

    public Job findOne(String jobId) {
        return jobDao.getJobById(jobId);
    }

    public Boolean delete(String jobId) {
        Job jobToDelete = jobDao.getJobById(jobId);
        if (jobToDelete == null) {
            return false;
        }
        jobDao.deleteJob(jobToDelete);
        return true;

    }

    public Job save(Job job) {
        return jobDao.insertJob(job);
    }

    public Job update(Job job) {
        return jobDao.updateJob(job);
    }
}
