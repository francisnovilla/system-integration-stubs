package org.ancillaryarm.stubs

import org.apache.camel.Body
import org.apache.camel.Exchange
import org.apache.camel.Handler
import org.apache.camel.Processor
import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

/**
 * Created by francis on 3/01/15.
 */
@Component
class RestGetStubRoute extends RouteBuilder {

  String script

  @Override
  void configure() throws Exception {

    script = this.class.getResource('RestGetStub.groovy').text

// configure to use restlet on localhost with the given port
// and enable auto binding mode
    restConfiguration().component("restlet").host("localhost").port(8080)//.bindingMode(RestBindingMode.auto);

    rest("/stubs")
        .get("/restget")
        .to("direct:get")

    from("direct:get")
      .process(new ScriptProcessor())

  }

  class ScriptProcessor implements Processor {

    @Override
    void process(Exchange exchange) throws Exception {
      Binding binding = new Binding();
      binding.setVariable("in.body", exchange.getIn().getBody());
      GroovyShell shell = new GroovyShell(binding);

      Object value = shell.evaluate(script)
      Object responseCode = binding.getVariable("CamelHttpResponseCode")
      responseCode != null ? exchange.getOut().setHeader("CamelHttpResponseCode", responseCode) : null

      exchange.getOut().setBody(value)

    }
  }
}
