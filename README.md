## 2주차 - DB 설계, domain 작성하기

## 📌 인스타그램 기능 설명

DB 모델링을 하기 전에 인스타그램의 주요 기능을 먼저 알아보자!!

<img width="1293" alt="스크린샷 2024-09-20 오전 12 32 29" src="https://github.com/user-attachments/assets/67b380bb-23ce-420e-871c-cbf28e573905">

사용자는 사진과 함께 글을 작성하여 게시글을 올릴 수 있다. 이때, 사진은 여러장 올릴 수 있으며 최대 10장 까지 가능하다.

게시글에 좋아요, 댓글을 달 수 있고, 대댓글도 가능하다.

</br>


<img width="532" alt="스크린샷 2024-09-20 오전 12 37 51" src="https://github.com/user-attachments/assets/a358ec27-c786-4736-b995-708558080c5a">

각 사용자들은 1대 1 메시지를 주고 받을 수 있다.

</br>


<img width="724" alt="스크린샷 2024-09-20 오전 1 21 52" src="https://github.com/user-attachments/assets/3031ee35-3182-40f8-8746-71f80ea74573">

각 사용자는 팔로워와 팔로우를 가진다.

(팔로워: 나를 팔로우하는 사람, 팔로우: 내가 팔로우하는 사람)

</br>


### 📌 ERD

위의 기능을 바탕으로 ERD를 설계해봤다.

