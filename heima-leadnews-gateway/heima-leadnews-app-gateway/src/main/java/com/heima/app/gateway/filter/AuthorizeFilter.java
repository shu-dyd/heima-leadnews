package com.heima.app.gateway.filter;

import com.heima.app.gateway.util.AppJwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("<<<<<<<<<<<<<<<进入网关的全局过滤器中>>>>>>>>>>>>>>>>>>");
        // 1.获取request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 2.判断是否是登陆
        if(request.getURI().getPath().contains("/login")){
            System.out.println("<<<<<<<<<<<<<<<是登陆URL>>>>>>>>>>>>>>>>>>");
            return chain.filter(exchange);
        }

        // 3.如果不是登陆，就说明可能是登陆之后访问其他的URL，获取token来进行校验
        String token = request.getHeaders().getFirst("token");

        // 4.判断token是否存在
        if(StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        try {
            // 5.判断token是否有效
            Claims claimsBody = AppJwtUtil.getClaimsBody(token);
            // 是否过期
            int result = AppJwtUtil.verifyToken(claimsBody);
            if(result == 1 || result == 2){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        } catch (Exception e) {
            // 解析失败
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 6.放行

        return chain.filter(exchange);
    }

    /**
     * 优先级设置，值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
