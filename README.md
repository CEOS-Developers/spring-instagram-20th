# spring-instagram-20th
CEOS 20th BE study - instagram clone coding

# 1주차


## ERD

### 전체 구조

![img_2.png](img_2.png)

### 세부 구조

#### 1. 유저 간 1:1 DM 기능

![img_3.png](img_3.png)

   - 처음에는 한 명의 유저는 여러 dm방을 가질 수 있고, 하나의 dm방은 여러 유저와 관계를 맺을 수 있기에 다대다 관계이므로 1:N, N:1 관계로 풀어주기 위해 중간테이블을 두려고 했다. 하지만 이 방식에서는 Message 테이블이 User와 Room을 직접 참조하지 못하고 중간테이블을 거쳐야 해 구조가 복잡해져 다시 생각해보았다. 1:1 채팅이기에 하나의 채팅방은 두명의 유저와만 관계를 맺기 때문에 굳이 다대다 관계로 볼 필요가 없이, DmRoom 엔티티에 유저1 id와 유저2 id를 외래키로 두어 각 유저와 Dm방이 관계를 맺도록 함으로써 DM방과 User를 N:1관계로 보았다. 대신 이 관계를 두 유저와 가지므로 두 번 가지게 된다.

   - 각 메시지마다 담아야 하는 정보가 많기 때문에 메시지를 채팅방 엔티티에 포함시키지 않고 Message 엔티티로 따로 분리하고 User와 DmRoom 엔티티와 관계를 맺도록 했다.


#### 2. 게시글 기능

![img_4.png](img_4.png)

   - 게시글 이미지 : 사진을 포함한 게시글을 작성할 수 있도록 이미지 엔티티를 생성하였으며, 게시글 ID를 외래 키로 설정하여 각 게시글에 사진을 첨부할 수 있도록 구성하였다. 서버에 이미지를 업로드한 후 해당 이미지의 URL을 데이터베이스에 저장하도록 하였다.
   
   - 좋아요 수 : 누가 좋아요 눌렀는지 조회할 일은 적지만 수는 대부분 게시글과 함께 표시되므로 Post 엔티티에 좋아요 수를 저장한다.

   - 게시글 좋아요 : 한 명의 유저는 여러 게시글에 좋아요를 누를 수 있고, 한 게시글은 여러 유저로부터 좋아요를 받을 수 있어 다대다 관계다. 따라서 중간테이블인 게시글 좋아요 테이블을 두고 유저 id와 게시글 id을 외래키로 가져 관계를 맺도록 했다.


#### 3. 댓글 기능

![img_5.png](img_5.png)

   - 대댓글 : 댓글과 대댓글은 1:N 관계이고, 둘의 필드 구성이 매우 유사해 엔티티를 분리하지 않고, Comment 엔티티를 순환참조 하도록 구성했다. 대댓글을 구현하기위해 Comment 엔티티에 부모댓글의 id인 parent_id를 외래키로 설정하고 이를 참조하도록 했다. 해당 댓글이 부모댓글이라면 parent_id는 null이므로 parent_id 필드는 null 값을 허용해준다.

   - 댓글 좋아요 : 댓글 좋아요 엔티티를 두고 유저 id와 게시글 id을 외래키로 가져 관계를 맺도록 했다.

#### 4. 유저 기능

![img_6.png](img_6.png)

   - 회원가입 : 휴대폰 번호 or 이메일, 성명, 사용자 이름(닉네임), 비밀번호를 입력해 회원가입 한다. 이때 사용자 이름은 중복되지 않는지 검사해주어야 한다.
   
   - 로그인 : 휴대폰 번호 or 이메일 or 사용자 이름과 비밀번호를 입력해 로그인한다.

   - 프로필 이미지 : 사용자는 하나의 프로필 사진만 설정할 수 있고, 별도로 관리할 이미지 속성이 없기 때문에 이미지 엔티티를 따로 생성하지 않고, User 엔티티에 이미지 URL을 저장하도록 했다.

   - 팔로우 기능 : 유저 간에는 팔로잉과 팔로워 관계가 존재한다. 한 유저는 여러 유저를 팔로우 할 수 있고, 한 유저는 여러 유저로부터 팔로우를 받을 수 있다. 따라서 Follow 테이블에 팔로우하는 유저의 ID와 팔로우받는 유저의 ID를 각각 외래 키로 저장하였다.
   

