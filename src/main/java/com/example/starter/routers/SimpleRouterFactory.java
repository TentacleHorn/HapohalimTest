package com.example.starter.routers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public interface SimpleRouterFactory {

  public Router getRouter(Vertx vertx);
}
