package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.utils.Utils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

@UtilityClass
@Slf4j
public class ApiHandler {

    public <T, K> ResponseEntity<CommonRes> handle(T req, Function<T, K> function) {
        log.info("API request: {}", Utils.object2Json(req));
        var rsData = function.apply(req);
        var commonRes = new CommonRes<>(rsData);
        log.info("API response: {}", Utils.object2Json(commonRes));
        return ResponseEntity.ok(commonRes);
    }

    public ResponseEntity<CommonRes<?>> badRequest(CommonRes<?> commonRes) {
        log.info("Error response: {}", Utils.object2Json(commonRes));
        return ResponseEntity.ok(commonRes);
    }

    public ResponseEntity<CommonRes<?>> badRequest(String errorCode, String errorMsg) {
        CommonRes<?> commonRes = new CommonRes<>(errorCode, errorMsg);
        log.info("Error response: {}", Utils.object2Json(commonRes));
        return ResponseEntity.ok(commonRes);
    }
}

