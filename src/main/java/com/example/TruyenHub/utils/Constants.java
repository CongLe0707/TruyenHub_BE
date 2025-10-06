package com.example.TruyenHub.utils;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;



@UtilityClass
@Slf4j
public class Constants {
    public static final String JWT_SECRET = "${JWT_SECRET:change-me-secret}";
    public static final long ACCESS_TOKEN_TTL_SECONDS = 900; // 15 minutes
    public static final long REFRESH_TOKEN_TTL_SECONDS = 1209600; // 14 days
    public static final String REDIS_BLACKLIST_PREFIX = "jwt:blacklist:";
}