## 엔티티 생성
Test에 쓰일 Post 엔티티는 다음과 같다.
```
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor // Builder는 파라미터 있는 생성자가 필요
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    private String content;
    private int like_num;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="post")
    private List<PostImage> images;

}

```

Post를 조회할때 images와 댓글을 함께 조회 할 일이 많을 것 같아 양방향 매핑해주었다. 좋아요 리스트는 조회할 일이 많지 않고 수만 자주 조회되므로 좋아요 수를 post필드에 추가하였다.

cf) JPA의 Entity는 기본 생성자(NoArgsConstructor)가 반드시 필요하다!

-> 이유 : JPA는 데이터베이스에서 조회한 값을 엔티티 객체로 변환할 때 **Reflection**을 사용하여 객체를 생성한다. 이 과정에서 기본 생성자를 호출해 빈 객체를 먼저 만든 후, 조회한 데이터를 각 필드에 매핑한다. 따라서 기본 생성자가 없다면  JPA는 객체를 생성할 수 없고, 결국 데이터베이스에서 조회한 값을 엔티티로 변환하는 작업이 실패하게 된다.
Reflection이란? : 리플렉션은 자바에서 제공하는 기능으로, 구체적인 Class Type을 알지 못해도 런타임에 해당 클래스의 이름, 변수, 메소드에 접근할 수 있게 해준다.


## Repository 단위 테스트
Repository 단위테스트에는 @JpaDataTest를 많이 사용한다고 한다.

#### 1) Post Repository에서 user_id로 Post를 찾아 조회하는 단위테스트.

```
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({PostRepository.class,UserRepository.class})
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private Post post1;
    private Post post2;
    private Post post3;
    private User user;

    @BeforeEach // 테스트 실행 전에 실행
    void setUp(){
        user=User.builder()
                .nickname("sh")
                .username("test1")
                .phone("010-1111-1111")
                .email("11@naver.com")
                .password("111")
                .introduce("test")
                .followed_num(0)
                .following_num(1)
                .profile_image_url("https://example.com/default-profile.png")
                .isPublic(true)
                .build();

        post1=Post.builder()
                .content("testPost 1")
                .like_num(0)
                .user(user)
                .build();

        post2=Post.builder()
                .content("testPost 2")
                .like_num(1)
                .user(user)
                .build();

        post3=Post.builder()
                .content("testPost 3")
                .like_num(0)
                .user(user)
                .build();

        userRepository.save(user);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
    }

    @Test
    @Transactional
    void 게시글_조회_테스트(){

        //given
        Long userId=user.getId();

        //when
        List<Post> posts=postRepository.findByUser_Id(userId);

        //then
        // 게시글 갯수 확인
        assertEquals(3, posts.size());
        // 게시글 내용 확인
        assertEquals("testPost 1", posts.get(0).getContent());
        assertEquals("testPost 2", posts.get(1).getContent());
        assertEquals("testPost 3", posts.get(2).getContent());

    }
}

```

- 과정 : @BeforeEach를 사용하여 테스트 전에 user,post 객체를 생성하여 repository에 저장하게끔 했다. 이후, user 객체에서 user_id를 가져와 이 user_id를 사용하여 해당 사용자의 post를 조회하는 테스트를 수행

cf) 처음에는 `@Import({PostRepository.class,UserRepository.class})` 를 빼고 테스트 진행했더니 `No qualifying bean of type 'com.ceos20.instagram.post.repository.PostRepository' available` 오류가 발생했다..

이는 내가 구현한 PostRepository가 JpaRepository를 상속한 인터페이스가 아니라 직접 구현된 클래스이기 때문이다. Spring은 JpaRepository를 상속하는 인터페이스를 자동으로 빈으로 등록하지만, 그렇지 않은 직접 구현한 리포지토리는 자동으로 빈을 생성하지 않으므로 이를 수동으로 등록해야한다. 따라서 **@Import(PostRepository.class)** 를 통해 해당 리포지토리를 테스트에 수동으로 주입해야 한다.


테스트 성공 후 아래와 같은 쿼리가 출력된다.

