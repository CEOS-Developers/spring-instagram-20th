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

## 1️⃣ JWT 인증(Authentication) 방법에 대해서 알아보기

### 1. **JWT를 이용한 인증 방식**

JWT는 주로 클라이언트-서버 간의 통신에서 사용자 인증에 사용되는 JSON 기반의 토큰입니다. 토큰을 발급받은 사용자는 토큰을 이용해 인증 상태를 유지할 수 있습니다.

### **액세스 토큰과 리프레쉬 토큰 구조**

- **액세스 토큰**: 짧은 유효 기간을 가지며, 서버에서 리소스에 접근할 때마다 확인하는 용도로 사용됩니다. 서버는 클라이언트의 요청에 포함된 액세스 토큰을 확인하여 사용자의 인증 상태를 파악합니다.
- **리프레쉬 토큰**: 액세스 토큰이 만료되었을 때 새로운 액세스 토큰을 발급받기 위한 용도로 사용됩니다. 리프레쉬 토큰은 서버에만 저장되어 보안이 높은 편이며, 유효 기간이 더 길고 한 번 사용되면 만료되는 경우가 많습니다.

### **JWT 구조**

JWT는 세 가지 파트로 나뉘며, 점(.)으로 구분됩니다:

1. **Header**: 토큰 타입과 해싱 알고리즘 정보를 포함합니다.
2. **Payload**: 사용자 ID, 권한 등 클레임(claim) 정보를 포함합니다.
3. **Signature**: Header와 Payload를 결합한 뒤 비밀 키로 서명하여 무결성을 보장합니다.

### **장점**

- **무상태성**: 서버는 세션 상태를 저장할 필요가 없으므로 수평 확장에 유리합니다.
- **효율성**: 클라이언트와 서버 간의 통신이 간결하고 빠릅니다.

### **단점**

- **안전성 문제**: 토큰이 유출되면 쉽게 재발급되지 않으므로 보안이 중요한 서비스에서는 주의가 필요합니다.
- **페이로드 크기 제한**: 페이로드가 커질수록 토큰의 크기도 커지므로 대역폭이 늘어납니다.

### 2. **세션 기반 인증**

세션 기반 인증은 사용자가 로그인을 하면 서버가 세션을 생성하고, 이를 세션 저장소에 보관하는 방식입니다. 사용자는 서버로부터 세션 ID를 받으며, 이후 요청마다 이 세션 ID를 함께 보내어 인증을 진행합니다.

### **구성 요소**

- **세션 ID**: 클라이언트가 서버와 인증할 때 사용하는 고유 식별자입니다.
- **세션 저장소**: 서버에 저장된 사용자 상태 정보로, 보통 Redis나 메모리 상에서 관리됩니다.

### **장점**

- **안전성**: 서버가 인증 상태를 관리하여 사용자 정보 보안이 상대적으로 우수합니다.

### **단점**

- **확장성 문제**: 세션은 서버 상태를 관리해야 하므로 서버 간 확장 시 공유 세션 저장소가 필요합니다.

### 3. **쿠키 기반 인증**

쿠키 기반 인증은 주로 웹 브라우저에서 세션 ID를 쿠키로 저장하여 인증을 유지하는 방식입니다. 클라이언트가 서버에 요청할 때마다 쿠키에 저장된 세션 ID가 전송됩니다.

### **구성 요소**

- **쿠키**: 클라이언트가 저장한 세션 ID를 서버에 전송하여 인증을 유지하는 방법입니다.
- **보안 설정**: HttpOnly, Secure 같은 쿠키 옵션을 설정하여 CSRF와 XSS 공격에 대비합니다.

### **장점**

- **자동 인증**: 브라우저는 자동으로 쿠키를 전송하므로 사용자가 별도의 인증 정보를 입력할 필요가 없습니다.

### **단점**

- **보안 위험**: 쿠키 탈취 공격을 방지하기 위한 추가적인 보안 설정이 필요합니다.

### 4. **OAuth**

