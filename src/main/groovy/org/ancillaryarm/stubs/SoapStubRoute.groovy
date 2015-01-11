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
class SoapStubRoute extends RouteBuilder {

  String script

  @Override
  void configure() throws Exception {

    script = this.class.getResource('SoapStub.groovy').text

    from("cxf:http://localhost:8081/stubs/StockQuoteService?wsdlURL=StockQuote.wsdl&dataFormat=PAYLOAD")
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
