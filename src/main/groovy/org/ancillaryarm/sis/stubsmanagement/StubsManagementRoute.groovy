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

import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.rest.RestBindingMode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

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
        .post("/{stub}").bindingMode(RestBindingMode.off)
        .to("direct:stubsmanagement-set")
        .delete("/{stub}")
        .to("direct:stubsmanagement-reset")


    from("direct:stubsmanagement-get")
        .setHeader(Exchange.CONTENT_TYPE, simple("text/plain"))
        .process { exchange ->
      String scriptName = exchange.getIn().getHeader('stub')
      String script = scriptCache.getScript(scriptName)
      exchange.getIn().setBody(script, String.class)
    }

    from("direct:stubsmanagement-set")
        .setHeader(Exchange.CONTENT_TYPE, simple("text/plain"))
        .process { exchange ->
      String scriptName = exchange.getIn().getHeader('stub')
      String script = exchange.getIn().getBody(String.class)
      scriptCache.setScript(scriptName, script)
    }

    from("direct:stubsmanagement-reset")
        .process { exchange ->
      String scriptName = exchange.getIn().getHeader('stub')
      scriptCache.resetScript(scriptName)
    }
  }

}