OAuth는 외부 애플리케이션이 사용자 대신 자원에 접근할 수 있도록 승인하는 프로토콜입니다. 대표적으로 구글, 페이스북 로그인에서 사용됩니다.

### **구성 요소**

- **Resource Owner**: 리소스 소유자, 보통 사용자입니다.
- **Client**: 외부 애플리케이션으로, 리소스 소유자에게 권한을 요청합니다.
- **Authorization Server**: 클라이언트가 권한을 요청할 때 인증을 담당합니다.
- **Resource Server**: 보호된 리소스가 있는 서버로, 클라이언트가 접근을 요청합니다.

### **OAuth 2.0 인증 과정**

1. 사용자가 클라이언트에게 접근 권한을 승인합니다.
2. 클라이언트는 Authorization Server에서 액세스 토큰을 발급받습니다.
3. 클라이언트는 Resource Server에 액세스 토큰을 사용하여 리소스에 접근합니다.

### **장점**

- **범용성**: 여러 서비스에 동일한 인증 방식 적용 가능.

### **단점**

- **구현 복잡성**: 권한 부여 및 토큰 관리를 위한 복잡한 구현이 필요합니다.

### 요약

| 방식 | 인증 방법 | 장점 | 단점 |
| --- | --- | --- | --- |
| JWT | 액세스/리프레쉬 토큰 사용 | 무상태성, 간결한 인증 | 보안 문제, 페이로드 크기 제한 |
| 세션 | 서버 세션 상태 관리 | 보안성 높음 | 확장성 문제 |
| 쿠키 | 브라우저 쿠키에 세션 ID 저장 | 자동 인증 편리 | 쿠키 탈취 위험 |
| OAuth | 외부 애플리케이션에 권한 부여 | 범용성, 다양한 애플리케이션에 적용 가능 | 구현 복잡성, 토큰 관리 필요 |

이 방식들 중 각 서비스의 성격과 필요에 맞게 선택하여 사용하면 효과적입니다.

CSRF는 사용자가 의도하지 않은 요청을 서버로 보내게 만들어 권한을 탈취하는 공격이며, XSS는 악성 스크립트를 삽입하여 사용자의 브라우저에서 정보를 탈취하거나 악성 행동을 유도하는 공격입니다. 
CSRF -> 서버가 사용자를 신뢰 XSS ->  사용자가 서버를 신뢰 
CSRF (Cross-Site Request Forgery)
1. 정의: CSRF는 악의적인 사용자가 사용자가 의도하지 않은 요청을 피해자의 브라우저를 통해 서버로 보내는 공격입니다. 피해자는 자신이 공격당하고 있다는 사실을 알지 못한 채, 정상적인 사용자로 위장하여 악의적인 요청을 보내게 됩니다.
2. 발생 원인:

서버가 요청을 처리할 때, 요청이 사용자로부터 온 것인지 확인하는 방법이 부족할 때 발생합니다. 보통 세션 ID나 쿠키를 통해 사용자를 인증하지만, 이를 검증하는 적절한 방법이 없다면 CSRF 공격에 취약해질 수 있습니다.
3. 공격 과정:

공격자는 피해자가 로그인한 웹사이트를 대상으로 악의적인 요청을 만든 후, 피해자가 해당 웹사이트에 접속한 상태에서 공격자가 삽입한 악성 스크립트나 링크를 클릭하도록 유도합니다.
피해자가 링크나 악성 요청을 클릭하면, 피해자의 브라우저는 이미 로그인된 상태에서 해당 요청을 서버로 보내게 됩니다.
서버는 해당 요청이 정상적인 사용자의 요청이라고 판단하고 요청을 처리하게 됩니다. 이로 인해 공격자가 원래 의도한 악의적인 동작이 수행됩니다.
예시:

사용자가 은행 사이트에 로그인한 상태에서 악성 웹사이트에 접속합니다.
악성 웹사이트는 피해자 계좌에서 다른 계좌로 송금하는 요청을 은행 서버로 보냅니다.
은행 서버는 이 요청이 정상적인 사용자로부터 온 것이라 판단하고 송금을 처리합니다.
XSS (Cross-Site Scripting)
1. 정의: XSS는 공격자가 웹 애플리케이션에 악성 스크립트를 삽입하여 피해자의 브라우저에서 실행시키는 공격입니다. XSS는 사용자의 정보나 세션을 탈취하거나, 페이지를 변조하거나, 악성 코드가 실행되도록 할 수 있습니다.

