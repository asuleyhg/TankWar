package nettyChat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public static void main(String[] args) throws Exception{
        //
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        //启动Server的辅助类
        ServerBootstrap bootstrap = new ServerBootstrap();
        //指定接客和服务的线程组，第一个参数代表他是接客的线程组，第二个参数代表他是用来服务的线程组
        bootstrap.group(bossGroup,workerGroup);
        //指定channel的类型，NioServerSocketChannel代表异步全双工
        bootstrap.channel(NioServerSocketChannel.class);
        //netty帮我们内部处理accept的过程
        bootstrap.childHandler(new MyChildInitializer());
        //
        bootstrap.bind(8888).sync();
    }


}

class MyChildInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new MyChildHandler());
    }


}class MyChildHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString());
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
