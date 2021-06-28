package com.fairychar.netty.learning.server.hander;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Datetime: 2021/6/24 11:46 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@ChannelHandler.Sharable
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @CauseType(NullPointerException.class)
    private void handleNull(ChannelHandlerContext ctx,NullPointerException e){
        System.out.println("处理了空指针");
    }


    @CauseType(ArrayIndexOutOfBoundsException.class)
    private void handleOut(ChannelHandlerContext ctx,ArrayIndexOutOfBoundsException e){
        System.out.println("处理了数组超长");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        dispatch(ctx, cause);
    }

    private void dispatch(ChannelHandlerContext ctx, Throwable cause) {
        Method[] declaredMethods = this.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            CauseType causeType = method.getDeclaredAnnotation(CauseType.class);
            Optional.ofNullable(causeType).ifPresent(a->{
                Class type = a.value();
                if (cause.getClass() == type){
                    method.setAccessible(true);
                    try {
                        method.invoke(this,ctx,cause);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    @Documented
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @interface CauseType {
        Class value();
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