2. 발생 원인:

웹 애플리케이션이 사용자가 입력한 데이터를 제대로 필터링하지 않거나 이스케이프 처리하지 않는 경우 발생합니다. 공격자는 악성 스크립트를 입력 폼에 삽입하여 이를 다른 사용자의 브라우저에서 실행시키려고 합니다.
3. 공격 과정:

공격자는 웹 애플리케이션의 입력 필드나 URL에 JavaScript와 같은 악성 스크립트를 삽입합니다.
서버는 사용자가 입력한 데이터를 제대로 필터링하거나 이스케이프 처리하지 않고, 페이지에 반영합니다.
다른 사용자가 해당 페이지를 로드하면, 악성 스크립트가 실행되어 사용자의 쿠키, 세션 정보, 개인 정보 등을 탈취하거나, 사용자가 모르게 악의적인 작업을 수행합니다.
예시:

공격자가 게시판에 악성 스크립트를 삽입하여 다른 사용자가 게시판을 열었을 때 해당 스크립트가 실행되도록 합니다.
이 스크립트는 사용자의 쿠키 정보를 탈취하여 공격자가 이를 활용할 수 있게 합니다.
CSRF와 XSS의 차이점
구분	CSRF	XSS
공격 방법	사용자가 의도하지 않은 요청을 보내게 만듦.	악성 스크립트를 삽입하여 사용자의 브라우저에서 실행됨.
피해 대상	사용자가 이미 로그인한 상태인 경우에 유효.	웹 애플리케이션을 사용하는 모든 사용자.
공격의 목적	사용자의 권한을 이용해 악의적인 요청을 수행.	사용자의 정보나 세션을 탈취하거나 악성 동작을 실행.
취약점 발생 원인	서버가 요청의 출처를 검증하지 않거나, 토큰 등을 사용하지 않는 경우.	사용자 입력 데이터를 제대로 필터링하지 않거나 이스케이프 처리하지 않는 경우.
예방 방법	CSRF 토큰 사용, Referer 헤더 검증, SameSite 쿠키 속성 사용 등.	입력 값 검증 및 이스케이프 처리, Content Security Policy(CSP) 적용 등.
결론


JWT (JSON Web Token)는 일반적으로 **CSRF 취약점이 발생하지 않는 구조**로 여겨지지만, 그 이유는 **주로 JWT의 저장 방식**과 **인증 방식의 차이**에서 비롯됩니다.

### 1. CSRF 취약점과 세션 쿠키
CSRF 공격은 웹 애플리케이션이 **쿠키에 저장된 세션 정보**를 통해 사용자를 인증할 때 주로 발생합니다. 웹 브라우저는 동일 사이트 정책 때문에 같은 사이트에서 발생한 요청은 자동으로 쿠키를 포함하여 서버로 전송하므로, 악의적인 사이트가 사용자의 세션을 악용해 요청을 보낼 수 있습니다.

### 2. JWT 인증 방식
JWT는 CSRF 공격을 방지하는 구조적 특성이 있습니다. 그 이유는 다음과 같습니다.

- **브라우저의 자동 전송을 피함**: JWT는 쿠키 대신 주로 **로컬 스토리지나 세션 스토리지**에 저장하고, 이를 사용할 때는 **헤더에 명시적으로 포함하여 서버에 전송**합니다. 브라우저는 로컬 스토리지나 세션 스토리지에 저장된 데이터를 자동으로 전송하지 않으므로, CSRF 공격이 발생하기 어렵습니다.

- **명시적인 인증 전송**: JWT를 보낼 때는 `Authorization` 헤더에 `Bearer` 토큰 형식으로 전송하는 경우가 일반적입니다. 따라서, 요청을 보낼 때마다 클라이언트가 명시적으로 JWT를 포함해야 하므로 CSRF와 같은 자동 전송 문제가 발생하지 않습니다.

