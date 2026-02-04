package com.bamti.dosa.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    // 성공 시 데이터만 보냄
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "요청 성공", data);
    }

    // 성공인데 데이터는 없을 때 (예: 삭제 완료)
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, "요청 성공", null);
    }

    // 실패 시 메시지 보냄
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }
}