package com.mebatch.kdp.controller;

import java.util.Calendar;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class JobLaunchService {
	@Autowired
	private JobLauncher jobLauncher;

	public void launchJob(Job job) {
		try {
			JobParameters jobParameters = new JobParametersBuilder()
					.addDate("timestamp", Calendar.getInstance().getTime()).toJobParameters();
			JobExecution jobExecution = jobLauncher.run(job, jobParameters);

		} catch (Exception e) {

			throw new RuntimeException("launch job exception ", e);
		}
	}
}