### 3. 토큰 자체의 특징
JWT는 클라이언트와 서버 간에 서명된 토큰으로 발급되어, 서버가 특정 조건에 따라 유효성을 검증할 수 있습니다. 서버는 토큰의 서명과 만료 시간을 확인하여 요청의 유효성을 검증하므로, 중간에서 토큰이 위조되거나 변조될 위험이 낮습니다.

### 주의할 점: JWT도 CSRF에 안전하지 않은 경우
JWT가 반드시 CSRF에 안전한 것은 아닙니다. 만약 **JWT를 쿠키에 저장**하고, 이 쿠키가 `HttpOnly` 속성이 없는 경우, CSRF 취약점이 발생할 수 있습니다. 이를 방지하기 위해서는 토큰을 로컬 스토리지에 저장하거나, 쿠키를 사용할 경우 `SameSite=strict` 또는 `SameSite=lax` 속성을 추가해 CSRF 방어를 강화할 필요가 있습니다.

### 요약
JWT가 CSRF에 상대적으로 안전한 이유는 주로 **명시적인 인증 전송 방식**과 **쿠키가 아닌 로컬 스토리지/세션 스토리지 사용** 때문입니다.

## 2️⃣ 액세스 토큰 발급 및 검증 로직 구현하기

### 전체 흐름:

1. 클라이언트는 `/login` 또는 `/register` API를 호출하여 로그인/회원가입 요청을 보냅니다.
2. `AuthController`는 요청을 받아 `AuthService`로 전달합니다.
3. `AuthService`는 로그인 정보 또는 회원 정보를 처리하고, 로그인 시 `TokenProvider`를 통해 JWT 액세스 토큰을 생성합니다.
4. 클라이언트는 JWT 토큰을 받아 저장하고, 보호된 API에 접근할 때 이 토큰을 HTTP 요청 헤더에 포함시켜 보냅니다.
5. `JwtAuthenticationFilter`는 요청에서 토큰을 추출하고, `TokenProvider`를 통해 토큰의 유효성을 검증합니다.
6. 유효한 토큰이면, `Authentication` 객체를 생성하여 `SecurityContextHolder`에 설정하고, 요청을 처리합니다.

## 3️⃣ 회원가입 및 로그인 API 구현하고 테스트하기

