package com.example.ballkkaye._core.error;


import com.example.ballkkaye._core.error.ex.*;
import com.example.ballkkaye._core.util.Script;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 -> 잘못된 요청
    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e) {
        return Script.back(e.getMessage());
    }


    //401 -> 인증 안됨
    @ExceptionHandler(Exception401.class)
    public String ex401(Exception401 e) {
        return Script.href(e.getMessage(), "/login-form");
    }


    // 403 -> 권한 없음
    @ExceptionHandler(Exception403.class)
    public String ex403(Exception403 e) {
        return Script.alert(e.getMessage());
    }


    // 404 -> 자원을 찾을 수 없음
    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e) {
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e) {
        log.error("[500 서버 오류] {}", e.getMessage(), e); // 메시지와 스택트레이스 함께 출력
        return Script.back(e.getMessage());
    }


    // 그 외 오류
    @ExceptionHandler(Exception.class)
    public String exUnKnown(Exception e) {
        String html = """
                <script>
                    alert('${msg}');
                    history.back();
                </script>
                """.replace("${msg}", "관리자용 예외 발생.");
        log.error("[관리자용 예외 발생] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        return html;
    }
}
