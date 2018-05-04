package org.ancillaryarm.sis.exchangesreceived

import org.apache.camel.Exchange
import org.springframework.stereotype.Component

/**
 * Runtime store of stub scripts. Keeps a copy of default stub (loaded from groovy scripts) to enable resets.
 */
@Component
class ExchangeCache {

  Map cache = [:]

  def getExchange(String scriptName) {
    cache[scriptName]
  }

  def setExchange(String scriptName, Exchange exchange) {
    cache[scriptName] = exchange
  }

}