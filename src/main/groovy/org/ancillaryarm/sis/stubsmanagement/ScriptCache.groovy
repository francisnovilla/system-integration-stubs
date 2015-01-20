package org.ancillaryarm.sis.stubsmanagement

import org.springframework.stereotype.Component

/**
 * Runtime store of stub scripts. Keeps a copy of default stub (loaded from groovy scripts) to enable resets.
 */
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