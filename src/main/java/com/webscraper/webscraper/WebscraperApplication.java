package com.webscraper.webscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class WebscraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebscraperApplication.class, args);
	}


	@Bean(name="workExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(30);
		executor.setMaxPoolSize(150);
		executor.setQueueCapacity(1000);
		executor.setThreadNamePrefix("worker-");
		executor.initialize();
		return executor;
	}

	@Bean(name="workExecutor2")
	public Executor workExecutorTwo() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(150);
		executor.setQueueCapacity(1000);
		executor.setThreadNamePrefix("work2-");
		executor.initialize();
		return executor;
	}

	@Bean(name="SymbolTask")
	public Executor SymbolTask() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(150);
		executor.setQueueCapacity(1000);
		executor.setThreadNamePrefix("SymbolTask-");
		executor.initialize();
		return executor;
	}

}
