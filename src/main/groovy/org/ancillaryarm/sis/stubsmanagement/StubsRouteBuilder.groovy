package org.ancillaryarm.sis.stubsmanagement

import com.google.common.base.Function
import com.google.common.collect.Maps
import org.ancillaryarm.sis.exchangesreceived.ExchangeCache
import org.ancillaryarm.sis.exchangesreceived.StubExchange
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

/**
 * Loads default stub scripts, relies on naming convention (replacing trailing 'Route' with 'Script')
 */
abstract class StubsRouteBuilder extends RouteBuilder {

  @Autowired
  ScriptCache scriptCache

  @Autowired
  ExchangeCache exchangeCache


  String scriptName

  StubsRouteBuilder() {
    scriptName = this.class.getSimpleName().replaceFirst('Route', 'Script')
  }

  @PostConstruct
  def init() {
    println scriptName
    def script = this.class.getResource(scriptName + '.groovy').text
    if (script) {
      scriptCache.setDefaultScript(scriptName, script)
      scriptCache.setScript(scriptName, script)
    }
  }

  String getScript() {
    scriptCache.getScript(scriptName)
  }

  Processor defaultExchangeSaver = new Processor() {
    @Override
    void process(Exchange exchange) throws Exception {
      StubExchange stubExchange = new StubExchange(
          inBody: exchange.getIn().getBody(String.class),
          properties: Maps.transformValues(exchange.getProperties(), new Function() {
            @Override
            Object apply(Object o) {
              return o as String
            }
          }),
          headers: Maps.transformValues(exchange.getIn().getHeaders(), new Function() {
            @Override
            Object apply(Object o) {
              return o as String
            }
          })
      )
      exchangeCache.setExchange(scriptName, stubExchange)
    }
  }


}