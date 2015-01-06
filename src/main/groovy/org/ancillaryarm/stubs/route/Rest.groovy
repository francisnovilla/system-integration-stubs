package org.ancillaryarm.stubs.route

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

/**
 * Created by francis on 3/01/15.
 */
@Component
class Rest extends RouteBuilder {

  @Override
  void configure() throws Exception {

// configure to use restlet on localhost with the given port
// and enable auto binding mode
    restConfiguration().component("restlet").host("localhost").port(8080)//.bindingMode(RestBindingMode.auto);

    rest("/say")
        .get("/hello").to("direct:hello")
        .get("/bye").consumes("application/json").to("direct:bye")
        .post("/bye").to("mock:update");

    from("direct:hello")
        .transform().constant("Hello World");
    from("direct:bye")
        .transform().constant("Bye World");


  }
}
