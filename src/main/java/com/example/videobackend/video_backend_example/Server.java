package com.example.videobackend.video_backend_example;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Server {
    public static void main(String[] args) {
        VertxOptions vertxOptions = new VertxOptions();

        vertxOptions.setMaxEventLoopExecuteTimeUnit(TimeUnit.SECONDS);
        vertxOptions.setMaxEventLoopExecuteTime(15); //for bulk images upload

        DeploymentOptions opt = new DeploymentOptions();
        opt.setWorker(true);

        Vertx vertx = Vertx.vertx(vertxOptions);
        vertx.deployVerticle(new MainVerticle(), opt);

        System.out.println("Main verticle deploy");

    }
}
