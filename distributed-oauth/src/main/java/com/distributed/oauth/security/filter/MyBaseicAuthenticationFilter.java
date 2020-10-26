//package com.distributed.oauth.security.filter;
//import com.alibaba.fastjson.JSON;
//import net.mapone.ReturnResult;
//import net.mapone.enums.Flag;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Base64;
//
///**
// * 认证不带客户端信息参数的过滤
// *
// * @author: Mr.zhang
// * @Date: 2020/2/24 17:34
// */
//@Component
//public class MyBaseicAuthenticationFilter extends OncePerRequestFilter {
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        if (!httpServletRequest.getRequestURI().equalsIgnoreCase("/oauth/token")) {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            return;
//        }
//        String[] clientDetails = this.isHasClientDetails(httpServletRequest);
//
//        if (clientDetails == null) {
//            httpServletResponse.getWriter().write(JSON.toJSONString(new ReturnResult(Flag.EMPTY)));
//            return;
//        }
//        this.handle(httpServletRequest, httpServletResponse, clientDetails, filterChain);
//    }
//
//    private void handle(HttpServletRequest request, HttpServletResponse response, String[] clientDetails, FilterChain filterChain) throws IOException, ServletException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        UsernamePasswordAuthenticationToken token =
//                new UsernamePasswordAuthenticationToken(clientDetails[0], clientDetails[1], authentication.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(token);
//        filterChain.doFilter(request, response);
//    }
//
//
//    /**
//     * 判断请求头中是否包含client信息，若包含简单处理
//     *
//     * @param request
//     * @return
//     */
//    private String[] isHasClientDetails(HttpServletRequest request) {
//        String[] params = null;
//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (header != null) {
//            String basic = header.substring(0, 5);
//            if (basic.toLowerCase().contains("basic")) {
//                String tmp = header.substring(6);
//                String defaultClientDetails = new String(Base64.getDecoder().decode(tmp));
//                String[] clientArrays = defaultClientDetails.split(":");
//                if (clientArrays.length != 2) {
//                    return params;
//                } else {
//                    params = clientArrays;
//                }
//            }
//        }
//        String id = request.getParameter("client_id");
//        String secret = request.getParameter("client_secret");
//        if (header == null && id != null) {
//            params = new String[]{id, secret};
//        }
//        return params;
//    }
//}
