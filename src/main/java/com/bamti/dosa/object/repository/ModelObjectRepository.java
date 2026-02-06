package com.bamti.dosa.object.repository;

import com.bamti.dosa.object.entity.ModelObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelObjectRepository extends JpaRepository<ModelObject, Long> {
    // 필요한 경우 나중에 여기에 검색 메소드 추가 (ex: findByName...)
}