package hapohalim;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

@ExtendWith(VertxExtension.class)
public class TestXMLRoute {

  private WebClient client;

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
    client = WebClient.create(vertx);
  }


  void testHTML(String html, int expectedValue, VertxTestContext testContext) throws Throwable {
    try {
      JsonObject data = new JsonObject()
        .put("html", html);

      client.post(8888, "localhost", "/xml/count").sendJson(data, res -> {
        if (res.failed()) {
          testContext.failNow(new IOException("request failed"));
        }
        int actualValue = res.result().bodyAsJsonObject().getInteger("validTags");
        if (actualValue != expectedValue) {
          testContext.failNow(new Exception("incorrect return value : " + actualValue));
        }
        testContext.completeNow();
      });
    } catch (Exception e) {
      testContext.failNow(e);
    }
  }

  @Test
  void testHTMLRegular1(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testHTML("<html><head></head><body><div><div></div></body></html>", 4, testContext);
  }
  @Test
  void testHTMLRegular2(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testHTML("<html><head></head><body><div><div></div></div></body></html>", 5, testContext);
  }
  @Test
  void testHTMLRegular3(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testHTML("<html><head></head><body><div><div></div></div>", 3, testContext);
  }
  @Test
  void testHTMLRegular4(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testHTML("<html><head></head><body><div><div></div></div></p></i>", 3, testContext);
  }

  @Test
  void testHTMLCloseOrder1(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testHTML("<div><a></div></a>", 1, testContext);
  }


  @Test
  void testHTMLJunkText1(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testHTML("<div>text</div>", 1, testContext);
  }

  @Test
  void testHTMLJunkText2(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testHTML("<div><a>text</div>extra text</a>", 1, testContext);
  }







}
