package net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
    //客户端一有连接，就将他的channel加到这个channelGroup中，这个集合由GlobalEventExecutor这个特有的线程来维护
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public void serverStart() {
        //
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            //启动Server的辅助类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //指定接客和服务的线程组，第一个参数代表他是接客的线程组，第二个参数代表他是用来服务的线程组
            ChannelFuture future = bootstrap.group(bossGroup, workerGroup)
                    //指定channel的类型，NioServerSocketChannel代表异步全双工
                    .channel(NioServerSocketChannel.class)
                    //netty帮我们内部处理accept的过程
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                            ByteBuf buf = (ByteBuf) msg;
//                                            ServerFrame.INSTANCE.updateClientMsg(buf.toString());
                                            //服务器收到客户端发来的消息之后，就把这个消息转发给所有的客户端
                                            clients.writeAndFlush(msg);
//                                          ctx.writeAndFlush(msg);
                                        }

                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            super.exceptionCaught(ctx, cause);
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            ServerFrame.INSTANCE.updateServerMsg("One Client connected!");
                                            //一有连接就把他加入到服务器里面的channel集合中,方便之后转发消息给所有客户端
                                            clients.add(ctx.channel());
                                        }
                                    });
                        }
                    }).bind(8888).sync();
            ServerFrame.INSTANCE.updateServerMsg("server started!");
            future.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
