package org.ancillaryarm.sis.servers

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

@Component
class WireMockServerWrapper {

  WireMockServer wireMockServer

  @PostConstruct
  void init() {
    wireMockServer = new WireMockServer(wireMockConfig().port(8089)) //No-args constructor will start on port 8080, no HTTPS
    wireMockServer.start()
  }
}
