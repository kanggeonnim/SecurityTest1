//package com.example.security2.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class MyFilter3 implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//
//        // 토큰: cos 이걸 만들어줘야함. id, pw 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어 주고, 그걸 응답을 해준다.
//        // 요청할 때마다 header에 Authorization에 value값으로 토큰을 가지고옴
//        // 그때 토큰이 넘어오면 이토큰이 내가만든 토큰이 맞는지 검증만 하면됨.
//        if(req.getMethod().equals("POST")){
//            System.out.println("POST요청됨");
//            String headerAuth = req.getHeader("Authorization");
//            System.out.println(headerAuth);
//            System.out.println("필터3");
//            if(headerAuth.equals("cos")){
//                filterChain.doFilter(req,res);
//            }else{
//                PrintWriter out = res.getWriter();
//                out.println("인증안됨.");
//            }
//        }
//    }
//}
