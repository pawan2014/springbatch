package com.mebatch.kdp.ex2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

public class ItemProcessorListenerEx2 implements ItemProcessListener<String, String> {

	Logger logger = LoggerFactory.getLogger(ItemProcessorListenerEx2.class);

	@Override
	public void beforeProcess(String item) {
		logger.info("beforeProcess ItemProcessorListenerEx2");
	}

	@Override
	public void afterProcess(String item, String result) {
		logger.info("afterProcess ItemProcessorListenerEx2");
	}

	@Override
	public void onProcessError(String item, Exception e) {
		logger.error(" onProcessError ItemProcessorListenerEx2");
	}
}