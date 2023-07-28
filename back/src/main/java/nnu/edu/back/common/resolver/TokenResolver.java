package nnu.edu.back.common.resolver;

import nnu.edu.back.common.utils.JwtTokenUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/07/28/20:04
 * @Description:
 */
public class TokenResolver implements HandlerMethodArgumentResolver {
    public String tokenHead = "Bearer ";
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtTokenParser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = (request.getHeader("authorization")).substring(this.tokenHead.length());
        JwtTokenParser jwtTokenParser = parameter.getParameterAnnotation(JwtTokenParser.class);
        return JwtTokenUtil.getUserInfoByToken(jwtTokenParser.value(), token);
    }
}
