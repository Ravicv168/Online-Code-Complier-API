package com.example.compilerapi.config;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomRejectedExecutionHandler implements RejectedExecutionHandler{

	private static final Logger logger = LoggerFactory.getLogger(CustomRejectedExecutionHandler.class);
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		logger.error("Task rejected: {}", r);
        logger.error("Thread pool is full! Current pool size: {}, Active threads: {}, Queue size: {}, Task count: {}",
                executor.getPoolSize(),
                executor.getActiveCount(),
                executor.getQueue().size(),
                executor.getTaskCount());
		
	}

}