1. @BeforeEach 를 통해 user와 post1,2,3 객체를 생성 후 테이블에 저장
```
Hibernate: 
    insert 
    into
        user
        (email, introduce, is_public, nickname, password, phone, profile_image_url, username) 
    values
        (?, ?, ?, ?, ?, ?, ?, ?)
Hibernate: 
    insert 
    into
        post
        (content, created_at, like_num, user_id) 
    values
        (?, ?, ?, ?)
Hibernate: 
    insert 
    into
        post
        (content, created_at, like_num, user_id) 
    values
        (?, ?, ?, ?)
Hibernate: 
    insert 
    into
        post
        (content, created_at, like_num, user_id) 
    values
        (?, ?, ?, ?)
```

2. findByUser_Id의 파라미터로 전달받은 user_id와 같은 user_id를 가진 post를 post 테이블에서 찾는다.

```
Hibernate: 
    select
        p1_0.post_id,
        p1_0.content,
        p1_0.created_at,
        p1_0.like_num,
        p1_0.user_id 
    from
        post p1_0 
    where
        p1_0.user_id=?
```

#### 2. Comment Repository post id를 통한 부모댓글 조회, 부모댓글 id를 통한 자식댓글 조회테스트
```
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommentRepository.class, UserRepository.class,PostRepository.class})
public class CommentRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Post post1;
    private User user;
    private Comment parent;
    private Comment child1;
    private Comment child2;

    @BeforeEach
        // 테스트 실행 전에 실행
    void setUp(){
        user=User.builder()
                .nickname("sh")
                .username("test1")
                .phone("010-1111-1111")
                .email("11@naver.com")
                .password("111")
                .introduce("test")
                .profile_image_url("https://example.com/default-profile.png")
                .isPublic(true)
                .build();

        post1=Post.builder()
                .content("testPost 1")
                .like_num(0)
                .user(user)
                .build();

        parent=Comment.builder()
                .context("I'm parent")
                .post(post1)
                .user(user)
                .build();

        child1=Comment.builder()
                .context("I'm child1")
                .post(post1)
                .user(user)
                .parent(parent)
                .build();

        child2=Comment.builder()
                .context("I'm child2")
                .post(post1)
                .user(user)
                .parent(parent)
                .build();





        userRepository.save(user);
        postRepository.save(post1);
        commentRepository.save(parent);
        commentRepository.save(child1);
        commentRepository.save(child2);

    }

    @Test
    @Transactional
    void 댓글_조회_테스트(){

        //given
        Long postId=post1.getId();
        Long parentId=parent.getId();

        //when
        List<Comment> parents=commentRepository.findByPost_Id(postId);
        List<Comment> childs=commentRepository.findByParent_Id(parentId);

        //then
        // 게시글 갯수 확인
        assertEquals(1, parents.size());
        assertEquals(2, childs.size());

        // 게시글 내용 확인
        assertEquals("I'm parent", parents.get(0).getContext());
        assertEquals("I'm child1", childs.get(0).getContext());
        assertEquals("I'm child2", childs.get(1).getContext());

    }
}

```

쿼리 조회 결과

1. @BeforeEach 를 통해 user와 post1, parent, child1,2 객체를 생성 후 테이블에 저장
```
Hibernate: 
    insert 
    into
        user
        (email, introduce, is_public, nickname, password, phone, profile_image_url, username) 
    values
        (?, ?, ?, ?, ?, ?, ?, ?)
Hibernate: 
    insert 
    into
        post
        (content, created_at, like_num, user_id) 
    values
        (?, ?, ?, ?)
Hibernate: 
    insert 
    into
        comment
        (context, created_at, parent_id, post_id, user_id) 
    values
        (?, ?, ?, ?, ?)
Hibernate: 
    insert 
    into
        comment
        (context, created_at, parent_id, post_id, user_id) 
    values
        (?, ?, ?, ?, ?)
Hibernate: 
    insert 
    into
        comment
        (context, created_at, parent_id, post_id, user_id) 
    values
        (?, ?, ?, ?, ?)
```

2. findByPost_Id의 파라미터로 전달받은 post_id와 같은 post_id를 가진 부모댓글을 Comment 테이블에서 찾는다. findByParent_Id의 파라미터로 전달받은 parent_id와 같은 parent_id를 가진 자식 댓글을 Comment 테이블에서 찾는다.

