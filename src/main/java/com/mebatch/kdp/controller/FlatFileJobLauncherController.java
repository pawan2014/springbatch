package com.mebatch.kdp.controller;

import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;

public class FlatFileJobLauncherController {

	@Autowired
	private JobLaunchService jobLaunchService;

	@Autowired
	private Job jobUseFlatFileWithRepositoryItemWriter;

	@Autowired
	private Job jobUseFlatFileWithJdbcBatchItemWriter;

	// @GetMapping("/repos")
	public void launchJobUseFlatFileWithRepositoryItemWriter() {
		// return jobLaunchService.launchJob(jobUseFlatFileWithRepositoryItemWriter);
	}

	// @GetMapping("/jdbc")
	public void launchJobUseFlatFileWithJdbcBatchItemWriter() {
		// return jobLaunchService.launchJob(jobUseFlatFileWithJdbcBatchItemWriter);
	}

}