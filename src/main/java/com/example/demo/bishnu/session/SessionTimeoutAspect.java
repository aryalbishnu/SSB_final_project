package com.example.demo.bishnu.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class SessionTimeoutAspect {

    private static final int SESSION_TIMEOUT_SECONDS = 30;

    @Around("@annotation(requireSessionTimeoutCheck)")
    public Object checkSessionTimeout(ProceedingJoinPoint joinPoint, RequireSessionTimeoutCheck requireSessionTimeoutCheck) throws Throwable {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        long currentTimeMillis = System.currentTimeMillis();
        long lastAccessedTimeMillis = session.getLastAccessedTime();
        long sessionTimeoutMillis = SESSION_TIMEOUT_SECONDS * 1000;

        if (currentTimeMillis - lastAccessedTimeMillis > sessionTimeoutMillis) {
          Model model = (Model) joinPoint.getArgs()[3]; // Assuming the Model parameter is at index 3
          model.addAttribute("title", "Session Timeout");
            return "session"; // Redirect to session timeout page
        } else {
            return joinPoint.proceed(); // Proceed with the original method execution
        }
    }
}
