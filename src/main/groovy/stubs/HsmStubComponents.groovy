package stubs

import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by francis on 19/07/15.
 */
@Configuration
class HsmStubComponents {

  @Bean
  LoggingHandler loggingHandler() {
    return new LoggingHandler(LogLevel.INFO);
  }

  @Bean
  LengthFieldPrepender hsmLengthPrepender() {
    return new LengthFieldPrepender(4);
  }

}
