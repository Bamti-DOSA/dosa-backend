package com.bamti.dosa.object.service;

import com.bamti.dosa.object.dto.ModelObjectResponse;
import com.bamti.dosa.object.repository.ModelObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModelObjectService {

    private final ModelObjectRepository modelObjectRepository;

    // 모든 모델 리스트 조회
    public List<ModelObjectResponse> getAllModels() {
        return modelObjectRepository.findAll().stream()
                .map(ModelObjectResponse::from)
                .collect(Collectors.toList());
    }
}