package stubs

import io.netty.channel.ChannelHandler
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import org.apache.camel.component.netty4.ChannelHandlerFactory
import org.springframework.stereotype.Component

/**
 * Created by francis on 19/07/15.
 */
@Component
public class HsmFrameDecoder extends LengthFieldBasedFrameDecoder implements ChannelHandlerFactory {

  HsmFrameDecoder() {
    super(1024, 0, 4);
  }

  @Override
  ChannelHandler newChannelHandler() {
    return new HsmFrameDecoder()
  }
}
