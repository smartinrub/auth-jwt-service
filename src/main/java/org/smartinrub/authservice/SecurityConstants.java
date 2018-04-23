package org.smartinrub.authservice;

public class SecurityConstants {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String JWT_SECRET = "secret";
    public static final long JWT_EXPIRATION_TIME = 40_000;
}
