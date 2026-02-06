package com.bamti.dosa.object.service;

import com.bamti.dosa.object.dto.ModelObjectResponse;
import com.bamti.dosa.object.dto.ModelObjectResponse.PartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelObjectService {

    /**
     * Retrieve all predefined model objects with their assembly metadata and optional part lists.
     *
     * @return a List of ModelObjectResponse where each element contains objectId, name, type,
     *         description, assemblyModelUrl, and optionally a list of PartDto entries for component parts
     */
    public List<ModelObjectResponse> getAllModels() {
        List<ModelObjectResponse> models = new ArrayList<>();

        // 1. Drone (가짜 - 이미지)
        models.add(ModelObjectResponse.builder()
                .objectId(1L).name("Drone").type("Robotics & Automation")
                .description("고성능 카메라와 정밀 제어 시스템을 탑재한 다목적 드론입니다.")
                .assemblyModelUrl("drone/completed/drone_final.png")
                .build());

        // 2. Leaf Spring (가짜 - 이미지)
        models.add(ModelObjectResponse.builder()
                .objectId(2L).name("Leaf Spring").type("Automotive & Parts")
                .description("트럭 및 대형 차량의 충격을 흡수하는 판스프링 서스펜션입니다.")
                .assemblyModelUrl("leaf_spring/completed/leaf_spring_final.png")
                .build());

        // 3. Machine Vice (진짜 - GLB)
        models.add(ModelObjectResponse.builder()
                .objectId(3L).name("Machine Vice").type("Industrial Tools")
                .description("가공 작업을 위해 공작물을 강력하게 고정하는 정밀 머신 바이스입니다.")
                .assemblyModelUrl("machine_vice/completed/machine_vice_final.glb")
                .parts(getMachineViceParts())
                .build());

        // 4. Robot Arm (가짜 - 이미지)
        models.add(ModelObjectResponse.builder()
                .objectId(4L).name("Robot Arm").type("Robotics & Automation")
                .description("6축 다관절 산업용 로봇 팔입니다.")
                .assemblyModelUrl("robot_arm/completed/robot_arm_final.png")
                .build());

        // 5. Robot Gripper (가짜 - 이미지)
        models.add(ModelObjectResponse.builder()
                .objectId(5L).name("Robot Gripper").type("Robotics & Automation")
                .description("로봇 팔 끝단에 부착하여 물체를 잡거나 옮기는 그리퍼 모듈입니다.")
                .assemblyModelUrl("robot_gripper/completed/robot_gripper_final.png")
                .build());

        // 6. Suspension (진짜 - GLB)
        models.add(ModelObjectResponse.builder()
                .objectId(6L).name("Suspension").type("Automotive & Parts")
                .description("차체의 진동을 제어하고 승차감을 향상시키는 코일오버 서스펜션 시스템입니다.")
                .assemblyModelUrl("suspension/completed/suspension_final.glb")
                .parts(getSuspensionParts())
                .build());

        // 7. V4 Engine (진짜 - GLB)
        models.add(ModelObjectResponse.builder()
                .objectId(7L).name("V4 Engine").type("Mechanical Engineering")
                .description("컴팩트한 크기에 강력한 출력을 내는 V형 4기통 엔진입니다.")
                .assemblyModelUrl("v_4_engine/completed/v_4_engine_final.glb")
                .parts(getV4EngineParts())
                .build());

        return models;
    }

    /**
     * Create the list of parts that compose the suspension assembly.
     *
     * Each element is a PartDto describing a suspension component with its display name,
     * mesh identifier, and a brief description.
     *
     * @return a List of PartDto containing the suspension parts: "베이스" (base), "고정 너트" (nut),
     *         "중심축 (로드)" (rod), and "코일 스프링" (spring)
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
     * Build the list of parts that make up a machine vice.
     *
     * @return a List of PartDto where each entry contains the part's display name, mesh name, and a short description
     */
    private List<PartDto> getMachineViceParts() {
        List<PartDto> parts = new ArrayList<>();
        parts.add(PartDto.builder().name("가이드 본체").meshName("part_1_fuhrung").description("이동 죠의 움직임을 가이드하는 본체입니다.").build());
        parts.add(PartDto.builder().name("고정 죠").meshName("part_2_feste_backe").description("물체를 고정하는 움직이지 않는 턱입니다.").build());
        parts.add(PartDto.builder().name("이동 죠").meshName("part_3_lose_backe").description("스크류에 의해 전후로 움직이는 턱입니다.").build());
        parts.add(PartDto.builder().name("스크류 소켓").meshName("part_4_spindelsockel").description("회전 스크류를 지지하는 소켓 부품입니다.").build());
        parts.add(PartDto.builder().name("클램핑 죠").meshName("part_5_spannbacke").description("공작물을 직접 압착하는 플레이트입니다.").build());
        parts.add(PartDto.builder().name("가이드 레일").meshName("part_6_fuhrungschiene").description("죠의 정밀한 직선 운동을 돕는 레일입니다.").build());
        parts.add(PartDto.builder().name("사다리꼴 스크류").meshName("part_7_trapez_spindel").description("회전력을 직선 운동으로 바꾸는 핵심 나사입니다.").build());
        parts.add(PartDto.builder().name("기본 베이스").meshName("part_8_grundplatte").description("바이스 전체를 지탱하는 바닥 판입니다.").build());
        return parts;
    }

    /**
     * Builds the list of parts for a V4 engine.
     *
     * Each entry contains the part's display name, a base `meshName` (intended for startsWith matching against mesh identifiers), and a short description.
     *
     * @return a list of PartDto representing the V4 engine components
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