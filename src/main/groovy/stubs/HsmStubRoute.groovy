package stubs

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import org.ancillaryarm.sis.stubsmanagement.StubsRouteBuilder
import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.LoggingLevel
import org.apache.camel.Processor
import org.apache.camel.impl.JndiRegistry
import org.apache.camel.impl.PropertyPlaceholderDelegateRegistry
import org.apache.camel.spi.Registry
import org.apache.camel.spring.spi.ApplicationContextRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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

                exchange.in.body  = Unpooled.copiedBuffer('ABC'.bytes)
            }
        })

    }
}
