# spring-instagram-20th
CEOS 20th BE study - instagram clone coding
## 1. 데이터베이스 모델링

## pre. 요구사항 분석

**[인스타그램 서비스 분석]**

### 1. 서비스 개요

인스타그램은 사용자들이 게시글을 작성하고, 댓글을 달며, 좋아요를 누르고, 메시지를 주고받을 수 있는 기능을 제공합니다.

### 2. 기능 요구사항

### 2.1 게시글 기능

- **게시글 조회**: 사용자는 타인의 게시글을 조회할 수 있습니다.
- **게시글 작성**: 사용자는 텍스트와 사진을 포함하여 게시글을 작성할 수 있습니다.
- **게시글 수정 및 삭제**: 사용자는 자신이 작성한 게시글을 수정하거나 삭제할 수 있습니다.

### 2.2 댓글 기능

- **댓글 작성**: 사용자는 게시글에 댓글을 작성할 수 있습니다.
- **대댓글 기능**: 사용자는 댓글에 대한 대댓글을 작성할 수 있습니다.
- **댓글 수정 및 삭제**: 사용자는 자신이 작성한 댓글 및 대댓글을 수정하거나 삭제할 수 있습니다.

### 2.3 좋아요 기능

- **좋아요**: 사용자는 게시글에 좋아요를 누를 수 있습니다.
- **좋아요 취소**: 사용자는 자신이 누른 좋아요를 취소할 수 있습니다.

### 2.4 Direct Message (DM) 기능

- **DM 작성**: 사용자는 다른 사용자와 1:1로 메시지를 주고받을 수 있습니다.
- **DM 삭제**: 사용자는 자신이 보낸 DM을 삭제할 수 있습니다.

### 구현 계획

### Domain (Entity) 클래스

- 각 테이블에 대응하는 Entity 클래스를 정의하고, JPA 어노테이션을 통해 MySQL 데이터베이스와 매핑합니다.

### Repository 계층

- **JpaRepository**를 확장한 Repository 인터페이스를 정의하여, 기본 CRUD 작업을 자동으로 수행합니다.

### 서비스 계층

- 비즈니스 로직을 처리하며, Repository를 통해 데이터베이스와 상호작용합니다.

### 컨트롤러 계층

- 사용자 요청을 처리하고, 클라이언트와 서버 간의 데이터 교환을 관리합니다.

## 1) 개념적 설계

**1-1) 개체(entity)와 속성(attribute) 추출**

**1. 사용자 (Users)**

- **UserID** (PK) : 사용자 고유 식별자
- **Username** : 사용자 이름
- **Email** : 이메일 주소
- **Password** : 암호
- **ProfilePicture** : 프로필 사진
- **Bio** : 사용자 소개

**2. 게시글 (Post)**

- **PostID** (PK) : 게시글 고유 식별자
- **UserID** (FK) : 게시글 작성자
- **Image** : 이미지 파일
- **Caption** : 게시글 내용
- **Timestamp** : 작성 시간

**3. 댓글 (Comment)**

- **CommentID** (PK) : 댓글 고유 식별자
- **PostID** (FK) : 댓글이 달린 게시글
- **UserID** (FK) : 댓글 작성자
- **Text** : 댓글 내용
- **Timestamp** : 댓글 작성 시간

~~**4. 대댓글 (Reply)**

- **ReplyID** (PK) : 대댓글 고유 식별자
- **CommentID** (FK) : 대댓글이 달린 댓글
- **UserID** (FK) : 대댓글 작성자
- **Text** : 대댓글 내용
- **Timestamp** : 대댓글 작성 시간~~

**5. 좋아요 (Likes)**

- **LikeID** (PK) : 좋아요 고유 식별자
- **PostID** (FK) : 좋아요가 달린 게시글
- **UserID** (FK) : 좋아요를 누른 사용자
- **Timestamp** : 좋아요 시간

**6. 다이렉트 메시지 (DirectMessage)**

- **MessageID** (PK) : 메시지 고유 식별자
- **SenderID** (FK) : 메시지 발신자
- **ReceiverID** (FK) : 메시지 수신자
- **Text** : 메시지 내용
- **Timestamp** : 메시지 전송 시간

## 2) 논리적 설계

릴레이션(스키마)

개체 → 릴레이션 이름

속성 → 릴레이션의 속성

