package com.bamti.dosa.object.controller;

import com.bamti.dosa.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

// 👇 아까 해결한 올바른 import 경로 (presigner 포함)
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;

@RestController
@RequestMapping("/api/models") // 프론트엔드 요청 URL은 그대로 유지합니다.
@RequiredArgsConstructor
public class ModelController {

    private final S3Presigner s3Presigner;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * 3D 모델(또는 이미지) 다운로드용 Pre-signed URL 발급
     * 예시: GET /api/models?filename=drone/parts/arm_gear.glb
     */
    @GetMapping
    public ApiResponse<String> getModelUrl(@RequestParam("filename") String filename) {

        // 1. S3 파일 정보 설정
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filename) // S3 내 파일 경로
                .build();

        // 2. URL 유효기간 설정 (10분)
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();

        // 3. 최종 URL 생성
        String url = s3Presigner.presignGetObject(presignRequest).url().toString();

        return ApiResponse.success(url);
    }
}