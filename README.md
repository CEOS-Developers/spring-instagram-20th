# spring-instagram-20th
CEOS 20th BE study - instagram clone coding

## 🪴 데이터 모델링 개념 공부

### 🌱 데이터 모델링

**데이터 모델링이란**

업무 내용을 분석하여 이해하고, 약속된 표기법에 의해 표현하는 것

**데이터 모델링 순서**

1. 업무 파악 (요구사항 수집 및 분석)
2. 개념적 데이터 모델링
    
    하고자 하는 일의 데이터 간의 관계를 구상
    
    e.g. 피터 첸 표기법으로 ERD 다이어그램 구성
    
    ![image](https://github.com/user-attachments/assets/75824021-4bb3-474f-a6ca-88347b2c5831)

    
    ![image](https://github.com/user-attachments/assets/d1a4aa73-4100-45c2-8b02-a6b19635152b)

    
3. 논리적 데이터 모델링
    
    데이터 타입 명시, 데이터 간의 관계 정립, 테이블의 키 지정
    
    ![image](https://github.com/user-attachments/assets/3fba406d-83fe-46ba-96ec-c6f7d2cea23b)

    
4. 물리적 데이터 모델링
    
    최종적으로 데이터를 관리할 데이터베이스를 선택하고, 선택한 데이터 베이스에 실제 테이블을 만드는 작업
    
    즉, 만든 시각적인 구조를 실제 SQL 코딩을 통해 완성하는 단계
    
    ![image](https://github.com/user-attachments/assets/bd606919-aeff-4a60-9477-297fc7d645e4)

  ```java
    /* 테이블 생성 */
    
    -- 회원정보
    create table member_tbl ( 
      member_uid bigint primary key auto_increment,
      member_name varchar(45) unique not null,
      member_pwd varchar(45) not null,
      member_status boolean not null
    );
    
    -- 로그인기록정보
    create table login_info_tbl( 
      member_name varchar(45) not null,
      info_ip varchar(45) not null,
      info_date datetime not null,
      constraint fk_member_name foreign key (member_name) references member_tbl (member_name)
    );
    
    -- 게시판
    create table board_tbl ( 
      board_uid bigint primary key auto_increment,
      member_name varchar(45) not null,
      board_title varchar(45) not null,
      board_date datetime not null,
      board_hit int not null,
      board_post varchar(5000) not null,
      constraint fk_member_name foreign key(member_name) references member_tbl(member_name)
    );
    
    -- 게시판 풀텍스트 인덱스 생성
    create Fulltext index idx_title on board_tbl ( board_title );
    create Fulltext index idx_post on board_tbl ( board_post );
    -- show index from board_tbl ;
    
    -- 댓글
    create table reply_tbl ( 
      reply_uid bigint primary key auto_increment,
      board_uid bigint not null,
      member_name varchar(45) not null,
      reply_date datetime not null,
      reply_post varchar(1000) not null,
      foreign key(board_uid) references board_tbl(board_uid),
      foreign key(member_name) references member_tbl(member_name)
    );
    
    -- 댓글 풀텍스트 인덱스 생성
    create Fulltext index idx_reply on reply_tbl ( reply_post );
  ```
        
    

### 🌱 ERD 그리기

ERD (Entity Relationship Diagram): Entity 개체 Relationship을 중점적으로 표시하는 데이터베이스 구조를 한 눈에 알아보기 위해 그려놓는 다이어그램

**엔티티 Entity**

정의 가능한 사물 또는 개념 e.g. 학생

데이터베이스의 테이블이 엔티티로 표현된다고 보면 됨

**엔티티 속성 Attribute**

엔티티에는 개체가 갖고 있는 속성을 포함함 e.g. 학생 엔티티의 학번, 이름, 주소, 전공 

데이터베이스의 테이블의 각 필드(컬럼)들이 엔티티 속성

**엔티티 도메인 Domain**

속성의 값, 타입, 제약사항 등에 대한 값의 범위를 표현하는 것

사용자의 기호에 따라 속성 타입만 그릴 수도 있고, 가독성을 위해 생략할 수도 있음

데이터 타입을 명시할 때, 데이터베이스가 지원하는 타입에 맞게 해야됨

### 🌱 ERD 키와 제약 조건 표기법

**Primary Key(PK / 주 식별자)**

중복이 없고 NULL 값이 없는 유일한 값에 지정하는 식별자

**NOT NULL** 

N 혹은 NN으로 표기

**Foreign Key(FK / 외래 식별자)**

외래 식별자를 표시할 때에는 선을 이어주는데, 개체와 관계를 따려 표시

### 🌱 ERD 엔티티 관계 표기법

| 항목 | 식별자 관계 | 비식별자 관계 |
| --- | --- | --- |
| 목적 | 강한 연결관계 표현 | 약한 연결관계 표현 |
| 자식 주 식별자 영향 | 자식 주식별자의 구성에 포함 | 자식 일반 속성에 포함됨 |
| 표기법 | 실선 표현 | 점선 표현 |
| 연결 고려 사항 | - 반드시 부모 엔티티 종속 | - 자식 주 식별자 구성을 독립적으로 구성
- 상속받은 주식별자 속성을 타 엔터티에 차단 필요
- 부모 쪽의 관계 참여가 선택 관계 |

**식별자 관계**

- 자식이 부모의 PK를 FK로 참조해서 자신의 PK로 설정

![image](https://github.com/user-attachments/assets/bb26d8b1-526e-49cc-8b9f-ac0f9914da63)


**비식별자 관계**

- 부모의 PK를 FK로 참조해서 일반 속성으로 사용

![image](https://github.com/user-attachments/assets/5c47339a-9966-4f81-9ee3-5245fae07675)


### 🌱 ERD 관계의 카디널리티

카디널리티: 한 개체에서 발생할 수 있는 발생 횟수. 다른 개체에서 발생할 수 있는 발생 횟수와 연관됨

**One to One Cardinality (1:1 관계)**

e.g. 학생과 신체 정보 → 한 명의 학생은 하나의 신체 정보만 갖기 때문에

![image](https://github.com/user-attachments/assets/fb08619b-7ae9-4eb9-a4a7-c708e31d68cd)


**One to Many Cardinality (1:N 관계)**

e.g. 한 명의 학생은 여러 개의 취미를 가질 수도 있음

![image](https://github.com/user-attachments/assets/2a663ea9-2028-410e-9cd3-372a067d7276)


**Many to Many Cardinality(M:N 관계)**

e.g. 제품 엔티티 입장에서,TV 제품은 삼성 티비, 애플 티비, lg 티비 등 여러 제조업체 제품이 있을 수 있음

제조업체 엔티티 입장에서도 삼성 제조업체는 TV 뿐만 아니라 세탁기, 냉장고 등 여러 제품을 생성할 수 있음

![image](https://github.com/user-attachments/assets/53f10f8f-29e6-4e07-bfd2-75096228c23f)


두 엔티티가 M:N 관계에 있는 경우, 두 개의 엔티티만으로는 서로를 표현하는데 부족함 → 1:N, N:1로 조정하는 작업이 필요

이를 위해 중간 엔티티를 만들어 기존 두 엔티티의 공유 속성 역할을 하게 됨

![image](https://github.com/user-attachments/assets/73410540-1cb7-402d-a91f-0e8805601cb4)


### 🌱 ERD 관계의 참여도

관계선의 각 측의 끝자락에 기호를 표시

- | : 반드시 있어야 하는 개체
- O : 없어도 되는 개체

![image](https://github.com/user-attachments/assets/f41fdb56-de23-42da-b471-004edf0a3e9c)


![image](https://github.com/user-attachments/assets/025140d0-5dd2-4078-af04-1d7fe4e86e56)


**관계의 선택 기호**

e.g. 취미를 가진 학생이 있을 수도 있고, 취미가 없는 학생이 있을 수도 있음

**관계의 필수 기호**

e.g. 학법 21003 학생의 취미가 낚시라는 정보가 있다면, 21003 학생의 정보는 반드시 학생 엔티티에 존재해야함

### 🌱 JOIN

![image](https://github.com/user-attachments/assets/2c274646-0da2-4b92-9b2e-31b6dc2b3e9b)


## 🪴 ERD Cloud 사용법

[ERDCloud](https://www.erdcloud.com/)

### 🌱 ERD 생성

1. title 작성
    - 논리적 이름: 왼쪽. 내가 알아보기 위한 이름
    - 물리적 이름: 오른쪽. 실제 데이터베이스에 저장되는 테이블 이름
    
    ![image](https://github.com/user-attachments/assets/fa58e271-1ff9-4c2e-8dea-0ee9cdf11725)

    
2. attribute 추가
    - 노란색 버튼: PK attribute 추가
    - 파란색 버튼: 일반 attribute 추가
    
    ![image](https://github.com/user-attachments/assets/0a368aef-1644-4544-a5e6-c86bdfd20f22)

    

### 🌱 노출되는 속성 선택

오른쪽 위 톱니바퀴 모양 아이콘 → 테이블에서 어떤 속성을 보여줄 지 설정 가능

- Display: 화면에서 보여지는 테이블의 속성을 선택하는 곳
- share: 다른 사용자와 동시 작업을 할 지 선택

![image](https://github.com/user-attachments/assets/40c508f4-cf22-4ca7-ab81-5781531b32d8)


### 🌱 관계도 그리기

왼쪽 메뉴바에서 선택 

관계를 해제하려면 연결괴어 있는(FK로 등록된) attribute를 삭제해야 함

- Identifying Relationship: 실선
- Non-Identifying Relationship: 점선

## 🪴인스타그램 기능 분석

### 🌱 요구사항

- 게시글 조회
- 게시글에 사진과 함께 글 작성하기
- 게시글에 댓글 및 대댓글 기능
- 게시글에 좋아요 기능
- 게시글, 댓글, 좋아요 삭제 기능
- 유저 간 1:1 DM 기능
- 회원 기능은 필수로 추가 → 추후 로그인에서 사용됨

### 🌱 기능 분석

**User & Follow**
| 인스타그램 화면       | ERD                                             | 
|----------------|--------------------------------------------------------|
| ![image](https://github.com/user-attachments/assets/d6e62cd4-f437-45ce-80d8-a1da9b5dfc60) | ![image](https://github.com/user-attachments/assets/c44eae34-9613-489c-96bf-f36caa0ea173) |

[ 기능분석 ]

- 생일을 기준으로 만 14세 이상만 가입할 수 있다

[ 관계분석 ]

- 한 명의 회원은 아무도 팔로우하지 않거나, 여러 명을 팔로우할 수 있다 (1:N)
- 한 명의 회원은 게시물을 아무것도 올리지 않거나 여러 개를 올릴 수 있다 (1:N)

**Post & Post Image & Post Like & Scrap**
| 인스타그램 화면       | ERD                                             | 
|----------------|--------------------------------------------------------|
|![image](https://github.com/user-attachments/assets/b282863d-33a0-402e-a309-6178bcf5683c) | ![image](https://github.com/user-attachments/assets/f249369c-6727-4045-9a9b-ef89412ff09e) |


[ 기능분석 ]

- 이미지와 본문, 생성일
- 게시물에는 여러 장의 사진을 올릴 수 있다

[ 관계분석 ]

- 한 명의 회원은 여러 게시물을 스크랩할 수 있고, 한 개의 게시물은 여러 회원에게 스크랩될 수 있다 (M:N)
- 한 명의 회원은 여러개의 게시물에 좋아요를 누를 수 있고, 하나의 게시물에는 여러 명이 좋아요를 누를 수 있다 (M:N)

**Comment & Comment Like**
| 인스타그램 화면       | ERD                                             | 
|----------------|--------------------------------------------------------|
| ![image](https://github.com/user-attachments/assets/c65cce9c-9d45-4831-9fcd-d7a0ff425464) | ![image](https://github.com/user-attachments/assets/a98cbbb2-9836-4dc0-a73d-68035af7259c) |


[ 기능분석 ]

- 댓글을 게시글에 달린다
- 댓글에는 대댓글이 달릴 수 있다
- 대댓글의 수가 표시된다
    
    → 부모 id가 null이 아닐 때 부모 id에 딸린 댓글 수로 계산
    

[ 관계분석 ]

- 한 명의 회원은 여러 개의 댓글을 달 수 있다(1:N)
- 하나의 댓글에는 여러 명이 좋아요를 누를 수 있고 한 명의 회원은 여러 개의 댓글에 좋아요를 누를 수 있다(M:N)
- 하나의 게시글에는 여러 개의 댓글이 달릴 수 있다(1:N)

**DM & DM Room**
| 인스타그램 화면       | ERD                                             | 
|----------------|--------------------------------------------------------|
| ![image](https://github.com/user-attachments/assets/0b685773-59c4-4506-81f0-ceb6cb03ffef) | ![image](https://github.com/user-attachments/assets/7c69ab9f-597b-40c3-85e2-01f8c408ac4d)|


[ 기능분석 ]

- 1:1 채팅방
- 보냈던 DM을 언급해 답장할 수 있다
- DM 방에서 주고 받는 이미지의 경우, url 한 줄로 만들 수 있기 때문에 따로 엔티티를 만들지 않는다

[ 관계분석 ]

- DM 답장을 위해서는 하나의 DM만 언급이 가능하다 (1:1)
- 하나의 DM방에는 여러 개의 DM이 있다(1:N)
- 한 명의 회원은 여러 개의 DM을 보낼 수 있다 (1:N)
- 한 명의 회원은 여러 개의 DM 방에 들어갈 수 있고, 하나의 DM방에는 2명의 회원이 들어갈 수 있다 (M:N)

### 🌱 ERD

https://www.erdcloud.com/d/qF45tQoypstpAqQW3

![image](https://github.com/user-attachments/assets/145cb4c6-55ea-4425-9681-363bc7d14c15)

## 🪴MySQL 연결

```bash
user@Seoahs-MacBook ~ % mysql -uroot -p
Enter password: 
mysql> show databases;
mysql> CREATE DATABASE instagram;
```

```java
	implementation 'mysql:mysql-connector-java:8.0.33'
```

## 🪴JPA

### 🌱 JPA란

Java Persistence API

자바 진영의 ORM 기술 표준

어플리케이션과 JDBC 사이에서 동작

즉, 자바 객체를 데이터베이스 테이블에 저장하거나, 테이블 데이터를 자바 객체로 변환하는 작업을 자동으로 해주는 도구

![image](https://github.com/user-attachments/assets/cb06ffa0-6f02-482c-9e4a-62687e69e4b9)


### 🌱 JPA를 사용해야 하는 이유

- 반복적/기본적 SQL을 JPA가 만들어 실행해줌
- SQL과 데이터 중심의 설계 대신 객체 중심의 설계 가능 → 객체 지향적 개발 가능
- DB 구조를 변경하거나 DB 종류를 바꾸는 것도 쉬워짐
- 생산성을 높일 수 있고, 유지보수가 간편함

### 🌱 JPA 사용하기

1. build.gradle에 dependency 추가
    
    ```java
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    }
    ```
    
2. application.yml에 JPA 설정 추가(MySQL 설정)
    
    ```java
    spring:
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: {JDBC URL}
        username: {DATABASE USERNAME}
        password: {DATABASE PASSWORD}
      jpa:
        database: mysql
        database-platform: org.hibernate.dialect.MySQL8Dialect
        hibernate:
          ddl-auto: {OPTION}
          show_sql: true
        properties:
          hibernate:
            dialect: org.hibernate.dialect.MySQL8Dialect
            database-platform: org.hibernate.dialect.MySQL8Dialect
            format_sql: true
    ```
    
    - **url**: jdbc:mysql://[ip주소]:[port]/DB이름
    - **ddl-auto** options: `create` `create-drop` `update` `validate` `none`
        - `create`: 엔티티 정보를 바탕으로 테이블도 직접 생성
        - `update`: 애플리케이션이 실행될 때 기존 테이블 구조를 유지하면서, 엔티티 클래스의 변경 사항만 데이터베이스 테이블에 반영
    - `show_sql` : hibernate가 DB에 날리는 모든 쿼리(DDL, DML)을 보여줌
    - `format_sql` : 보여지는 쿼리를 예쁘게 포맷팅

### 🌱 JPA Mapping

**자바 객체와 데이터베이스 테이블을 서로 연결하는 과정**

→ 이를 통해 자바 객체를 데이터베이스에 저장하거나, 반대로 데이터베이스에서 자바 객체로 데이터를 가져올 수 있음

**Entity 매핑**

자바 클래스에 `@Entity`를 붙여 데이터베이스의 테이블과 연결

**필드 매핑**

자바 클래스의 필드(변수)를 데베 테이블의 칼럼과 연결

기본적으로 필드 이름과 컬럼 이름이 같으면 자동으로 매핑되지만, `@Column`을 사용해 컬럼 이름을 면시적으로 지정할 수도 있음

```java
@Column(name = "user_name")
private String name; // 자바의 name 필드를 데이터베이스의 user_name 컬럼에 매핑
```

**관계 매핑**

엔티티 간의 관계를 매핑

`@OneToOne` `@OneToMany` `@ManyToMany`

e.g. 한 사용자가 여러 주문을 할 수 있는 경우

## 🪴 클래스 생성

엔티티마다 클래스를 생성하고, 필요한 필드 추가

### 🌱 필드 구성 예시

```java
package com.ceos20.spring_boot.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment_content", nullable = false, length = 200)
    private String commentContent;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "like_num", nullable = false)
    private int likeNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Column(name = "post_id2")
    private Long postId2;

}

```

**Entity 클래스 생성 시**

- `@Entity`
    
    해당 클래스가 JPA 엔티티임을 나타냄
    
    데이터베이스 테이블과 매핑하고 관리하는 클래스로 지정
    
- `@Getter` `@Setter`
    
    lombok을 사용해 자동으로 getter와 setter 메서드 생성
    
- `@NoArgsConstructor(access = AccessLevel.PROTECTED)`
    
    기본 생성자에 대한 접근 제어를 제한할 수 있음
    
    즉, 외부에서 기본 생성자를 직접 호출하지 못하도록 제한하고, 같은 패키지 내에서나 하위 클래스에서만 생성자를 사용할 수 있게 함
    
- `@AllArgsConstructor`
    
    클래스에 존재하는 모든 필드에 대해 생성자를 자동으로 만들어줌
    
- `@Builder`
    
    빌더 패턴을 자동으로 생성해줌
    
    선택적으로 필드를 설정할 수 있다는 장점
    
- `@Table(name = "따로 지정할 테이블명")`
    
    엔티티와 매핑되는 테이블 이름 지정
    
    지정 안 하면 클래스 이름이 기본값으로 사용됨
    

**Primary Key 속성**

권장하는 식별자 전략: `Long형 + 대체키 + 키 생성전략`

- `@Id`
    
    테이블의 기본 키와 객체의 필드 매핑
    
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`
    
    `@Id` 만 사용할 경우 기본 키 값을 직접 할당해줘야 하는데, 기본 키를 직접 할당하는 대신 데이터베이스에서 생성해주는 값 사용
    
- `@Column(name = "comment_id")`
    
    따로 지정할 속성명
    

**Foreign Key 속성과 연관 관계**

- 연간관계 `@OneToMany`   `@ManyToOne`   `@OneToOne`   `@ManyToMany`
- `@JoinColumn(name = "조인속성")`
- 로딩
    - 즉시 로딩
        
        `@XToOne(OneToOne, ManyToOne) 관계`는 디폴트 값이 즉시 로딩이므로 직접 지연 로딩으로 설정해야 함
        
    - 지연 로딩
        
        `@XToMany`는 기본이 LAZY
        
        지연 로딩이 트랜잭션 밖에서 안 되는 등의 이슈가 있지만 다른 대안 사용
        

**일반 속성**

- `@Column(name = "연결할 column 명", nullable = false)`

**Enum 타입**

🤔 **Enum 타입**

열거형을 표현하는 데이터 타입. 고정된 상수 집합을 정의할 때 사용

미리 정해진 몇 가지 값만 가질 수 있는 변수를 만들고 싶을 때 사용

```java
public enum Season {
    SPRING, SUMMER, FALL, WINTER
}

Season currentSeason = Season.SPRING;
```


- `@Enumerated(EnumType.String)`
    
    Enum의 값 자체(문자열)을 저장하도록 할 수 있음
    
- `EnumType.ORDINAL`
    
    Enum 값의 순서(index. 0부터 시작)을 데베에 저장
    

⚠️ `EnumType.ORDINAL` 사용 시 **Enum 값이 추가되거나 순서가 바뀔 때 문제가 발생할 수 있음**

새로운 Enum 값이 중간에 추가되면 기존에 저장된 값들과 순서가 맞지 않게 돼서 데이터 무결성을 헤칠 수 있음

→ `EnumType.STRING` 사용 권장



## 🪴Repository

**JpaRepository가 상속하는 Interface**

- 형식: `<객체, ID Type>`
    
    ```java
    @Repository
    public interface MemberRepository extends JpaRepository<Member, Long>{
    }
    ```
    
- 스프링 데이터 JPA 제공 기능
    - 인터페이스를 통한 기본적인 CRUD
    - `findByName()` `findByEmail()`처럼 메서드 이름만으로 조회 기능 제공
    - `in` 이나 `exist` `distinct` 조건도 사용 가능
        - `in` : 특정 값이 지정된 값들 중 일치하는게 하나라도 있는지 확인
            
            ```java
            List<User> findByAgeIn(List<Integer> ages);
            ```
            
        - `exist` : 특정 조건을 만족하는 레코드가 존재하는지 확인
            
            ```java
            boolean existsByEmail(String email);
            ```
            
        - `distinct` : 중복된 결과를 제거하고 고유한 결과만 반환하도록 하는 것
            
            ```java
            List<User> findDistinctByAge(int age);
            ```
            
    - 페이징 기능 자동 제공
        
       
        🤔 **페이징 기능**
        
        한 번에 많은 데이터를 가져오는 대신 데이터를 일정한 크기로 잘라서 페이지 단위로 가져오는 기능
        
        Pageable 인터페이스와 함께 사용되며, 페이지 번호와 페이지 크기를 지정해서 쿼리의 결과를 나눠서 받을 수 있음
        

# 🌳 인스타그램 서비스 코드

## 🪴서비스 계층 코드

소프트웨어 아키텍처 비즈니스 로직을 담당하는 계층

Controller와 DAO(데이터 액세스 계층 or Repository)사이에서 중간 역할을 수행하며, 주로 애플리케이션의 주요 비즈니스 규칙과 처리를 수행

### 🌱 Directory Architecure

도메인형 패키지 구조로 구성

![image](https://github.com/user-attachments/assets/ab693db7-54dc-4b45-b563-55f2479bdd72)


![image](https://github.com/user-attachments/assets/5587819f-588a-4675-b67d-dd7820dddc76)


### 🌱 서비스 코드의 기본구조


💡 **MVC 패턴 (Model-View-Controller)**

애플리케이션을 3가지 역할로 나눠 설계하는 구조를 의미

1. **Model 클래스** 만들기
    
    핵심 데이터와 그 데이터를 처리하는 로직을 즉, 구조를 정의하는 클래스
    
    데이터 → 데이터베이스에 저장되는 객체(Entity)
    
    ```java
    public class User {
    	private Long id;
    	private String name;
    	private String email;
    }
    ```
    
2. Model 객체를 담는 **Repository 클래스** 구현하기
    
    Model 객체를 데이터베이스에 저장하거나 조회하는 역할
    
    즉, Model과 데이터베이스 사이에서 CRUD 작업을 처리하는 클래스
    
    스프링에서는 JpaRepository를 사용해 기본적인 데베 작업을 자동으로 처리할 수 있음
    
    ```java
    import org.springframework.data.jpa.repository.JpaRepository;
    
    public interface UserRepository extends JpaRepository<User, Long> {
    	User findByEmail(String email);
    }
    ```
    
3. Repository에 인덱싱하기 위한 **Service 클래스** 만들기
    
    비즈니스 로직을 처리하는 계층
    
    
    🤔 **비즈니스 로직**
    
    애플리케이션의 핵심 기능을 처리하는 규칙이나 알고리즘
    
    사용자의 요청이 들어왔을 때 이를 어떻게 처리할지를 결정하는 단계
    
    e.g. 사용자가 회원가입을 할 때 이메일 중복 확인, 비밀번호 암호화 등
    
    
    
    주로 Repository를 통해 데이터를 가져오고 처리한 뒤 Controller에게 전달
    
    ```java
    // 이메일로 사용자를 조회하거나 새 사용자를 생성하는 비즈니스 로직 처리
    @Service
    public class UserService {
    
        private final UserRepository userRepository;
    
        @Autowired
        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }
    
        public User getUserByEmail(String email) {
            return userRepository.findByEmail(email);
        }
    
        public User createUser(User user) {
            return userRepository.save(user);
        }
    }
    
    ```
    
4. Service를 통해 Model이 담겨 있는 Repository에 접근하기 위한 **Controller 클래스**
    
    사용자의 요청을 받아서 처리하고, 그 결과를 반환하는 역할
    
    주로 웹 요청을 처리하는 계층으로, Service를 통해 필요한 데이터를 조회하고, 그 결과를 사용자에서 반환하는 역할
    
    HTTP 요청/응답을 처리하는 역할
    
    ```java
    import org.springframework.web.bind.annotation.*;
    
    @RestController
    @RequestMapping("/users")
    public class UserController {
    
        private final UserService userService;
    
        @Autowired
        public UserController(UserService userService) {
            this.userService = userService;
        }
    
        @GetMapping("/{email}")
        public User getUserByEmail(@PathVariable String email) {
            return userService.getUserByEmail(email);
        }
    
        @PostMapping
        public User createUser(@RequestBody User user) {
            return userService.createUser(user);
        }
    }
    ```
    

e.g. 사용자가 브라우저에서 *사용자 정보 조회* 요청을 보냄

→ Controller는 그 요청을 받아 어떤 작업이 필요한지 결정한 후 Service로 요청 전달

```java
@GetMapping("/{email}")
public User getUserByEmail(@PathVariable String email) {
    return userService.getUserByEmail(email);  // Service 호출
}
```

→ Service는 비즈니스 로직을 처리하면서 Repository를 호출해 데베에 해당 정보를 조회

```java
public User getUserByEmail(String email) {
    // 1. 이메일 형식이 유효한지 검증하는 비즈니스 로직
    if (!email.contains("@")) {
        throw new IllegalArgumentException("Invalid email format");
    }
    
    // 2. Repository를 통해 데이터베이스에서 사용자 정보를 조회
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));
}
```

→ Repository는 Model 객체로 데이터를 반환

요청된 이메일에 해당하는 사용자를 조회하는 작업

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // 이메일로 사용자 찾기
}
```

→ 이를 Service가 받아 다시 Controller에 전달

→ Controller는 그 데이터를 사용자에게 응답으로 반환



**클래스 선언**

`@Service` 로 서비스 클래스임을 선언하기

**필드 주입**

서비스 클래스는 Repository나 다른 서비스 클래스들을 의존성으로 가질 수 있음 → `@Autowired` 사용

**비즈니스 로직 메서드**

데이터를 가져와서 가공하거나, 데이터베이스에 저장하는 작업

e.g. User

**UserService.java**

```java
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Transactional
    public User registerUser(User user) {
        // 1. 만 14세 이상만 가입 가능
        if (!isValidAge(user.getBirthday())){
            throw new IllegalArgumentException("만 14세 이상만 가입이 가능합니다.");
        }

        // 2. 중복된 이름 허용 안함
        if (userRepository.existsByName(user.getName())) {
            throw new IllegalArgumentException("이미 해당 이름으로 가입된 사용자가 존재합니다.");
        }

        return userRepository.save(user);
    }

    // 만 14세 이상인지 확인
    // 생년월일을 받아 나이가 14세 이상인지 확인하는 메서드
    private boolean isValidAge(LocalDateTime birthday) {
        LocalDate birthDate = birthday.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();
        return age >= 14;
    }

    // 사용자 조회
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    // 사용자 삭제
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
```

**UserRepository.java**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 특정 이름을 가진 사용자가 있는지 확인
    boolean existsByName(String name);

    // 특정 ID로 사용자 조회
    Optional<User> findById(Long userId);
}
```

## 🪴DTO (Data Transfer Object)

**데이터를 묶어서 전달하기 위해 사용하는 객체**

### 🌱 DTO의 역할

- 계층 간(Controller-View, Controller-Service 등)의 데이터 전송을 담당
- DTO는 단순히 데이터를 담기 위한 객체이므로 비즈니스 로직이 포함되지 않음
- 엔티티 객체는 보통 데이터베이스와 직접적으로 매핑되는데, 이를 외부로 노출하면 보안에 문제가 발생할 수도 있음. 이를 방지하기 위해 DTO를 사용해 필요한 데이터만 전달할 수 있음


🤔 **Entity vs DTO**

Entity

데이터베이스와 직접 매핑되는 객체 → 주로 데베와의 상호작용을 처리

DTO

순수하게 데이터 전송을 위한 객체

엔티티에서 불필요한 정보를 제거하거나, 여러 엔티티의 데이터를 하나로 묶어서 전송할 수 있음




🤔 **MVC에서 DTO 역할**

View

사용자가 보는 화면(UI)를 처리하는 역할

DTO는 view와 cotroller 사이에서 주로 사용되며 view에 표시할 데이터만을 담아 전달함

Controller

view나 클라이언트에게 결과를 반환할 때 엔티티 객체를 그대로 반환하는 것이 아니라, DTO로 변환해서 반환하는 경우가 많음

Service

데베에서 가져 온 엔티티를 변환하여 DTO로 전달하거나, DTO 데이터를 받아 엔티티로 변환하는 작업을 수행



### 🌱 DTO 구성

- 전송에 필요한 정보만 포함
    
    비밀번호나 내부 ID등은 DTO에 포함시키지 않음
    
- 같은 엔티티라도 그때그때 필요한 정보가 다르면 여러 개의 DTO를 만드는게 좋음
- 생성자, getter, setter, builder 패턴
    
    
    🤔 **Builder 패턴**
    
    복잡한 객체를 쉽고 유연하게 만들도록 도와주는 설계 패턴
    
    생성자를 사용해 객체를 만들 때, 필드가 많으면 매개변수들의 순서나 의미를 헷갈리기 쉬움 + 몇몇 매개변수는 선택 사항일 수도 있음
    
    → Builder 패턴을 사용해 체이닝 방식으로 각 필드를 설정하고, 마지막에 한 번에 객체를 생성할 수 있음
    
    
    🤔 **체이닝 방식**
    
    메서드 호출을 연속적으로 연결해 하나의 명령처럼 동작하게 하는 프로그래밍 기법
    
    
    
    ```java
    public class User {
        private String name;
        private int age;
        private String address;
        private String email;
    
        // UserBuilder 클래스를 통해 객체를 생성
        public static class Builder {
            private String name;
            private int age;
            private String address;
            private String email;
    
            public Builder name(String name) {
                this.name = name;
                return this;
            }
    
            public Builder age(int age) {
                this.age = age;
                return this;
            }
    
            public Builder address(String address) {
                this.address = address;
                return this;
            }
    
            public Builder email(String email) {
                this.email = email;
                return this;
            }
    
            // 빌드 메서드로 최종적으로 User 객체를 생성
            public User build() {
                return new User(this);
            }
        }
    
        // build() 메서드가 호출되면, 이걸 통해 빌더에 설정된 값들이 최종 User 객체에 설정되는 역할
        private User(Builder builder) {
            this.name = builder.name;
            this.age = builder.age;
            this.address = builder.address;
            this.email = builder.email;
        }
    }
    ```
    
    ```java
    User user = new User.Builder()
                   .name("Alice")
                   .age(30)
                   .address("123 Main St")
                   .email("alice@example.com")
                   .build();
    ```
    
     
    

e.g. User

**회원가입**

- 팔로우/팔로워 수나 게시글 수는 회원가입 시 필요하지 않음
    
    → 기본 값 0으로 설정해놓음
    

UserCreateDTO.java

```java
public class UserCreateDTO {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String gender;
    @Getter
    @Setter
    private LocalDateTime birthday;
    @Getter
    @Setter
    private String profileImage;

    public UserCreateDTO() {}

    public UserCreateDTO(String name, String password, String gender, LocalDateTime birthday, String profileImage) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.profileImage = profileImage;
    }

}
```

UserDTO.java

```java
public class UserDTO {
    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String gender;
    @Getter
    @Setter
    private LocalDateTime birthday;
    @Getter
    @Setter
    private String profileImage;
    @Getter
    @Setter
    private Long uploadPost;
    @Getter
    @Setter
    private Long followNum;
    @Getter
    @Setter
    private Long followingNum;

    public UserDTO() {}

    public UserDTO(Long userId, String username, String password, String gender, LocalDateTime birthday, String profileImage, Long uploadPost, Long followNum, Long followingNum) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.profileImage = profileImage;
        this.uploadPost = uploadPost;
        this.followNum = followNum;
        this.followingNum = followingNum;
    }

}
```

UserService.java

```java
@Transactional
    public UserDTO registerUser(UserCreateDTO userCreateDTO) {
        // 1. 만 14세 이상만 가입 가능
        if (!isValidAge(userCreateDTO.getBirthday())){
            throw new IllegalArgumentException("만 14세 이상만 가입이 가능합니다.");
        }

        // 2. 중복된 이름 허용 안함
        if (userRepository.existsByName(userCreateDTO.getName())) {
            throw new IllegalArgumentException("이미 해당 이름으로 가입된 사용자가 존재합니다.");
        }

        // DTO -> 엔티티 변환
        User user = User.builder()
                .name(userCreateDTO.getName())
                .password(userCreateDTO.getPassword())
                .gender(userCreateDTO.getGender())
                .birthday(userCreateDTO.getBirthday())
                .profileImage(userCreateDTO.getProfileImage())
                .uploadPost(0L) //기본값 설정
                .followerNum(0L)
                .followingNum(0L)
                .build();

        // 저장 후 UserDTO 반환
        User savedUser = userRepository.save(user);
        return new UserDTO(
            savedUser.getUserId(),        // 자동 생성된 userId
            savedUser.getName(),
            savedUser.getPassword(),
            savedUser.getGender(),
            savedUser.getBirthday(),
            savedUser.getProfileImage(),
            savedUser.getUploadPost(),
            savedUser.getFollowerNum(),
            savedUser.getFollowingNum()
        );
    }
```

**회원 조회**

- password나 gender는 필요없을 것 같아서 뺌

UserProfileDTO.java

```java
public class UserProfileDTO {

    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String profileImage;
    @Getter
    @Setter
    private Long uploadPost;
    @Getter
    @Setter
    private Long followerNum;
    @Getter
    @Setter
    private Long followingNum;

    // 기본 생성자
    public UserProfileDTO() {}

    // 모든 필드를 포함하는 생성자
    public UserProfileDTO(Long userId, String name, String profileImage, Long uploadPost, Long followerNum, Long followingNum) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.uploadPost = uploadPost;
        this.followerNum = followerNum;
        this.followingNum = followingNum;
    }

}
```

UserService.java

```java
@Transactional(readOnly = true)
    public UserProfileDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        // User 엔티티를 UserDTO로 변환하여 반환
        return new UserProfileDTO(
                user.getUserId(),
                user.getName(),
                user.getProfileImage(),
                user.getUploadPost(),
                user.getFollowerNum(),
                user.getFollowingNum()
        );
    }
```

**회원 삭제**

- 식별자 `user_id` 만 있으면 삭제할 수 있으므로 DTO 안 만들어줌

# 🌳 영속성 컨텍스트

**JPA에서 엔티티 객체들을 관리하는 일종의 저장소 또는 캐시**

즉, 데이터베이스에 저장된 데이터와 자바 애플리케이션의 엔티티 객체 사이의 중간 다리 역할

### 🌱 EntityManagerFactory, EntityManager

**Entity**

RDB의 테이블과 매핑되는 객체

**EntityManager**

Entity를 저장하고, 수정하고, 삭제하고, 조회하는(CRUD) 등 Entity와 관련된 모든 일을 처리하는 것

- 데이터베이스와 연결된 영속성 컨텍스트를 관리하는 객체
- 상태를 가짐 e.g. 데베에서 조회한 엔티티를 메모리에 저장하고, 트랜젝션이 끝날 때 그 데이터를 데베에 반영
- 하나의 트랜젝션 동안 영속성 컨텍스트에 저장된 엔티티들을 관리
    
    만약 여러 스레드가 하나의 EntityManager를 공유해서 동시에 작업 → 각 스레드가 서로 다른 엔티티를 다루거나, 같은 엔티티의 상태를 동시에 변경하려고 할 때, 충돌이 발생할 수 있음
    
    따라서 EntityManager는 각 스레드가 독립적으로 사용해야하고, Thread Safe 하지 않다고 말할 수 있음
    

**EntityManagerFactory**

Entity를 관리하는 EntityManager를 생성하는 공장

- 하나만 만들어져서 애플리케이션 전체에서 사용됨
- stateless → 내부에 어떤 상태나 데이터를 가지고 있지 않음
    
    때문에 여러 스레드가 동시에 사용해도 문제가 발생하지 않음 → Thread Safe
    

![image](https://github.com/user-attachments/assets/ac3bc100-2b06-49e6-867c-85a993e6bf37)


엔티티 매니저는 트랜젝션을 시작할 때, 커넥션을 획득함

### 🌱 영속성 컨텍스트

**엔티티를 영구적으로 저장하는 환경**

- JPA를 이용하는데 가장 중요한 요소
- 엔티티매니저는 엔티티를 영속성 컨텍스트에 보관하고 관리함
    
    ![image](https://github.com/user-attachments/assets/62d63bbe-7734-4a81-9566-097b05168526)

    

**영속성 컨텍스트의 특징**

- 영속성 컨텍스트와 식별자 값
    
    영속성 컨텍스트 안에서 관리되는 엔티티는 식별자 값을 반드시 가져야 함
    
    → key-value로 엔티티를 관리하기 때문
    
- 영속성 컨텍스트와 데이터 베이스 저장
    
    영속성 컨텍스트에는 DB에서 가져온 엔티티들이 메모리에 저장됨
    
    → 엔티티를 수정하면 수정 사항은 일단 메모리에 남아 있고, 바로 DB에 반영되지는 않음
    
    → flush를 호출하면 메모리에 있는 변경 사항들이 DB에 반영됨
    
    → 아직 트랜젝션은 끝나지 않았음. 반영된 데이터 나중에 롤백 가능
    
    
    🤔 **FLUSH**
    
    JPA에서 최적화를 위해 엔티티의 변경 사항을 바로바로 DB에 반영하지 않고, 필요할 때 한꺼번에 반영
    
    flush가 발생하는 3가지 경우
    
    1. `transaction.commit()`
    2. `entityManager.flush()`
    3. JPQL 쿼리 실행 전
    
    e.g
    
    1. 변경: `entity.setName("New Name");`
        
        엔티티 객체의 이름을 변경했지만, 아직 DB에는 반영되지 않음
        
    2. flush 호출: `em.flush();`
        
        메모리에 있던 변경된 이름이 DB에 저장
        
    3. 트랜젝션 커밋: `em.getTransaction().commit();`
        
        트랜젝션이 커밋되면서 변경이 확정
        
        롤백으로 flush된 내용도 되돌릴 수 있긴 함
        
   
    

영속성 컨텍스트가 엔티티를 관리함으로 얻는 이점

- 1차 캐시
    
    영속성 컨텍스트 내부에서 엔티티를 보관하는 저장소
    
    1. 데이터 조회 시 1차 캐시에 이미 있는지 확인하고, 데이터가 있으면 가져옴
    2. 1차 캐시에 데이터가 없으면, DB에 데이터를 요청
    3. DB에서 받은 데이터를 다음에 재사용할 수 있도록 1차 캐시에 저장
    
    ⇒ DB에서 조회하는 횟수를 줄여, 성능상 이점을 가져올 수 있음
    
- 같은 엔티티에 대해 객체 동일성 보장 by 1차 캐시
    
    불필요한 중복을 방지해주고, 변경 사항을 일관성 있게 관리할 수 있도록 함
    
- 트랙잭션을 지원하는 쓰기 지연
    
    쓰기 지연: 영속성 컨텍스트에서 바로 DB로 쿼리를 보내지 않고 SQL 쿼리를 쓰기 지연 저장소에 모아놨다가, 영속성 컨텍스트가 flush 하는 시점에 모아둔 SQL 쿼리를 데이터베이스로 보내는 기능
    
    1차 캐시에 저장하는 persist 명령어를 사용하면, 쓰기 지연 SQL 저장소에 query문을 임시로 저장
    
- 변경 감지
    1. 1차 캐시에 저장될 때 최초 저장 시점의 SnapShot을 저장
    2. commit을 하면 엔티티와 스냅샷을 비교
    3. 변경을 감지하면 UPDATE 쿼리를 쓰기 지연 SQL 저장소에 생성하고, commit되기 전 내부적으로 flush가 호출이 되어 변경된 사항이 DB에 반영

### 🌱 엔티티 생명주기

![image](https://github.com/user-attachments/assets/eaca30e0-0f00-4014-9385-48eb7c33bc4e)


**비영속 (new / transient)**

영속성 컨텍스트와 전혀 관계가 없는 상태

```java
Customer customer = new Customer(); 
customer.setId(1L);
customer.setFirstName("ceos");
customer.setLastName("kim");
```

**영속 (managed)**

영속성 컨텍스트에 저장된 상태

```java
em.persist(customer);
```

**준영속 (detached)**

영속성 컨텍스트에 저장되었다가 분리된 상태

```java
// 영속 상태의 customer 객체(엔티티)를 영속성 컨텍스트에서 분리
em.detach(customer);

// 영속 상태의 모든 객체를 영속성 컨텍스트에서 분리
em.clear();

// 영속성 컨텍스트를 종료
em.close();
```

**삭제 (removed)**

삭제된 상태

```java
// customer 엔티티를 영속성 컨텍스트에서 분리하고, DB에서도 삭제한다.
em.remove(customer);
```

### 🌱 트랜잭션 범위의 영속성 컨텍스트

**스프링 컨테이너는 트랜잭션 범위의 영속성 컨텍스트 전략을 기본으로 사용**

→ 트랜잭션이 없는 프레젠테이션 계층(controller)에서의 엔티티는 준영속 상태이므로 변경 감지, 지연 로딩 등이 동작하지 않음

![image](https://github.com/user-attachments/assets/bafac4da-93e5-428c-bc63-a8a95032b3c0)


# 🌳 N + 1 문제

## 🪴 로딩 전략

### 🌱 즉시로딩 (Eager Loading)

연관관계가 있는 엔티티를 데이터베이스에서 즉시 로드해 메인 엔티티와 함께 로드

- `@XToOne` 연관관계 → 즉시로딩이 디폴트
- 즉시로딩을 사용하면 연관된 모든 엔티티를 즉시 로드하기 때문에 성능 문제가 발생할 수 있음
    
    → 모든 연관관계는 지연로딩으로 설정하는 것이 좋음
    
    b/c N+1 문제
    

### 🌱 지연 로딩 (Lazy Loading)

연관된 엔티티가 실제로 필요할 때까지 DB에서 로드를 지연시킴

- `@OneToMany` `@ManyToMany` 연관관계 → 지연로딩이 디폴트
- 필요한 경우에만 연관된 엔티티를 로드하기 때문에 성능을 최적화할 수 있음

## 🪴  N +  1 문제

**N개의 객체를 조회할 때 하나의 쿼리가 나가고, 이때 조회한 N개의 객체에 연관된 객체를 조회하기 위해 N개의 쿼리가 추가적으로 나가는 문제**

→ 불필요하게 많은 쿼리가 실행되어 성능이 저하되는 것

e.g. Order와 연관된 Customer를 지연 로딩으로 설정했을 경우

→ Order에 연관된 Customer를 조회할 때마다 N번의 추가 쿼리가 발생

```java
List<Order> orders = em.createQuery("SELECT o FROM Order o", Order.class).getResultList();  // 1개의 쿼리 실행
for (Order order : orders) {
    Customer customer = order.getCustomer();  // N개의 추가 쿼리 발생 (각 Order마다 Customer 조회)
}
```

### 🌱 해결방법: Fetch Join

**한 번의 쿼리로 연관된 엔티티를 모두 가져오게 함**

e.g.

```java
List<Order> orders = em.createQuery(
    "SELECT o FROM Order o JOIN FETCH o.customer", Order.class
).getResultList();  // 1번의 쿼리로 Order와 Customer를 모두 조회
```

### 🌱 해결방법: Entity Graph

**특정 엔티티를 어떻게 로딩할지 지정하는 방법**

엔티티의 특정 연관 필드를 명시적으로 즉시로딩하도록 설정할 수 있음

e.g. Order에서 Customer와 Product가 지연 로딩으로 적용됨

```java
@Entity
public class Order {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 기본적으로 지연 로딩
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)  // 기본적으로 지연 로딩
    private Product product;
}
```

Customer만 즉시로딩 하고 싶을 때

1. Entity Graph 정의
    
    ```java
    @Entity
    @NamedEntityGraph(
        name = "Order.withCustomer",    // 그래프 이름
        attributeNodes = @NamedAttributeNode("customer")  // 'customer'를 즉시 로딩
    )
    public class Order {
        @Id
        private Long id;
    
        @ManyToOne(fetch = FetchType.LAZY)
        private Customer customer;
    
        @ManyToOne(fetch = FetchType.LAZY)
        private Product product;
    }
    ```
    
2. Entity Graph 적용
   
   a. EntityManager로 조회
        
        ```java
        EntityGraph<?> entityGraph = em.getEntityGraph("Order.withCustomer");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", entityGraph);
        
        Order order = em.find(Order.class, 1L, hints);  // Customer는 즉시 로딩, Product는 지연 로딩
        ```
        
    
    b. Spring Data JPA로 조회
    
    ```java
    public interface OrderRepository extends JpaRepository<Order, Long> {
        
        @EntityGraph(value = "Order.withCustomer", type = EntityGraph.EntityGraphType.FETCH)
        List<Order> findAll();
    }
    ```

# 🌳 JWT 인증 방법

## 🪴 로그인 인증

### 🌱 로그인 인증은 왜 하는 걸까?

HTTP 통신으로 요청하면 응답을 한 후 종료되는 stateless 특징 때문에 연결이 끊어지므로 누가 로그인 중인지를 기억해야 하기 때문이다.

### 🌱 4가지 인증방식

**Cookie**

- key-value 쌍으로 이루어진 문자열
- 사용자 브라우저에 저장
- 4KB 이하의 한정적인 저장 공간으로 용량이 제한됨
- 브라우저마다 쿠키 지원 형태가 달라 브라우저간 공유가 불가능
- 보안에 취약하다는 단점(요청시 쿠키의 값을 그대로 보내기 때문)
- httpOnly flag로 클라이언트 단에서의 접근으로부터는 보호 가능
- 요청마다 쿠키를 담아 보내므로 쿠키 사이즈가 커지면 네트워크 부하가 심해짐
- Cookie 인증 방식
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/287bec0a-d20e-49e8-a756-c88be00e3f00/image.png)
    
    1. 처음에 클라이언트는 쿠키 없이 요청을 보낸다
    2. 서버는 이에 대한 응답을 할 때 클라이언트에 저장하고 싶은 정보를 응답 헤더의 Set-Cookie에 저장된 쿠기를 담아 보낸다
    3. 이후 클라이언트는 요청을 보낼 때마다 요청 헤더의 Cookie에 저장된 쿠키를 담아 보낸다
    4. 서버는 쿠키에 담긴 정보를 통해 클라인언트가 누군지 식별하거나 정보를 바탕으로 광고를 띄운다

**Session**

- Key/Value 쌍으로 이루어짐.
- 쿠키가 보안에 취약하기에 비밀번호같은 민감한 인증 정보를 브라우저가 아닌 서버 측에 저장하고 관리하는 것
- 서버 메모리나 서버 로컬 파일 또는 데이터 베이스에 저장(세션 저장소에 저장, 추가적인 저장 공간이 필요)
- 사용자 식별자인 session id를 저장과 정보를 저장
- 사용자가 많아지면 정보를 찾는 데이터 매칭에 오랜 시간이 걸리면서 부하가 가해짐
- 쿠키에 session id를 저장
- 위의 쿠키가 노출되더라도 session id가 개인정보를 가지지 않아 1번째 쿠키 인증보다는 안전하지만 해커가 세션 ID 자체를 탈취하여 위장하여 접근할 수 있다는 한계가 있음(하이재킹 공격)
- Session 인증 방식
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/00f7c9d9-3482-4116-a18c-297b3b6f93e5/image.png)
    
    1. 사용자가 로그인한다.
    2. 서버는 회원 확인 후 세션 저장소에 Session ID를 저장한다.
    3. 로그인에 대한 응답으로 Cookie에 Session ID를 담아 전달한다.
    4. 사용자가 요청할 때마다 Session ID가 담긴 Cookie와 함께 보낸다.
    5. 서버는 세션 저장소에 Session ID와 일치하는 지 확인한다.
    6. Session ID가 일치하면 응답을 보낸다.

**JWT(JSON Wev Token)**

- 사용자를 인증하고 식별하기 위한 정보들을 암호화시킨 토큰
- JSON 데이터를 URL로 이용할 수 있는 문자(Base64 URL-safe Encode)로 인코딩하여 직렬화한 것.
- 전자 서명도 있어 JSON의 변조를 체크할 수 있음
- 쿠키를 통해 클라이언트에 저장
- 단순히 HTTP 요청시 헤더에 토큰을 첨부하는 것만으로 데이터를 요청하고 응답을 받아올 수 있음
- '.'을 기준으로 Header, Payload, Signature 나누어짐
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/34bb0813-9126-47dd-81b3-2ddc81a3bbef/image.png)
    
- JWT 인증 방식
    
    JWT는 Access Token만으로도 인증 방식을 구현할 수 있음. 하지만 한 번 발급되면 유효기간이 만료될 때까지 삭제를 할 수 없어 만료 전에 해커에게 정보가 털린다면 대처할 방법이 없음. 그렇다고 유효기간을 짧게 하면 자주 인증해야된다는 불편함이 생김. 이에 Refresh Token을 같이 발급하여 문제를 해결하고 있음. 

    Refresh Token이 탈취될 수도 있지 않을까?
    
    Access Token처럼 똑같이 탈취당할 때의 취약점이 있다. 하지만 다른 점이 있다면 매 요청마다 HTTP 통신으로 노출이되는 Access Token과 달리, Access Token이 만료됐을 경우에만 네트워크 통신으로 Refresh Token을 서버로 보내기 때문에 탈취될 위험이 적다는 보안의 이점이 있다.
    
    

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/ee9e9399-ea42-4fb3-8669-c8b99d568298/image.png)

1. 사용자가 로그인한다
2. 서버는 회원 확인 후 서명된 JWT 생성하여 클라이언트에 응답한다 이때 Access Token과 Refresh Token을 같이 전달한다 
3. 사용자가 요청할 때마다 Access Token와 함께 보낸다
4. 서버에서 Access Token을 검증한다 
5. 검증이 완료되면 응답을 보낸다
6. Access Token 만료되었다
7. 사용자가 Access Token과 함께 데이터를 요청한다
8. 서버에서 Access Token이 만료된 것을 확인한다
9. 만료되었다는 것을 알려주는 응답을 보낸다
10. 사용자는 만료 응답을 받고 Access Token과 Refresh Token을 같이 담아 발급 요청을 보낸다
11. Refresh Token을 확인한 후 Access Token을 발급한다
12. Access Token과 함께 응답을 보낸다

**OAuth(Open Authorization)**

- 별도의 회원가입 없이 외부 서비스에서도 인증을 가능하게 하고 해당 서비스의 API를 이용하게 해주는 프로토콜. 현재는 2.0.
- 인터넷 사용자들이 비밀번호를 제공하지 않고 다른 웹사이트 상의 자신들의 정보에 대해 웹사이트나 애플리케이션의 접근 권한을 부여할 수 있는 공통적인 수단으로서 사용되는, 접근 위임을 위한 개방형 표준
- OAuth는 여러 곳에 개인정보를 제공하고 싶지 않은 점으로 생긴 이유가 큼. 여러 곳에 제공하게 되면 피싱에 둔감해지고 무엇보다 안전하다는 보장이 없기 때문
- OAuth 인증 방식
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/243810d3-52e5-48c3-abd8-fbea5dc7133d/image.png)
    
    1. 자원 소유자(사용자)가 구글 로그인을 요청한다.
    2. 클라이언트는 인증 서버에 로그인 페이지를 요청한다.
    3. 인증 서버가 로그인 페이지를 제공한다.
    4. 제공받은 로그인 페이지에 ID와 비밀번호를 입력한다.
    5. 입력받은 값으로 인증 서버에 요청한다.
    6. 인증 서버에 Authorization code를 발급한다.
    7. 이 code로 인증 서버에 Access Token를 요청한다.
    8. 인증 서버에서 Access Token을 발급해준다.
    9. 인증이 완료되었다.
    10. 자원 서버에 Access Token을 담아 데이터를 요청한다.
    11. Access Token을 검증 후 응답을 준다.
    
    
    만약 Access Token이 만료됐거나 위조되었다면, Client는 Authorization Server에 Refresh Token을 보내 Access Token을 재발급 받음
    

### 🌱 레퍼런스

[https://velog.io/@gusdnr814/로그인-인증-4가지-방법](https://velog.io/@gusdnr814/%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%9D%B8%EC%A6%9D-4%EA%B0%80%EC%A7%80-%EB%B0%A9%EB%B2%95)

[https://velog.io/@dee0518/로그인-인증-방식](https://velog.io/@dee0518/%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%9D%B8%EC%A6%9D-%EB%B0%A9%EC%8B%9D)


# 🌳 엑세스 토큰 발급

## 🪴 TokenProvider 클래스

Spring Security와 JWT를 사용하여 Access Token을 생성하고 검증하는 역할

**변수 및 상수**

- `secret` : 토큰을 암호화하고 검증하는 데 필요한 비밀 키. 서버에서만 알고 있어야 함
- `TOKEN_VALIDITY_IN_MILLISECONDS` : 토큰이 얼마 동안 유효한지 설정하는 시간. e.g. 1시간(= 3600000밀리초) 동안 유효한 토큰을 만들 수 있음
- `BEARER_PREFIX` : `Authorization` 헤더에 토큰을 붙일 때 사용하는 "Bearer "라는 문자열

**토큰 발급(createAccessToken)**

사용자에게 JWT 토큰을 만들어주는 메서드

- 사용자가 로그인하면, 이 메서드가 사용자 ID와 권한 정보를 이용해 새로운 토큰을 만듦
- 토큰에 들어가는 정보는 사용자 ID와 권한(e.g. ROLE_USER 또는 ROLE_ADMIN) 등이 있으며, 이 정보는 Payload 부분에 저장됨
- 생성된 토큰에는 유효기간이 설정되며, 만료 시간 이후에는 더 이상 사용할 수 없음

**토큰에서 사용자 정보 추출(getTokenUserId)**

JWT 토큰에서 사용자 ID를 꺼내오는 메서드

- 토큰의 Payload 부분에서 사용자 ID 정보를 읽어오고, 이를 통해 누구의 요청인지 알 수 있음

**인증 정보 생성(getAuthentication)**

토큰을 바탕으로 Spring Security가 이해할 수 있는 인증 정보를 생성

- `UserDetailsService`를 사용해 토큰에서 추출한 사용자 ID로 사용자 정보를 가져옴
- 이 정보를 이용해 `UsernamePasswordAuthenticationToken` 객체를 만들고, Spring Security가 해당 사용자를 인증할 수 있도록 함

**토큰의 유효성 검사(validateAccessToken)**

토큰이 유효한지, 즉 조작되거나 만료되지 않았는지 검사하는 메서드

- 유효한 토큰이면 `true`를 반환하고, 그렇지 않으면 `false`를 반환
    
    e.g. 예를 들어, 만료된 토큰이라면 다시 로그인하거나, 리프레시 토큰으로 새 토큰을 받아야 함
    

**초기 설정(afterPropertiesSet)**

클래스가 생성된 후 비밀 키(`secret`)를 기반으로 서명에 사용할 실제 키(`key`)를 준비하는 메서드입니다.

- 서버가 이 키를 사용해 토큰에 서명하고, 검증할 때도 이 키로 확인

## 🪴 customfilter 내용 채우기

`JwtAuthenticationFilter` : Spring Security의 커스텀 필터. 사용자가 요청을 보낼 때마다 JWT 토큰을 검증해 사용자 인증을 처리하는 역할

→ `OncePerRequestFilter`를 상속해서 요청당 한 번만 실행되도록 구성

### 🌱 `doFilterInternal` 메서드 구현

- 코드 구조
    
    ```java
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    
        // 1. 요청 헤더에서 토큰을 추출
        String token = tokenProvider.getAccessToken(request);
    
        // 2. 토큰이 존재하고, 유효한지 확인
        if (token != null && tokenProvider.validateAccessToken(token)) {
    
            // 3. 유효한 토큰이라면, 토큰에서 인증 정보를 가져오기
            Authentication authentication = tokenProvider.getAuthentication(token);
    
            // 4. SecurityContext에 인증 정보를 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    
        // 5. 다음 필터로 요청을 넘기기
        filterChain.doFilter(request, response);
    }
    
    ```
    

**요청 헤더에서 토큰 추출**

`TokenProvider`의 `getAccessToken(request)` 메서드를 사용하여 `Authorization` 헤더에서 토큰을 추출

이때 Bearer 접두사를 제거하고 실제 JWT 토큰만 반환하게 됨

→ `Authorization: Bearer [JWT토큰]`에서 `[JWT토큰]` 부분을 추출

**토큰 유효성 검사**

`TokenProvider`의 `validateAccessToken(token)` 메서드를 사용하여 토큰이 유효한지 검사

유효한 토큰이라면 다음 단계로 넘어가고, 만료되었거나 조작된 토큰이라면 인증 절차를 진행하지 않고 다음 필터로 넘김

**유효한 토큰일 경우 인증 정보 생성**

`TokenProvider`의 `getAuthentication(token)` 메서드를 호출하여 토큰에서 사용자 정보를 추출하고, 이를 기반으로 `Authentication` 객체를 생성

이 객체는 사용자 ID와 권한 등의 정보를 포함하며, Spring Security가 요청한 사용자를 인식하는 데 사용됨

**SecurityContext에 인증 정보 설정**

`SecurityContextHolder.getContext().setAuthentication(authentication)`을 통해 Spring Security의 `SecurityContext`에 인증 정보를 저장

이렇게 하면 요청이 처리되는 동안 서버가 해당 사용자를 인증된 상태로 인식하게 됨

**다음 필터로 요청 전달**

`filterChain.doFilter(request, response)`는 요청을 다음 필터로 전달하는 역할

이 부분이 있어야 요청이 정상적으로 컨트롤러로 전달됨

만약 이 메서드를 호출하지 않으면, 요청이 여기서 멈추고 더 이상 진행되지 않음

# 🌳 Docker 알아보기

## 🪴  가상화와 컨테이너의 개요

### 🌱 가상화

**하나의 물리적인 서버를 여러 개의 가상 서버로 분할해 사용하는 기술**

→ 기업 서버 활용도를 높여주고 개인 사용자에게는 안정적인 개발 환경을 제공

가상화 기술이 등장하기 전의 전통적인 서버 구조에서는 하나의 서버로 여러 웹 애플리케이션을 실행하면 서로 충돌하는 문제가 발생했었음

→ 여러 웹 애플리케이션이 각기 다른 운영체제나 라이브러리를 사용했기 때문

그러나 가상화 기술이 도입되면서 하나의 서버로 다수의 웹 애플리케이션을 운영할 수 있게 되었음

### 🌱 가상화의 종류

**개발 환경 가상화**

개발자가 웹 애플리케이션을 개발할 때 필요한 환경을 가상으로 구축하게 하는 기술

한 컴퓨터에서 여러 웹 애플리케이션을 개발하더라도 각각의 개발 환경을 구성할 수 있음

즉, 개발 환경마다 관련 라이브러리나 패키지를 독립적으로 관리해 일관된 개발 환경을 유지할 수 있음

e.g. 파이썬 개발을 위한 가상 환경을 만들어주는 가상 환경 관리자 ‘아나콘다(anaconda)’

**머신 가상화**

가상 머신(VM, Virtual MAchine)으로 하나의 서버(Host)에서 여러 Guest 운영체제를 실행할 수 있게 하는 기술

웹 애플리케이션마다 다른 운영체제를 사용해야 하는 경우나 특정 웹 애플리케이션을 특정 운영체제에서만 실행할 수 있는 경우에 유용

e.g. 버추얼박스(VirtualBox), VMware, 시트릭스(Citrix), WSL(Window Subsystem for Linux)

**운영체제 수준 가상화**

호스트 운영체제(실제 하드웨어 본체에 설치한 운영체제) 위에 격리된 컨테이너를 여러 개 만들어 각각의 컨테이너 안에서 웹 애플리케이션을 실행하는 기술

하나의 운영체제 상에서 격리된 컨테이너를 여러 개 생성하고, 각각의 컨테이너에서 웹 애플리케이션을 실행하기 때문에 다른 웹 애플리케이션과 충돌하는 것을 방지할 수 있음

e.g. 도커, 쿠버네티스

### 🌱 컨테이너

**가상화 기술 중 하나로, 격리된 여러 개의 실행 환경을 제공하는 기술**

운영체제 수준 가상화를 기반으로, 웹 애플리케이션을 실행하는 데 필요한 라이브러리, 실행 파일, 구성 파일 등이 포함된 패키지로 이루어져 있음

**💡 가상 머신 vs 컨테이너**

가상 머신

하이퍼바이저(hypervisor)라는 소프트웨어 계층을 사용해 호스트 운영체제 위에 게스트 운영체제를 설치하고 실행하는 방식

각 가상 머신은 자체적인 게스트 운영체제와 웹 애플리케이션을 가지며, 본 시스템으로부터 물리적인 하드웨어 자원(CPU, 메모리, 스토리지 등)을 할당받아 사용


**🤔 Hypervisor**

OS들의 커널을 번역하여 하드웨어에게 전달함으로써, OS들에게 자원을 분배하며 조율하는 역할

![image](https://github.com/user-attachments/assets/11022afb-73e5-441a-be39-b1824eeda1e0)

컨테이너

호스트 운영체제 위에서 동작

즉, 운영체제 수준 가상화 기술을 사용해 호스트 운영체제 위에 격리된 실행환경을 만듦

이처럼 격리된 실행 환경을 컨테이너라고 하며, 각 컨테이너는 독립된 파일 시스템, 네트워크 서비스 인터페이스, 프로세스 등을 기반으로 동작

컨테이너에는 자체적인 게스트 운영체제가 없고 호스트 운영체제의 자원을 공유박아 실행하므로 웹 애플리케이션을 더 빠르게 실행하고, 본 시스템의 물리적인 자원을 더 효율적으로 활용함

가상 머신과 컨테이너의 장단점

- 가상 머신은 각각의 게스트 운영체제와 해당 운영체제 위에서 동작하는 소프트웨어 스택(운영체제 관련 소프트웨어)을 복제해야 하기 때문에 컴퓨팅 자원(메모리, 디스크 등)을 더 많이 사용
    
    반면, 컨테이너는 여러 개의 웹 애플리케이션이 호스트 운영체제 하나를 공유하기 때문에 가상 머신보다 컴퓨팅 자원을 덜 사용
    
- 가상 머신은 각각의 운영체제와 소프트웨어 스택을 복제해야 하기 때문에 시작 시간(웹 애플리케이션을 구동하기까지 걸리는 시간)이 느림
    
    반면, 컨테이너는 시작 시간이 빠름
    
- 가상 머신은 더 많은 컴퓨팅 자원을 필요로 하므로 가볍지 않고, 이식성이 낮으며, 배포와 관리가 어려움
    
    반면, 컨테이너는 가볍고, 이식성이 뛰어나며, 배포와 관리가 용이
    

![image](https://github.com/user-attachments/assets/3d94ebc6-ad3f-4ed5-a187-f5331ad7362e)


**💡 시스템 컨테이너 vs 애플리케이션 컨테이너**

시스템 컨테이너

운영체제 위에 하드웨어 가상화 없이 운영체제를 실행하는 컨테이너

여러 애플리케이션과 서비스가 동일한 환경에서 실행되어야 할 때, OS 수준에서 강력한 격리가 필요할 때 사용

“가상 서버”와 같은 역할

애플리케이션 컨테이너

한 컨테이너에서 단 하나의 애플리케이션(프로세스)를 실행하는 것을 목표로 하는 컨테이너

단일 애플리케이션을 빠르게 배포하고 싶을 때, 마이크로서비스 아키텍처를 구현할 때 사용

“가상 환경”과 같은 역할



### 🌱 컨테이너를 사용해야 하는 이유

컨테이너는 여러 개의 서버가 필요한 상황에서 각 서버를 독립적으로 운영하는 효과를 내기 위해 사용

- 하나의 서버로 다양한 용도와 환경, 기능을 충족해야 할 때 여러 개의 컨테이너로 나눠 구현할 수 있음
- 각 서버가 컨테이너별로 관리되므로 소프트웨어나 라이브러리를 설치할 때 의존성을 고려하지 않아도 되고, 컨테이너별로 소프트웨어를 간단히 설치할 수 있음
- 서버를 이전하거나 서버에 여러 소프트웨어를 다시 설치해야 할 때 컨테이너 이미지를 받아 간편하게 이전 및 설치할 수 있음


**🤔 컨테이너 이미지**

컨테이너 실행에 필요한 모든 것이 포함된 패키지


## 🪴  컨테이너 플랫폼: 도커

### 🌱 도커의 개념

도커(Docker)는 컨테이너 기술을 이용해 웹 애플리케이션을 배포하고 실행하는 오픈 소스 플랫폼

→ 웹 애플리케이션을 실행하는 데 필요한 모든 환경을 패키징해 컨테이너 이미지를 만들고, 이 이미지를 이용해 컨테이너를 생성

e.g. 자바와 스프링 부트로 개발한 웹 애플리케이션이 있다면

1. 이 웹 애플리케이션을 만들고 실행하는 데 필요한 요소(JDK, JAR 파일 등)를 포함해 하나의 이미지로 만든 후
2. 이 이미지를 활용해 컨테이너를 생성하고
3. 해당 컨테이너에서 웹 애플리케이션을 실행

도커는 컨테이너 기술의 장점을 최대한 활용할 수 있도록 다음과 같은 기능을 제공함

- 컨테이너 간 통신을 위한 네트워크 구성 기능
- 여러 컨테이너를 동시에 관리하기 위한 오케스트레이션 기능
- 컨테이너 이미지를 저장하고 관리하는 기능
- 가상 머신과 달리 호스트 운영체제를 공유하기 때문에 빠르고 가벼움

하지만 컨테이너 내부에 설치된 웹 애플리케이션이 호스트 운영체제를 공유하므로 호스트 운영체제의 제한을 받을 수 있다는 단점이 있음

### 🌱 도커의 구조

도커의 구조는 크게 클라이언트와 서버로 나뉨

사용자가 도커 클라이언트를 통해 `docker` 로 시작되는 도커 명령어를 입력 →  도커 호스트(도커 서버)의 도커 데몬(도커 엔진)에 명령이 전달됨 →  컨테이너 생성 및 실행, 컨테이너 이미지  관리 등의 작업이 수행됨

![image](https://github.com/user-attachments/assets/aef3260c-9472-42d5-8705-f5b7d21bb9d5)


도커 데몬은 API로 들어온 명령을 수행하는데, 이때 도커드(dockerd)라는 프로세스를 통해 동작함. 도커드는 컨테이너와 컨테이너 이미지 관리의 주체.

도커가 대중화되면서 최근에는 도커 허브(Docker Hub)와 같은 컨테이너 레지스트리(container registry, 컨테이너 이미지 저장소)도 등장함. 이를 통해 다양한 웹 애플리케이션과 미들웨어, 프레임워크 등이 컨테이너 이미지로 제공돼 사용자가 웹 애플리케이션을 쉽게 배포하고 실행할 수 있게 됨.

[Docker Hub Container Image Library | App Containerization](https://hub.docker.com/)

### 🌱 컨테이너 이미지

**컨테이너 실행에 필요한 모든 것이 포함된 패키지**

웹 애플리케이션에 필요한 모든 소스 코드, 런타임, 라이브러리, 환경 변수 등의 구성 요소가 들어 있음

컨테이너 이미지는 한 번 만들면 다른 서버 환경에서도 동일하게 사용할 수 있어 호환성이 좋음

컨테이너 이미지를 만들고 이를 이용해 컨테이너를 실행하는 과정은 다음 그림과 같이 진행됨

도커에서 만든 컨테이너 이미지를 도커 이미지라고 함

도커 이미지는 도커 파일(Dockerfile)이라는 텍스트 파일을 이용해 만듦. 도커 파일에는 웹 애플리케이션의 구성과 실행 방법을 정의하는 명령어가 작성되어 있으며, 이를 빌드하면 도커 이미지가 생성되고, 도커 이미지를 실행하면 컨테이너가 실행됨

![image](https://github.com/user-attachments/assets/db32296e-d90f-447f-ba54-977ad74f6c6a)


이는 개발자나 운영 팀에 많은 이점을 안겨줌. 컨테이너 이미지를 사용하면 웹 애플리케이션 배포와 관리가 훨씬 쉬워지고, 어떤 서버 환경에서 웹 애플리케이션을 실행하더라도 일관된 결과를 얻을 수 있음

### 🌱 컨테이너의 주요 명령어

도커 명령어라고도 함

`docker run [컨테이너_이미지_이름] [옵션]` : 도커 컨테이너를 생성하고 실행하는 명령

컨테이너 이미지를 기반으로 컨테이너를 만들고, 옵션 값을 입력해 컨테이너 실행 시 여러 조건을 설정할 수 있음

```bash
# docker run 이미지이름 (컨테이너 이름 랜덤으로 생성됨)
docker run httpd

# docker run --name 컨테이너이름 이미지이름 (컨테이너이름을 명시적으로 정해줌)
docker run --name ws2 httpd

# docker run -d 이미지이름 (백그라운드로 실행_detached mode)
docker run --name ws2 -d httpd

# docker 이미지를 실행하는 기본 구문
docker run [OPTIONS] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]

# [OPIONS]: 선택 사항으로, 컨테이너 실행 시 설정할 다양한 옵션을 지정 (아래 표 참고)
# [IMAGE]: 실행할 Docker 이미지 이름을 지정하는 부분
# [:TAG|@DIGEST]: 이미지의 특정 태그나 다이제스트를 지정할 수 있는 선택 사항
# >> :TAG : 이미지의 특정 버전을 지정할 수 있고, 생략하면 기본적으로 latest 태그가 적용됨
# >> @DIGEST : 이미지의 고유한 식별자로, 특정 해시 값을 사용하여 특정 버전의 이미지를 정확히 가리킬 수 있도록 함
# [COMMAND]: 컨테이너에서 실행할 기본 명령어를 지정. 이미지에 설정된 기본 명령어가 있다면 생략 가능하며, 기본 명령어를 덮어쓰고 싶을 때 지정.
# [ARG...]: 실행할 명령어의 인수를 의미. 즉, [COMMAND]로 지정한 명령어에 전달할 추가적인 세부 지시사항이나 값을 설정할 수 있는 부분.
```

`docker start [컨테이너_이름]` : 중지된 컨테이너를 시작하는 명령

컨테이너가 중지된 상태에서 시작하고 싶을 때 사용

`docker stop [컨테이너_이름]` : 실행 중인 컨테이너를 중지하는 명령

컨테이너를 안전하게 중지하고 종료

`docker restart [컨테이너_이름]` : 실행 중인 컨테이너를 재시작하는 명령

컨테이너를 중지하고 다시 시작

`docker rm [컨테이너_이름]` : 컨테이너를 삭제하는 명령. 컨테이너를 중지해야만 삭제할 수 있음

`docker rm —force [컨테이터_이름]` : 강제 삭제

`docker rmi [이미지명]` : 이미지 삭제

`docker ps` : 실행 중인 컨테이너 목록을 보여주는 명령

`docker ps -a` `docker ps -all` : 중지된 컨테이너 포함 모든 컨테이너 조회

현재 실행 중인 컨테이너의 상태를 확인

`docker logs [컨테이너_이름]` : 컨테이너의 로그를 확인하는 명령

컨테이너에서 발생한 로그 메세지를 보여주므로 디버깅 및 모니터링할 때 사용

`docker exec [옵션] [컨테이너_이름(또는 ID)] [실행할_명령]` : 실행 중인 컨테이너 내부에서 명령을 실행

컨테이너에 접속해 특정 명령을 실행하거나 컨테이너끼리 상호작용할 수 있음

`docker build -t [도커_이미지_이름:tag]` : 도커 이미지를 생성하는 명령

도커 파일을 빌드해 이미지를 만들고 태그를 지정(해당 도커 이미지의 버전 지정)

`docker pull [도커_이미지_이름:tag]` : 도커 이미지를 다운로드하는 명령

도커 허브와 같은 도커 이미지 저장소에서 도커 이미지를 가져옴

### 🌱 컨테이너 실행 과정

도커에서 컨테이너로 *Hello from Docker!*라는 메세지를 출력하는 웹 애플리케이션의 실행 과정을 살펴보자

도커 이미지의 이름은 ‘hello-world’이며, 도커 클라이언트의 명령은 `docker run hello-world` 이다

1. 도커가 실행되면서 도커 데몬이 시작됨
2. 도커 데몬은 hello-world라는 도커 이미지를 로컬 저장소에서 찾음. 이미지가 없는 경우, 도커 허브에서 해당 이미지를 찾아 다운로드
3. 도커 데몬은 찾은 이미지를 기반으로 새로운 컨테이너를 생성하고 실행
4. 컨테이너가 실행되면 hello-world 이미지에 포함된 실행 파일이 동작하고, *Hello from Docker!*라는 메세지와 함께 몇 가지 정보를 출력
5. 명령 실행을 완료하면 컨테이너 종료

## 🪴  컨테이너 오케스트레이션

도커의 등장으로 다양한 개발 환경에서 만든 웹 애플리케이션을 손쉽게 컨테이너에 올려 실행할 수 있어 기존의 복잡한 서버 세팅 과정을 거치지 않고도 가상화 기술을 구현할 수 있게 됐음. 이에 컨테이너와 이를 운영하는 서버 컴퓨터가 늘어나 거대한 서버 클러스터(server cluster, 여러 서버를 하나의 시스템으로 묶는 것)가 형성됨

이러한 서버 클러스터에서 다수의 컨테이너를 관리하는 프로세스를 컨테이너 오케스트레이션(container orchestration)이라고 함. 앞서 설명했듯이 컨테이너는 웹 애플리케이션을 실행하는 데 사용하는 경량의 가상화된 환경이고, 컨테이너 오케스트레이션은 이러한 컨테이너의 생성/배포/관리/확장을 자동화하는 데 사용되는 도구. 대표적으로 쿠버네티스, 도커 스윔, 아파치 메소스 등이 있음

## 🪴  포트 포워딩

Host OS(우리가 사용하는 컴퓨터 OS) 위에서 도커 engine이 실행되면, 도커 container를 실행시킬 수 있음

이 때, Host의 port와 container의 port를 맵핑시켜줘야 외부에서 container로 접근할 수 있는데, 이를 포트 포워딩이라고 함

`docker run -p [host_port]:[container_port] [이미지명]` 

e.g. `docker run --name test -d -p 8088:80 httpd`

`-p` 옵션: container의 por를 host에 publish 한다

cf) -p의 p는 port가 아니라 publish의 축약어

[docker run](https://docs.docker.com/reference/cli/docker/container/run/#publish)

![image](https://github.com/user-attachments/assets/25cdc323-cab6-4396-adac-bff7c1af6d32)


Host의 8088번 포트(외부포트)와 Containerdml 80번 포트(내부포트)를 포워딩시켜줌

도커는 호스트 컴퓨터의 8088포트로 들어오는 네트워크 트래픽을 주시하다가 필요한 트래픽을 컨테이너의 80 포트로 전달

`--publish` 라는 플래그 덕분에 호스트 컴퓨터의 물리 네트워크 주소가 컨테이너의 가상 네트워크 주소에 접근할 수 있는 것. 왜냐하면 이 가상 주소는 도커 내부에서만 존재하는 주소이기 때문. 하지만 `—publish` 라는 플래그를 통해 컨테이너의 포트가 공개되었으므로 컨테이너로 트래픽을 전달할 수 있는 것

컴퓨터에서 [localhost](http://localhost) 8080번 포트로 접속하면, 컨테이너로부터 응답을 받을 수 있는 것을 확인할 수 있음

![image](https://github.com/user-attachments/assets/73c06ac9-6ffb-4623-b5b0-577fa24d36bf)



## 🪴  컨테이너 내부에 접근하기

컨테이너 내부로 직접 접근해서 컨테이너 안에서 명령어를 실행해보자

`docker exec -it 컨테이너명 /bin/bash (또는 /bin/sh)`
![image](https://github.com/user-attachments/assets/a23a75e0-573d-448a-9d5f-babd99f1c81d)
나가고 싶으면 `exit`

## 🪴  호스트와 컨테이너의 디렉토리를 연결(마운트)

컨테이너는 경우에 따라 삭제하거나, 교체하는 일이 빈번하므로 컨테이너 내부에 접근해서 파일을 관리하는 것은 좋지 않음

docker run 시, `-v` 옵션을 사용해 Host 컴퓨터(사용자의 실제 컴퓨터)의 File System과 컨테이너의 File System을 연결(마운트) 시킬 수 있음
![image](https://github.com/user-attachments/assets/ff6ad9ff-9903-4ed2-9e14-7301a52aaeaf)
`docker run --name ws3 -d -p 8080:80 -v ~/Documents:/usr/local/apache2/htdocs/ httpd`

- Host 내부의 ~/Documents 디렉토리와 컨테이너의 /usr/local/apache2/htdocs/ 디렉토리를 연결
- 컨테이너의 /usr/local/apache2/htdocs/ 에 Host의 ~/Documents 파일들이 복사되는 것