![instagram](https://github.com/user-attachments/assets/9affb881-463c-455c-a07a-18f8202488be)

- User (사용자)
    - 회원가입/로그인에 필요한 기본적인 정보(이름, 닉네임, 이메일, 비밀번호 등)를 가진다.
- Post (게시글)
    - 본문, 작성 일자를 가진다.
    - 게시글은 사용자에 종속되므로 사용자의 pk를 fk로 가진다. → 게시글의 작성자가 누군지 알 수 있다.
- Image (사진)
    - 이미지 url, 사진 순서 정보를 가진다.
    - 사진은 게시글에 종속되므로 게시글의 pk를 fk로 가진다. → 사진이 어느 게시글에 속한지 알 수 있다.
- PostLike (게시글 좋아요)
    - 사용자와 게시글에 종속되므로 사용자, 게시글 pk를 모두 fk로 가진다. → 누가 어떤 게시글에 좋아요를 눌렀는지 알 수 있다.
- Comment (댓글)
    - 댓글 본문, 작성 일자를 가진다.
    - 댓글은 작성자와 게시글에 종속되므로 작성자, 게시글의 pk를 fk로 가진다. → 누가 어떤 게시글에 댓글을 썼는지 알 수 있다.
    - 대댓글의 경우, 부모 댓글에 종속되므로 부모 댓글의 pk를 fk로 가진다. → 부모 댓글이 뭔지 알 수 있다.
- ChatRoom (채팅방)
    - 채팅방에 참여한 두 사용자에 의해 종속되므로, 두 사용자의 pk를 fk로 가진다. → 채팅방에 참여한 사람이 누군지 알 수 있다.
- Message (채팅방-메시지)
    - 메시지 본문, 작성 일자를 가진다.
    - 채팅방과 사용자에 종속되므로 채팅방, 사용자 pk를 모두 fk로 가진다. → 누가 어떤 채팅방에서 메시지를 보냈는지 알 수 있다.
- Follow (팔로우)
    - 팔로우, 팔로워 모두 사용자에 종속되므로 두 사용자의 pk를 fk로 가진다. → 누가 누구를 팔로우 했는지 알 수 있다.
 
</br>


---

## 📌 Domain 작성

</br>


repository를 구현하기 전에 먼저 domain을 작성했다.

### **🤷🏻‍♀️ 근데** Domain이 뭐에요?

JPA는 ORM을 지원하는 API인데, 자바 객체를 자동으로 DB 테이블에 매핑해주고 주로 SQL을 작성하지 않고도 데이터베이스와 상호작용할 수 있도록 도와주는 것이 **ORM(Object-Relational Mapping)** 이다.

테이블과 매핑하기 위해 데이터베이스 필드 값을 클래스 형태로 표현한 것을 **Entity(엔티티)** 라고 하고, 엔티티를 포함한 비지니스 로직을 표현하는 개념들을 정의한 전체 영역을 **domain(도메인)** 이라고 한다.

</br>


도메인에 작성한 User 엔티티를 살펴보자!

<img width="550" alt="스크린샷 2024-09-20 오전 1 08 24" src="https://github.com/user-attachments/assets/354cd5eb-2830-4393-84fd-924225c0dd26">

- @Entity 어노테이션을 사용하여 JPA에서 해당 클래스가 데이터베이스 테이블과 매핑되는 엔티티임을 나타낼 수 있다.
- @All/NoArgsConstructor 를 사용하여 생성자를 자동으로 생성해줬다.
- 엔티티는 기본키를 꼭 가져야하므로 @Id 를 통해 기본키를 지정해줬다.
- @GeneratedValue(strategy=GenerationType.IDENTITY) 는 기본키를 자동으로 생성할때 사용하는 전략을 정의하는 어노테이션이다.

</br>


### NoArgsConstructor(access=AccessLevel.PROTECTED) 를 사용한 이유!

일단 @NoArgsCostructor는 파라미터가 없는 기본 생성자를 자동으로 만들어주는 어노테이션이다.

```java
public class Student {

	private String name;
	private int age;
	
	public Student(){}     // 파라미터가 없는 기본 생성자
}
```

```java
@NoArgsConstructor
public class Student {

	private String name;
	private int age;
}
```

JPA는 데이터베이스에서 값을 읽어오거나, 새 데이터를 삽입할 때 엔티티 객체를 자동으로 만들어야 하는데 이때 기본 생성자를 통해 객체를 생성한다고 한다.

→ 따라서 @Entity 어노테이션을 사용했다면 @NoArgsConstructor도 꼭 같이 사용해주자!

</br>


이 어노테이션은 access 인자를 통해 접근 범위를 제어할 수 있다고 한다.

<img width="589" alt="스크린샷 2024-09-20 오전 2 14 27" src="https://github.com/user-attachments/assets/52f9499b-1d1c-4bd3-a1b0-47b7e33978e8">

AccessLevel의 기본 값이 PUBLIC 이지만, PROTECTED로 바꾸는 것이 좋다.

</br>


**💡 왜 PROTECTED가 더 좋을까?**

일단 접근 제어를 **PUBLIC** 으로 설정한다면 외부에서 해당 클래스의 기본 생성자를 호출할 수 있게 된다. 객체 생성에 대한 제어를 외부로 넘기게 되고, 엔티티 클래스를 무분별하게 인스턴스화 할 수 있는 문제점이 생긴다.

그럼 접근 제어를 **PRIVATE** 으로 설정하면 해결되는거 아닌가요? JPA는 리플렉션 이라는 기술로 기본 생성자를 호출해 객체를 생성하는데, 접근 제어가 private인 경우 생성자가 리플렉션을 사용할 수 없어 JPA가 객체를 생성하지 못 하게 된다.

접근 제어가 **PROTECTED** 인 생성자는 같은 패키지 내의 다른 클래스와 상속받은 클래스에서 호출할 수 있다. 따라서 JPA가 객체를 생성할 수 있게 하면서, 외부에서 직접적으로 호출하지 못 하도록 제한하여 클래스 설계를 더 안전하게 할 수 있다!!

</br>

---

## 📌 EntityManager로 Repository 구현

Repository를 어떻게 구현할 수 있을까? 

먼저 다음 두 개념에 대해서 알아보자.

</br>


**엔티티 매니저(Entity Manager)**

- 영속성 컨텍스트를 생성, 관리하며 엔티티를 저장, 수정, 삭제, 조회하는 등 엔티티와 관련된 모든 일을 처리하는 역할을 한다.
- 데이터베이스와 상호작용하는 핵심 요소이다.

  </br>
  

**영속성 컨텍스트(Persistence Context)**

- 애플리케이션과 데이터베이스 사이에서 엔티티 객체를 관리하는 공간이다.
- **쿼리를 생성해 DB로 전달하는 역할**을 한다.

</br>


이렇게 영속성 컨텍스트가 DB로 쿼리를 전달하는 역할을 하므로, DB에 전달하고 싶은 객체는 꼭 **영속성 컨텍스트에 의해 관리**되어야 하고, **엔티티 매니저가 엔티티를 영속화**(영속성 컨텍스트에 저장된 상태) 해줘야 한다.

→ 따라서 Repository를 구현하기 위해 객체를 DB에 저장하는 코드를 작성하고 싶다면, 먼저 엔티티 매니저를 사용하여 영속성 컨텍스트에 영속화 시키자.

</br>


다음과 같이 persist를 호출하면 객체를 영속화 시킬 수 있다.

```java
em.persist(user);    // user 영속화
```

</br>

persist를 하면 영속성 컨텍스트의 1차 캐시에 엔티티가 저장됨과 동시에 쓰기 지연 SQL 저장소에 DB에 날릴 쿼리가 저장된다.

이 쿼리는 트랜잭션 커밋 시점 또는 엔티티 매니저가 flush를 호출한 시점에 DB로 쿼리를 전송한다.

→ 따라서 우리는 영속화만 시켜주면 트랜잭션 커밋 시점에 자동으로 DB에 쿼리를 전송할 수 있다.

<img width="748" alt="스크린샷 2024-09-20 오후 2 34 54" src="https://github.com/user-attachments/assets/b0e96904-dfa5-4e9c-b6dc-d15c60b99743">

**그런데 모든 쿼리가 쓰기 지연 SQL 저장소에 저장되나요?**

아니요! 영속성 컨텍스트에 관리되는 엔티티의 상태가 변경될 때 생기는 SQL 쿼리를 저장합니다.

- persist()
    - INSERT 쿼리 : 영속성 컨텍스트에 새로 추가된 엔티티를 DB에 저장하기 위해 Insert 쿼리가 저장된다.
- merge()
    - UPDATE 쿼리 : 영속성 컨텍스트에 관리되는 엔티티의 상태가 변경되었을 때 update 쿼리가 저장된다.
- remove()
    - DELETE 쿼리 : 영속성 컨텍스트에서 엔티티를 제거할 때 delete 쿼리가 저장된다.
 
</br>


**그러면 다른 쿼리들은 어떻게 하나요?**

조회같은 쿼리의 경우 entityManager의 createQuery를 통해 쿼리를 작성할 수 있고, getResultList(), getSingleResult()와 같은 메서드를 호출하여 해당 쿼리를 즉시 실행할 수 있다.

</br>


⬇️ EntityManager를 사용해 PostRepository를 구현한 코드이다.

<img width="826" alt="스크린샷 2024-09-20 오후 12 35 05" src="https://github.com/user-attachments/assets/0bac111b-6539-45fd-b6ef-d16d67035b06">

- DB에 저장될 객체를 persist 해주었다
- user 기준으로 조회하는 쿼리를 createQuery를 사용하여 직접 작성해줬다.

</br>


---

## 📌 테스트 코드 작성

```java
@SpringBootTest
@Transactional
public class PostRepositoryTest {

    private final PostRepository postRepository;
    private final EntityManager em;

    @Autowired
    public PostRepositoryTest(PostRepository postRepository, EntityManager em) {
        this.postRepository = postRepository;
        this.em = em;
    }
    
		@Test
		@DisplayName("author로 게시글 조회 테스트")
		void findByAuthorTest() throws Exception {
		    // given
		    User user1 = User.builder()
		            .username("user1")
		            .nickname("user1")
		            .password("password")
		            .build();
		    em.persist(user1);
		
		    User user2 = User.builder()
		            .username("user2")
		            .nickname("user2")
		            .password("password")
		            .build();
		    em.persist(user2);
		
		    User user3 = User.builder()
		            .username("user3")
		            .nickname("user3")
		            .password("password")
		            .build();
		    em.persist(user3);
		
		    Post post1 = Post.builder()
		            .content("user1이 작성한 post1")
		            .author(user1)
		            .build();
		    em.persist(post1);
		
		    Post post2 = Post.builder()
		            .content("user1이 작성한 post2")
		            .author(user1)
		            .build();
		    em.persist(post2);
		
		    Post post3 = Post.builder()
		            .content("user2이 작성한 post3")
		            .author(user2)
		            .build();
		    em.persist(post3);
		
		    // when
		    List<Post> findPosts = postRepository.findByAuthor(user1);
		
		    // then
		    assertEquals(2, findPosts.size());
		
		    for (Post findPost : findPosts) {
		        System.out.println("content: " + findPost.getContent());
		    }
		}
}
```

author로 게시글 조회하는 findByAuthor 테스트 코드를 작성했다.

사용자 user1, 2, 3을 생성하고, user1이 post1,2를 user2가 post3을 작성하도록 해줬다.

→ findByAuthor에 user1을 넘겨줬을때 post1, 2를 잘 찾아오는지 확인해보자!

</br>


<img width="1246" alt="스크린샷 2024-09-20 오전 11 16 43" src="https://github.com/user-attachments/assets/4a846e94-1885-4b2e-a009-32ec3c604729">

두 게시글이 잘 조회되는 것을 확인할 수 있다!

</br>


### 🚨 test code 작성하며 만난 오류

```
 org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.ceos20.instagram.user.domain.User
```

처음에는 em.persist() 코드를 작성해주지 않아 위와 같은 오류가 발생했다.

→ **영속되지 않은 (transient) 객체가 다른 엔티티와의 관계에서 참조 되었을 때** 발생한 에러이다.

</br>


user를 영속성 컨텍스트에 저장하지 않고, post 생성시 author 필드에 참조해서 발생한 에러라고 생각했다.

→ **em.persist(user)** 를 통해 객체를 영속성 컨텍스트에 저장하여 에러를 해결했다.

</br>


**🚨 의문!!**

**커밋 시점 또는 em.flush()를 호출했을 때 DB에 쿼리가 나가는 것으로 알고 있는데,** 

**나는 em.persist()만 했을 뿐인데 어떻게 DB에 잘 저장되어 조회가 되는 걸까?**

</br>


→ JPA는 쿼리를 실행하기 전에 영속성 컨텍스트의 상태와 데이터 베이스를 동기화 하려고 한다.

따라서 findByAuthor()와 같은 조회 쿼리를 실행하기 전에 자동으로 flush가 호출되어, 영속성 컨텍스트에 있던 변경 사항이 데이터 베이스에 잘 반영되는 것이다.

(아하! 그래서 아까 엔티티 매니저를 통해 조회 쿼리를 생성한건가? 엔티티 매니저를 사용하면 영속성 컨텍스트에 의해 관리되니깐!)

</br>


---

## 💡 JPA 관련 궁금증 해결

### 1. 어떻게 data jpa는 interface 만으로도 함수 구현이 되는가?

</br>


**Spring Data JPA**

Spring Data JPA는 JPA를 더 쉽게 사용할 수 있도록 하는 spring framework의 모듈이다.

기존 jpa로 데이터베이스와 상호작용 하려면 개발자가 직접 복잡한 쿼리문을 작성해야 하는데, Spring Data Jpa는 이런 복잡한 과정을 추상화하여 더 간단하게 데이터 베이스와 상호작용할 수 있도록 해준다.

</br>

<img width="849" alt="스크린샷 2024-09-21 오후 4 01 46" src="https://github.com/user-attachments/assets/df8801e6-5119-437c-a641-0f6a26e4157d">

기존에는 캐릭터 이름 기준으로 캐릭터 리스트를 반환하려면 내가 직접 쿼리 문을 작성해서 데이터베이스와 상호작용 했는데

</br>

<img width="645" alt="스크린샷 2024-09-21 오후 4 03 11" src="https://github.com/user-attachments/assets/0efb5b3c-e104-4340-b6c8-ff487c5836a9">

JpaRepository를 상속 받으면 별도로 구현할 필요 없이 메서드 이름에 따라 쿼리가 자동으로 생성되어 데이터베이스에서 데이터를 조회할 수 있다.

JpaRepository는 제네릭 인터페이스이기 때문에, 엔티티 클래스와 id 타입을 명시해줘야 한다.

- Post: 데이터 베이스와 매핑되는 클래스
- Long: Post 엔티티의 id 타입

</br>
  

→ 여기서 PostRepositoy를 interface로 선언했는데..어떻게 interface 만으로 함수 구현이 가능할까??

**바로 프록시(Proxy) 덕분이다!**

### 프록시(Proxy)

<img width="466" alt="스크린샷 2024-09-21 오후 4 37 48" src="https://github.com/user-attachments/assets/8a94958b-f44f-462c-94e8-ff6097cbb85b">

CharacterRepository를 출력해보면 proxy라고 출력 됨을 확인할 수 있다.

Spring Data Jpa는 JpaRepository를 상속받는 인터페이스를 만나면, 이 인터페이스의 구현체를 자동으로 생성하고 빈으로 등록해 의존성 주입을 해준다. 

구현체를 자동으로 생성할 때는 SimpleJpaRepository를 사용한다 → 이 구현체는 CharacterRepository 의 메서드를 호출할 때 내부적으로 SimpleJpaRepository의 메서드를 호출하는 형태로 동작한다.

</br>


```java
@Repository
@Transactional(readOnly = true)
public class SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation<T, ID> {
	
	// ...
	
	public SimpleJpaRepository(...., EntityManager entityManager) {

		// ...
		this.entityManager = entityManager;
	}
	
	@Override
	@Transactional
	public <S extends T> S save(S entity) {

		Assert.notNull(entity, "Entity must not be null");

		if (entityInformation.isNew(entity)) {
			entityManager.persist(entity);
			return entity;
		} else {
			return entityManager.merge(entity);
		}
	}
}
```

SimpleJpaRepository는 생성자 주입을 통해 EntityManager를 주입받는다.

save와 같은 내부 동작 호출 시에 주입받은 EntityManager를 사용하여 동작하는 것을 볼 수 있다.

</br>


**프록시 동작 정리**

- 애플리케이션을 실행하면, Spring이 인터페이스를 스캔한다.
- 인터페이스에 정의된 메서드 이름을 분석해, 적절한 JPA 쿼리를 생성하는 코드를 구현한다.
- 프록시 객체가 생성되고, 이 프록시 객체가 Repository 인터페이스를 구현한다.
- 애플리케이션에서 Repository 인터페이스의 메서드를 호출하면 프록시 객체가 대신 JPA 동작을 수행한다.

</br>


🚨 **근데 여기서 드는 의문점은 SimpleJpaRepository는 생성자를 통해 entity manager를 주입받는다.** 

**하지만 싱클톤 객체는 한번만 할당을 받는데.. 한번 연결할 때 마다 생성되는 entity manager를 생성자 주입으로 받아도 되는 걸까?**

**그럼 트랜잭션이 달라져도 계속 똑같은 entity manager를 사용하는게 아닐까?**

</br>


### 2. EntityManager는 사실 프록시 객체이다!

</br>


EntityManager는 기본적으로 프록시 객체로 제공된다.

이 프록시 객체는 실제 EntityManager 인스턴스의 메서드 호출을 가로채고, 실행 중인 트랜잭션과 관련된 EntityManager의 인스턴스와 연결된다.

</br>


따라서 SimpleJpaRepository는 프록시 EntityManager를 주입받고, 이 프록시 객체는 특정 트랜잭션이 시작될 때마다 해당 트랜잭션에 맞는 실제 EntityManager 인스턴스에 접근한다.

→ 따라서 SimpleJpaRepository에 EntityManager를 한번만 할당해도 되는 이유이다.

</br>


### 3. fetch join 할 때 distinct를 안하면 생길 수 있는 문제

</br>


**근데 일단 fetch join을 어떤 경우에 사용하는 것일까?**

**📌 N+1 문제**

<img width="704" alt="스크린샷 2024-09-21 오후 5 37 30" src="https://github.com/user-attachments/assets/edc0305c-03ce-4697-adba-4c99e87817d5">

(Order의 참조된 필드는 LAZY Loading으로 설정됨)

</br>


<img width="707" alt="스크린샷 2024-09-21 오후 5 37 50" src="https://github.com/user-attachments/assets/7f9b7a40-1367-45a7-8d99-4af1c99e28d2">

위와 같은 코드가 있다고 해보자.

findAllByString으로 Order를 조회해오면 **조회 쿼리가 1번** 발생한다.

조회된 Order의 필드를 파싱해서 SimpleOrderDto에 넣어주는 코드인데, SimpleOrderDto를 보면 Member와 Delivery를 참조하는 것을 볼 수 있다.

하나의 order를 조회할 때, 연관된 member, delivery도 각각 1번씩 또 조회 쿼리를 날려줘야 한다.

따라서 N개의 order가 있다면 **1(order 조회) + N(각 order의 member 조회) + N(각 order의 delivery 조회) 번의 쿼리**가 나가게 된다.

→ order 테이블 조회 시, member, delivery 테이블도 조회

→ 쿼리를 너무 많이 보내는거 같은데요…최적화하고 싶다!!

</br>


**이를 최적화 하기 위해 fetch-join을 사용한다!**

**📌 fetch-join**

연관된 엔티티까지 함께 조회할 수 있도록 하는 쿼리 방법이다. 

<img width="525" alt="스크린샷 2024-09-21 오후 5 53 00" src="https://github.com/user-attachments/assets/ea99392b-cdad-4398-b53f-9b7f654ed3a0">


사진에서 볼 수 있듯이 

팀A를 조회할 때 **팀A 조회(쿼리 1번) + 팀A에 속한 멤버 조회(쿼리 2번)** 이렇게 1+N 쿼리가 나갈 수도 있는데

fetch-join을 사용하면 팀A에 연관된 멤버를 같은 테이블로 join 하는 것을 확인할 수 있다.

join된 테이블을 조회하므로 딱 한번의 쿼리를 날려 팀A와 소속된 멤버까지 조회할 수 있다.

</br>


위의 Order 예시를 fetch-join으로 수정해보면

<img width="702" alt="스크린샷 2024-09-21 오후 5 48 30" src="https://github.com/user-attachments/assets/de97da02-d2c7-4385-8457-da5f9de93ee5">

order를 조회할 때 한 번의 query로 member, delivery를 가져와 select 절에 넣어 한 번에 조회할 것이다.

</br>


실제로 쿼리문을 출력해보면

<img width="306" alt="스크린샷 2024-09-21 오후 5 51 55" src="https://github.com/user-attachments/assets/e1cf2934-fd35-4bf3-b4ab-8ad2df85ac6c">

한번의 쿼리로 모두 조회된 것을 확인할 수 있다!!

</br>


**하지만 fetch-join을 사용할 때는 주의할 점이 있다!**

<img width="498" alt="스크린샷 2024-09-21 오후 6 02 30" src="https://github.com/user-attachments/assets/0bc9379a-2b8c-401b-914e-7dda194799f5">

만약 Order가 OrderItem이라는 엔티티를 참조하고 있다고 해보자. order와 orderItem에는 위와 같이 데이터가 들어가있다.

orderId가 4인 order에 orderItem 6, 7이 있다. orderId 4를 조회하려면 1 + 2 번의 쿼리가 발생하여 fetch-join을 통해 쿼리를 최적화 할 것이다.

<img width="855" alt="스크린샷 2024-09-21 오후 6 04 47" src="https://github.com/user-attachments/assets/a7cbec7a-742c-4d48-8f27-a6e86630f53d">

join된 테이블을 보면 fetch-join의 문제점을 알 수 있는데

orderId가 같은 row가 연관된 orderItem 만큼 생성된다는 문제이다.→ 같은 order의 엔티티 조회수가 증가해버린다!

</br>


<img width="702" alt="스크린샷 2024-09-21 오후 6 08 12" src="https://github.com/user-attachments/assets/ca22bd35-aafa-4df3-8f0f-e90bb6ce0907">

이를 해결하기 위해 **distinct** 키워드를 사용한다.

distinct 키워드를 사용하면 fetch-join시 발생하는 중복 조회를 막아준다. DB에서 반환되는 결과로 Order는 중복 없이 1개만 존재하게 되고, Order에 연관된 OrderItem을 모두 포함하여 반환된다.

→ 이는 DB에서 실제로 row를 합쳐주는 것이 아니고 애플리케이션 레벨에서 중복 제거를 해준다고 한다. (JPA는 결과를 수신한 후, 중복된 Order를 필터링해 최종 결과를 반환함)

----
## 3주차 - service 작성하기

## 💡 toEntity(), fromEntity() 를 사용한 이유!

DTO에서 `toEntity()` 메서드를 사용하는 것은 서비스 클래스에서 불필요한 엔티티 생성 로직을 DTO 클래스로 이동시켜 코드의 역할을 분리하고 가독성을 높일 수 있다.

→ 서비스 클래스는 비즈니스 로직에만 집중할 수 있다!!

<br/>

```java
 @Transactional
  public void saveUser(SaveUserRequest saveUserRequest) {

      User user = User.builder()
              .username(saveUserRequest.getUsername())
              .nickname(saveUserRequest.getNickname())
              .password(saveUserRequest.getPassword())
              .email(saveUserRequest.getEmail())
              .build();
      userRepository.save(user);
  }
```

원래 서비스 층에서 builder를 사용하여 객체를 만들었는데.. 서비스 코드가 너무 복잡해 보기 싫었다.

<br/>

```java
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserRequest {

    private String username;
    private String nickname;
    private String password;
    private String email;

    public User toEntity() {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }
}
```

```java
@Transactional
public void saveUser(SaveUserRequest saveUserRequest) {

    User user = saveUserRequest.toEntity();
    userRepository.save(user);
}
```

SaveUserRequest DTO 내에 toEntity()라는 메서드를 통해 dto를 만들어 리턴해주었다. 서비스 코드에서는 toEntity() 메서드만 호출하면 바로 dto를 받을 수 있어 코드가 깔끔해진다.

<br/>

---

## 💡 Transactional(readOnly = true)를 사용한 이유!


### Transactional은 어떤 경우에 사용하는걸까?

```java
public void saveParent() {
    saveMom();
    saveDad();
}

private void saveMom() {
    Member mom = Member.builder()
        .name("엄마")
        .build();
    
    memberRepository.save(mom);
}

private void saveDad() {
    Member dad = Member.builder()
        .name("아빠")
        .build();
    
    memberRepository.save(dad);
}
```

부모님 정보를 저장하는 saveParent()라는 메서드가 있다고 해보자.

saveMom() 을 먼저 호출하고 후에 saveDad()를 호출한다.

만약 saveDad() 메서드에 오류가 발생하더라도 mom 데이터는 이미 DB에 저장되어있어 데이터의 일관성이 깨질 수 있다.

우리는 mom, dad 중 하나라도 에러가 나면 진행되었던 작업을 롤백 해주고, 성공하면 모두 저장해주고 싶다.

→ 이렇게 여러 작업을 하나의 작업으로 묶고 싶을 때 **transaction** 을 사용하면 된다. 

<br/>

```java
@Transactional
public void saveParent() {
    saveMom();
    saveDad();
}
```

이렇게 @Transactional 어노테이션을 써주면 saveMom()과 saveDad()를 하나의 작업으로 묶어줄 수 있다!

<br/>

### 💡 Transactional(readOnly = true)의 장점

Transactional 어노테이션에 readOnly 옵션을 설정할 수 있다. 

이는 JPA 영속성 컨텍스트가 수행하는 **변경감지(Dirty Checking)** 와 관련이 있다.

<br/>

<img width="723" alt="스크린샷 2024-09-28 오후 8 04 12" src="https://github.com/user-attachments/assets/c02000a0-c1d9-41d0-b9f7-6c31b99c7e9d">

JPA는 영속성 컨텍스트에 엔티티를 보관할 때 최초 엔티티 상태를 저장하고 있다. → 이를 snapshot이라고 한다.

트랜잭션이 커밋될때, 영속성 컨텍스트에 저장된 엔티티와 스냅샷을 비교하여 변경 사항을 확인하고, 만약 변경사항이 있다면 **UPDATE 쿼리**를 쓰기 지연 SQL 저장소에 저장한다. 

<br/>

**근데 만약 Transactional(readOnly = true)를 설정해주면**

JPA가 해당 트랜잭션이 읽기 전용임을 인지하고, 트랜잭션 커밋 시에 자동으로 flush()를 날리지 않는다.

만약, 엔티티의 상태가 변한다해도 Dirty Checking을 하지 않는다.

→ 따라서 조회용으로 가져온 엔티티의 예상치 못한 수정을 방지할 수 있다.

PostService 코드를 한번 보자

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

		// ...

    // 게시글 생성
    @Transactional
    public void createPost(CreatePostRequest createPostRequest, User user) {

        // 글 저장
        Post post = Post.toEntity(createPostRequest, user);
        postRepository.save(post);

        // 이미지 저장
        List<Image> images = createPostRequest.getImages().stream()
                .map(image -> Image.toEntity(post, image))
                .collect(Collectors.toList());
        imageRepository.saveAll(images);
    }
}
```

기본적으로 Transactional(readOnly = true)로 설정해주었고, 단순 조회가 아닌 경우에는 메서드 위에 @Transactional 어노테이션을 붙였다.

이 코드에서도 createPost()라는 메서드 안에서 save(Post)와 saveAll(images)로 두개의 작업을 하는데 둘 중 하나라도 에러가 나면 묶어서 롤백해야하기 때문에 트랜잭션이 꼭 필요하다.

<br/>

---

## 💡 N + 1 문제와 fetch-join 직접 해보기

**전체 post와 각 post의 author를 출력하는 테스트 코드를 작성해봤다.**

```java
@Test
@DisplayName("N+1 문제")
void findPostLazy() {
    // given
    User user1 = User.builder()
            .username("user1")
            .nickname("user1")
            .password("password")
            .email("user1@naver.com")
            .build();
    em.persist(user1);

    User user2 = User.builder()
            .username("user2")
            .nickname("user2")
            .password("password")
            .email("user2@naver.com")
            .build();
    em.persist(user2);

    Post post1 = Post.builder()
            .author(user1)
            .content("post1")
            .build();
    em.persist(post1);

    Post post2 = Post.builder()
            .author(user2)
            .content("post2")
            .build();
    em.persist(post2);

    em.flush();
    em.clear();

    // when
    System.out.println("=======================query start===========================");
    List<Post> posts = postRepository.findAll();

    // then
    for (Post post : posts) {
        System.out.println("post = " + post.getContent());
        System.out.println("post.getAuthor().getClass() = " + post.getAuthor().getClass());
        System.out.println("post.getAuthor().getUsername() = " + post.getAuthor().getUsername());
    }
}
```

<br/>

### 1) 즉시로딩(EAGER)인 경우

```java
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public void updateContent(String content) {
        this.content = content;
    }
}
```

Post 엔티티에서 author와 ManyToOne 관계를 가지고, 기본 fetch type인 **즉시로딩(EAGER)** 로 설정해주었다.

<img width="710" alt="스크린샷 2024-09-28 오후 12 37 55" src="https://github.com/user-attachments/assets/0db7c6fd-d445-42f1-a908-298862d4fbc6">

<img width="698" alt="스크린샷 2024-09-28 오후 12 40 06" src="https://github.com/user-attachments/assets/ef42cd6d-6d2e-4c1a-a40a-dbd249b5cee5">

테스트 코드를 돌려보면 post를 조회할때 author(user)도 함께 조회하는 것을 확인할 수 있다.

<br/>

🚨 **하지만 나는 post만 조회하고 싶었는데 user 조회 쿼리도 추가로 나가면…손해 아닌가요?**

**만약 post가 50개 있다면, 전체 post를 조회하는 쿼리 1번 + 각 post의 author를 조회하는 쿼리 50번이 추가로 나갈 것이다 (N + 1 문제)**

**→ Post를 조회할때 user를 조회하지 않으면 되는거 아닌가요? 즉, LAZY 로딩을 하면 되는거 아닌가요?**

<br/>

### 2) 지연로딩(LAZY)인 경우

이번엔 지연로딩으로 설정하고 다시 확인해보자

```java
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    public void updateContent(String content) {
        this.content = content;
    }
}
```

일단 Post 엔티티를 보면 author와 ManyToOne 관계를 가지고 있고, 지연로딩(LAZY)임을 확인 할 수 있다.

<br/>

<img width="683" alt="스크린샷 2024-09-28 오전 9 33 05" src="https://github.com/user-attachments/assets/039f2f42-e4ef-42b5-a657-c31b0f5668da">

`postRepository.findAll()` 부분이다.

전체 post를 불러오는 쿼리 1번이 호출된 것을 확인할 수 있다.

<br/>

<img width="809" alt="스크린샷 2024-09-28 오전 9 33 30" src="https://github.com/user-attachments/assets/09c5106e-dc45-459d-9105-f9de7116a537">

post의 author가 지연로딩으로 설정되어 있어 `getAuthor()`를 했을 때는 proxy 객체가 들어가고,

`getAuthor().getUsername()`으로 author를 실제 사용할 때 쿼리가 나가는 것을 확인할 수 있다.

→ 첫번째 post에 대한 author를 조회하는 쿼리가 1번 나갔다.

<br/>

<img width="819" alt="스크린샷 2024-09-28 오전 9 33 56" src="https://github.com/user-attachments/assets/8d5d0386-1cc0-4fcb-a70e-36a319e20242">

→ 두번째 post에 대한 author를 조회하는 쿼리가 1번 나갔다.

<br/>

🚨 **N번의 쿼리가 언제 나가냐에 차이지 지연로딩에도 N + 1 문제가 발생 함을 볼 수 있다. 그럼 어떻게 해결해야 하나요?? → SQL상에서 join이 나가도록 하면 된다.**

<br/>

### 3) fetch-join 적용

**PostRepository**

```java
@Query("SELECT p FROM Post p LEFT JOIN fetch p.author")
List<Post> findAllPost();
```

left join을 통해 post와 user를 join 해줬다.

<br/>

<img width="535" alt="스크린샷 2024-09-28 오후 1 41 23" src="https://github.com/user-attachments/assets/483a3c08-c5fa-4bb0-abec-7a04af7ada5a">

이렇게 한번의 쿼리로 post, user 모두 조회할 수 있다. → N + 1 문제 해결

<br/>

## 🚨 Service 계층 Test Code 작성하기

FollowService의 테스트 코드를 보자

```java
@SpringBootTest
public class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;
    @InjectMocks
    private FollowService followService;
    
    // ...
    
 }
