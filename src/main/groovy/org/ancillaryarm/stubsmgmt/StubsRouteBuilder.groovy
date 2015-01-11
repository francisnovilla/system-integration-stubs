package org.ancillaryarm.stubsmgmt

import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

abstract class StubsRouteBuilder extends RouteBuilder {

   @Autowired
   ScriptCache scriptCache

  String scriptName

  StubsRouteBuilder() {
    scriptName = this.class.getSimpleName().replaceFirst('Route', 'Script')
  }

  @PostConstruct
  def init() {
    def script = this.class.getResource(scriptName + '.groovy').text
    scriptCache.setDefaultScript(scriptName, script)
    scriptCache.setScript(scriptName, script)
  }

  String getScript() {
    scriptCache.getScript(scriptName)
  }



}