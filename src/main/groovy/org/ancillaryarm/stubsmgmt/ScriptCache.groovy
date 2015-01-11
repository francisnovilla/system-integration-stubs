package org.ancillaryarm.stubsmgmt

import org.springframework.stereotype.Component

@Component
class ScriptCache {

  Map defaultCache = [:]
  Map cache = [:]

  String getScript(scriptName) {
    cache[scriptName]
  }

  def setScript(scriptName, script) {
    cache[scriptName] = script
  }

  def setDefaultScript(scriptName, script) {
    defaultCache[scriptName] = script
  }

  def resetScript(scriptName) {
    cache[scriptName] = defaultCache[scriptName]
  }

}