```
Hibernate: 
    select
        c1_0.comment_id,
        c1_0.context,
        c1_0.created_at,
        c1_0.parent_id,
        c1_0.post_id,
        c1_0.user_id 
    from
        comment c1_0 
    where
        c1_0.post_id=? 
        and c1_0.parent_id is null
Hibernate: 
    select
        c1_0.comment_id,
        c1_0.context,
        c1_0.created_at,
        c1_0.parent_id,
        c1_0.post_id,
        c1_0.user_id 
    from
        comment c1_0 
    where
        c1_0.parent_id=?

```
## (옵션) JPA 관련 문제 해결

### Q1. 어떻게  data jpa는 interface만으로도 함수가 구현이 되는가?

![img_1.png](img_1.png)

Spring Data JPA는 애플리케이션 실행 시, JpaRepository 인터페이스를 상속하는 repository 인터페이스에 대해 프록시 패턴을 사용해 SimpleJpaRepository 기반의 구현체를 동적으로 생성하고, 이를 빈으로 등록해 의존성을 주입해주기 때문이다.

**SimpleJpaRepository**

```
@Repository
@Transactional(readOnly = true)
public class SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation<T, ID> {
	...
    
    @Transactional
	@Override
	public <S extends T> S save(S entity) {

		Assert.notNull(entity, "Entity must not be null.");

		if (entityInformation.isNew(entity)) {
			em.persist(entity);
			return entity;
		} else {
			return em.merge(entity);
		}
	}
    
    ...
}
```

-> @Repository 어노테이션이 붙어있고 save와 같은 메소드가 구현되어 있는 것을 확인할 수 있다. 이와 같이 EntityManager를 주입받아 JPA를 직접 사용하는 방식으로 구현되어 있다.


### Q2. Data jpa를 찾다보면 SimpleJpaRepository에서  entity manager를 생성자 주입을 통해서 주입 받는다. 근데 싱글톤 객체는 한번만 할당을  받는데, 한번 연결 때 마다 생성이 되는 entity manager를 생성자 주입을 통해서 받는 것은 수상하지 않는가? 어떻게 되는 것일까? 한번 알아보자 

우선 EntityManager에 대해 살펴보자. 모든 JPA의 동작은 Entity들을 기준으로 돌아가게 되는데, 이 때 Entity들을 관리하는 클래스가 Entity Manager다. Entity Manager는 여러 스레드가 동시에 접근하면 동시성 문제가 발생하므로 이를 하나로 공유하면 안되고, 매 트랜잭션마다 새로 만들어주어야 한다. 즉, EntityManager는 트랜잭션 시작 시 생성되고 트랜잭션이 종료되면 닫히기 때문에 매번 새로운 트랜잭션마다 새로운 Entity Manager 인스턴스가 생성된다.

이제 질문으로 돌아와서, 싱글톤 객체인 SimpleJpaRepository에서 매번 새로운 EntityManager를 주입받는 것이 가능한 이유에 대해 살펴보면 결국 프록시 패턴과 관련있다고 한다. Spring Data JPA에서 Entity Manager가 생성자 주입을 통해 주입될 때, 실제 EntityManager를 주입하는 것이 아니라 실제 EntityManager를 연결해주는 EntityManager 프록시 객체를 주입해준다.이 프록시는 현재 트랜잭션에 대한 참조를 통해 실제 EntityManager 인스턴스에 접근하기에, 트랜잭션이 다르더라도 프록시가 적절한 EntityManager를 제공하여 SimpleJpaRepository가 항상 적절한 EntityManager와 함께 작동할 수 있다.


### Q3. fetch join 할 때 distinct를 안하면 생길 수 있는 문제

fetch join이란? : jpa에서 일반 join을 사용해 엔티티를 가져올 경우, 그 엔티티와 연관된 다른 엔티티까지 한번에 함께 조회하여 가져오지 않는다.
`select m from Member m join m.team` 이렇게 join을 사용하여 Member를 조회할 때 소속된 팀도 같이 가지고 올 경우, "select m from Member m"으로 쿼리를 보내고 결괏값으로 받은 객체들에서 member.getTeam으로 팀을 가져오게 되어 전체 Member를 조회하는 쿼리 한 개, Member와 연관된 Team을 조회하는 쿼리가 최대 N개가 발생하여 쿼리가 최대 N+1개 날라가는 문제가 생긴다. (모든 멤버들이 각각 다른 팀에 속해있으면 팀의 갯수 N만큼 쿼리문이 날라감, 모든 멤버들이 동일한 팀에 속한 경우는 1번 날라감) 

