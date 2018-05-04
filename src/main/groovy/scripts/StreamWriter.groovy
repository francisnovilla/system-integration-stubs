package scripts

import com.google.common.base.Charsets

import javax.xml.bind.DatatypeConverter

/**
 * Created by francis on 18/07/15.
 */

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