![image](https://github.com/user-attachments/assets/8442176e-a1f5-4541-bc3b-4605e23b3303)
![image](https://github.com/user-attachments/assets/e6ce2244-c167-4fdb-a141-9ec005f97b40)

## 4️⃣ 토큰이 필요한 API 1개 이상 구현하고 테스트하기

### 1. **토큰이 필요한 API - 게시글 작성**

게시글 작성 API에서는 사용자가 게시글을 작성할 때 **JWT 토큰**을 보내야 하며, 이 토큰을 검증하여 해당 사용자가 인증된 사용자인지 확인합니다. 이를 위해 **JWT 인증 필터**가 필요

### 2. **게시글 작성 API 구현 흐름**

- 사용자가 게시글을 작성하려면 **로그인 후 받은 JWT 토큰**을 `Authorization` 헤더에 포함시켜 요청을 보내야 합니다.
- 서버는 **JWT 토큰을 검증**하여, 토큰이 유효한지, 만료되었는지 확인합니다.
- 토큰이 유효한 경우, 해당 사용자가 작성한 게시글을 저장합니다.
- 토큰이 유효하지 않거나 없으면, **403 Forbidden** 에러를 반환합니다.

# 6주차 

## docker-compose로 스프링부트+mysql 빌드

**Dockerfile**

```jsx
# 1. 베이스 이미지 설정 (Java 17 사용)
FROM openjdk:17-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle 빌드 결과물 복사
COPY build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

# 4. 실행 명령 설정
ENTRYPOINT ["java", "-jar", "app.jar"]
```

- 순서 FROM > WORKDIR > … 재빌드시 효율성 위해

**docker-compose.yml**

```jsx
version: '3.8'

services:
  # Spring Boot 애플리케이션 서비스
  spring-instagram:
    build: .
    ports:
      - "8080:8080"
    env_file:
      - .env  # .env 파일을 여기서 참조
    environment:
      SPRING_DATASOURCE_URL: ${SPRING_DATASOURCE_URL}
      SPRING_DATASOURCE_USERNAME: ${SPRING_DATASOURCE_USERNAME}
      SPRING_DATASOURCE_PASSWORD: ${SPRING_DATASOURCE_PASSWORD}
      JWT_SECRET: ${JWT_SECRET}
      JWT_EXPIRATION_TIME: ${JWT_EXPIRATION_TIME}
    depends_on:
      - mysql

  # MySQL 서비스
  mysql:
    image: mysql:8
    env_file:
      - .env  # .env 파일을 여기서 참조
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: ${MYSQL_DATABASE}
    ports:
      - "3307:3306"

```

![스크린샷 2024-11-16 095111](https://github.com/user-attachments/assets/8f3753fd-5acf-4fe2-84b4-6ea61e27ca85)

`docker-compose up` - 컨테이너 실행

![스크린샷 2024-11-16 095358](https://github.com/user-attachments/assets/b23190b9-9e88-4fcd-9aba-c92d501194a2)

`docker ps` - 현재 실행중인 컨테이너 확인

![스크린샷 2024-11-16 095517](https://github.com/user-attachments/assets/d49ac681-51cc-4e1f-a80d-82a7ff57b453)

❗Error❗

**com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure**

springboot 가 계속 Exited되는 것을 볼 수 있음

gradle build > docker 이미지 생성 > 컨테이너 실행

로컬에서의 application.yml 설정과 docker 컨테이너에서의 application.yml설정은 달라야함

도커는 애플리케이션과 데이터베이스를 분리된 컨테이너로 실행하므로, 컨테이너 내부에서 동작하는 데이터베이스에 접근하기 위해서는

데이터베이스 컨테이너의 이름이거나 도커 네트워크에서 설정한 별칭을 이용해야한다.

## Spring profiles

Spring Boot에서는 환경별로 `application.properties` 또는 `application.yml` 파일을 분리하여 프로파일별 설정을 적용할 수 있습니다.

예를 들어:

- `application-dev.yml` (개발 환경)
- `application-prod.yml` (운영 환경)

환경별 설정 파일 (`application-dev.yml`):

```yaml
server:
  port: 8080
database:
  url: jdbc:mysql://localhost:3306/dev_db

```

환경별 설정 파일 (`application-prod.yml`):

```yaml
server:
  port: 8081
database:
  url: jdbc:mysql://localhost:3306/prod_db
```

### 2. **프로파일 활성화**

- **코드로 설정**: `application.yml` 파일에서 기본 프로파일 설정.

    ```yaml
    spring:
      profiles:
        active: dev
    ```


**application.yml(일부)**

```jsx
spring:
  config:
    activate:
      on-profile: dev  # 기본 활성화할 프로파일을 dev로 설정

--- <<application-dev.yml 
spring:
  config:
    activate:
      on-profile: dev  # 로컬 개발 환경 프로파일
  datasource:
    url: jdbc:mysql://localhost:3306/insta  # 로컬 MySQL 연결 URL
    username: 
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver

--- <<application-prod.yml 
spring:
  config:
    activate:
      on-profile: prod  # Docker 환경 프로파일
  datasource:
    url: jdbc:mysql://mysql-1:3306/insta  # Docker MySQL 연결 URL
    username: 
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver
```

**@ActiveProfiles("dev") 어노테이션**

```jsx
package com.ceos20.spring_instagram;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
class SpringInstagramApplicationTests {

	@Test
	void contextLoads() {
	}

}
```

**Dockerfile 설정**

```jsx
ENV SPRING_PROFILES_ACTIVE=prod
```

`docker-compose up --build` - 컨테이너 실행 + build

**“The following 1 profile is active: “prod”**

![스크린샷 2024-11-16 120807](https://github.com/user-attachments/assets/583817c9-e2b7-4ac6-81c9-3681b7967af7)

[localhost:8080](http://localhost:8080) 접속!

![스크린샷 2024-11-16 113026](https://github.com/user-attachments/assets/67b4c257-b1d2-44fa-8b3b-a56d388be5df)

❗항상 공식문서를 참조하자

https://docs.docker.com/compose/how-tos/environment-variables/set-environment-variables/

https://docs.docker.com/desktop/troubleshoot-and-support/troubleshoot/#self-diagnose-tool

https://velog.io/@fill0006/springbootmysqldocker-%EB%A9%80%ED%8B%B0-%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88-%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%84%B1

### 배포 방법

### **ECS + Fargate + RDS + API Gateway**

1. **API Gateway**:
    - API Gateway를 통해 외부에서 애플리케이션에 접근합니다.
    - 인증, 로깅, API 라우팅 등을 처리합니다.
2. **ECS + Fargate**:
    - Spring Boot 애플리케이션을 Docker 컨테이너로 실행합니다.
    - Fargate는 서버리스 방식으로 컨테이너를 관리하여, EC2 인스턴스를 관리할 필요가 없습니다.
    - 애플리케이션의 스케일링을 자동으로 처리할 수 있습니다.
3. **RDS**:
    - MySQL을 AWS RDS에서 관리하여 데이터베이스를 운영합니다.
    - RDS를 사용하면 데이터베이스 관리가 간편해지고, 자동 백업과 확장성이 보장됩니다.
4. **VPC**:
    - ECS, RDS, API Gateway 등을 모두 동일한 VPC 내에 배치하여 네트워크를 관리합니다.
    - 보안을 강화하고, VPC 내에서만 접근 가능하도록 설정할 수 있습니다.

### **EC2 +  RDS + API Gateway + ALB**

1. **EC2 인스턴스**:
    - EC2에 Docker 컨테이너로 애플리케이션을 배포합니다.
    - EC2의 장점은 서버에 대한 완전한 제어가 가능하다는 점입니다.
2. **RDS**:
    - MySQL 데이터베이스는 RDS에서 관리합니다.
    - EC2에서 직접 데이터베이스를 관리할 필요가 없으며, RDS가 자동 백업과 확장성을 제공합니다.
3. **API Gateway**:
    - API Gateway를 통해 외부에서 애플리케이션에 접근합니다.
    - 인증, 로깅, API 라우팅 등을 처리합니다.

참고 블로그

https://velog.io/@jjeongdong/Devops-Docker-Compose%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-EC2%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C-%EB%B0%B0%ED%8F%AC#ec2-linux%EC%97%90%EC%84%9C-docker-compose-%EC%8B%A4%ED%96%89

https://velog.io/@jkijki12/Docker-Compose%EB%A1%9C-EC2%EC%97%90-Spring-%EB%B0%B0%ED%8F%AC%ED%95%B4%EB%B3%B4%EA%B8%B0

### EC2 + RDS 진행

```jsx
mkdir spring-instagram
git clone <git repository url>
```

docker-compose.yml, application.yml 수정

- rds endpoint 로 수정

```jsx
docker ps
```

### gradle 설정

- 최신 Gradle 설치 스크립트를 사용:

    ```bash
    
    wget https://services.gradle.org/distributions/gradle-8.3-bin.zip
    sudo mkdir /opt/gradle
    sudo unzip -d /opt/gradle gradle-8.3-bin.zip
    
    ```

- Gradle를 시스템 경로에 추가:

    ```bash
    
    sudo nano /etc/profile.d/gradle.sh
    
    ```

  아래 내용을 추가:

    ```bash
    
    export GRADLE_HOME=/opt/gradle/gradle-8.3
    export PATH=$GRADLE_HOME/bin:$PATH
    ```

- 변경사항 반영:

    ```bash
    
    source /etc/profile.d/gradle.sh
    ```


컨테이너 실행 확인

![image](https://github.com/user-attachments/assets/9eef1fe0-0fab-493f-8e73-68c8cbc43cb5)
