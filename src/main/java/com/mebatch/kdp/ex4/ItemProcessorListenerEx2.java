package com.mebatch.kdp.ex4;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

public class ItemProcessorListenerEx2 implements ItemProcessListener<String, Future<String>> {

	Logger logger = LoggerFactory.getLogger(ItemProcessorListenerEx2.class);

	@Override
	public void beforeProcess(String item) {
		logger.info("beforeProcess ItemProcessorListenerEx2");
	}

	@Override
	public void onProcessError(String item, Exception e) {
		logger.error(" onProcessError ItemProcessorListenerEx2");
	}

	@Override
	public void afterProcess(String item, Future<String> result) {
		// TODO Auto-generated method stub

	}
}