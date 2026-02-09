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
        parts.add(PartDto.builder()
                .name("베이스")
                .meshName("base")
                .description("서스펜션의 하중을 지지하고 차체에 고정되는 기초 구조물입니다.\n무게: 약 1.2kg\n\n⚠️ 주의사항: 설치 면의 평탄도 0.05mm 이내 유지 필수\n\n📖 시험 포인트:\n- 응력 분포(Stress Distribution) 해석\n- 피로 한도(Fatigue Limit)와 내구 수명\n- 볼트 체결부의 전단 응력 계산")
                .build());

        parts.add(PartDto.builder()
                .name("고정 너트")
                .meshName("nut")
                .description("댐퍼 로드와 상단 마운트를 단단히 체결하는 풀림 방지 너트입니다.\n무게: 약 25g\n\n⚠️ 주의사항: 규정 토크(45 N·m) 준수, 과토크 시 나사산 파손\n\n📖 시험 포인트:\n- 나사의 자립 조건(Self-locking Condition)\n- 마찰 계수와 조임력(Preload)의 관계\n- 진동에 의한 풀림 메커니즘")
                .build());

        parts.add(PartDto.builder()
                .name("중심축 (로드)")
                .meshName("rod")
                .description("댐퍼 내부에서 피스톤과 함께 왕복 운동하며 감쇠력을 발생시키는 축입니다.\n무게: 약 850g\n\n⚠️ 주의사항: 표면 스크래치 발생 시 오일 누유 원인\n\n📖 시험 포인트:\n- 좌굴(Buckling) 하중 계산 (Euler's Formula)\n- 표면 조도와 마찰 저항\n- 유체 역학적 감쇠(Damping) 원리")
                .build());

        parts.add(PartDto.builder()
                .name("코일 스프링")
                .meshName("spring")
                .description("노면의 충격 에너지를 탄성 위치 에너지로 흡수하는 핵심 부품입니다.\n무게: 약 2.1kg\n\n⚠️ 주의사항: 유효 권수(Active Coils) 변형 시 스프링 상수 변화\n\n📖 시험 포인트:\n- 훅의 법칙 (F = kx) 및 스프링 상수(k) 계산\n- 전단 탄성 계수(G)와 비틀림 응력\n- 서징(Surging) 현상과 고유 진동수")
                .build());

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
                .description("이동 죠의 직선 운동을 정밀하게 안내하는 가이드 블록입니다.\n무게: 약 4.5kg\n\n⚠️ 주의사항: 습동면 윤활유(VG68) 주기적 도포 필요\n\n📖 시험 포인트:\n- 미끄럼 베어링의 원리\n- 마모율과 면압(Contact Pressure) 계산\n- 열팽창에 의한 클리어런스 변화")
                .partUrl("machine_vice/parts/part_1_fuhrung.glb")
                .build());

        parts.add(PartDto.builder()
                .name("고정 죠")
                .meshName("part_2_feste_backe")
                .description("공작물을 기준면에 밀착시켜 고정하는 고정밀 턱(Jaw)입니다.\n무게: 약 1.8kg\n\n⚠️ 주의사항: 충격 가해짐 금지, 기준면 손상 시 정밀도 저하\n\n📖 시험 포인트:\n- 기준면(Datum) 설정의 중요성\n- 반력(Reaction Force)과 모멘트 평형\n- 표면 경도(HRC)와 내마모성")
                .partUrl("machine_vice/parts/part_2_feste_backe.glb")
                .build());

        parts.add(PartDto.builder()
                .name("이동 죠")
                .meshName("part_3_lose_backe")
                .description("스크류 회전에 의해 전후로 이송되며 공작물을 가압하는 턱입니다.\n무게: 약 2.3kg\n\n⚠️ 주의사항: 가이드 레일과 이물질 끼임 주의\n\n📖 시험 포인트:\n- 자유물체도(FBD) 분석\n- 마찰력과 구동 효율(Efficiency)\n- 쐐기 효과(Wedge Effect) 방지 설계")
                .partUrl("machine_vice/parts/part_3_lose_backe.glb")
                .build());

        parts.add(PartDto.builder()
                .name("스크류 소켓")
                .meshName("part_4_spindelsockel")
                .description("구동 스크류의 축방향 하중을 지지하는 베어링 하우징 역할을 합니다.\n무게: 약 950g\n\n⚠️ 주의사항: 스러스트 베어링 유격 점검 필수\n\n📖 시험 포인트:\n- 베어링의 수명 계산 (L10 Life)\n- 축방향 하중(Thrust Load) 지지 구조\n- 끼워맞춤 공차(Interference Fit) 선정")
                .partUrl("machine_vice/parts/part_4_spindelsockel.glb")
                .build());

        parts.add(PartDto.builder()
                .name("클램핑 죠")
                .meshName("part_5_spannbacke")
                .description("공작물과 직접 접촉하여 마찰력으로 파지하는 플레이트입니다.\n무게: 약 450g\n\n⚠️ 주의사항: 마모 시 파지력 저하, 교체 주기 준수\n\n📖 시험 포인트:\n- 마찰 계수와 클램핑력(Clamping Force) 계산\n- 접촉 응력(Hertzian Stress) 이론\n- 재료의 항복 강도와 안전율")
                .partUrl("machine_vice/parts/part_5_spannbacke.glb")
                .build());

        parts.add(PartDto.builder()
                .name("가이드 레일")
                .meshName("part_6_fuhrungschiene")
                .description("죠의 이탈을 방지하고 직선도를 보장하는 레일입니다.\n무게: 약 800g\n\n⚠️ 주의사항: 볼트 조임 순서(대각선) 준수하여 변형 방지\n\n📖 시험 포인트:\n- 빔의 처짐(Deflection) 계산\n- 모멘트 하중과 비틀림 강성\n- 선형 가이드 시스템의 종류")
                .partUrl("machine_vice/parts/part_6_fuhrungschiene.glb")
                .build());

        parts.add(PartDto.builder()
                .name("사다리꼴 스크류")
                .meshName("part_7_trapez_spindel")
                .description("회전 운동을 강력한 직선 운동(압축력)으로 변환하는 동력 전달 요소입니다.\n무게: 약 1.1kg\n\n⚠️ 주의사항: 나사산 마모 한계 게이지로 정기 점검\n\n📖 시험 포인트:\n- 사다리꼴 나사의 효율 공식 (η = tan(λ) / tan(λ+ρ'))\n- 자립 조건과 역구동성(Back-driving)\n- 비틀림 모멘트와 전달 동력(P=Tω)")
                .partUrl("machine_vice/parts/part_7_trapez_spindel.glb")
                .build());

        parts.add(PartDto.builder()
                .name("기본 베이스")
                .meshName("part_8_grundplatte")
                .description("바이스 전체의 강성을 유지하고 절삭력을 바닥으로 분산시키는 주물 바디입니다.\n무게: 약 12.5kg\n\n⚠️ 주의사항: 설치 시 수평계 사용하여 0.02mm/m 이내 레벨링\n\n📖 시험 포인트:\n- 주조 공정 및 잔류 응력 제거(Annealing)\n- 단면 2차 모멘트(I)와 굽힘 강성(EI)\n- 진동 감쇠능(Damping Capacity) 비교 (주철 vs 강)")
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
        parts.add(PartDto.builder()
                .name("커넥팅 로드")
                .meshName("connecting_rod")
                .description("피스톤의 왕복 운동을 크랭크축의 회전 운동으로 변환하는 링크입니다.\n무게: 약 450g\n\n⚠️ 주의사항: 대단부 볼트 조임 시 소성역 체결법 적용 필수\n\n📖 시험 포인트:\n- 관성력(Inertia Force) 계산\n- 좌굴 하중과 세장비(Slenderness Ratio)\n- 고주기 피로(High Cycle Fatigue) 해석")
                .build());

        parts.add(PartDto.builder()
                .name("커넥팅 로드 캡")
                .meshName("connecting_rod_cap")
                .description("커넥팅 로드를 크랭크 저널에 체결하기 위한 하단 캡입니다.\n무게: 약 180g\n\n⚠️ 주의사항: 짝(Pair)이 바뀌면 조립 불가 (고유 번호 확인)\n\n📖 시험 포인트:\n- 볼트의 체결 축력과 피로 파괴\n- 유체 윤활(Hydrodynamic Lubrication) 이론\n- 베어링 메탈의 소착(Seizure) 현상")
                .build());

        parts.add(PartDto.builder()
                .name("크랭크 축")
                .meshName("crankshaft")
                .description("엔진의 출력을 최종적으로 회전력(토크)으로 인출하는 메인 샤프트입니다.\n무게: 약 15.2kg\n\n⚠️ 주의사항: 저널 부 오일 홀(Oil Hole) 막힘 주의\n\n📖 시험 포인트:\n- 비틀림 진동(Torsional Vibration)과 댐퍼\n- 동적 균형(Dynamic Balance) 잡기\n- 응력 집중 계수(Kt)와 필렛 반경")
                .build());

        parts.add(PartDto.builder()
                .name("피스톤")
                .meshName("piston")
                .description("연소실의 폭발 압력을 직접 받아내는 핵심 부품입니다.\n무게: 약 320g\n\n⚠️ 주의사항: 피스톤 링 간극(End Gap) 120도 간격 배치\n\n📖 시험 포인트:\n- 열응력(Thermal Stress)과 열전달 해석\n- 피스톤 슬랩(Slap) 소음 원인\n- 블로바이(Blow-by) 가스 생성 원리")
                .build());

        parts.add(PartDto.builder()
                .name("피스톤 핀")
                .meshName("piston_pin")
                .description("피스톤과 커넥팅 로드를 연결하며, 강력한 전단력과 휨 모멘트를 견디는 핀입니다.\n무게: 약 110g\n\n⚠️ 주의사항: 스냅 링(Snap Ring) 장착 상태 확인, 이탈 시 실린더 파손\n\n📖 시험 포인트:\n- 전단 응력(Shear Stress) 및 굽힘 모멘트 계산\n- 헤르츠 접촉 응력(Contact Stress)\n- 표면 침탄 열처리(Carburizing)의 목적")
                .build());

        return parts;
    }
}