package com.fairychar.netty.learning.server.hander;

import com.fairychar.netty.learning.util.ShowUtil;
import io.netty.channel.*;
import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Datetime: 2021/6/21 11:22 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@AllArgsConstructor
@ChannelHandler.Sharable
public class TickChannelHandler extends SimpleChannelInboundHandler<String> {

    private int wait;

    private final static ExecutorService single = Executors.newSingleThreadExecutor();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, final String msg) throws Exception {
        Runnable task = () -> {
            ShowUtil.showThread("channel will be tick after 5 seconds");
            try {
                TimeUnit.SECONDS.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //即使把链接关闭了,这句也不会报错
            ctx.channel().writeAndFlush("试试会报什么错");
            System.out.println("out: " + msg);
        };
        single.execute(task);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelFuture closeFuture = ctx.channel().closeFuture();
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                System.out.println("channel 不可用了");
            }
        });
        closeFuture.get();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("in task");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
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