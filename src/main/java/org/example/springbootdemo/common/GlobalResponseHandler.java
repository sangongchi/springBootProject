package org.example.springbootdemo.common;

import cn.hutool.json.JSONUtil;
import org.example.springbootdemo.common.Result;
import org.example.springbootdemo.utils.ResultUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    private static final List<String> EXCLUDE_PATH = Arrays.asList(
            "/swagger", "/v3/api-docs", "/webjars"
    );

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> converter,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if(EXCLUDE_PATH.stream().anyMatch(path -> request.getURI().getPath().contains(path))){
            return body;
        }

        if(body instanceof String) {
            return JSONUtil.toJsonStr(ResultUtils.success(body));
        }

        if(body instanceof IPage) {
            IPage<?> pageData = (IPage<?>) body;
            Result<?> result = ResultUtils.success(pageData.getContent());
            result.setPage(new Result.PageInfo(
                    pageData.getNumber(),
                    pageData.getSize(),
                    pageData.getTotalElements()
            ));
            return result;
        }

        return body instanceof Result ? body : ResultUtils.success(body);
    }
}