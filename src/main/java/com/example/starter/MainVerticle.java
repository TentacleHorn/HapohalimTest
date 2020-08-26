package com.example.starter;

import com.example.starter.routers.DefaultRouter;
import com.example.starter.routers.XMLRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();
    initRouters(server);
    server.listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  public void initRouters(HttpServer server) {
    Router router = Router.router(vertx);
    router.mountSubRouter("/", new DefaultRouter().getRouter(vertx));
    router.mountSubRouter("/xml", new XMLRouter().getRouter(vertx));
    server.requestHandler(router);
  }
}
