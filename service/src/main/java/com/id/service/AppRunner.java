package com.id.service;

import com.id.service.service.UserService;

public class AppRunner {
    public static void main(String[] args) {
        var userService = new UserService();
        System.out.println(userService.getUser(25L));
    }
}
