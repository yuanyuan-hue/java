package com.qdc.demoeurekazuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

public class MyPreFilter extends ZuulFilter {
//    过滤器类型
    @Override
    public String filterType() {
        return "pre";
    }

//    过滤器的执行顺序
    @Override
    public int filterOrder() {
        return 2;
    }

//    是否执行过滤
    @Override
    public boolean shouldFilter() {
        return true;
    }

//    过滤器执行过程
    @Override
    public Object run() throws ZuulException {
        System.out.println("MyPreFilter");
        return null;
    }
}
