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
package scripts

import org.ancillaryarm.sis.stubsmanagement.StubsMain

// http://camel.apache.org/running-camel-standalone-and-have-it-keep-running.html
main = new StubsMain()

// run until you terminate the JVM
System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
String[] args = ["-ac", "stubs-spring-context.xml"]
main.run(args)