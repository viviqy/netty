package com.fairychar.rpc.provider.handler;

import com.fairychar.rpc.common.RpcClass;
import com.fairychar.rpc.common.utils.RpcCallUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Datetime: 2021/3/12 14:18 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
public class RequestHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Gson gson = new Gson();
        try {
            RpcClass rpcClass = gson.fromJson(msg, RpcClass.class);
            Object program = RpcCallUtil.proxyRpcProgram(rpcClass);
            System.out.println(program.getClass().getDeclaredMethods());
            Method declaredMethod = program.getClass().getDeclaredMethod(rpcClass.getMethod(), null);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(program, rpcClass.getArgs());
            System.out.println(invoke);
            System.out.println(program);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
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