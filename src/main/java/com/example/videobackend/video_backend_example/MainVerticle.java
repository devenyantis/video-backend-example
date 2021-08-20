package com.example.videobackend.video_backend_example;

import com.zandero.rest.RestRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {

		Router router = RestRouter.register(vertx, new VideoEndpoint());

		router.route().handler(io.vertx.ext.web.handler.CorsHandler.create(".*.")
				.allowedMethod(io.vertx.core.http.HttpMethod.GET)
				.allowedMethod(io.vertx.core.http.HttpMethod.POST)
				.allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
				.allowedMethod(HttpMethod.DELETE)
				.allowedMethod(io.vertx.core.http.HttpMethod.PUT)
				.allowedHeader("Access-Control-Allow-Method")
				.allowedHeader("Access-Control-Allow-Origin")
				.allowedHeader("Cache-Control")
				.allowedHeader("Pragma")
				.allowedHeader("Content-Type"));

		vertx.createHttpServer()
				.requestHandler(router)
				.listen(9999);

		System.out.println("Server Up");
	}
}
