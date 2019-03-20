package com.zensar.custom.bean;

import com.zensar.service.HelloService;

public class CustomHelloService implements HelloService{
	@Override
    public void hello() {
        System.out.println("We are overriding our custom Hello Service");
    }
}
