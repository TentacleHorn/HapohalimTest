package hapohalim.routers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class DefaultRouter implements SimpleRouterFactory {
  @Override
  public Router getRouter(Vertx vertx) {
    Router router = Router.router(vertx);
    router.get("/").handler(context -> {
      context.response().end(
        "Welcome to our API." +
          "\nCurrent available routes:" +
          "\n * /xml/count");
    });
    return router;
  }
}
