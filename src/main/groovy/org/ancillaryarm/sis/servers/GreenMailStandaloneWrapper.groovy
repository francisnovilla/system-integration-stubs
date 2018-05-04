package org.ancillaryarm.sis.servers

import com.icegreen.greenmail.standalone.GreenMailStandaloneRunner
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class GreenMailStandaloneWrapper {

  @PostConstruct
  void init() {
    Properties properties = new Properties()
    properties.put('greenmail.setup.test.all', true)
    //properties.put('greenmail.verbose', true)
    GreenMailStandaloneRunner.configureLogging(properties);
    new GreenMailStandaloneRunner().doRun(properties);
  }

}
