package com.fairychar.rpc.provider.service;

import com.fairychar.rpc.common.api.ICustomerService;
import com.fairychar.rpc.common.pojo.Customer;

import java.util.Arrays;
import java.util.List;

/**
 * Datetime: 2021/3/12 14:16 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
public class CustomerService implements ICustomerService {
    @Override
    public List<Customer> findAll() {
        return Arrays.asList(
                new Customer(1, "ergou")
                , new Customer(2, "fengzi")
        );
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