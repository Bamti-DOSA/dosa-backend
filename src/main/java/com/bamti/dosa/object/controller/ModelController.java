package com.bamti.dosa.object.controller;

import com.bamti.dosa.global.response.ApiResponse;
import com.bamti.dosa.object.dto.ModelObjectResponse;
import com.bamti.dosa.object.service.ModelObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;
import java.util.List;

@RestController
// [수정] 이제 이 컨트롤러는 '모델'과 관련된 모든 것을 처리합니다.
// URL prefix를 하나로 통일하기보다, 메서드별로 명확히 나누는 게 좋습니다.
@RequiredArgsConstructor
public class ModelController {

    private final S3Presigner s3Presigner;
    private final ModelObjectService modelObjectService; // [추가] 서비스 연결

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    // 1. [기존] 3D 모델 파일 다운로드 URL 발급
    @GetMapping("/api/models")
    public ApiResponse<String> getModelUrl(@RequestParam("filename") String filename) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();

        String url = s3Presigner.presignGetObject(presignRequest).url().toString();
        return ApiResponse.success(url);
    }

    // 2. [신규] 모든 3D 모델 리스트 조회 API
    @GetMapping("/api/objects")
    public ApiResponse<List<ModelObjectResponse>> getAllObjects() {
        List<ModelObjectResponse> models = modelObjectService.getAllModels();
        return ApiResponse.success(models);
    }
}