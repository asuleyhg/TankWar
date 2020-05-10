package nettyChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {


    public void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try{
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new MyHandler());
            }
        });
        ChannelFuture future = b.connect("localhost", 8888).sync();
        future.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(workerGroup != null){
                workerGroup.shutdownGracefully();
            }
        }

    }

    private static class MyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(msg.toString());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ByteBuf buf = Unpooled.copiedBuffer("yihangguo".getBytes());
            ctx.writeAndFlush(buf);
        }
    }
}
