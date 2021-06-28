package com.fairychar.netty.learning.server.hander;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class AsyncLongWaitHandler extends SimpleChannelInboundHandler<String> {

    private int wait;

    private final static ExecutorService single = Executors.newSingleThreadExecutor();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, final String msg) throws Exception {
        Runnable task = () -> {
            System.out.println("in " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(wait);
            } catch (InterruptedException e) {
            }
            System.out.println("out: " + msg);
        };
        single.execute(task);
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