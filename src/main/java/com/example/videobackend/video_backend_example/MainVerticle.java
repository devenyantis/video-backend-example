package com.example.videobackend.video_backend_example;

import com.zandero.rest.RestRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {

		Router router = RestRouter.register(vertx, new VideoEndpoint());

		vertx.createHttpServer()
				.requestHandler(router)
				.listen(9999);

		System.out.println("Server Up");
	}
}
