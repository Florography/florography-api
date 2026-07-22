# Florography API

**꽃과 함께하는 감정 표현 플랫폼** - REST API 백엔드 서버

## 📋 개요

Florography API는 **꽃을 통해 감정을 표현하고 공유하는 플랫폼**의 백엔드 서버입니다. 사용자 인증, 편지 작성, 정원 관리, 꽃 정보 제공 등 다양한 기능을 제공하는 REST API를 구현했습니다.

## 🛠 기술 스택

| 구분 | 기술 |
|------|------|
| **프레임워크** | Spring Boot 4.0.7 |
| **언어** | Java 21 |
| **빌드** | Gradle |
| **인증** | Spring Security, OAuth2 |
| **토큰** | JWT (io.jsonwebtoken) |
| **데이터베이스** | MySQL |
| **ORM/쿼리** | MyBatis 4.0.1 |
| **API 문서** | SpringDoc OpenAPI 3.0.2 |
| **테스트** | JUnit 5 |
| **개발 도구** | Lombok, Spring DevTools |

## 📁 프로젝트 구조

```
src/main/java/com/korit/florographyapi/
├── controller/        # REST 엔드포인트
├── service/          # 비즈니스 로직
├── repository/       # 데이터 접근 계층 (MyBatis)
├── dto/              # 데이터 전송 객체
├── entity/           # JPA 엔티티 / 데이터베이스 모델
├── config/           # 설정 클래스 (Security, OAuth2 등)
├── filter/           # 요청 필터 (JWT 검증)
├── common/           # 공통 유틸리티, 예외 처리
├── comment/          # 댓글 기능
├── flowerdictionary/ # 꽃 정보 사전
└── ...

src/main/resources/
├── application.yml   # 애플리케이션 설정
├── application-dev.yml
└── mapper/           # MyBatis XML 매퍼
```

## 🚀 빠른 시작

### 설치 및 실행

```bash
# 1. 프로젝트 클론
git clone <repository-url>
cd florography-api

# 2. MySQL 데이터베이스 생성 및 설정
# application.yml에서 DB 연결 정보 설정

# 3. 빌드
./gradlew build

# 4. 서버 실행
./gradlew bootRun

# 또는
java -jar build/libs/florography-api-0.0.1-SNAPSHOT.jar
```

서버는 기본적으로 `http://localhost:8080`에서 실행됩니다.

### 개발 모드

```bash
# Hot reload를 지원하는 개발 모드 실행
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## 📚 주요 기능

### 1. **사용자 인증 (User Authentication)**
- 회원가입 / 로그인
- OAuth2 소셜 로그인 연동 (Google, Kakao, Naver 등)
- JWT 기반 토큰 관리
- 토큰 갱신 (Refresh Token)
- 로그아웃

### 2. **편지 기능 (Heart Letter)**
- 감정을 담은 편지 작성
- 편지 조회 / 수정 / 삭제
- 편지에 댓글 추가
- 편지 공유 기능

### 3. **정원 관리 (Garden)**
- 개인 정원 생성 및 관리
- 정원에 꽃 심기
- 정원 상태 조회

### 4. **꽃 정보 (Flower Dictionary)**
- 다양한 꽃의 정보 제공
- 꽃별 의미 및 특징 조회
- 꽃의 계절 정보

### 5. **씨드 기록 (Seed Record)**
- 씨앗 심기 기록
- 성장 과정 기록
- 기록 조회

### 6. **커뮤니티 기능 (Share Board)**
- 사용자 간 정보 공유 게시판
- 댓글 기능
- 좋아요 기능

## 🔐 보안 기능

- **Spring Security**: 요청 인증 및 권한 검증
- **OAuth2**: 소셜 로그인 통합
- **JWT**: Stateless 토큰 기반 인증
- **CORS**: 크로스 도메인 요청 처리
- **입력 검증**: Bean Validation 사용

## 📖 API 문서

프로젝트 실행 후 다음 URL에서 Swagger UI 확인:

```
http://localhost:8080/swagger-ui.html
```

OpenAPI 스펙:
```
http://localhost:8080/v3/api-docs
```

## 🗄 데이터베이스

### 주요 테이블

- `users` - 사용자 정보
- `heart_letters` - 편지
- `letter_comments` - 편지 댓글
- `gardens` - 정원
- `garden_flowers` - 정원의 꽃
- `flower_dictionary` - 꽃 정보
- `seed_records` - 씨드 기록
- `share_board` - 공유 게시판
- `board_comments` - 게시판 댓글

## 🧪 테스트

```bash
# 모든 테스트 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests "com.korit.florographyapi.controller.*"

