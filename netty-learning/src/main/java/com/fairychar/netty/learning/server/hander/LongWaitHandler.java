package com.fairychar.netty.learning.server.hander;

import com.fairychar.netty.learning.util.ShowUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * Datetime: 2021/6/21 11:22 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@AllArgsConstructor
@ChannelHandler.Sharable
public class LongWaitHandler extends SimpleChannelInboundHandler<String> {

    private int wait;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("in " + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(wait);
        System.out.println("out: " + msg);

        //放到了ctx.channel.eventLoop的taskQueue里
        Runnable task = () -> {
            ShowUtil.showThread("in eventLoop");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
            }
            ShowUtil.showThread("out eventLoop");
        };
        ctx .channel().eventLoop().execute(task);


        //放到了ctx.channel.eventLoop的scheduledTaskQueue里
        ctx.channel().eventLoop().schedule(task,3,TimeUnit.SECONDS);
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