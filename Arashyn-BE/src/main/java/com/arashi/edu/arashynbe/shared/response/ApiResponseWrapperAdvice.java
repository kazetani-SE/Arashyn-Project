package com.arashi.edu.arashynbe.shared.response;

import com.arashi.edu.arashynbe.shared.exception.ApiError;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    String declaringClass = returnType.getDeclaringClass().getPackageName();

    return declaringClass.startsWith("com.arashi.edu.arashynbe");
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {

    if (body instanceof ApiResponse<?> || body instanceof ApiError) {
      return body;
    }

    if (body == null) {
      return null;
    }

    int statusCode = 200;
    if (response instanceof ServletServerHttpResponse servletResponse) {
      statusCode = servletResponse.getServletResponse().getStatus();
    }

    return ApiResponse.builder()
            .status(statusCode)
            .message("Success")
            .data(body)
            .build();
  }
}