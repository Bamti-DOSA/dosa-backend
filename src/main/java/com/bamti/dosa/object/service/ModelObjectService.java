package com.bamti.dosa.object.service;

import com.bamti.dosa.object.dto.ModelObjectResponse;
import com.bamti.dosa.object.dto.ModelObjectResponse.PartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 3D 모델 객체 정보를 관리하고 제공하는 서비스입니다.
 * 현재 버전에서는 하드코딩된 모델 데이터를 반환합니다.
 */
@Service
@RequiredArgsConstructor
public class ModelObjectService {

    /**
     * 시스템에 등록된 모든 3D 모델 리스트를 반환합니다.
     * 각 모델에는 메타데이터와 부품(Part) 정보가 포함됩니다.
     *
     * @return 전체 3D 모델 응답 리스트
     */
    public List<ModelObjectResponse> getAllModels() {
        List<ModelObjectResponse> models = new ArrayList<>();

        // 1. Drone (드론)
        models.add(ModelObjectResponse.builder()
                .objectId(1L)
                .name("Drone")
                .type("Robotics & Automation")
                .description("고성능 카메라와 정밀 제어 시스템을 탑재한 다목적 드론입니다.")
                .assemblyModelUrl("drone/completed/drone_final.png")
                .thumbnailUrl("drone/thumbnail/drone_thumbnail.png")
                .parts(Collections.emptyList())
                .build());

        // 2. Leaf Spring (판스프링)
        models.add(ModelObjectResponse.builder()
                .objectId(2L)
                .name("Leaf Spring")
                .type("Automotive & Parts")
                .description("트럭 및 대형 차량의 충격을 흡수하는 판스프링 서스펜션입니다.")
                .assemblyModelUrl("leaf_spring/completed/leaf_spring_final.png")
                .thumbnailUrl("leaf_spring/thumbnail/leaf_spring_thumbnail.png")
                .parts(Collections.emptyList())
                .build());

        // 3. Machine Vice (머신 바이스)
        models.add(ModelObjectResponse.builder()
                .objectId(3L)
                .name("Machine Vice")
                .type("Industrial Tools")
                .description("가공 작업을 위해 공작물을 강력하게 고정하는 정밀 머신 바이스입니다.")
                .assemblyModelUrl("machine_vice/completed/machine_vice_final.glb")
                .thumbnailUrl("machine_vice/thumbnail/machine_vice_thumbnail.png")
                .parts(getMachineViceParts())
                .build());

        // 4. Robot Arm (로봇 팔)
        models.add(ModelObjectResponse.builder()
                .objectId(4L)
                .name("Robot Arm")
                .type("Robotics & Automation")
                .description("6축 다관절 산업용 로봇 팔입니다.")
                .assemblyModelUrl("robot_arm/completed/robot_arm_final.png")
                .thumbnailUrl("robot_arm/thumbnail/robot_arm_thumbnail.png")
                .parts(Collections.emptyList())
                .build());

        // 5. Robot Gripper (로봇 그리퍼)
        models.add(ModelObjectResponse.builder()
                .objectId(5L)
                .name("Robot Gripper")
                .type("Robotics & Automation")
                .description("로봇 팔 끝단에 부착하여 물체를 잡거나 옮기는 그리퍼 모듈입니다.")
                .assemblyModelUrl("robot_gripper/completed/robot_gripper_final.png")
                .thumbnailUrl("robot_gripper/thumbnail/robot_gripper_thumbnail.png")
                .parts(Collections.emptyList())
                .build());

        // 6. Suspension (서스펜션)
        models.add(ModelObjectResponse.builder()
                .objectId(6L)
                .name("Suspension")
                .type("Automotive & Parts")
                .description("차체의 진동을 제어하고 승차감을 향상시키는 코일오버 서스펜션 시스템입니다.")
                .assemblyModelUrl("suspension/completed/suspension_final.glb")
                .thumbnailUrl("suspension/thumbnail/suspension_thumbnail.png")
                .parts(getSuspensionParts())
                .build());

        // 7. V4 Engine (V4 엔진)
        models.add(ModelObjectResponse.builder()
                .objectId(7L)
                .name("V4 Engine")
                .type("Mechanical Engineering")
                .description("컴팩트한 크기에 강력한 출력을 내는 V형 4기통 엔진입니다.")
                .assemblyModelUrl("v_4_engine/completed/v_4_engine_final.glb")
                .thumbnailUrl("v_4_engine/thumbnail/v_4_engine_thumbnail.png")
                .parts(getV4EngineParts())
                .build());

        return models;
    }

