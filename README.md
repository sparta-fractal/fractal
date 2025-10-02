# Fractal Cache API
[![Java](https://img.shields.io/badge/Java-17-007396?style=flat&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot 3.5.6](https://img.shields.io/badge/Spring%20Boot-3.5.6-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot) 
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat&logo=mysql&logoColor=white)](https://www.mysql.com/)
![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)
> 중고나라 관리 시스템 REST API 백엔드 개발 프로젝트
<img width="1435" height="985" alt="image" src="https://github.com/user-attachments/assets/c46b28f9-e767-437a-922a-2051a5f5dcca" />

## 📋 목차

- [프로젝트 개요](#-프로젝트-개요)
- [팀 소개](#-팀-소개)
- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [API 명세](#-api-명세)
- [ERD](#-erd)

## 🎯 프로젝트 개요

중고나라 관리 시스템의 REST API 백엔드를 개발하는 프로젝트입니다. 
Spring Boot를 기반으로 확장 가능하고 유지보수가 용이한 API 서버를 구축하며, 
Spring Cache를 활용한 성능 최적화에 중점을 두고 있습니다.

### 주요 목표
- REST API 설계 원칙에 따른 명세서 작성
- Spring Boot 기반의 확장 가능한 API 서버 구축
- Spring Cache를 활용한 성능 최적화
- 대용량 데이터 처리를 위한 효율적인 아키텍처 설계

## 👥 팀 소개

| 이름 | 역할 | GitHub |
|------|------|--------|
| **백도현** | Comment, Tag, Category | [@DoH100](https://github.com/DoH100) |
| **박은지** | User, Tag, Category | [@eunji-0508](https://github.com/eunji-0508) |
| **김민형** | 대용량 Dummy 데이터 적재, Product, Search | [@MinHyeongK](https://github.com/MinHyeongK) |
| **구안득** | Product, Tag, Category | [@Kuandyk](https://github.com/4x2vk) |
| **이성호** | Auth, Product, Search | [@AllSungho](https://github.com/AllSungho) |

## ✨ 주요 기능

### 사용자 관리 및 인증/인가
- [x] 회원 가입
- [x] 로그인 (JWT 기반)
- [x] 회원 탈퇴 (Soft Delete)
- [x] 프로필 수정
- [x] 비밀번호 수정

### 상품 관리
- [x] 상품 생성
- [x] 상품 수정
- [x] 상품 단건 조회
- [x] 판매글 페이징 조회
- [x] 상품 삭제 (Soft Delete)
- [x] 판매글 키워드 검색 (Cache 미적용)
- [x] 판매글 키워드 검색 (Cache 적용)

### 태그
- [x] 태그 리스트 반환
- [x] 태그-상품 단건 조회 (Cache 미적용)
- [x] 태그-상품 단건 조회 (Cache 적용)

### 카테고리
- [x] 카테고리 생성
- [x] 모든 카테고리 조회
- [x] 카테고리 수정
- [x] 카테고리 삭제 (Soft Delete)
- [x] 카테고리-상품 단건 조회 (Cache 미적용)
- [x] 카테고리-상품 단건 조회 (Cache 적용)

### 댓글 관리
- [x] 댓글 생성
- [x] 댓글 수정
- [x] 댓글 목록 조회

## 🛠 기술 스택

### Backend
- **Language**: Java 17
- **Framework**: Spring Boot 3.5.6
- **ORM**: Spring Data JPA
- **Database**: MySQL 8.0 (Production), H2 (Test)
- **Cache**: Spring Cache + Caffeine 3.1.8
- **Security**: JWT (jjwt 0.11.5) + BCrypt (0.10.2)
- **AOP**: Spring AOP
- **Validation**: Spring Validation
- **Build Tool**: Gradle

### DevOps & Tools
- **API Testing**: Postman

## 📁 프로젝트 구조

프로젝트는 크게 **common**과 **domain** 두 개의 최상위 패키지로 구성됩니다.

- **common**: 계층별로 구분된 공통 관심사
  - core: 핵심 비즈니스 로직 (DTO, Entity, Exception)
  - crosscutting: 횡단 관심사 (AOP, Annotation)
  - infra: 인프라스트럭처 계층 (Cache, Security, JPA, Web)
- **domain**: 도메인 주도 설계(DDD) 기반 비즈니스 로직

각 도메인은 독립적으로 Controller, Service, Repository, Entity, DTO로 구성

```
fractal/
│
├── common/
│   │
│   ├── core/
│   │   ├── dto/
│   │   │   ├── ApiPageResponse.java
│   │   │   ├── ApiResponse.java
│   │   │   └── AuthUser.java
│   │   │
│   │   ├── entity/
│   │   │   └── BaseEntity.java
│   │   │
│   │   └── exception/
│   │       ├── CommonErrorCode.java
│   │       ├── ErrorCode.java
│   │       ├── GlobalException.java
│   │       └── GlobalExceptionHandler.java
│   │
│   ├── crosscutting/
│   │   ├── annotation/
│   │   │   └── Auth.java
│   │   │
│   │   └── aop/
│   │       └── CacheEvictAspect.java
│   │
│   └── infra/
│       ├── cache/
│       │   ├── CacheConfig.java
│       │   └── CacheUtil.java
│       │
│       ├── jpa/
│       │   └── JpaAuditingConfig.java
│       │
│       ├── scheduler/
│       │   └── SchedulerConfig.java
│       │
│       ├── security/
│       │   ├── AuthUserArgumentResolver.java
│       │   ├── JwtFilter.java
│       │   ├── JwtUtil.java
│       │   └── PasswordEncoder.java
│       │
│       └── web/
│           ├── FilterConfig.java
│           └── WebConfig.java
│
├── domain/
│   │
│   ├── auth/
│   │   ├── controller/
│   │   │   └── AuthController.java
│   │   │
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   │   ├── AuthLoginRequest.java
│   │   │   │   ├── AuthRegisterRequest.java
│   │   │   │   └── AuthWithdrawRequest.java
│   │   │   │
│   │   │   └── response/
│   │   │       └── AuthResponse.java
│   │   │
│   │   ├── exception/
│   │   │   └── AuthErrorCode.java
│   │   │
│   │   └── service/
│   │       ├── AuthService.java
│   │       └── AuthServiceApi.java
│   │
│   ├── category/
│   │   ├── controller/
│   │   │   ├── CategoryControllerV1.java
│   │   │   └── CategoryControllerV2.java
│   │   │
│   │   ├── dto/
│   │   │   ├── CategoryCreateRequest.java
│   │   │   ├── CategoryMapper.java
│   │   │   ├── CategoryProductResponse.java
│   │   │   └── CategoryResponse.java
│   │   │
│   │   ├── entity/
│   │   │   └── Category.java
│   │   │
│   │   ├── exception/
│   │   │   └── CategoryErrorCode.java
│   │   │
│   │   ├── repository/
│   │   │   └── CategoryRepository.java
│   │   │
│   │   └── service/
│   │       ├── CategoryProductServiceV1.java
│   │       ├── CategoryProductServiceV2.java
│   │       ├── CategoryServiceApi.java
│   │       ├── CategoryServiceV1.java
│   │       └── CategoryServiceV2.java
│   │
│   ├── comment/
│   │   ├── controller/
│   │   │   └── CommentController.java
│   │   │
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   │   └── CommentRequest.java
│   │   │   │
│   │   │   └── response/
│   │   │       └── CommentResponse.java
│   │   │
│   │   ├── entity/
│   │   │   └── Comment.java
│   │   │
│   │   ├── repository/
│   │   │   └── CommentRepository.java
│   │   │
│   │   └── service/
│   │       ├── CommentService.java
│   │       └── CommentServiceApi.java
│   │
│   ├── product/
│   │   ├── controller/
│   │   │   ├── ProductControllerV1.java
│   │   │   └── ProductControllerV2.java
│   │   │
│   │   ├── dto/
│   │   │   ├── ProductCreateRequest.java
│   │   │   ├── ProductListResponse.java
│   │   │   ├── ProductResponse.java
│   │   │   ├── ProductSimpleResponse.java
│   │   │   └── ProductUpdateRequest.java
│   │   │
│   │   ├── entity/
│   │   │   ├── Product.java
│   │   │   ├── ProductCategory.java
│   │   │   └── ProductTag.java
│   │   │
│   │   ├── exception/
│   │   │   └── ProductErrorCode.java
│   │   │
│   │   ├── repository/
│   │   │   └── ProductRepository.java
│   │   │
│   │   └── service/
│   │       ├── ProductCacheService.java
│   │       ├── ProductServiceApi.java
│   │       ├── ProductServiceV1.java
│   │       └── ProductServiceV2.java
│   │
│   ├── search/
│   │   ├── entity/
│   │   │   └── Search.java
│   │   │
│   │   ├── repository/
│   │   │   └── SearchRepository.java
│   │   │
│   │   └── service/
│   │       ├── SearchService.java
│   │       └── SearchServiceApi.java
│   │
│   ├── tag/
│   │   ├── controller/
│   │   │   ├── TagControllerV1.java
│   │   │   └── TagControllerV2.java
│   │   │
│   │   ├── dto/
│   │   │   └── response/
│   │   │       ├── TagProductResponse.java
│   │   │       └── TagResponse.java
│   │   │
│   │   ├── entity/
│   │   │   └── Tag.java
│   │   │
│   │   ├── exception/
│   │   │   └── TagErrorCode.java
│   │   │
│   │   ├── repository/
│   │   │   └── TagRepository.java
│   │   │
│   │   └── service/
│   │       ├── TagCacheService.java
│   │       ├── TagProductServiceV1.java
│   │       ├── TagProductServiceV2.java
│   │       ├── TagService.java
│   │       └── TagServiceApi.java
│   │
│   └── user/
│       ├── constant/
│       │   └── SuccessMessages.java
│       │
│       ├── controller/
│       │   └── UserController.java
│       │
│       ├── dto/
│       │   ├── request/
│       │   │   ├── UpdatePasswordRequest.java
│       │   │   └── UpdateUserProfileRequest.java
│       │   │
│       │   └── response/
│       │       ├── UpdatePasswordResponse.java
│       │       └── UpdateUserProfileResponse.java
│       │
│       ├── entity/
│       │   └── User.java
│       │
│       ├── exception/
│       │   └── UserErrorCode.java
│       │
│       ├── repository/
│       │   └── UserRepository.java
│       │
│       └── service/
│           ├── UserService.java
│           └── UserServiceApi.java
│
└── FractalApplication.java
```

## 📚 API 명세

### 인증 (Auth)
**Base Path**: `/api/v1/auth`

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 회원가입 | `POST` | `/signup` | 새로운 사용자를 등록합니다 |
| 로그인 | `POST` | `/login` | 사용자 인증 후 JWT를 발급합니다 |
| 회원탈퇴 | `POST` | `/withdraw` | 현재 로그인된 사용자를 탈퇴 처리합니다 |

### 내 정보 관리 (User Profile)

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 내 정보 수정 | `PUT` | `/api/v1/users/me` | 현재 로그인된 사용자의 프로필 정보를 수정합니다 |
| 비밀번호 변경 | `PUT` | `/api/v1/users/me/password` | 현재 로그인된 사용자의 비밀번호를 변경합니다 |

### 태그 (Tag)

#### V1 - Cache 미적용
**Base Path**: `/api/v1/tags`

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 전체 태그 목록 조회 | `GET` | `/` | 시스템에 등록된 모든 태그의 목록을 조회합니다 |
| 태그별 상품 목록 조회 | `GET` | `/{tagId}` | 특정 태그에 속한 상품 목록을 페이징하여 조회합니다 |

#### V2 - Cache 적용
**Base Path**: `/api/v2/tags`

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 전체 태그 목록 조회 | `GET` | `/` | 시스템에 등록된 모든 태그의 목록을 조회합니다 |
| 태그별 상품 목록 조회 | `GET` | `/{tagId}` | 🔐 특정 태그의 상품 목록을 페이징 조회합니다 (조회수 중복 증가 방지) |

### 카테고리 (Category)

#### V1 - Cache 미적용
**Base Path**: `/api/v1/categories`

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 전체 카테고리 목록 조회 | `GET` | `/` | 시스템에 등록된 모든 카테고리의 목록을 조회합니다 |
| 카테고리별 상품 목록 조회 | `GET` | `/{categoryId}` | 특정 카테고리에 속한 상품 목록을 페이징하여 조회합니다 |
| 카테고리 생성 | `POST` | `/` | 새로운 카테고리를 생성합니다 |
| 카테고리 정보 수정 | `PUT` | `/{categoryId}` | 특정 카테고리의 정보를 수정합니다 |
| 카테고리 삭제 | `DELETE` | `/{categoryId}` | 특정 카테고리를 삭제 처리합니다 |

#### V2 - Cache 적용
**Base Path**: `/api/v2/categories`

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 카테고리별 상품 목록 조회 | `GET` | `/{categoryId}` | 특정 카테고리에 속한 상품 목록을 페이징하여 조회합니다 |
| 카테고리 정보 수정 | `PUT` | `/{categoryId}` | 특정 카테고리의 정보를 수정합니다 |
| 카테고리 삭제 | `DELETE` | `/{categoryId}` | 특정 카테고리를 삭제 처리합니다 |

### 상품 (Product)

#### V1 - Cache 미적용
**Base Path**: `/api/v1/products`

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 상품 목록 조회 및 검색 | `GET` | `/` | 상품 목록을 페이징하여 조회합니다 (`?keyword=` 검색 가능) |
| 개별 상품 상세 조회 | `GET` | `/{productId}` | 특정 상품의 상세 정보를 조회합니다 |
| 상품 등록 | `POST` | `/` | 새로운 상품을 등록합니다 |
| 상품 정보 수정 | `PUT` | `/{productId}` | 특정 상품의 정보를 수정합니다 |
| 상품 삭제 | `DELETE` | `/{productId}` | 특정 상품을 삭제 처리합니다 |

#### V2 - Cache 적용
**Base Path**: `/api/v2/products`

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 상품 목록 조회 및 검색 | `GET` | `/` | 상품 목록을 페이징하여 조회합니다 (`?keyword=` 검색 가능) |
| 상품 등록 | `POST` | `/` | 새로운 상품을 등록합니다 |
| 상품 정보 수정 | `PUT` | `/{productId}` | 특정 상품의 정보를 수정합니다 |
| 상품 삭제 | `DELETE` | `/{productId}` | 특정 상품을 삭제 처리합니다 |

### 댓글 (Comment)
**Base Path**: `/api/v1/products/{productId}/comments`

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 댓글 작성 | `POST` | `/` | 🔐 특정 상품에 새로운 댓글을 작성합니다 |
| 댓글 목록 조회 | `GET` | `/` | 특정 상품의 댓글 목록을 페이징하여 조회합니다 |

> 🔐 표시는 인증이 필요한 API입니다.

## 🗄 ERD

**ERD**: [사진 링크](https://github.com/sparta-fractal/fractal/issues/99#issue-3473407710) 🔗

## 🎨 와이어프레임

**와이어프레임**: [링크](https://www.figma.com/design/JK5q9Gx5hLp0YNrYVgN2Hy/Untitled?node-id=0-1&t=bPhBzZ6UyPoqUv3e-0) 🔗

---
### Installation

1. 레포지토리 클론
```bash
git clone https://github.com/sparta-fractal/fractal.git
cd fractal
```

2. 데이터베이스 설정
```bash
# MySQL 데이터베이스 생성
CREATE DATABASE fractal_db;
```

3. application.yml 설정
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/fractal_db
    username: your_username
    password: your_password
```

4. 애플리케이션 실행
```bash
./gradlew bootRun
```

## 📄 License

This project is licensed under the MIT License.

## 🤝 Contributing

프로젝트에 기여하고 싶으시다면 이슈를 등록하거나 Pull Request를 보내주세요!

---

<div align="center">

Made with ❤️ by Fractal Team

</div>
