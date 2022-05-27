package com.epam.esm.security.exception;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.FrameError;
import com.epam.esm.exception.FrameException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.epam.esm.exception.MessageException.USER_NOT_AUTHORIZED;

public class CustomEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorCode errorCode = ErrorCode.ITEM_NOT_AUTHORIZED_EXCEPTION;
        Locale locale = request.getLocale();
        String exceptionMessage = messageSource.getMessage(USER_NOT_AUTHORIZED, null, locale);
        FrameError frameError = new FrameError(errorCode, exceptionMessage);
        FrameException frameException = new FrameException(status, frameError);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status.value());
        response.getWriter().write(objectMapper.writeValueAsString(frameException));
    }
}
