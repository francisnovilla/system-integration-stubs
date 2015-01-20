package org.ancillaryarm.sis.stubsmanagement

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.builder.RouteBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by francis on 3/01/15.
 */
@Component
class StubsManagementRoute extends RouteBuilder {

  Logger LOGGER = LoggerFactory.getLogger(StubsManagementRoute.class)

  @Autowired
  ScriptCache scriptCache


  @Override
  void configure() throws Exception {

    rest("/stubsmanagement")
        .get("/{stub}")
        .to("direct:stubsmanagement-get")
        .post("/{stub}")
        .to("direct:stubsmanagement-set")
        .delete("/{stub}")
        .to("direct:stubsmanagement-reset")


    from("direct:stubsmanagement-get")
        .process(new Processor() {
      @Override
      void process(Exchange exchange) throws Exception {
        String scriptName = exchange.getIn().getHeader('stub')
        String script = scriptCache.getScript(scriptName)
        exchange.getIn().setBody(script, String.class)
      }
    })

    from("direct:stubsmanagement-set")
        .process(new Processor() {
      @Override
      void process(Exchange exchange) throws Exception {
        String scriptName = exchange.getIn().getHeader('stub')
        String script = exchange.getIn().getBody(String.class)
        scriptCache.setScript(scriptName, script)
      }
    })

    from("direct:stubsmanagement-reset")
        .process(new Processor() {
      @Override
      void process(Exchange exchange) throws Exception {
        String scriptName = exchange.getIn().getHeader('stub')
        scriptCache.resetScript(scriptName)
      }
    })


  }


}
