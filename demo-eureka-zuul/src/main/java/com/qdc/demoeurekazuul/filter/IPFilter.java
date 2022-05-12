package com.qdc.demoeurekazuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class IPFilter extends ZuulFilter {
    private String[] whitelist;
    @Value("${yxwfilter.ip.whitelist}")
    private String strIPWhitelist;
    @Value("${yxwfilter.ip.whitelistenabled}")
    private String WhitelistEnabled;
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        if("true".equalsIgnoreCase(WhitelistEnabled)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println(strIPWhitelist);
        whitelist=strIPWhitelist.split("\\,");
        RequestContext ctx=RequestContext.getCurrentContext();
        HttpServletRequest req=ctx.getRequest();
//        获取IP地址
        String ipAddr=this.getIpAddr(req);
        System.out.println("请求IP地址为:["+ipAddr+"]");
        List<String> ips=new ArrayList<>();
        for (int i=0;i<whitelist.length;i++){
            System.out.println(whitelist[i]);
            ips.add(whitelist[i]);
        }
        System.out.println("whitelist:"+ips.toString());
        if (!ips.contains(ipAddr)){
            System.out.println("未通过IP地址校验.["+ipAddr+"]");
            ctx.setResponseStatusCode(401);
            ctx.setSendZuulResponse(false);
            ctx.getResponse().setContentType("application/json;charset=UTF-8");
            ctx.setResponseBody("{\"errrocode\":\"00001\", \"errmsg\": \"IpAddr is forbidden![" + ipAddr+ "]\"}");

        }
        return null;
    }

    private String getIpAddr(HttpServletRequest request) {
//        真实IP
        String ip=request.getHeader("X-Forwarded-For");
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
//            代理IP
            ip=request.getHeader("Proxy-Client-IP");
        }
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
//            集群代理IP
            ip=request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
//            客户IP
            ip=request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getRemoteAddr();
        }
        return ip;
    }
}
