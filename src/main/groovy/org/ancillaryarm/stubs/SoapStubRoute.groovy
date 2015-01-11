package org.ancillaryarm.stubs

import org.ancillaryarm.stubsmgmt.StubsRouteBuilder
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
class SoapStubRoute extends StubsRouteBuilder {

  @Override
  void configure() throws Exception {

    from("cxf:http://localhost:8081/stubs/StockQuoteService?wsdlURL=StockQuote.wsdl&dataFormat=PAYLOAD")
        .process(new ScriptProcessor())


  }

  class ScriptProcessor implements Processor {

    @Override
    void process(Exchange exchange) throws Exception {
      Binding binding = new Binding();
      binding.setVariable("in.body", exchange.getIn().getBody());
      GroovyShell shell = new GroovyShell(binding);

      Object value = shell.evaluate(getScript())
      Object responseCode = binding.getVariable("CamelHttpResponseCode")
      responseCode != null ? exchange.getOut().setHeader("CamelHttpResponseCode", responseCode) : null

      exchange.getOut().setBody(value)

    }
  }
}
