package org.ancillaryarm.sis.exchangesreceived

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.builder.RouteBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by francis on 20/01/15.
 */
@Component
class ExchangesReceivedRoute extends RouteBuilder {

  Logger LOGGER = LoggerFactory.getLogger(ExchangesReceivedRoute.class)

  @Autowired
  ExchangeCache exchangeCache

  @Override
  void configure() throws Exception {

    rest("/exchangesreceived")
        .get("/{stub}")
        .to("direct:exchangesreceived-get")


    from("direct:exchangesreceived-get")
        .process(new Processor() {
      @Override
      void process(Exchange exchange) throws Exception {
        String scriptName = exchange.getIn().getHeader('stub')
        def storedExchange = exchangeCache.getExchange(scriptName)
        exchange.getIn().setBody(storedExchange)
      }

    })
  }


}
