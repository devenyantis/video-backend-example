package com.example.videobackend.video_backend_example;

import io.vertx.core.Vertx;

public class Server {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());

        System.out.println("Main verticle deploy");

    }
}
