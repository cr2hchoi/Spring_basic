package com.kb.gallery.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {
    //세션 입력
    // 유저 요청 -> 세션 저장소에 key와 함께 저장. 기존에 같은 key값이 있다면 덮어쓰기
    public static void setSesstion(HttpServletRequest request, String key, Object value){
        request.getSession().setAttribute(key, value);
        //request.getSession(): 현재 요청을 보낸 브라우저 고유의 HttpSession 객체를 톰캣 메모리에서 찾음 (방이 없으면 새로 개설)
        //.setAttribute(key, value): 찾아온 세션 저장소에 데이터를 보관합니다.
    }

    //세션 값 조회
    //로긘 사용자 조회 목적
    public static Object getSession(HttpServletRequest request, String key){
        return request.getSession().getAttribute(key);
    }

    // 세션 종료
    // 로그아웃 처리
    public static void removeSession(HttpServletRequest request, String key){
        request.getSession().removeAttribute(key);
    }
}
