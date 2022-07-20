package com.example.mybatisplustest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplustest.entity.User;
import com.example.mybatisplustest.mapper.UserMapper;
import com.example.mybatisplustest.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl extends ServiceImpl<UserMapper, User> implements UserService {

}
