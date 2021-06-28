package com.fairychar.netty.learning;

import com.fairychar.netty.learning.server.hander.AsyncLongWaitHandler;
import com.fairychar.netty.learning.server.hander.LongWaitHandler;
import com.fairychar.netty.learning.server.hander.TickChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * Datetime: 2021/1/8 12:23 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
public class App {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            final LoggingHandler loggingHandler = new LoggingHandler();
            ChannelFuture bindFuture = serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(loggingHandler)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("channel hashcode=" + ch.hashCode());
                            StringEncoder stringEncoder = new StringEncoder();
                            StringDecoder stringDecoder = new StringDecoder();
                            LongWaitHandler longWaitHandler = new LongWaitHandler(10);
                            TickChannelHandler tickChannelHandler = new TickChannelHandler(10);
                            AsyncLongWaitHandler asyncLongWaitHandler = new AsyncLongWaitHandler(5);
                            ch.pipeline().addLast(loggingHandler)
                                    .addLast(stringEncoder)
                                    .addLast(stringDecoder)
//                                    .addLast(longWaitHandler)
                            .addLast(tickChannelHandler)
                            ;

                        }
                    })
                    .bind(10000);
            bindFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("绑定端口成功");
                }
            });
            bindFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
/*
                                      /[-])//  ___        
                                 __ --\ `_/~--|  / \      
                               /_-/~~--~~ /~~~\\_\ /\     
                               |  |___|===|_-- | \ \ \    
____________ _/~~~~~~~~|~~\,   ---|---\___/----|  \/\-\   
____________ ~\________|__/   / // \__ |  ||  / | |   | | 
                      ,~-|~~~~~\--, | \|--|/~|||  |   | | 
                      [3-|____---~~ _--'==;/ _,   |   |_| 
                                  /   /\__|_/  \  \__/--/ 
                                 /---/_\  -___/ |  /,--|  
                                 /  /\/~--|   | |  \///   
                                /  / |-__ \    |/         
                               |--/ /      |-- | \        
                              \^~~\\/\      \   \/- _     
                               \    |  \     |~~\~~| \    
                                \    \  \     \   \  | \  
                                  \    \ |     \   \    \ 
                                   |~~|\/\|     \   \   | 
                                  |   |/         \_--_- |\
                                  |  /            /   |/\/
                                   ~~             /  /    
                                                 |__/   W<

*/