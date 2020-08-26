package com.example.starter.routers;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class XMLRouter implements SimpleRouterFactory {
  @Override
  public Router getRouter(Vertx vertx) {
    Router router = Router.router(vertx);

    router.post("/count").handler(context -> {
      context.response().end("xml");
    });

    return router;
  }
}
