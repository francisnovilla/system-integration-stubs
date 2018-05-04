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

import com.google.common.base.Charsets

import javax.xml.bind.DatatypeConverter

def s = new Socket("localhost", 32300);

s.withStreams { inStream, outStream ->
  outStream << DatatypeConverter.parseHexBinary('00000011') //17 bytes follow
  outStream << "Hello test server"  // send request first
  outStream << DatatypeConverter.parseHexBinary('00000011')
  outStream << "Hello test server"  // send request first
  sleep(1000)

  ByteArrayOutputStream buffer = new ByteArrayOutputStream();

  byte[] data = new byte[4096];
  while (inStream.available() > 0) {
    int c = inStream.read(data, 0, data.length)
    buffer.write(data, 0, c);
  }

  byte[] bytes = buffer.toByteArray()

  println DatatypeConverter.printHexBinary(bytes)
  println new String(bytes, Charsets.ISO_8859_1)

}
s.close();