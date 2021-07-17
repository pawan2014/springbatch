package com.mebatch.kdp.ex4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("ex4")
public class ServiceProcessor {

	public String getName(String str) {
		return str + "<<";
	}

	public Future<String> getNameF(String str) {
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		Executors.newCachedThreadPool().submit(() -> {
			Thread.sleep(500);
			completableFuture.complete(str + "<<>");
			return null;
		});
		return completableFuture;
	}

	public Future<String> getNameAsync(String str) {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> str + "<<>>");
		return future;
	}

}
