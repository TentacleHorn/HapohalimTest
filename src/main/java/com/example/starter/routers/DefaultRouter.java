package com.example.starter.routers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class DefaultRouter implements SimpleRouterFactory {
  @Override
  public Router getRouter(Vertx vertx) {
    Router router = Router.router(vertx);
    router.get("/").handler(context -> {
      context.request().response().end(
        "Welcome to our API." +
          "\nCurrent available routes:" +
          "\n * /xml");
    });
    return router;
  }
}
