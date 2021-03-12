package com.fairychar.rpc.common.api;

import com.fairychar.rpc.common.pojo.Customer;

import java.io.Serializable;
import java.util.List;

/**
 * Datetime: 2021/3/12 14:06 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
public interface ICustomerService  {
    List<Customer> findAll();
}
