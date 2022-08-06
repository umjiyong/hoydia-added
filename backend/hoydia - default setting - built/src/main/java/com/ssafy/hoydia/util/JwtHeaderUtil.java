package com.ssafy.hoydia.util;

import javax.servlet.http.HttpServletRequest;

public class JwtHeaderUtil {

    //jwt 토큰에서 bearer 항목이 있을시 bearer 부분을 떼고 토큰을 가져오는 util class

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

}
