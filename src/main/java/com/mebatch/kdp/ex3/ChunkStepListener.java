package com.mebatch.kdp.ex3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.context.annotation.Profile;

/**
 * ChunkListener provides interceptions and life cycle methods for spring batch
 * chunks. We use chunks when we are working on a set of items that are to be
 * combined as a unit within a transaction. There are two methods beforeChunk()
 * and afterChunk().
 * 
 * The beforeChunk() method gets executed after the transaction has started but
 * before it executes the read on ItemReader. The afterChunk() method gets
 * executed post the commit of the chunk.
 * 
 * @author pawan
 *
 */
@Profile("ex3")
public class ChunkStepListener implements ChunkListener {

	Logger logger = LoggerFactory.getLogger(ChunkStepListener.class);

	@Override
	public void beforeChunk(ChunkContext chunkContext) {
		logger.info("beforeChunk");
	}

	@Override
	public void afterChunk(ChunkContext chunkContext) {
		logger.info("afterChunk");
	}

	@Override
	public void afterChunkError(ChunkContext chunkContext) {
		logger.error("afterChunkError");
	}
}