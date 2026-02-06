package com.bamti.dosa.object.controller;

import com.bamti.dosa.global.response.ApiResponse;
import com.bamti.dosa.object.dto.ModelObjectResponse;
import com.bamti.dosa.object.service.ModelObjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 로그를 위해 추가
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;
import java.util.List;

@Slf4j // 로깅 활성화
@RestController
@RequiredArgsConstructor
public class ModelController {

    private final S3Presigner s3Presigner;
    private final ModelObjectService modelObjectService;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    // 1. [수정] 3D 모델 파일 다운로드 URL 발급 (보안 적용)
    @GetMapping("/api/models")
    public ApiResponse<String> getModelUrl(@RequestParam("filename") String filename) {

        // ==========================================
        // 🔒 [보안] 입력값 검증 로직 추가
        // ==========================================

        // 1. Null 또는 빈 값 체크
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("파일명(filename)은 필수 파라미터입니다.");
        }

        // 2. 경로 조작(Path Traversal) 방지
        // "../"를 통해 버킷 상위 폴더나 다른 경로로 이동하는 것을 차단
        if (filename.contains("..") || filename.startsWith("/")) {
            log.warn("경로 조작 시도 감지됨: {}", filename); // 보안 로그 남기기
            throw new IllegalArgumentException("유효하지 않은 파일 경로입니다.");
        }

        // 3. 확장자 제한 (Whitelisting)
        // 우리가 허용한 3D 모델과 이미지만 접근 가능하도록 제한
        if (!filename.endsWith(".glb") && !filename.endsWith(".gltf") && !filename.endsWith(".png")) {
            log.warn("허용되지 않은 확장자 요청: {}", filename);
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다. (.glb, .gltf, .png 만 허용)");
        }

        // ==========================================
        // 🚀 S3 URL 발급 및 예외 처리
        // ==========================================
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10)) // 유효시간 10분
                    .getObjectRequest(getObjectRequest)
                    .build();

            String url = s3Presigner.presignGetObject(presignRequest).url().toString();
            return ApiResponse.success(url);

        } catch (SdkException e) {
            // AWS S3 통신 중 에러 발생 시 (네트워크 오류, 인증 오류 등)
            log.error("S3 Presigned URL 발급 실패: filename={}, error={}", filename, e.getMessage());
            throw new RuntimeException("S3 서비스 연결 중 오류가 발생했습니다.");
        }
    }

    // 2. 모든 3D 모델 리스트 조회 API
    @GetMapping("/api/objects")
    public ApiResponse<List<ModelObjectResponse>> getAllObjects() {
        List<ModelObjectResponse> models = modelObjectService.getAllModels();
        return ApiResponse.success(models);
    }
}