```

일단 우리는 Service 계층의 **단위 테스트 코드** 를 작성할 것이다.

따라서 service에서 사용하는 repository는 mock을 통해 모의 객체로 생성해줘야 한다.

- `@Mock` : @Mock으로 선언된 필드는 Mockito가 해당 클래스의 모의 객체를 생성하여 주입한다.
    - 모의 객체이므로 해당 클래스의 예상 동작을 우리가 직접 작성해줘야 한다.
- `@InjectMocks` : followService는 @InjectMocks 어노테이션을 통해 모의 객체들을 주입받을 수 있다.

<br/>

```java
 		private User user;
    private User follower1;
    private User follower2;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .nickname("user")
                .password("password")
                .build();

        follower1 = User.builder()
                .id(2L)
                .nickname("follower1")
                .password("password")
                .build();

        follower2 = User.builder()
                .id(3L)
                .nickname("follower2")
                .password("password")
                .build();
    }
```

- 테스트 코드 시작 전 객체들을 준비해줬다. (내가 id도 직접 정해주면 된다)

  <br/>

```java
    @Test
    @DisplayName("나를 팔로우하는 사람들 반환 테스트")
    void getFollowerTest() {
        // given
        List<User> followers = Arrays.asList(follower1, follower2);
        when(followRepository.findByToUser(user)).thenReturn(followers);

        // when
        List<GetFollowerResponse> responses = followService.getFollower(user);

        // then
        assertEquals(2, responses.size());
        assertEquals(follower1.getId(), responses.get(0).getUserId());
        assertEquals(follower2.getId(), responses.get(1).getUserId());
    }
```

- `when(followRepository.findByToUser(user)).thenReturn(followers)` 을 통해 모의 객체인 followRepository의 예상 동작을 정의한다.
    - 'followRepository의 findByToUser메서드의 매개변수로 user가 넘어오면, followers를 반환해라' 라는 의미이다.