이를 해결하기 위해 `select m from Member m join fetch m.team` 이렇게 한 번의 쿼리로 연관된 엔티티까지 한번에 함께 조회하는 `fetch join` 방법이 사용된다. 즉, 나와 관련된 것들을 다 긁어오게 되어 즉시로딩 같은 역할을 하게 된다.

근데 fetch join을 사용할 때 distinct를 안 하면 문제가 생길 수 있다. 일대다 fetch join의 경우, 부모 엔티티가 자식 엔티티의 수만큼 중복돼서 나타나는 문제가 있다.  
`select t from Team t join fetch t.member` 으로 Team(일)을 조회할 때 팀이 속한 Member(다)도 조회할 때, inner join에 의해 매칭되는 데이터를 반환하여 Team A에 속한 멤버가 3명이면 Team A가 세 번 조회되는 문제가 발생한다. 이를 막으려면 `select distinct t from Team t join fetch t.members` 이렇게 distinct 키워드를 붙여 각 팀마다 한 번씩만 조회되게 해야 한다. 이때 distinct는 SELECT 대상(Team)에 대해서 중복제거 한다.

일대다를 패치 조인한다면 꼭 distinct 를 써야 한다 !


### Q4. fetch join 을 할 때 생기는 에러가 생기는 3가지 에러 메시지의 원인과 해결 방안

#### 1) `HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!`

- 의미 : fetch join 과 pagination 을 같이 사용하면 페이징이 되지 않고, "모든 데이터"를 가져와 메모리에 올려두고 페이징을 처리한다는 뜻

- 원인 : 1:N 관계를 fetch join하게 되면, 주요 엔티티인 1의 데이터가 중복이 돼서 data row 수가 늘어나기 때문에 데이터베이스의 limit과 offset을 이용한 쿼리를 통해 pagination하는 것이 불가능하다고 한다. 이 문제를 해결하기 위해 fetch join을 + 페이징 기능을 사용하려고 하는 경우, Hibernate에서는 자체적으로 모든 데이터를 불러와 주요 엔티티의 중복 row를 없앤 후 offset과 limit을 적용하여 어플리케이션으로 보내주는데 이 작업이 메모리에서 일어나게 돼서 위험하다.

- 해결법 : application.yml에 default_batch_fetch_size 설정하기. (처음부터 member를 fetch join해서 가져오는 대신 지연로딩을 유지하고, hibernate의 default_batch_fetch_size옵션을 사용하여
 부모(1, Team) 엔티티의 key가 default_batch_fetch_size 개수만큼 쌓일 때까지 기다린 후, in절에 부모 key를 넘겨주어 한 번의 쿼리로 연관(자식, Member) 엔티티를 조회하도록 한다. **select member where teamId in (1,2,3,...,n)으로 조회**)



#### 2) `query specified join fetching, but the owner of the fetched association was not present in the select list`

- 원인 : fetch join은 "엔티티티 상태에서" 엔티티 그래프를 참조하기 위해 사용하는거라서, 엔티티가 아닌 dto를 조회하는데 (select DTO from ~) fetch join 을 쓰면 문제가 발생한다.

- 해결법 : fetch join을 제거하고 그냥 join 사용하기


#### 3) `org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags`

- 의미 : bag 컬렉션이란? 순서가 없고 키가 없으며, 중복을 허용한다. Java 컬렉션에는 Bag가 구현되어 있지 않아 List를 사용한다.

- 원인 : 1:N 관계에서 쿼리가 동시에 2개 이상의 연관테이블에 fetch join을 사용할 때 발생 (`select t from Team t join fetch t.members join fetch t.rules`)

- 해결법 : application.yml에 default_batch_fetch_size 설정하기

```
spring:
  jpa:
    properties:
      hibernate.default_batch_fetch_size: 10
```


#### 정리 : 지연로딩을 사용하더라도 N+1 문제를 겪어 이를 해결하기 위해 fetch join을 사용하는데, xxToOne에서 fetch join 사용이 자유롭지만 xxToMany에서는 오류 뜨는 경우가 있으니 잘 사용하기