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