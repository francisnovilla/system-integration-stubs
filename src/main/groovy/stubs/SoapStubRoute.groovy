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
package stubs

import org.ancillaryarm.sis.stubsmanagement.StubsRouteBuilder
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.springframework.stereotype.Component

@Component
class SoapStubRoute extends StubsRouteBuilder {

  @Override
  void configure() throws Exception {

    from("cxf:http://localhost:8081/stubs/StockQuoteService?wsdlURL=StockQuote.wsdl&dataFormat=PAYLOAD")
        .process(defaultExchangeSaver)
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
