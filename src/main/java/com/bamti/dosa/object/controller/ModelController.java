package com.bamti.dosa.object.controller;

import com.bamti.dosa.global.response.ApiResponse;
import com.bamti.dosa.object.dto.ModelObjectResponse;
import com.bamti.dosa.object.service.ModelObjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 로그를 위해 추가
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;
import java.util.List;

/**
 * 3D 모델 관련 요청을 처리하는 컨트롤러입니다.
 * 모델 목록 조회 및 S3 파일 다운로드용 Presigned URL 발급 기능을 제공합니다.
 */
@Slf4j // 로깅 활성화
@RestController
@RequiredArgsConstructor
public class ModelController {

    private final S3Presigner s3Presigner;
    private final ModelObjectService modelObjectService;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * S3에 저장된 3D 모델 파일(.glb) 또는 이미지(.png)의 다운로드 URL(Presigned URL)을 생성합니다.
     * <p>
     * 보안을 위해 파일명 검증(Null 체크, 경로 조작 방지) 및 확장자 화이트리스트 검사를 수행합니다.
     * 생성된 URL은 10분간 유효합니다.
     * </p>
     *
     * @param filename S3 객체 키 (경로 포함, 예: v_4_engine/completed/v_4_engine_final.glb)
     * @return 유효 시간이 설정된 S3 Presigned URL
     * @throws IllegalArgumentException 파일명이 유효하지 않거나, 허용되지 않은 확장자인 경우 발생
     * @throws RuntimeException S3 서비스 연결 중 오류 발생 시
     */
    @GetMapping("/api/models")
    public ApiResponse<String> getModelUrl(@RequestParam("filename") String filename) {
        String sanitizedFilename = filename.replaceAll("[\\r\\n]", "_");

        // ==========================================
        // 🔒 [보안] 입력값 검증 로직 추가
        // ==========================================

        // 1. Null 또는 빈 값 체크
        if (filename == null || filename.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일명(filename)은 필수 파라미터입니다.");
        }

        // 2. 경로 조작(Path Traversal) 방지
        // "../"를 통해 버킷 상위 폴더나 다른 경로로 이동하는 것을 차단
        if (filename.contains("..") || filename.startsWith("/")) {
            log.warn("경로 조작 시도 감지됨: {}", sanitizedFilename); // 보안 로그 남기기
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 경로입니다.");
        }

        // 3. 확장자 제한 (Whitelisting)
        // 우리가 허용한 3D 모델과 이미지만 접근 가능하도록 제한
        String lowerFilename = filename.toLowerCase();
        if (!lowerFilename.endsWith(".glb") && !lowerFilename.endsWith(".gltf") && !lowerFilename.endsWith(".png")) {
            log.warn("허용되지 않은 확장자 요청: {}", sanitizedFilename);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다. (.glb, .gltf, .png 만 허용)");
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
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "S3 서비스 연결 중 오류가 발생했습니다.");
        }
    }

    /**
     * 서비스에서 제공하는 모든 3D 모델의 메타데이터와 구성 부품(Parts) 목록을 조회합니다.
     * <p>
     * 현재 DB 연동 없이 하드코딩된 정적 데이터를 반환합니다.
     * </p>
     *
     * @return 3D 모델 정보와 부품 리스트가 포함된 응답 객체 리스트
     */
    @GetMapping("/api/objects")
    public ApiResponse<List<ModelObjectResponse>> getAllObjects() {
        List<ModelObjectResponse> models = modelObjectService.getAllModels();
        return ApiResponse.success(models);
    }
}