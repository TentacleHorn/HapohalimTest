package com.example.starter.routers.XML;

import com.example.starter.routers.SimpleRouterFactory;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;

public class XMLRouter implements SimpleRouterFactory {
  @Override
  public Router getRouter(Vertx vertx) {
    Router router = Router.router(vertx);

    XMLParser xmlParser = new XMLParser();

    router.post("/count").handler(context -> {
      JsonObject data = context.getBodyAsJson();
      String html = data.getString("html");
      context.response().end("xml " + xmlParser.getTagCount(html));
    });

    return router;
  }
}
