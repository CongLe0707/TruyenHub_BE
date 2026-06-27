package com.example.TruyenHub.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonReq <T> {

    private String requestId;
    private String requestTime;
    
    @jakarta.validation.Valid
    private T data;

    public CommonReq(T data) {
        this.data = data;
    }
}
