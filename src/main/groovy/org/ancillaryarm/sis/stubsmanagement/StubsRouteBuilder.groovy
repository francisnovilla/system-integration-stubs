/*
 * Copyright (C) 2018 Francis Novilla
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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