# 테스트 커버리지 확인
./gradlew test jacocoTestReport
```

## 🔧 환경 설정

### application.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/florography
    username: root
    password: ${DB_PASSWORD}
    
  jpa:
    hibernate:
      ddl-auto: validate

  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}

server:
  port: 8080
  servlet:
    context-path: /api
```

## 📝 API 엔드포인트 예시

### 인증
- `POST /api/auth/signup` - 회원가입
- `POST /api/auth/login` - 로그인
- `POST /api/auth/refresh` - 토큰 갱신
- `POST /api/auth/logout` - 로그아웃

### 편지
- `GET /api/letters` - 편지 목록 조회
- `POST /api/letters` - 편지 작성
- `GET /api/letters/{id}` - 편지 상세 조회
- `PUT /api/letters/{id}` - 편지 수정
- `DELETE /api/letters/{id}` - 편지 삭제

### 정원
- `GET /api/gardens` - 정원 조회
- `POST /api/gardens` - 정원 생성
- `PUT /api/gardens/{id}` - 정원 수정

### 꽃 정보
- `GET /api/flowers` - 꽃 목록
- `GET /api/flowers/{id}` - 꽃 상세 정보

## 💡 주요 설정 사항

1. **JWT 토큰 설정**: `config/JwtConfig.java`
2. **Spring Security**: `config/SecurityConfig.java`
3. **OAuth2**: `config/OAuth2Config.java`
4. **CORS**: `config/CorsConfig.java`
5. **MyBatis**: `config/MyBatisConfig.java`

## 🐛 문제 해결

### MySQL 연결 오류
- DB 서버 실행 여부 확인
- application.yml의 데이터베이스 설정 확인
- MySQL 포트(기본 3306) 확인

### 토큰 만료 오류
- 클라이언트에서 `POST /api/auth/refresh` 호출하여 토큰 갱신

### OAuth2 설정 오류
- Google/Kakao/Naver 개발자 콘솔에서 클라이언트 ID/시크릿 확인
- Redirect URI가 올바르게 등록되어 있는지 확인

## 📚 추가 문서

- [OAuth2 통합 가이드](./docs/OAuth2_계정연동_구현_가이드.md)
- [API 명세서](./docs/API-CONTRACT.md)
- [트러블슈팅 가이드](./docs/Florography_프로젝트_트러블슈팅_상세.md)

## 👥 기여

프로젝트에 기여하려면:

1. 이슈를 생성하거나 기존 이슈를 확인
2. Feature 브랜치 생성 (`git checkout -b feature/amazing-feature`)
3. 변경 사항 커밋 (`git commit -m 'feat: 새로운 기능 추가'`)
4. 브랜치에 푸시 (`git push origin feature/amazing-feature`)
5. Pull Request 생성

## 📄 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다.

## ❓ FAQ

**Q. 데이터베이스는 자동으로 생성되나요?**  
A. `application.yml`의 `ddl-auto` 설정에 따라 결정됩니다. 운영 환경에서는 `validate`로 설정하고 수동으로 스키마를 관리하는 것을 권장합니다.

**Q. CORS 오류가 발생합니다.**  
A. `config/CorsConfig.java`에서 허용된 오리진을 확인하고 필요시 웹 프로젝트의 URL을 추가하세요.

**Q. OAuth2로 로그인하는데 자꾸 리다이렉트됩니다.**  
A. 각 OAuth2 제공자의 개발자 콘솔에서 Redirect URI를 정확하게 설정했는지 확인하세요.

---

**마지막 업데이트**: 2026-07-20
