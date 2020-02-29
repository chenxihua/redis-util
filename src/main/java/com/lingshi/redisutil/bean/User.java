package com.lingshi.redisutil.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: User
 * @Create By: chenxihua
 * @Author: Administrator
 * @Date: 2020/2/29 15:19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {


    private Integer id;
    private String username;
    private String password;


}
