package stubs

import io.netty.buffer.Unpooled
import org.ancillaryarm.sis.stubsmanagement.StubsRouteBuilder
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.springframework.stereotype.Component

/**
 * Created by francis on 18/07/15.
 */
@Component
class HsmStubRoute extends StubsRouteBuilder {

  @Override
  void configure() throws Exception {

    from("netty4:tcp://localhost:32300?sync=true&decoders=#loggingHandler,hsmFrameDecoder&encoders=#hsmLengthPrepender")
        .process(new Processor() {
      @Override
      void process(Exchange exchange) throws Exception {

        exchange.in.body = Unpooled.copiedBuffer('ABC'.bytes)
      }
    })

  }
}
