package hapohalim.routers.XML;

import hapohalim.routers.SimpleRouterFactory;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class XMLRouter implements SimpleRouterFactory {
  private XMLParser xmlParser;
  public XMLRouter() {
    this.xmlParser = new XMLParser();
  }

  @Override
  public Router getRouter(Vertx vertx) {
    Router router = Router.router(vertx);

    /*
    return count of valid HTML Tag Scopes <a> </a> = + 1
    Type: post
    Accept: Application/json
    Data: { "html": @someHTML }
    Response:
    {"validTags" @tagCount }
     */
    router.post("/count").handler(context -> {
      context.response().putHeader("Content-Type", "application/json");
      JsonObject data = context.getBodyAsJson();
      String html = data.getString("html");
      JsonObject resBody = new JsonObject()
        .put("validTags", xmlParser.getTagCount(html));
      context.response().end(Json.encodePrettily(resBody));
    });

    return router;
  }
}