    /**
     * Suspension 모델의 부품 목록을 생성하여 반환합니다.
     *
     * @return Suspension 부품(PartDto) 리스트
     */
    private List<PartDto> getSuspensionParts() {
        List<PartDto> parts = new ArrayList<>();
        parts.add(PartDto.builder().name("베이스").meshName("base").description("서스펜션의 하단 지지 구조물입니다.").build());
        parts.add(PartDto.builder().name("고정 너트").meshName("nut").description("부품들을 단단히 고정하는 너트입니다.").build());
        parts.add(PartDto.builder().name("중심축 (로드)").meshName("rod").description("댐퍼 내부에서 움직이는 중심축입니다.").build());
        parts.add(PartDto.builder().name("코일 스프링").meshName("spring").description("충격을 흡수하는 핵심 탄성 부품입니다.").build());
        return parts;
    }

    /**
     * Machine Vice 모델의 부품 목록을 생성하여 반환합니다.
     *
     * @return Machine Vice 부품(PartDto) 리스트
     */
    private List<PartDto> getMachineViceParts() {
        List<PartDto> parts = new ArrayList<>();

        parts.add(PartDto.builder()
                .name("가이드 본체")
                .meshName("part_1_fuhrung")
                .description("이동 죠의 움직임을 가이드하는 본체입니다.")
                .partUrl("machine_vice/parts/part_1_fuhrung.glb") // 추가됨
                .build());

        parts.add(PartDto.builder()
                .name("고정 죠")
                .meshName("part_2_feste_backe")
                .description("물체를 고정하는 움직이지 않는 턱입니다.")
                .partUrl("machine_vice/parts/part_2_feste_backe.glb")
                .build());

        parts.add(PartDto.builder()
                .name("이동 죠")
                .meshName("part_3_lose_backe")
                .description("스크류에 의해 전후로 움직이는 턱입니다.")
                .partUrl("machine_vice/parts/part_3_lose_backe.glb")
                .build());

        parts.add(PartDto.builder()
                .name("스크류 소켓")
                .meshName("part_4_spindelsockel")
                .description("회전 스크류를 지지하는 소켓 부품입니다.")
                .partUrl("machine_vice/parts/part_4_spindelsockel.glb")
                .build());

        parts.add(PartDto.builder()
                .name("클램핑 죠")
                .meshName("part_5_spannbacke")
                .description("공작물을 직접 압착하는 플레이트입니다.")
                .partUrl("machine_vice/parts/part_5_spannbacke.glb")
                .build());

        parts.add(PartDto.builder()
                .name("가이드 레일")
                .meshName("part_6_fuhrungschiene")
                .description("죠의 정밀한 직선 운동을 돕는 레일입니다.")
                .partUrl("machine_vice/parts/part_6_fuhrungschiene.glb")
                .build());

        parts.add(PartDto.builder()
                .name("사다리꼴 스크류")
                .meshName("part_7_trapez_spindel")
                .description("회전력을 직선 운동으로 바꾸는 핵심 나사입니다.")
                .partUrl("machine_vice/parts/part_7_trapez_spindel.glb")
                .build());

        parts.add(PartDto.builder()
                .name("기본 베이스")
                .meshName("part_8_grundplatte")
                .description("바이스 전체를 지탱하는 바닥 판입니다.")
                .partUrl("machine_vice/parts/part_8_grundplatte.glb")
                .build());

        return parts;
    }

    /**
     * V4 Engine 모델의 부품 목록을 생성하여 반환합니다.
     * 이름이 중복되는 부품(.001 등)은 공통 접두어(Prefix)를 사용하여 정의합니다.
     *
     * @return V4 Engine 부품(PartDto) 리스트
     */
    private List<PartDto> getV4EngineParts() {
        List<PartDto> parts = new ArrayList<>();
        // .001 등이 붙은 부품들은 startsWith로 처리되도록 공통 이름만 사용합니다.
        parts.add(PartDto.builder().name("커넥팅 로드").meshName("connecting_rod").description("피스톤의 동력을 크랭크축에 전달합니다.").build());
        parts.add(PartDto.builder().name("커넥팅 로드 캡").meshName("connecting_rod_cap").description("커넥팅 로드를 크랭크축에 고정하는 덮개입니다.").build());
        parts.add(PartDto.builder().name("크랭크 축").meshName("crankshaft").description("왕복 운동을 회전 운동으로 바꾸는 엔진의 중심축입니다.").build());
        parts.add(PartDto.builder().name("피스톤").meshName("piston").description("폭발 압력을 받아 왕복 운동하는 핵심 부품입니다.").build());
        parts.add(PartDto.builder().name("피스톤 핀").meshName("piston_pin").description("피스톤과 커넥팅 로드를 연결하는 고정 핀입니다.").build());
        return parts;
    }
}