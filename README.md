# spring-instagram-20th
CEOS 20th BE study - instagram clone coding

## 2주차
### 1) DB 모델링
#### 요구사항
- 게시글 조회
- 게시글에 사진과 함께 글 작성
- 게시글에 댓글 및 대댓글 기능
- 게시글에 좋아요 기능
- 게시글, 댓글, 좋아요 삭제 기능
- 유저 간 1:1 DM 기능

#### 기능 분석
**[User]**
|내 정보 설정 페이지|연락처 추가 페이지|프로필 페이지|
|------|---|---|
|![image](https://github.com/user-attachments/assets/8c592b62-8ae9-49ba-985c-fc7b5339f5e8)|![image](https://github.com/user-attachments/assets/9019e531-c145-4c6c-8b18-ac4d3751090e)|![image](https://github.com/user-attachments/assets/4fc59823-c367-4e5d-b84f-843060c2b583)|
|이름, 사용자 이름, 성별, 소개, 프로필|전화번호, 이메일|게시물, 팔로잉, 팔로워 수|

- 계정 생성: 30자 미만 사용자 이름(닉네임) + 패스워드 + 자동으로 가입일자 저장
- 나머지 필드는 계정 생성 이후 수정 가능


**[관계 분석]**

- User와 User 사이 Follow 관계(m:n) - Follow 테이블 생성

![image](https://github.com/user-attachments/assets/7d45a084-23c9-4a44-8de2-dc13ec41be73)



**[Post]**
|게시글 조회|게시글 생성|
|------|---|
|![image](https://github.com/user-attachments/assets/316d5f0a-2301-4a26-a079-dcda73f1d9db)|![image](https://github.com/user-attachments/assets/dbf53200-f297-482d-8f3b-209e15fe89d5)|
|작성자, 날짜, 좋아요, 댓글, 스크랩|본문, 해시태그|

- 게시글 생성: 이미지와 본문, 해시태그로 생성 가능
- 게시글 좋아요, 댓글 생성, 스크랩 가능

**[관계 분석]**

- User와 Post 사이 Like 관계(m:n) - Like 테이블 생성
- User와 Post 사이 Scrap 관계(m:n) - Scrap 테이블 생성
- Post와 Hashtag 사이 PostHashtag 관계(m:n) - PostHashtag 테이블 생성

![image](https://github.com/user-attachments/assets/3b61e494-41c7-4022-818b-c982a7deafa8)


**[Comment]** <br />
![image](https://github.com/user-attachments/assets/f99d90cf-3c1f-4016-a336-d6e6c6560370) <br />
작성자, 작성일, 본문, 답글, 좋아요

- 댓글 생성: 하나의 게시글 여러개의 댓글 생성 가능
- 답글 작성: 댓글에 여러 답글 작성 가능
- 회원 태그: 유저를 태그
- 댓글 좋아요

**[관계 분석]**

- Post와 Comment 사이 관계 (1:m)
- Comment와 Comment 사이 관계 (1:m) - 답글 ➡️ Comment가 자기자신을 참조
- Comment와 User 사이 Like 관계 (m:n) - CommentLike 테이블 생성
- Comment와 User 사이 CommentUserTag 관계 (m:n) - CommentUserTag 테이블 생성

![image](https://github.com/user-attachments/assets/4d763a7d-0b7b-4df5-b61f-cc477da1b891)


**[DM]**
|DM 방|DM 관련 기능|
|------|---|
![image](https://github.com/user-attachments/assets/414924ce-fac9-4f38-8d20-32dac11fb682)|![image](https://github.com/user-attachments/assets/725d4e20-d2a6-46b2-9cc6-4bee7aa0f89b)|

- 메시지 전송: 유저 2명이 한 대화방에서 메시지를 주고 받음
- 메시지 공감: 메시지에 공감 표시 가능
- 읽음 여부: 읽음 여부 확인 가능

  **[관계 분석]**

  - User와 DM (m:n) - DMRoom 생성

![image](https://github.com/user-attachments/assets/7148313e-622d-432f-8bc6-bf1a6d409d65)


#### ERD
![image](https://github.com/user-attachments/assets/5f913c7a-cdec-476a-ac4e-b3cc8d9c8c38)

[👉 ERD 링크](https://www.erdcloud.com/d/fqBpSLZ9cCXL4rbae)

#### 💡구현
```java
package com.ceos20.instagram.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    // 팔로우 한 시간
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 친한 친구 여부
    private Boolean isBestFriend = false;

    // 팔로우 한 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="follower_id")
    private User followerId;

    // 팔로우 당한 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="following_id")
    private User followingId;


}

```

#### 💡연관관계
연관관계 매핑: 객체의 참조와 테이블의 외래키를 매핑하는 것

관계형 데이터베이스에서의 테이블 사이 연관관계 != 객체 지향 프로그램에서의 객체 사이 연관관계

차이를 해소하기 위한 기술: ORM

**[연관관계 매핑 고려사항]**

- **방향**: 단방향 연관관계, 양방향 연관관계
- **연관관계의 주인**: 양방향 연관관계에서 외래키를 관리하는 객체
- **다중성**: 다대일, 일대다, 일대일, 다대다
(실무에서는 다대다 관계를 사용하지 않고 테이블을 추가하여 이대다 혹은 다대일 관계로 풀어 사용한다고 합니다!)

만약 데이터 중심적 모델링을 하게된다면?
```java
class Member {
    private long id;
    private long teamId;
    private String userName;
}

class Team {
    private long id;
    private String teamName;
}
```
➡️ 객체지향 프로그래밍에서 객체를 제대로 활용할 수 없음

이렇게 할 경우 member가 속한 팀 정보를 조회하기 위해 teamId를 통해 Team 조회 필요
```java
Member findMember = em.find(Member.class, memberId);
Long findTeamId = findMember.getTeamId();
Team findTeam = em.find(Team.class, findTeamId);
```
이럴 경우 Memeber, Team을 조회하는 2개의 쿼리가 따로 필요

🔎 그렇다면 객체 중심의 모델링을 한다면?
```java
@Entity
public class Memeber {
    @Id @GeneratedValue
    private Long id;
    
    private String userName;
    
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
```
➕ 추가적으로 ORM이 매핑을 해주는 것까지가 연관관계의 끝

조금 더 @ManyToOne를 살펴보자면
```java
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {
    Class targetEntity() default void.class;

    CascadeType[] cascade() default {};

    FetchType fetch() default FetchType.EAGER;

    boolean optional() default true;
}
```
|코드|설명|
|---|---|
|```@Target({ElementType.METHOD, ElementType.FIELD})```|어노테이션을 메서드 혹은 필드에 사용 가능|
|```@Retention(RetentionPolicy.RUNTIME)```|어노테이션이 애플리케이션 실행 중 반영|
|```Class targetEntity() default void.class;```|관계가 설정된 대상 엔티티의 클래스를 지정(엔티티 지정안할 경우 기본 값 사용 / 이 기능은 거의 사용하지 않음)|
|```CascadeType[] cascade() default {};```|cascade 작업 정의 (PERSIST, MERGE, REMOVE, REFRES, DETACH)|
|```FetchType fetch() default FetchType.EAGER;```|글로벌 패치 전략 설정(EAGER, LAZY)|
|```boolean optional() default true;```|관계 필수 여부 설정(true일 경우 선택적, false일 경우 필수) |

❓) 즉시 로딩과 지연 로딩?

Member와 Team이 다대일 @ManyToOne 관계로 매핑되어 있다면, Member를 조회할 때 Team도 항상 함께 조회되어야 할까?

만약 지연로딩을 사용한다면, Member 조회할 때 Team 클래스를 보면 Proxy 객체로 조회가 됨!!
그 이후 팀 이름을 출력(팀 필드 접근)한다면 그제야 쿼리가 나감!

그렇다면 항상 지연 로딩을 사용하는 것이 좋은가??

답은 **NO**!!! 만약, 대부분의 비즈니스 로직에서 Member와 Team이 함게 필요하다면 쿼리가 따로따로 나가게 되기 때문에 네트워크를 통한 조회가 2번 필요해진다...! 따라서 대부분의 경우 함께 사용한다면 즉시 로딩을 사용하는 것이 현명하다.

즉시 로딩도 단점이 있는데... 바로 예상하지 못한 SQL이 발생하고 관계를 맺는 테이블이 많다면... 그만큼의 조인이 더 많이 발생하게 된다. 또한 JPQL에서 N+1 문제를 야기한다. 따라서 실무에서는 지연로딩만 사용한다고 한다...!

💡) 연관관계 매핑의 장점은 연관관계 객체를 바로 찾을 수 있고, Join Query 없이 Join이 가능, 유지보수가 용이하지만....!!! @OneToMany의 경우 DB I/O 성능 저하가 발생하게 된다... (ex. N+1 문제)

❓ N+1 문제란?

: 하나의 조회를 위해 총 N+1번의 쿼리가 실행되는 문제
```java
@Entity
public class Memeber {
    @Id @GeneratedValue
    private Long id;
    
    private String userName;
}

@Entity
public class Team {
    @Id @GeneratedValue
    private Long id;
    
    private teamName;
    
    @OneToMany
    @JoniColumn(name = "member_id")
    private List<Member> members;
}
```
이럴 경우 TeamA에 Member 12명이 포함되었다면 TeamA를 find()한다면...?
```sql
SELECT * FROM Team WHERE teamId = 1;
SELECT * FROM Member WHERE memberId = "member1";
SELECT * FROM Member WHERE memberId = "member2";
SELECT * FROM Member WHERE memberId = "member3";
SELECT * FROM Member WHERE memberId = "member4";
SELECT * FROM Member WHERE memberId = "member5";
SELECT * FROM Member WHERE memberId = "member6";
SELECT * FROM Member WHERE memberId = "member7";
SELECT * FROM Member WHERE memberId = "member8";
SELECT * FROM Member WHERE memberId = "member9";
SELECT * FROM Member WHERE memberId = "member10";
SELECT * FROM Member WHERE memberId = "member11";
SELECT * FROM Member WHERE memberId = "member12";
```

그렇다면 @JoinColumn은 무엇일까...?

: 외래키를 매핑할 때 사용하는 어노테이션
```java
@Repeatable(JoinColumns.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn {
    String name() default ""; // 컬럼 이름 지증

    String referencedColumnName() default "";

    boolean unique() default false; // 유니크 제약조건

    boolean nullable() default true; //null 값 허용 여부 설정

    boolean insertable() default true; // 엔티티 저장 시 필드도 같이 저장 (거의 사용 X)

    boolean updatable() default true; // 엔티티 수정 시 필드도 같이 수정 (false 옵션은 읽기 전용일 경우 사용)

    String columnDefinition() default ""; // 데이터베이스 컬럼 정보 직접 전달

    String table() default ""; // 하나의 엔티티를 두 개 이상의 테이블에 매핑 (거의 사용 X)

    ForeignKey foreignKey() default @ForeignKey(ConstraintMode.PROVIDER_DEFAULT);
}

```
|코드|설명|
|---|---|
|```String referencedColumnName() default "";```|외래키가 참조하는 대상 테이블의 컬럼명|
|```ForeignKey foreignKey() default @ForeignKey(ConstraintMode.PROVIDER_DEFAULT);}```|외래키 제약조건을 직접 지정 가능 -> 데이터무결성 보장, 성능 최적화, 유연성 제공을 위해 사용|

나머지는 @Column과 동일 ➡️ 객체 필드를 테이블의 컬럼에 매핑시켜주는 어노테이션

@Column은 생략 가능하다!
생략할 경우 속성들이 기본값 적용! 그렇다면 null은 어떤 식으로 처리가 되는가?

int와 같은 자바 기본 타입에는 null 값을 입력할 수 없다... 🥲

그러나 Integer 같은 객체 타입은 null 값이 허용된다.

그렇기 때문에 객체 타입으로 속성을 정의했는데 null 값을 허용하고 싶지 않다면 @NotNull이나 @Column(nullabe=false)를 해야한다.

#### ❓nullable=false와 @NotNull 비교
일반적으로 nullable=false 보다 @NotNull 추천

nullable=false는 엔티티 필드 값이 null로 채워진 상태에서 정상적으로 수행되다가 DB에 SQL 쿼리 도착 순간 테이블 컬럼의 NOT NULL 옵션에 의해 예외 발생!

그러나, @NotNull은 쿼리가 보내지기 전 JPA가 만든 엔티티 필드 값이 null로 채워지는 순간 예외 발생

➡️ @NotNull 어노테이션이 더 이전 단계에서 예외 검출하므로 더 안전!!

### 2) Repository 단위 테스트

#### 테스트 코드
```java
package com.ceos20.instagram.user.repository;

import com.ceos20.instagram.user.domain.Follow;
import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class FollowRepositoryTest {
    @Autowired
    FollowRepository followRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("user1을 팔로워 목록 조회")
    void findFollowingUsersTest() {
        // given
        User user1 = User.builder()
                .nickname("user1")
                .password("1234")
                .build();
        entityManager.persist(user1);

        User user2 = User.builder()
                .nickname("user2")
                .password("1234")
                .build();;
        entityManager.persist(user2);

        User user3 = User.builder()
                .nickname("user3")
                .password("1234")
                .build();;
        entityManager.persist(user3);

        User user4 = User.builder()
                .nickname("user4")
                .password("1234")
                .build();;
        entityManager.persist(user4);

        Follow target1 = Follow.builder()
                .followerId(user2)
                .followingId(user1)
                .build();

        Follow target2 = Follow.builder()
                .followerId(user3)
                .followingId(user1)
                .build();

        Follow Nontarget = Follow.builder()
                .followerId(user1)
                .followingId(user4)
                .build();

        // when
        followRepository.save(target1);
        followRepository.save(target2);
        followRepository.save(Nontarget);

        List<User> followingList = followRepository.findFollowerUsers(user1.getId());

        //then
        assertEquals(2, followingList.size());
    }
}

```
![image](https://github.com/user-attachments/assets/72c50bfa-95e1-4a05-9c97-1dc4dc7674ba)



