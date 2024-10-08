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