![erd](https://github.com/user-attachments/assets/49eea983-2974-45d1-aa0c-f19a31790f66)


테이블이름 user로 했더니 단위테스트에서 오류계속발생..

~~다른 분들은 잘되는것같길래 안바꾸고 해봤는데 계속 안돼서 users로 테이블 이름 변경하니 성공. .~~
24.09.28 Member로 수정 

![image (2)](https://github.com/user-attachments/assets/ace93beb-c781-4ab6-a3d2-c5a2ad068f8c)

# REPOSITORY 단위테스트

- `given` : 테스트 실행을 준비하는 단계
- `when` : 테스트를 진행하는 단계
- `then` : 테스트 결과를 검증하는 단계

# 3주차 ✈️✈️✈️

## 2주차 리팩토링

### 1. User 엔티티 Member로 수정

### 2.빌더 패턴 사용

기존: 직접 생성자 호출

```jsx
User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@test.com");
        user1.setPassword("password1");
        userRepository.save(user1);
```

`builder()` 패턴은 객체 생성 시 다양한 필드를 선택적으로 설정할 수 있도록 도와주는 패턴입니다. 아래와 같은 장점이 있습니다.

1. **가독성**: 여러 필드가 있는 객체를 생성할 때 코드가 직관적이고 읽기 쉬워집니다.
2. **불변성**: 빌더 패턴을 사용하면 불변 객체를 쉽게 만들 수 있습니다. 즉, 객체가 생성된 후에는 수정할 수 없게 하여 안전한 코드 작성을 도와줍니다.
3. **유연성**: 필수 필드만 지정하고 선택적으로 필드를 추가할 수 있어, 생성자가 복잡해지는 문제를 해결합니다.

```jsx
        Member member1 = Member.builder()
                .membername("member1")
                .email("member1@test.com")
                .password("password1")
                .build();
        memberRepository.save(member1);
```

위 코드는 객체를 생성하면서 필요한 필드만 설정하고, 필드를 설정하는 순서와 관계없이 유연하게 사용할 수 있습니다.

### 3. @EnableJpaAuditing 사용

이전코드: 개발자가 **직접 `timestamp` 필드를 관리**해야 한다

이전 코드에서는 댓글을 생성할 때마다 시간을 직접 코드로 설정하는 방식입니다:

```java
Comment comment = new Comment();
comment.setText("This is a comment");
comment.setTimestamp(LocalDateTime.now()); // 개발자가 명시적으로 시간을 설정

```

즉, 엔티티를 생성하거나 저장할 때 개발자가 **`LocalDateTime.now()` 같은 방식으로 시간을 직접 설정**해야 하며 이 과정은 반복적이고 번거로울 수 있음

### **자동 설정 (Auditing)**

`@CreatedDate`를 사용하면 **JPA Auditing 기능**이 자동으로 댓글이 생성된 시간을 기록해 줍니다.

즉, 개발자가 `LocalDateTime.now()`를 명시적으로 호출할 필요가 없고, JPA가 엔티티가 처음 저장될 때 생성 시간을 자동으로 처리합니다.

수정된 코드에서는 시간 설정 로직을 생략해도 JPA가 자동으로 아래와 같은 과정을 처리해 줍니다:

```java
Comment comment = new Comment();
comment.setText("This is a comment");
// 시간 설정 코드를 작성할 필요 없음

```

### 차이점 요약:

- **수동 설정 (이전 방식)**: 개발자가 댓글을 생성할 때마다 `LocalDateTime.now()`를 호출하여 시간 필드를 직접 할당.
- **자동 설정 (Auditing 사용)**: `@CreatedDate`로 JPA가 엔티티가 생성될 때 자동으로 시간을 기록.

### 4. Comment(댓글) , Reply(대댓글) 통합

### N+1 문제의 발생 과정

### 예시상황:

**Post(게시글)을 모두 불러오고 싶은 상황, 총 10개의 게시글이 있음**

```jsx
1번째 게시글(Post1) : CEOS 짱~ , ……………………작성자(멤버): 유지민

2번째 게시글(Post2): 백엔드 짱~, ……………………작성자(멤버): 황서아

…
…
…

10번째 게시글(Post10): spring 어려워~,……………작성자(멤버): 나혜인
```

1. **기본 쿼리 실행 (1번 실행)**: `postRepository.findAll()`을 호출하면, 데이터베이스에서 `Post` 엔티티 (게시글) 10개를 가져오는 **하나의 쿼리**가 실행됩니다.

    ```sql
    SELECT * FROM Post;
    ```

   이 쿼리는 `Post` 테이블에서 10개의 `Post` 데이터를 한 번에 가져오는 작업을 수행합니다.

   이 단계까지는 문제가 없습니다.

2. **연관된 엔티티 쿼리 실행 (N번 실행)**: 그런데, `Post` 엔티티에는 `Member`라는 연관 엔티티가 있습니다. (각 게시글에 연관된 멤버)

   JPA의 기본 설정인 `LAZY` 로딩 전략 ⇒  `findAll()` 메서드 호출 시 `Post` 엔티티만 가져오고, `Post`와 연관된 `Member`는 각각의 `Post`가 접근될 때마다 따로 조회하게 됨

    ```sql
    SELECT * FROM Member WHERE id = ?;  -- 첫 번째 Post의 Member 쿼리
    SELECT * FROM Member WHERE id = ?;  -- 두 번째 Post의 Member 쿼리
    ...
    SELECT * FROM Member WHERE id = ?;  -- 열 번째 Post의 Member 쿼리
    
    ```


즉, **각 `Post`가 참조하는 `Member` 정보를 개별적으로 쿼리**해서 가져옵니다.

그래서 첫 번째 `Post`에 연관된 `Member`를 가져오는 쿼리, 두 번째 `Post`에 연관된 `Member`를 가져오는 쿼리, … 이런 식으로 `N`개의 쿼리가 추가로 실행됩니다.

예시로 `Member`를 가져오는 쿼리는 다음과 같이 10번 실행됩니다:

이처럼 **`Post` 엔티티 10개를 조회하는 쿼리 1개 + 각 `Post`에 연관된 `Member`를 조회하는 10개의 추가 쿼리**가 발생. 그래서 **총 11개의 쿼리**가 실행되게 됨

**`Post`**가 100,000개면 100,001번 실행되겠죠?

### **`@EntityGraph`로 N+1 문제 해결**

- *`@EntityGraph`*는 JPA에서 연관된 엔티티를 **한 번의 쿼리로 미리 로드**할 수 있도록 설정하는 방법입니다.
- 즉, **Post**와 연관된 **Member**를 **한 번의 쿼리로 함께 조회**하게 하여, **추가적인 N번의 쿼리 발생을 방지**합니다.
- *즉시 로딩(EAGER Loading)**과 비슷하지만, 특정 조회에만 적용할 수 있는 점에서 더 유연함.

### **코드 설명**

### **1. `PostRepository`에서 `@EntityGraph` 사용**

```java

@EntityGraph(attributePaths = "member")
List<Post> findAll();
```

- **`@EntityGraph(attributePaths = "member")`**: `Post`와 연관된 `Member` 엔티티를 한 번에 가져옵니다.
    - `attributePaths = "member"`: `Post` 엔티티의 **`member` 필드**를 함께 로드하도록 설정합니다.

### **2. 테스트 코드에서 확인**

```java
java
코드 복사
List<Post> posts = postRepository.findAll();

// Then - 연관된 Member가 N+1 문제 없이 로드되었는지 확인
//isNotNull()은 Member가 정상적으로 로드되었음을 확인하는 것이며, 한 번의 쿼리로 Member까지 가져왔는지를 간접적으로 검증
assertThat(posts.get(0).getMember()).isNotNull();
assertThat(posts.get(1).getMember()).isNotNull();
assertThat(posts.get(2).getMember()).isNotNull();

```

- `postRepository.findAll()` 호출 시, **`@EntityGraph`*에 의해 **Post**와 **Member**가 **한 번에 로드**됩니다.
- 이로 인해, 각 **Post**와 연관된 **Member**가 **N번의 추가 쿼리 없이** 조회됩니다.

### CommentService 단위테스트

### **Mockito를 사용하는 이유:**

- **Mockito**는 테스트에서 **실제 데이터베이스**나 **외부 의존성** 없이, **의존 객체**들을 **Mock(가짜 객체)**으로 대체하여 비즈니스 로직을 테스트할 수 있도록 합니다.

### **CommentService 단위 테스트 작성:**

```java
package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Comment;
import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.repository.CommentRepository;
import com.ceos20.spring_instagram.repository.PostRepository;
import com.ceos20.spring_instagram.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    // CommentService를 테스트할 때 필요한 의존성을 주입받습니다.
    @InjectMocks
    private CommentService commentService;

    // Mock 객체로 의존성 주입: 실제 DB 접근 없이 Repository 동작을 테스트
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    // 테스트 시작 전에 Mock 객체를 초기화합니다.
    @BeforeEach
    public void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);
    }

    // 댓글 추가 기능을 테스트하는 메서드
    @Test
    public void whenValidInput_thenAddComment() {
        // Given: 테스트를 위한 가짜 postId와 memberId, 댓글 내용
        Long postId = 1L; // 1L은 long 타입 리터럴로, Post의 ID로 사용
        Long memberId = 1L; // Member의 ID로 사용
        String text = "Test comment"; // 댓글 내용

        // Post 객체 생성 및 Mock 설정
        Post post = Post.builder().postId(postId).build();
        // Member 객체 생성 및 Mock 설정
        Member member = Member.builder().memberId(memberId).build();

        // postId로 Post 조회 시, 가짜 post 객체 반환
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        // memberId로 Member 조회 시, 가짜 member 객체 반환
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        
        // commentRepository의 save() 호출 시, 댓글 저장 후 반환
        Comment comment = Comment.builder().post(post).member(member).text(text).build();
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // When: CommentService의 addComment 호출
        Comment result = commentService.addComment(postId, memberId, text);

        // Then: 반환된 댓글의 Post, Member, Text가 기대값과 일치하는지 확인
        assertThat(result.getPost()).isEqualTo(post);
        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getText()).isEqualTo(text);

        // postRepository와 memberRepository의 findById 메서드가 각각 1번 호출되었는지 검증
        verify(postRepository, times(1)).findById(postId);
        verify(memberRepository, times(1)).findById(memberId);
        // commentRepository의 save 메서드가 1번 호출되었는지 검증
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    // 대댓글 추가 기능을 테스트하는 메서드
    @Test
    public void whenValidInput_thenAddReply() {
        // Given: 테스트를 위한 가짜 postId, memberId, parentCommentId, 대댓글 내용
        Long postId = 1L; // Post의 ID
        Long memberId = 1L; // Member의 ID
        Long parentCommentId = 1L; // 부모 댓글의 ID
        String text = "Test reply"; // 대댓글 내용

        // Post, Member, 부모 Comment 객체 생성 및 Mock 설정
        Post post = Post.builder().postId(postId).build();
        Member member = Member.builder().memberId(memberId).build();
        Comment parentComment = Comment.builder().commentId(parentCommentId).build();

        // postId, memberId, parentCommentId로 각각 객체 조회 시 Mock 반환
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(commentRepository.findById(parentCommentId)).thenReturn(Optional.of(parentComment));

        // 대댓글 객체 생성 및 Mock 설정
        Comment reply = Comment.builder().post(post).member(member).parentComment(parentComment).text(text).build();
        when(commentRepository.save(any(Comment.class))).thenReturn(reply);

        // When: CommentService의 addReply 호출
        Comment result = commentService.addReply(postId, memberId, parentCommentId, text);

        // Then: 반환된 대댓글의 Post, Member, 부모 Comment, Text가 기대값과 일치하는지 확인
        assertThat(result.getPost()).isEqualTo(post);
        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getParentComment()).isEqualTo(parentComment);
        assertThat(result.getText()).isEqualTo(text);

        // postRepository, memberRepository, commentRepository의 메서드 호출 검증
        verify(postRepository, times(1)).findById(postId);
        verify(memberRepository, times(1)).findById(memberId);
        verify(commentRepository, times(1)).findById(parentCommentId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    // 특정 댓글의 대댓글 리스트 조회 기능을 테스트하는 메서드
    @Test
    public void whenValidParentCommentId_thenGetReplies() {
        // Given: 가짜 부모 댓글 ID
        Long parentCommentId = 1L;
        Comment parentComment = Comment.builder().commentId(parentCommentId).build();

        // 가짜 대댓글 객체 생성
        Comment reply1 = Comment.builder().commentId(2L).parentComment(parentComment).build();
        Comment reply2 = Comment.builder().commentId(3L).parentComment(parentComment).build();

        // parentCommentId로 부모 댓글 조회 시 Mock 반환
        when(commentRepository.findById(parentCommentId)).thenReturn(Optional.of(parentComment));
        // 부모 댓글에 달린 대댓글 리스트 반환
        when(commentRepository.findByParentComment(parentComment)).thenReturn(Arrays.asList(reply1, reply2));

        // When: CommentService의 getReplies 호출
        List<Comment> replies = commentService.getReplies(parentCommentId);

        // Then: 반환된 대댓글 리스트의 크기와 부모 댓글 확인
        assertThat(replies).hasSize(2);
        assertThat(replies.get(0).getParentComment()).isEqualTo(parentComment);
        assertThat(replies.get(1).getParentComment()).isEqualTo(parentComment);

        // 부모 댓글 조회 및 대댓글 리스트 조회 메서드가 1번씩 호출되었는지 검증
        verify(commentRepository, times(1)).findById(parentCommentId);
        verify(commentRepository, times(1)).findByParentComment(parentComment);
    }
}
```

### **설명:**

1. **`addComment` 테스트**:
    - 유효한 `postId`와 `memberId`로 댓글을 추가합니다.
    - `postRepository`와 `memberRepository`를 통해 `Post`와 `Member`를 조회한 후, `CommentRepository`를 통해 댓글을 저장하는지를 검증합니다.
2. **`addReply` 테스트**:
    - 유효한 `postId`, `memberId`, `parentCommentId`로 대댓글을 추가합니다.
    - 대댓글이 올바르게 부모 댓글과 연결되었는지를 검증합니다.
3. **`getReplies` 테스트**:
    - 부모 댓글 ID를 이용하여 대댓글 리스트를 조회합니다.
    - 부모 댓글에 연결된 대댓글들이 올바르게 조회되는지 검증합니다.

# 4주차

## 1️⃣ 인스타그램의 4가지 HTTP Method API 만들어봐요

### Comment

---

이 API는 게시물에 대한 댓글을 관리하는 엔드포인트를 제공합니다. 사용자는 새로운 댓글을 생성하고, 특정 게시물의 댓글을 조회하고, 특정 댓글의 세부 정보를 가져오며, 댓글을 삭제할 수 있습니다.

### 기본 URL

```bash

/api/comments
```

### 코드 URL 매핑 구조

- **클래스 수준**: `@RequestMapping("/api/comments")`는 이 클래스 내의 모든 메서드가 `/api/comments`로 시작하는 요청을 처리한다는 것을 의미합니다.
- **메서드 수준**:
   - `@PostMapping("/")`: 이 메서드는 `/api/comments/`에 대한 POST 요청을 처리하여 새로운 댓글을 생성합니다.
   - `@GetMapping("/post/{postId}")`: 이 메서드는 특정 게시물에 대한 댓글을 가져오는 GET 요청을 처리하며, URL 경로에서 `postId`를 변수로 사용합니다.
   - `@GetMapping("/{commentId}")`: 이 메서드는 특정 댓글을 조회하는 GET 요청을 처리하며, URL 경로에서 `commentId`를 변수로 사용합니다.
   - `@DeleteMapping("/{commentId}")`: 이 메서드는 특정 댓글을 삭제하는 DELETE 요청을 처리합니다.

### 1. 새로운 데이터를 create하도록 요청하는 API 만들기

- **새로운 댓글 생성**
- **URL**: `/api/comments/`
- **메서드**: `POST`
- **설명**: 특정 게시물과 회원에 연관된 새로운 댓글을 생성합니다.
- **요청 파라미터**:
   - `postId` (Long, 필수): 댓글이 추가될 게시물의 ID.
   - `memberId` (Long, 필수): 댓글을 작성하는 회원의 ID.
- **요청 본문**:
   - 댓글 내용을 포함하는 JSON 문자열 (예: `"This is a comment"`).
- **응답**:
   - **상태 코드**: `201 Created`
   - **본문**: 생성된 `CommentDTO` 객체를 반환합니다.

### 예시 요청

```
POST /api/comments/
Content-Type: application/json
{
    "text": "String"
}

```

### 예시 응답

```
HTTP/1.1 201 Created
Content-Type: application/json

{
    "commentId": 1,
    "postId": 1,
    "memberId": 1,
    "text": "This is a comment",
    "timestamp": "2024-01-01T12:00:00"
}

```

---

### 2. 모든 데이터를 가져오는 API 만들기

- **특정 게시물의 모든 댓글 가져오기**
- **URL**: `/api/comments/post/{postId}`
- **메서드**: `GET`
- **설명**: 특정 게시물에 달린 모든 댓글을 조회합니다.
- **경로 파라미터**:
   - `postId` (Long, 필수): 댓글을 조회할 게시물의 ID.
- **응답**:
   - **상태 코드**: `200 OK`
   - **본문**: 해당 게시물의 모든 댓글을 나타내는 `CommentDTO` 객체의 리스트를 반환합니다.

### 예시 요청

```

GET /api/comments/post/1
```

### 예시 응답

```
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "commentId": 1,
        "postId": 1,
        "memberId": 1,
        "text": "This is a comment",
        "timestamp": "2024-01-01T12:00:00"
    },
    {
        "commentId": 2,
        "postId": 1,
        "memberId": 2,
        "text": "This is another comment",
        "timestamp": "2024-01-01T12:05:00"
    }
]

```

---

### 3. 특정 데이터를 가져오는 API 만들기

- **특정 댓글 가져오기**
- **URL**: `/api/comments/{commentId}`
- **메서드**: `GET`
- **설명**: 특정 댓글의 세부 정보를 조회합니다.
- **경로 파라미터**:
   - `commentId` (Long, 필수): 조회할 댓글의 ID.
- **응답**:
   - **상태 코드**: `200 OK`
   - **본문**: 요청된 댓글을 나타내는 `CommentDTO` 객체를 반환합니다.

### 예시 요청

```

GET /api/comments/1

```

### 예시 응답

```

HTTP/1.1 200 OK
Content-Type: application/json

{
    "commentId": 1,
    "postId": 1,
    "memberId": 1,
    "text": "This is a comment",
    "timestamp": "2024-01-01T12:00:00"
}
```

---

### 4. 특정 데이터를 삭제 또는 업데이트하는 API

- **특정 댓글 삭제**
- **URL**: `/api/comments/{commentId}`
- **메서드**: `DELETE`
- **설명**: 특정 댓글을 삭제합니다.
- **경로 파라미터**:
   - `commentId` (Long, 필수): 삭제할 댓글의 ID.
- **응답**:
   - **상태 코드**: `204 No Content`
   - **본문**: 응답 본문은 없습니다.

### 예시 요청

```
DELETE /api/comments/1
```

### 예시 응답

```
HTTP/1.1 204 No Content

```

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/a5836003-a8d1-4201-bdfd-b04ef32ee5fb/928b9021-88b0-4177-96eb-4e2298da1f12/image.png)

## 2️⃣ 정적 팩토리 메서드를 사용해서 DTO 사용해봐요

```jsx
package com.ceos20.spring_instagram.dto;

import com.ceos20.spring_instagram.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDTO {
    private Long commentId;
    private Long postId;
    private Long memberId;
    private String text;
    private LocalDateTime timestamp;

    // 정적 팩토리 메서드
    public static CommentDTO from(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .memberId(comment.getMember().getMemberId())
                .text(comment.getText())
                .timestamp(comment.getTimestamp())
                .build();
    }
}
```

### 기존코드

```jsx
  public Comment addComment(Long postId, Long memberId, String text) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<Member> member = memberRepository.findById(memberId);

        if (post.isEmpty() || member.isEmpty()) {
            throw new IllegalArgumentException("잘못된 포스트 또는 회원 ID입니다.");
        }

        Comment comment = Comment.builder()
                .post(post.get())
                .member(member.get())
                .text(text)
                .build();

        return commentRepository.save(comment);
    }
```

### DTO사용 후

```jsx
public CommentDTO addComment(Long postId, Long memberId, String text) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("잘못된 포스트 ID입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("잘못된 회원 ID입니다."));

        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .text(text)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentDTO.from(savedComment);
    }
```

`CommentDTO` 객체를 반환하는 것이 `Comment` 객체를 직접 반환하는 것보다 여러 가지 면에서 장점이 있습니다:

1. **데이터 노출 제어**:
   - **DTO 사용**: `CommentDTO`는 필요한 정보만 포함할 수 있습니다. 예를 들어, 댓글 ID, 댓글 내용, 작성자 ID 등 클라이언트에 필요한 정보만 선택적으로 포함할 수 있습니다.
   - **직접 반환**: `Comment` 객체를 직접 반환하면, 데이터베이스 엔티티에 포함된 모든 필드가 클라이언트에 노출될 수 있습니다. 이로 인해 보안 및 데이터 노출 문제가 발생할 수 있습니다.
2. **API 응답의 일관성**:
   - **DTO 사용**: 여러 엔티티에 대해 통일된 형식으로 DTO를 정의하면 API 응답의 일관성을 유지할 수 있습니다. 클라이언트는 항상 같은 구조의 데이터를 받아보게 됩니다.
   - **직접 반환**: 다양한 엔티티가 혼합될 경우 응답 구조가 달라질 수 있으며, 클라이언트에서 이를 처리하기 어려워질 수 있습니다.
3. **의존성 감소**:
   - **DTO 사용**: 클라이언트는 DTO와 관련된 데이터 계약만 알고 있으면 됩니다. 데이터베이스의 변경 사항이 DTO에 반영되면, 클라이언트가 이 변경을 인식하지 않아도 되므로 유연성이 증가합니다.
   - **직접 반환**: 클라이언트가 엔티티 구조에 의존하게 되므로, 엔티티 구조가 변경되면 클라이언트의 코드도 수정해야 할 가능성이 높습니다.
4. **유효성 검증 및 변환**:
   - **DTO 사용**: DTO를 사용하면 입력 및 출력 데이터를 쉽게 검증하고 변환할 수 있습니다. 필요한 경우 DTO에 유효성 검증 어노테이션을 추가하여 클라이언트에서 보낸 데이터를 검증할 수 있습니다.
   - **직접 반환**: 엔티티를 직접 반환하면, 입력값의 유효성을 확인하기 어려워질 수 있습니다.
5. **테스트 용이성**:
   - **DTO 사용**: DTO를 사용하면 API 테스트 및 단위 테스트가 간편해집니다. DTO의 필드를 통해 특정 동작을 쉽게 검증할 수 있습니다.
   - **직접 반환**: 엔티티를 반환하는 경우, 데이터베이스에 대한 의존성이 높아져 테스트가 복잡해질 수 있습니다.

⇒ 유연성, 보안, 일관성, 유지보수성을 높일 수 있음

## 3️⃣ Global Exception를 만들어봐요

### 1. 커스텀 예외 클래스 정의

**PostNotFoundException.java**

```java
package com.ceos20.spring_instagram.global.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
```

**CommentNotFoundException.java**

```java
package com.ceos20.spring_instagram.global.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
```

**MemberNotFoundException.java**

```java
package com.ceos20.spring_instagram.global.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
```

### 2. GlobalExceptionHandler 클래스

전역 예외 핸들러에서 커스텀 예외를 처리할 수 있도록 함

**GlobalExceptionHandler.java**

```java
package com.ceos20.spring_instagram.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFound(CommentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePostNotFound(PostNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFound(MemberNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
    }
}
```

```jsx
    //공통 예외처리 메서드 
    private ResponseEntity<String> handleException(Exception ex, HttpStatus status) {
        log.warn("예외 발생: ", ex); // 경고 로그
        return ResponseEntity.status(status).body(ex.getMessage());
    }
}
```

### CommentService.java

```jsx
 // 특정 댓글 조회
    public CommentDTO getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        return CommentDTO.from(comment);
    }
```

https://github.com/user-attachments/assets/423fda31-fa40-4db2-83d5-b9934f635b7d

## 4️⃣ Swagger 연동 후 Controller 통합 테스트를 해봐요

```jsx
	implementation group: 'org.springdoc', name: 'springdoc-openapi-starter-webmvc-ui', version: '2.2.0'
```

https://github.com/user-attachments/assets/5858a98f-2d61-4398-9619-3094f787432e

https://github.com/user-attachments/assets/b938ce32-a9bf-40a1-9459-002c9bd83d25

### CommentControllerTest.java

```jsx

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Long postId;
    private Long memberId;
    private Long commentId;

    @BeforeEach
    public void setup() {
        // 테스트 전에 필요한 데이터베이스 초기화
        commentRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();

        Member member = Member.builder()
                .membername("testUser")
                .email("test@example.com")
                .password("password")
                .build();
        memberId = memberRepository.save(member).getMemberId();

        Post post = Post.builder()
                .member(member)
                .image("test.jpg")
                .caption("테스트 포스트")
                .build();
        postId = postRepository.save(post).getPostId();

        // 테스트용 댓글 추가
        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .text("테스트 댓글입니다.")
                .build();

        // 댓글을 실제로 추가하여 commentId를 저장
        commentId = commentRepository.save(comment).getCommentId();
    }

    @Test
    public void testCreateComment() throws Exception {
        // 댓글 생성 API 테스트
        String json = "새로운 테스트 댓글입니다.";

        mockMvc.perform(post("/api/comments/?postId=" + postId + "&memberId=" + memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("새로운 테스트 댓글입니다."));
    }

    @Test
    public void testGetAllCommentsByPost() throws Exception {
        // 특정 포스트에 대한 모든 댓글 가져오기 API 테스트
        mockMvc.perform(get("/api/comments/post/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCommentById() throws Exception {
        // 댓글을 추가한 후 해당 댓글의 ID를 사용하여 조회 테스트
        mockMvc.perform(get("/api/comments/{commentId}", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(commentId));
    }

    @Test
    public void testDeleteComment() throws Exception {
        // 댓글을 추가한 후 해당 댓글의 ID를 사용하여 삭제 테스트
        mockMvc.perform(delete("/api/comments/{commentId}", commentId))
                .andExpect(status().isNoContent());
    }
}

```

### error 처리

https://github.com/user-attachments/assets/2edd79d0-86e8-45b5-b54e-5fb016d6769b

HTTP 상태 코드 404는 요청한 리소스(예: 특정 댓글)가 서버에서 찾을 수 없음을 나타냅니다. 테스트 중 204 상태 코드(성공적인 요청을 나타내지만 응답 본문이 없음을 의미함)를 기대하고 있었지만 404 오류가 발생했기 때문에, 요청한 리소스가 존재하지 않는 상황이 발생

**'com.ceos20.spring_instagram.dto.CommentDTO'의 'from(com.ceos20.spring_instagram.domain.Comment)'을(를) '(com.ceos20.spring_instagram.dto.CommentDTO)'에 적용할 수 없습니다**

```jsx

// 테스트용 댓글 추가
Comment comment = Comment.builder()
        .post(post)
        .member(member)
        .text("테스트 댓글입니다.")
        .build();

// 댓글을 실제로 추가하여 commentId를 저장
commentId = commentRepository.save(comment).getCommentId(); // 추가된 댓글 ID 저장
commentRepository.save(CommentDTO.from(comment)).getCommentId() 

요약
CommentDTO.from(comment)는 Comment 엔티티를 CommentDTO로 변환하기 위한 메서드입니다.
CommentRepository.save() 메서드는 Comment 타입의 객체를 받아야 합니다. 
CommentDTO를 Comment로 변환하려면, Comment.builder()를 사용하여 
Comment 객체를 생성한 뒤 저장하는 방식으로 수정
```

CommentDTO.from(comment)에서는 Comment 타입의 객체를 전달해야 하는데, 현재 commentRepository.save(CommentDTO.from(comment))에서 CommentDTO 타입의 객체를 전달하고 있습니다. 이로 인해 타입 불일치 에러가 발생합니다.
올바르게 수정하려면, CommentDTO 객체를 Comment 엔티티로 변환하는 부분을 수정하고, Comment 타입을 전달하도록 해야 합니다.

```jsx
could not execute statement [Cannot delete or update a parent row: a 
foreign key constraint fails (instagram.comment, CONSTRAINT FKs1slvnkuemjsq2kj4h3vhx7i1 FOREIGN KEY (post_id) REFERENCES post (post_id))] [/* delete for com.ceos20.spring_instagram.domain.Post */delete from post where post_id=?]; SQL [/* delete for com.ceos20.spring_instagram.domain.Post */delete from post where post_id=?]; constraint [null]
```

이 오류는 외래 키 제약 조건 위반으로 발생합니다. `post`를 삭제하려고 할 때, `comment` 테이블의 `post_id`가 `post` 테이블의 `post_id`를 참조하고 있기 때문에 `post`에 연결된 댓글이 존재하는 경우 해당 `post`를 삭제할 수 없습니다. 데이터베이스는 연결된 댓글이 삭제되지 않은 상태에서 `post` 삭제를 허용하지 않도록 제약 조건을 설정합니다.

- **댓글을 먼저 삭제한 후 포스트를 삭제**: `postRepository.deleteAll()`을 호출하기 전에 `commentRepository.deleteAll()`을 먼저 호출하여 댓글을 삭제하면, 외래 키 제약 조건에 위배되지 않습니다.
- **연관된 엔티티를 삭제할 때 자동으로 댓글도 삭제**: `Comment`와 `Post` 간의 연관 관계를 설정할 때, `Post` 엔티티의 `comments` 필드에 `@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)`와 같은 방식으로 `cascade` 속성을 추가하여, `Post` 삭제 시 연관된 `Comment`도 자동으로 삭제되도록 설정할 수 있습니다.

## 5️⃣ 3주차까지 부족한 부분 리팩토링 해주세요

- ~~DTO 사용~~
- 변수명 통일

좀 더 자세한 에러로그를 확인하기 위해 ./gradlew test -i로 확인