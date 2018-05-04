package stubs

import org.ancillaryarm.sis.stubsmanagement.StubsRouteBuilder
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.springframework.stereotype.Component

/**
 * Created by francis on 3/01/15.
 */
@Component
class RestGetStubRoute extends StubsRouteBuilder {

  @Override
  void configure() throws Exception {

    rest("/stubs")
        .get("/restget")
        .to("direct:get")

    from("direct:get")
        .process(defaultExchangeSaver)
        .process(new ScriptProcessor())

  }

  class ScriptProcessor implements Processor {

    @Override
    void process(Exchange exchange) throws Exception {
      Binding binding = new Binding()
      binding.setVariable("in.body", exchange.getIn().getBody())
      GroovyShell shell = new GroovyShell(binding)

      Object value = shell.evaluate(getScript())
      def vars = binding.variables
      vars.ResponseCode ? exchange.out.headers['CamelHttpResponseCode'] = vars.ResponseCode : null
      vars.ContentType ? exchange.out.headers[Exchange.CONTENT_TYPE] = vars.ContentType : null

      exchange.getOut().setBody(value)

    }
  }
}
