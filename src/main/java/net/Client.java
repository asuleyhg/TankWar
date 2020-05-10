package net;

import com.yihg.tank.GameModel;
import com.yihg.tank.TankFream;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static final Client INSTANCE = new Client();

    private Channel channel = null;

    private Client(){}


    public void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try{
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                channel = socketChannel;
                socketChannel.pipeline()
                        .addLast(new MsgEncoder())
                        .addLast(new MsgDecoder())
                        .addLast(new MyHandler());
            }
        });
        ChannelFuture future = b.connect("localhost", 8888).sync();
        System.out.println("Connected to server!");
        future.channel().closeFuture().sync();
        System.out.println("Go on");

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(workerGroup != null){
                workerGroup.shutdownGracefully();
            }
        }

    }

    public void send(TankJoinMsg msg){
        channel.writeAndFlush(msg);
    }

    public void closeConnection(){
        channel.close();
    }

    private static class MyHandler extends SimpleChannelInboundHandler<TankJoinMsg> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TankJoinMsg msg) throws Exception {
            System.out.println(msg);
            msg.handle();

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new TankJoinMsg(TankFream.INSTANCE.getGm().getMyTank()));
        }
    }
}
