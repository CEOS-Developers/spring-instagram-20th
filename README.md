## 2주차 - DB 설계, domain 작성하기

## 📌 인스타그램 기능 설명

DB 모델링을 하기 전에 인스타그램의 주요 기능을 먼저 알아보자!!

<img width="1293" alt="스크린샷 2024-09-20 오전 12 32 29" src="https://github.com/member-attachments/assets/67b380bb-23ce-420e-871c-cbf28e573905">

사용자는 사진과 함께 글을 작성하여 게시글을 올릴 수 있다. 이때, 사진은 여러장 올릴 수 있으며 최대 10장 까지 가능하다.

게시글에 좋아요, 댓글을 달 수 있고, 대댓글도 가능하다.

</br>


<img width="532" alt="스크린샷 2024-09-20 오전 12 37 51" src="https://github.com/member-attachments/assets/a358ec27-c786-4736-b995-708558080c5a">

각 사용자들은 1대 1 메시지를 주고 받을 수 있다.

</br>


<img width="724" alt="스크린샷 2024-09-20 오전 1 21 52" src="https://github.com/member-attachments/assets/3031ee35-3182-40f8-8746-71f80ea74573">

각 사용자는 팔로워와 팔로우를 가진다.

(팔로워: 나를 팔로우하는 사람, 팔로우: 내가 팔로우하는 사람)

</br>


### 📌 ERD

위의 기능을 바탕으로 ERD를 설계해봤다.

![instagram](https://github.com/member-attachments/assets/9affb881-463c-455c-a07a-18f8202488be)

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

<img width="550" alt="스크린샷 2024-09-20 오전 1 08 24" src="https://github.com/member-attachments/assets/354cd5eb-2830-4393-84fd-924225c0dd26">

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

<img width="589" alt="스크린샷 2024-09-20 오전 2 14 27" src="https://github.com/member-attachments/assets/52f9499b-1d1c-4bd3-a1b0-47b7e33978e8">

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
em.persist(member);    // member 영속화
```

</br>

persist를 하면 영속성 컨텍스트의 1차 캐시에 엔티티가 저장됨과 동시에 쓰기 지연 SQL 저장소에 DB에 날릴 쿼리가 저장된다.

이 쿼리는 트랜잭션 커밋 시점 또는 엔티티 매니저가 flush를 호출한 시점에 DB로 쿼리를 전송한다.

→ 따라서 우리는 영속화만 시켜주면 트랜잭션 커밋 시점에 자동으로 DB에 쿼리를 전송할 수 있다.

<img width="748" alt="스크린샷 2024-09-20 오후 2 34 54" src="https://github.com/member-attachments/assets/b0e96904-dfa5-4e9c-b6dc-d15c60b99743">

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

<img width="826" alt="스크린샷 2024-09-20 오후 12 35 05" src="https://github.com/member-attachments/assets/0bac111b-6539-45fd-b6ef-d16d67035b06">

- DB에 저장될 객체를 persist 해주었다
- member 기준으로 조회하는 쿼리를 createQuery를 사용하여 직접 작성해줬다.

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
		    User member1 = User.builder()
		            .username("member1")
		            .nickname("member1")
		            .password("password")
		            .build();
		    em.persist(member1);
		
		    User member2 = User.builder()
		            .username("member2")
		            .nickname("member2")
		            .password("password")
		            .build();
		    em.persist(member2);
		
		    User member3 = User.builder()
		            .username("member3")
		            .nickname("member3")
		            .password("password")
		            .build();
		    em.persist(member3);
		
		    Post post1 = Post.builder()
		            .content("user1이 작성한 post1")
		            .author(member1)
		            .build();
		    em.persist(post1);
		
		    Post post2 = Post.builder()
		            .content("user1이 작성한 post2")
		            .author(member1)
		            .build();
		    em.persist(post2);
		
		    Post post3 = Post.builder()
		            .content("user2이 작성한 post3")
		            .author(member2)
		            .build();
		    em.persist(post3);
		
		    // when
		    List<Post> findPosts = postRepository.findByAuthor(member1);
		
		    // then
		    assertEquals(2, findPosts.size());
		
		    for (Post findPost : findPosts) {
		        System.out.println("content: " + findPost.getContent());
		    }
		}
}
```

author로 게시글 조회하는 findByAuthor 테스트 코드를 작성했다.

사용자 member1, 2, 3을 생성하고, user1이 post1,2를 user2가 post3을 작성하도록 해줬다.

→ findByAuthor에 user1을 넘겨줬을때 post1, 2를 잘 찾아오는지 확인해보자!

</br>


<img width="1246" alt="스크린샷 2024-09-20 오전 11 16 43" src="https://github.com/member-attachments/assets/4a846e94-1885-4b2e-a009-32ec3c604729">

두 게시글이 잘 조회되는 것을 확인할 수 있다!

</br>


### 🚨 test code 작성하며 만난 오류

```
 org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.ceos20.instagram.member.domain.Memberer
```

처음에는 em.persist() 코드를 작성해주지 않아 위와 같은 오류가 발생했다.

→ **영속되지 않은 (transient) 객체가 다른 엔티티와의 관계에서 참조 되었을 때** 발생한 에러이다.

</br>


user를 영속성 컨텍스트에 저장하지 않고, post 생성시 author 필드에 참조해서 발생한 에러라고 생각했다.

→ **em.persist(member)** 를 통해 객체를 영속성 컨텍스트에 저장하여 에러를 해결했다.

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

<img width="849" alt="스크린샷 2024-09-21 오후 4 01 46" src="https://github.com/member-attachments/assets/df8801e6-5119-437c-a641-0f6a26e4157d">

기존에는 캐릭터 이름 기준으로 캐릭터 리스트를 반환하려면 내가 직접 쿼리 문을 작성해서 데이터베이스와 상호작용 했는데

</br>

<img width="645" alt="스크린샷 2024-09-21 오후 4 03 11" src="https://github.com/member-attachments/assets/0efb5b3c-e104-4340-b6c8-ff487c5836a9">

JpaRepository를 상속 받으면 별도로 구현할 필요 없이 메서드 이름에 따라 쿼리가 자동으로 생성되어 데이터베이스에서 데이터를 조회할 수 있다.

JpaRepository는 제네릭 인터페이스이기 때문에, 엔티티 클래스와 id 타입을 명시해줘야 한다.

- Post: 데이터 베이스와 매핑되는 클래스
- Long: Post 엔티티의 id 타입

</br>
  

→ 여기서 PostRepositoy를 interface로 선언했는데..어떻게 interface 만으로 함수 구현이 가능할까??

**바로 프록시(Proxy) 덕분이다!**

### 프록시(Proxy)

<img width="466" alt="스크린샷 2024-09-21 오후 4 37 48" src="https://github.com/member-attachments/assets/8a94958b-f44f-462c-94e8-ff6097cbb85b">

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

<img width="704" alt="스크린샷 2024-09-21 오후 5 37 30" src="https://github.com/member-attachments/assets/edc0305c-03ce-4697-adba-4c99e87817d5">

(Order의 참조된 필드는 LAZY Loading으로 설정됨)

</br>


<img width="707" alt="스크린샷 2024-09-21 오후 5 37 50" src="https://github.com/member-attachments/assets/7f9b7a40-1367-45a7-8d99-4af1c99e28d2">

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

<img width="525" alt="스크린샷 2024-09-21 오후 5 53 00" src="https://github.com/member-attachments/assets/ea99392b-cdad-4398-b53f-9b7f654ed3a0">


사진에서 볼 수 있듯이 

팀A를 조회할 때 **팀A 조회(쿼리 1번) + 팀A에 속한 멤버 조회(쿼리 2번)** 이렇게 1+N 쿼리가 나갈 수도 있는데

fetch-join을 사용하면 팀A에 연관된 멤버를 같은 테이블로 join 하는 것을 확인할 수 있다.

join된 테이블을 조회하므로 딱 한번의 쿼리를 날려 팀A와 소속된 멤버까지 조회할 수 있다.

</br>


위의 Order 예시를 fetch-join으로 수정해보면

<img width="702" alt="스크린샷 2024-09-21 오후 5 48 30" src="https://github.com/member-attachments/assets/de97da02-d2c7-4385-8457-da5f9de93ee5">

order를 조회할 때 한 번의 query로 member, delivery를 가져와 select 절에 넣어 한 번에 조회할 것이다.

</br>


실제로 쿼리문을 출력해보면

<img width="306" alt="스크린샷 2024-09-21 오후 5 51 55" src="https://github.com/member-attachments/assets/e1cf2934-fd35-4bf3-b4ab-8ad2df85ac6c">

한번의 쿼리로 모두 조회된 것을 확인할 수 있다!!

</br>


**하지만 fetch-join을 사용할 때는 주의할 점이 있다!**

<img width="498" alt="스크린샷 2024-09-21 오후 6 02 30" src="https://github.com/member-attachments/assets/0bc9379a-2b8c-401b-914e-7dda194799f5">

만약 Order가 OrderItem이라는 엔티티를 참조하고 있다고 해보자. order와 orderItem에는 위와 같이 데이터가 들어가있다.

orderId가 4인 order에 orderItem 6, 7이 있다. orderId 4를 조회하려면 1 + 2 번의 쿼리가 발생하여 fetch-join을 통해 쿼리를 최적화 할 것이다.

<img width="855" alt="스크린샷 2024-09-21 오후 6 04 47" src="https://github.com/member-attachments/assets/a7cbec7a-742c-4d48-8f27-a6e86630f53d">

join된 테이블을 보면 fetch-join의 문제점을 알 수 있는데

orderId가 같은 row가 연관된 orderItem 만큼 생성된다는 문제이다.→ 같은 order의 엔티티 조회수가 증가해버린다!

</br>


<img width="702" alt="스크린샷 2024-09-21 오후 6 08 12" src="https://github.com/member-attachments/assets/ca22bd35-aafa-4df3-8f0f-e90bb6ce0907">

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
  public void saveUser(SaveUserRequest saveMemberRequest) {

      User member = User.builder()
              .username(saveMemberRequest.getUsername())
              .nickname(saveMemberRequest.getNickname())
              .password(saveMemberRequest.getPassword())
              .email(saveMemberRequest.getEmail())
              .build();
      memberRepository.save(member);
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
public void saveUser(SaveUserRequest saveMemberRequest) {

    User member = saveMemberRequest.toEntity();
    memberRepository.save(member);
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

<img width="723" alt="스크린샷 2024-09-28 오후 8 04 12" src="https://github.com/member-attachments/assets/c02000a0-c1d9-41d0-b9f7-6c31b99c7e9d">

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
    public void createPost(CreatePostRequest createPostRequest, User member) {

        // 글 저장
        Post post = Post.toEntity(createPostRequest, member);
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
    User member1 = User.builder()
            .username("member1")
            .nickname("member1")
            .password("password")
            .email("member1@naver.com")
            .build();
    em.persist(member1);

    User member2 = User.builder()
            .username("member2")
            .nickname("member2")
            .password("password")
            .email("member2@naver.com")
            .build();
    em.persist(member2);

    Post post1 = Post.builder()
            .author(member1)
            .content("post1")
            .build();
    em.persist(post1);

    Post post2 = Post.builder()
            .author(member2)
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

<img width="710" alt="스크린샷 2024-09-28 오후 12 37 55" src="https://github.com/member-attachments/assets/0db7c6fd-d445-42f1-a908-298862d4fbc6">

<img width="698" alt="스크린샷 2024-09-28 오후 12 40 06" src="https://github.com/member-attachments/assets/ef42cd6d-6d2e-4c1a-a40a-dbd249b5cee5">

테스트 코드를 돌려보면 post를 조회할때 author(member)도 함께 조회하는 것을 확인할 수 있다.

<br/>

🚨 **하지만 나는 post만 조회하고 싶었는데 member 조회 쿼리도 추가로 나가면…손해 아닌가요?**

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

<img width="683" alt="스크린샷 2024-09-28 오전 9 33 05" src="https://github.com/member-attachments/assets/039f2f42-e4ef-42b5-a657-c31b0f5668da">

`postRepository.findAll()` 부분이다.

전체 post를 불러오는 쿼리 1번이 호출된 것을 확인할 수 있다.

<br/>

<img width="809" alt="스크린샷 2024-09-28 오전 9 33 30" src="https://github.com/member-attachments/assets/09c5106e-dc45-459d-9105-f9de7116a537">

post의 author가 지연로딩으로 설정되어 있어 `getAuthor()`를 했을 때는 proxy 객체가 들어가고,

`getAuthor().getUsername()`으로 author를 실제 사용할 때 쿼리가 나가는 것을 확인할 수 있다.

→ 첫번째 post에 대한 author를 조회하는 쿼리가 1번 나갔다.

<br/>

<img width="819" alt="스크린샷 2024-09-28 오전 9 33 56" src="https://github.com/member-attachments/assets/8d5d0386-1cc0-4fcb-a70e-36a319e20242">

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

<img width="535" alt="스크린샷 2024-09-28 오후 1 41 23" src="https://github.com/member-attachments/assets/483a3c08-c5fa-4bb0-abec-7a04af7ada5a">

이렇게 한번의 쿼리로 post, member 모두 조회할 수 있다. → N + 1 문제 해결

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
 		private User member;
    private User follower1;
    private User follower2;

    @BeforeEach
    void setUp() {
        member = User.builder()
                .id(1L)
                .nickname("member")
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
        when(followRepository.findByToUser(member)).thenReturn(followers);

        // when
        List<GetFollowerResponse> responses = followService.getFollower(member);

        // then
        assertEquals(2, responses.size());
        assertEquals(follower1.getId(), responses.get(0).getUserId());
        assertEquals(follower2.getId(), responses.get(1).getUserId());
    }
```

- `when(followRepository.findByToUser(member)).thenReturn(followers)` 을 통해 모의 객체인 followRepository의 예상 동작을 정의한다.
    - 'followRepository의 findByToUser메서드의 매개변수로 user가 넘어오면, followers를 반환해라' 라는 의미이다.

<br/>

----
## 4주차 CRUD API 만들기

4주차 과제는 CRUD API 만들기였는데 새롭게 알게된 점과 코드를 작성하며 고민했던 부분 위주로 README를 작성해보았습니다!

## 💡 전역 예외 처리 (GlobalExceptionHandler)

```java
@Transactional
public void deletePost(Long postId) {

    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 post입니다"));
    postLikeRepository.deleteByPostId(postId);
    postRepository.delete(post);
}
```

기존에는 유효하지 않은 post id를 받은 경우, IllegalArgumentException을 통해 예외를 던져주었다.

이 코드에서 한번 생각해 볼 수 있었던 것은

**1. 문제 상황 : 500 에러**

<img width="572" alt="스크린샷 2024-11-03 오후 12 52 29" src="https://github.com/user-attachments/assets/5aba4eea-9223-4c65-873d-07e35b2a922d">

IllegalArgumentException을 던져 예외 처리를 했지만, 프론트가 받는 응답에는 500 Internal Server Error만 나타나게 된다.

이 경우 프론트에서 예외 처리를 하기 어렵겠다고 생각했고, 사용자 입장에서도 실제로는 리소스를 찾지 못 해 발생한 에러이지만 500 서버 에러가 발생해 혼란을 줄 수 있다고 생각했다.

<br/>

**2. 초기 해결방법 : try-catch**

```java
try {
    // 비즈니스 로직
} catch (IllegalArgumentException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure("존재하지 않는 post 입니다"));
}
```

컨트롤러마다 에러를 catch 해 예외에 따라 적절한 상태 코드와 응답을 만들어주는 방법을 생각했다.

→ **하지만 이 방법은 예외 처리 코드가 너무 반복될 것 같은..문제점이 있다.**

<br/>

**3. 최종 해결방법 : 전역 예외 처리**기

코드가 반복되는 비효율을 줄이기 위해 **전역 예외 처리기**를 도입하기로 했다.

모든 컨트롤러에 적용되는 공통 예외 처리를 한 곳에 모아놓는 **중앙집중화** 된 방식으로 코드의 중복없이 일관된 응답 형식을 제공할 수 있다는 장점이 있다.

그럼 이제 전역 예외 처리를 하기 위해 필요한 코드를 살펴보도록 하자!

<br/>

### 1. ExceptionCode

```java
@Getter
public enum ExceptionCode {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "해당 member는 존재하지 않습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "해당 post는 존재하지 않습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "해당 comment는 존재하지 않습니다."),
    NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, "해당 image는 존재하지 않습니다."),
    NOT_FOUND_POSTLIKE(HttpStatus.NOT_FOUND, "해당 postlike는 존재하지 않습니다."),
    NOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND, "해당 chatroom은 존재하지 않습니다."),
    NOT_FOUND_MESSAGE(HttpStatus.NOT_FOUND, "해당 message는 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
```

- **예외 상태** 와 **예외 메시지** 를 관리하기 위한 ENUM 클래스이다.
- 특정 리소스가 없는 경우 클라이언트에게 보낼 예외의 상태 코드와 메시지를 정의할 수 있다.
    - NOT_FOUND 이외에도 BAD_REQUEST같은 다양한 예외 상황을 정의할 수 있다.

<br/>

### 2. NotFoundException

```java
public class NotFoundException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public NotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public HttpStatus getStatus() {
        return exceptionCode.getStatus();
    }

    public String getMessage() {
        return exceptionCode.getMessage();
    }
}
```

- NotFoundException 이라는 사용자 정의 예외 클래스를 정의했다.
- 특정 ExceptionCode를 기반으로 예외를 발생시키며, 각 예외에 맞는 HTTP 상태 코드와 메시지를 제공하는 역할을 한다.

<br/>

### 3. GlobalExceptionHandler

**🧐 GlobalExceptionHandler란??**

여기가 바로 애플리케이션 전반에서 발생하는 예외들을 한 곳에서 처리하기 위한 전역 예외 처리기이다.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(final NotFoundException e) {

        ExceptionResponse exceptionResponse = ExceptionResponse.from(e);
        ApiResponse<Void> apiResponse = ApiResponse.failure(exceptionResponse);

        return ResponseEntity
                .status(exceptionResponse.getStatus())
                .body(apiResponse);
    }
}
```

- `@RestControllerAdvice`와 `@ExceptionHandler` 를 사용해, 특정 예외 발생 시 일관된 형식으로 응답을 반환하는 역할을 한다.
    - `@RestControllerAdvice` : `@Controller` + `@ResponseBody` 로 모든 컨트롤러에서 발생하는 예외를 전역적으로 처리하는 클래스 임을 나타낸다. ⇒ **여기가 바로 중앙 집중화를 가능하게 해주는 부분!**
    - `@ExceptionHandler(NotFoundException.class)` : NotFoundException이 발생했을 때, 이 메서드가 호출되도록 지정한다.

```java
@Transactional
public void deletePost(Long postId) {

    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_POST));
    postLikeRepository.deleteByPostId(postId);
    postRepository.delete(post);
}
```

이제 예외 발생 상황에서 NotFoundException을 던져주면, 먼저 코드를 호출한 부분으로 돌아가는데 컨트롤러에서 예외 처리를 따로 하지 않으므로 GlobalExceptionHandler가 예외를 잡게 된다.

GlobalExceptionHandler의 `handleNotFoundException` 메서드가 실행되어 예외 정보를 response에 담아 클라이언트에게 반환한다.

<br/>

## 💡 ResponseEntity VS 공통 응답 형식

<img width="580" alt="스크린샷 2024-11-03 오후 2 58 57" src="https://github.com/user-attachments/assets/ce4c941d-db5b-444f-bb8a-5ae51521c029">

HTTP 응답 메시지를 살펴보면

HTTP 상태 코드, 헤더, 애플리케이션에서 처리한 데이터 등을 response에 담아줘야 하는데..

우리는 이런 정보를 어떻게 응답할 수 있을까?

<br/>

### 1. ResponseEntity 사용하기

**HttpEntity**

<img width="564" alt="스크린샷 2024-11-03 오후 2 52 06" src="https://github.com/user-attachments/assets/c2754545-7389-4e99-b5d7-7e7cd208b50a">

먼저 스프링은 HttpEntity라는 클래스를 제공하는데 HttpHeader와 HttpBody를 포함하고 있다.

<br/>

**ResponseEntity**

<img width="459" alt="스크린샷 2024-11-03 오후 2 51 38" src="https://github.com/user-attachments/assets/f524eb86-16a4-4ac8-9cbc-e00368d937ab">

ResponseEntity 클래스는 HttpEntity를 상속받아 사용하고 HttpStatusCode를 포함하고 있다.

따라서 우리는 **ResponseEntity** 클래스를 사용해 http status, header, body를 전부 담아 클라이언트에게 응답할 수 있는 것이다!

```java
@GetMapping("/{postId}")
public ResponseEntity<GetPostResponse> getPostById(@PathVariable Long postId) {
    GetPostResponse getPostResponse = postService.getPostById(postId);
    
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(getPostResponse);
}
```

🚨 **이 경우에 문제점은 성공했을 때와 실패 했을 때의 응답 형식이 다르다는 것이다.**

응답 형식이 다르다면 프론트에서 예외 처리를 하기 어려워질 것이다. 이를 어떻게 해결하면 좋을까?

### 2. 공통 응답 형식

response를 공통된 형식으로 반환하는 방법이 있다.

```java
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private final String status;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(SUCCESS_STATUS, message, data);
    }

    public static <T> ApiResponse<T> failure(ExceptionResponse error) {
        return new ApiResponse<>(ERROR_STATUS, error.getMessage(), null);
    }
}
```

위의 코드 처럼 status, message, data 세개의 필드를 갖는 ApiResponse를 반환하면 요청 성공, 실패 모두 같은 형식의 response를 받을 수 있게 된다.

<img width="448" alt="스크린샷 2024-11-02 오후 5 19 01" src="https://github.com/user-attachments/assets/39eb7d7a-cac6-4f66-80b3-6f07ac04371b">

<img width="486" alt="스크린샷 2024-11-02 오후 5 18 49" src="https://github.com/user-attachments/assets/58c3106a-7f7b-46d2-a480-eddf560a98a8">

<br/>

### 🧐 그러면 둘 중에 무엇을 사용하는게 더 좋을까??

- ResponseEntity의 장점
    - 정교하게 HttpStatus를 조절하여 그 값을 실제로 HttpStatus에 반영 가능하다
- ResponseEntity의 단점
    - 성공 시와 실패 했을 때의 응답 형식이 다르다
- 공통 응답 형식의 장점
    - 공통된 형식으로 응답할 수 있어 프론트에서 예외 처리하기 쉬워진다
- 공통 응답 형식의 단점
    - 정교한 HttpStatus를 설정하기 까다로울 수 있다

이렇게 각각 장단점이 존재하여…프론트와 협의해서 사용하면 된다고 생각한다. 

응답에 맞는 정교한 HttpStatus가 중요하다고 생각해 ResponseEntity와 공통 응답 형식 모두 사용해줬다 

```java
@GetMapping("/{postId}")
public ResponseEntity<ApiResponse<GetPostResponse>> getPostById(@PathVariable Long postId) {

    GetPostResponse getPostResponse = postService.getPostById(postId);
    return ResponseBuilder.createApiResponse("게시글 단건 조회 완료", getPostResponse);
}
```

ApiResponse라는 공통 형식의 클래스를 만들어 ResponseEntity의 body에 담아 반환했다. 

<br/>

## 💡 @Login 커스튬 어노테이션

나중에 jwt 로그인을 구현하면 모든 요청마다 인증 검증 로직을 작성해야 되는데, 이를 간편하게 처리하고 바로 로그인 된 사용자를 주입받기 위해 `@Login` 이라는 커스튬 어노테이션을 만들어줬다.

```java
@Target(ElementType.PARAMETER)   // 이 어노테이션이 메서드의 파라미터에서만 적용될 수 있도록 함
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
```

```java
@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	  @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean isMemberType = parameter.getParameterType().equals(Member.class);
        return hasLoginAnnotation && isMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        // 추후 jwt로 현재 로그인한 Member 찾아 반환할 예정
        return member;
    }
}
```

<br/>

**HandlerMethodArgumentResolver 인터페이스**

- 스프링 MVC에서 컨트롤러 메서드의 파라미터를 처리하기 위해 사용하는 인터페이스이다.
- 컨트롤러 메서드의 파라미터로 **특정 객체를 자동으로 주입**할 수 있다.

<br/>

주로 두가지 메서드로 이루어져 있는데

- supportsParameter
    - 특정 파라미터가 처리 대상인지 결정하는 메서드이다.
    - 이 메서드는 boolean을 반환하고, true 일 경우 스프링이 이 파라미터에 대해 resolveArgument 메서드를 사용하도록 한다.
    - ex) 위의 코드를 보면 파라미터에 붙은 어노테이션이 맞는지, 파라미터의 타입이 Member가 맞는지 검사하고 있다
- resolveArgument
    - 실제로 파라미터에 전달할 객체를 생성하고 반환한다.

```java
@PostMapping
public ResponseEntity<ApiResponse<Void>> createPost(
        @RequestBody CreatePostRequest createPostRequest,
        @Login Member member) {

    postService.createPost(createPostRequest, member);
    return ResponseBuilder.createApiResponse("게시글 작성 완료", null);
}
```

나중에 `resolveArgument` 메서드에 토큰을 검사해 현재 로그인 된 회원 정보를 가져오고, DB에서 조회해 반환하는 로직을 작성하면, 컨트롤러 메서드에서 `@Login` 어노테이션을 붙여 **로그인된 member 정보를 바로 주입**받을 수 있게 된다.

--------
## 5주차 - Spring Security와 로그인

<br/>

## Stateless 에서 상태 저장하기

HTTP 프로토콜은 **Stateless** 하다.

클라이언트가 서버로 요청을 하고 응답을 받으면 접속을 끊어 상태정보를 저장하지 않는 특징을 가지고 있다.

그럼 만약 사용자가 로그인을 했는데 로그인 상태를 기억하고 있지 않다면, 페이지를 이동할 때마다 로그인을 다시 해야될 것이다.

→ 어떻게 해결하면 좋을까?? 로그인 상태(사용자가 누구인지, 권한은 어떤지 등)을 서버가 기억하고 있으면 좋지 않을까??

### 🍪 Cookie (쿠키)

서버가 클라이언트에 정보를 저장하고 불러올 수 있는 수단이다.

브라우저는 서버에서 받은 쿠키를 저장했다가 동일한 서버로 재요청할 때 쿠키를 함께 전송한다.

→ 만약 사용자가 로그인을 하면 서버는 ID, PW를 쿠키에 담아 클라이언트로 보낸다. 이후 클라이언트는 요청할떄 마다 로그인 정보가 담긴 쿠키를 서버로 보내고, 서버는 넘겨받은 로그인 정보로 DB를 조회해 사용자를 얻을 수  있다.

**쿠키의 단점**

- 사용자의 정보를 매번 요청에 담아 보내기 때문에 보안상 문제가 있다.
- 클라이언트에서 쿠키 정보를 쉽게 변경, 삭제, 가로채기 당할 수 있다.

### 💡 Session (세션)

서버에서 일정시간 동안 클라이언트의 상태를 유지하기 위해 사용한다.

서버에서 클라이언트별 유일한 sessionId를 부여하고, 세션 정보를 서버에 저장한다 

→ 이 sessionId를 클라이언트가 쿠키로 가지고 있다가 요청 보낼 때 서버로 같이 보낸다. 서버는 sessionId로 바로 어떤 클라이언트인지 식별 가능하다.

**세션의 단점**

- 서버에서 클라이언트의 상태를 모두 유지하고 있어야 하므로, 클라이언트 수가 많아지면 메모리나 DB에 부하가 심해진다.
- 매 요청 시 세션 저장소를 조회해야한다.

**⇒ 이런 cookie와 session의 단점을 보완하여 등장한게 JWT이다.**

### 🔐 JWT(Json Web Token)

인증에 필요한 정보들을 Token에 담아 암호화시켜 사용하는 것

사실 쿠키의 인증 방식과 달라지는게 별로 없긴 하지만 jwt는 **서명된 토큰** 이라는 점에서 다르다.

jwt는 . 을 기준으로 세 파트로 구성된다.

```
xxxxx.yyyyy.zzzzz
```

**1. Header**

토큰의 타입과 서명 생성에 필요한 알고리즘을 저장한다.

→ 보통 `HS512` 으로 암호화한다.

**2. Payload**

사용자 또는 토큰에 대한 정보를 key-value 형태로 저장하는 **Claim** 을 저장한다.

주의할점 → payload 에는 민감한 정보를 담으면 안된다. header와 payload는 json이 디코딩되어 있을 뿐이지 암호화 되어 있는게 아니어서 누구나 jwt를 디코딩하면 header와 payload의 정보를 확인할 수 있다.

**3. Signature**

signature은 header와 payload를 조합하여 서명한 값이다.

```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret key
)
```

## JWT 인증 방식

jwt로 인증하는 방식이 과연 안전할까?

만약 jwt를 탈취한 사람이 jwt로 요청을 한다면?? 서버는 해당 사용자를 믿고 요청을 받을 수 밖에 없을 것이다.

이러한 이유로 jwt에 **유효기간** 을 설정해야한다.

하지만 토큰의 유효기간이 짧다면..사용자는 로그인을 자주 해야한다. 하지만 유효기간을 길게 하면 탈취 위험이 있다. 

→ 이 모든 문제를 해결하기 위해 **유효 기간이 다른 두개의 토큰** 을 사용한다.

### AccessToken

유효기간이 짧은 토큰으로 api 통신할때 인증하는 토큰으로 사용한다.

### RefreshToken

유효기간이 만료된 accessToken을 재발급할때 사용되는 토큰이다.


------
## 6주차 Docker 🐳

## Docker 란??

애플리케이션과 그 애플리케이션이 동작하는데 필요한 모든 환경을 **컨테이너**라는 독립적인 단위로 묶어 배포할 수 있게 해주는 가상화된 플랫폼이다.

<img width="764" alt="스크린샷 2024-11-15 오전 1 16 41" src="https://github.com/user-attachments/assets/f09ab2ef-3146-4cd5-b2c1-8db898918651">


### 1) Image (이미지)

- 서비스 운영에 필요한 서버 프로그램, 소스코드 및 라이브러리, 컴파일된 실행 파일을 묶은 형태
- 특정 프로세스를 실행하기 위한 모든 파일과 설정값(환경)을 지닌 것으로, 더 이상의 의존성 파일을 컴파일하거나 이것저것 설치 할 필요 없는 상태의 파일 의미

→ 컨테이너를 생성하기 위한 템플릿 역할

### 2) Container (컨테이너)

- 애플리케이션과 그 애플리케이션이 동작하는 데 필요한 모든 파일, 라이브러리, 설정 등을 **하나의 독립된 환경으로 묶은 것**
- 이미지를 실행한 상태

## 🧩 Image layer 란?

<img width="804" alt="스크린샷 2024-11-15 오후 3 31 14" src="https://github.com/user-attachments/assets/f200f26a-790e-433f-a4ed-5c98eb0b7885">
나는 분명 이미지 1개를 pull 받은건데…7개의 데이터가 pull 받아진 것을 볼 수 있다.

왜 그런걸까?? 저 7개의 조각들은..무엇일까?

→ 각각의 조각을 docker image **layer**라고 한다.

docker 이미지는 여러 개의 **읽기 전용 레이어**로 구성되어 있고, 레이어들은 각각 독립적으로 저장되어 있다.

이미지를 수정하고 싶은 경우는 새로운 레이어를 만들어 위에 추가하는 방식으로 이미지를 업데이트 할 수 있다.

<img width="285" alt="스크린샷 2024-11-15 오후 3 56 57" src="https://github.com/user-attachments/assets/d8b41885-31e3-46de-923b-6b99de76af20">

**Base Image (베이스 이미지)**

- 첫번째 레이어
- 운영체제의 기본 파일 시스템을 포함하며, 다른 모든 레이어의 기반이 된다

**Intermediate Layer (중간 레이어)**

- 베이스 이미지 위에 추가되는 레이어
- 각 중간 레이어들은 Dockerfile의 명령어에 의해 생성된다
  - `RUN`, `COPY`, `ADD` 명령어는 새로운 중간 레이어를 생성한다.

**Final Layer (최종 레이어)**

- 컨테이너를 실행하기 위해 필요한 모든 파일과 설정 포함
- 이 레이어는 읽기 전용 이고, 컨테이너가 생성될 때 **읽기-쓰기 레이어**가 이 위에 추가된다.

**📌 layer 특징 요약**

- **Immutability (불변성)**
  - 한번 생성된 레이어는 변경될 수 없다
  - 변경이 필요하면 새로운 레이어를 생성한다
- **Caching (캐싱)**
  - docker는 이전에 생성된 각 레이어를 캐시에 저장한다
  - 새로운 빌드 과정에서 명령어가 예전과 동일하다면 캐시된 레이어를 재사용한다 → 이미 캐싱된 레이어는 다운로드나 재생산이 필요없어 시간이 절약된다
- **Shareability (공유성)**
  - 동일한 레이어는 여러 docker 이미지 간에 공유될 수 있다 → 저장 공간 절약
  - 여러 이미지에서 동일한 레이어가 사용되면 한번만 저장하고, 다른 이미지에서 이를 참조만 한다

---

## ‘Virtual Machine’ VS ‘Docker Container’

기존에는 가상 머신이라는 가상화 방법이 있었는데

가상 머신에 대해 간단하게 알아보고, 도커와의 차이를 생각해보자!

<img width="510" alt="스크린샷 2024-11-14 오후 8 41 17" src="https://github.com/user-attachments/assets/7d0d4f95-92db-44ef-bfb2-59245ae81b56">

**Virtual Machine (가상머신)**

- **Hypervisor**(하이퍼바이저)를 이용해 여러개의 운영체제를 하나의 호스트에서 생성해서 사용하는 방식
- 이때 하이퍼바이저 위에 올라온 운영체제를 Guest OS 라고한다.
- 시스템 자원을 가상화하고, 독립된 공간을 생성하는 작업은 하이퍼바이저를 거치므로 성능 손실이 크다.
- 가상머신은 guest os를 사용하기 위한 라이브러리, 커널 등을 포함하므로 배포할 때 이미지 용량이 커진다.

→ 가상머신은 **완전한 OS**를 구동하는 가상화 방식이다.

기존 가상머신의 단점을 보완하기 위해 나온 것이 Docker 입니다.

<img width="575" alt="스크린샷 2024-11-14 오후 8 48 07" src="https://github.com/user-attachments/assets/28b6603b-5f2b-4a30-825c-edac44746ae8">

**Docker Container (도커 컨테이너)**

- 컨테이너는 호스트 OS의 커널을 공유하여 애플리케이션과 필요한 라이브러리만 독립된 환경에 묶어 배포하는 방식

→ 컨테이너 기반의 가상화 기술이다.

**차이점**

1. 커널 공유
  - Docker 컨테이너는 호스트 OS의 커널을 공유한다.
  - VM은 하이퍼바이저 위에 각자의 커널을 포함한 Guest OS를 구동한다 → 도커에 비해 무겁고 실행 시간이 길어질 수 있다
2. 이미지 크기
  - Docker 컨테이너는 애플리케이션 실행에 필요한 라이브러리와 의존성만 포함하여 이미지의 크기가 상대적으로 작다
  - VM은 각자의 커널을 포함하기 때문에 이미지의 크기가 커진다
3. 가상화 방식
  - Docker는 프로세스 수준에서 격리하는 방식으로 가상화한다 → 운영체제의 기능(네임스페이스, cgroups 등)을 사용하여 각 컨테이너가 독립된 환경처럼 작동하도록 격리
  - VM은 하이퍼바이저를 통해 하드웨어 수준에서 격리되며, 완전한 OS를 구동하는 가상화 방식이다.

---

## Dockerfile 의 역할

docker에서 **이미지**를 생성하기 위한 용도로 작성하는 파일이다.

나는 도커에 내 스프링부트 애플리케이션을 올리고 싶다.

→ 스프링부트 애플리케이션 실행하는데 필요한 환경을 갖춘 이미지를 만들어줬다.

```
FROM openjdk:17
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY .env ./
ENTRYPOINT ["java","-jar", "/app.jar"]
```

- **FROM**
  - 베이스 이미지를 지정
    → 완전 아무 것도 없는 상태로 이미지를 만드는게 아니고 어느정도 기본을 갖춘 상태의 이미지를 토대로 만드는데 이를 베이스 이미지라고 한다
  - 위에서는 java 17 환경을 포함한 openjdk:17 이미지를 사용하여 Java 애플리케이션을 실행할 수 있는 환경을 제공
- **ARG**
  - Dockerfile 안에서 사용되는 환경변수 정의
- **ENTRYPOINT**
  - 컨테이너 시작 시 실행될 cmd 지정
  - `java -jar /app.jar` 명령어를 사용해 /app.jar 파일을 실행하여 자바 애플리케이션을 시작
- **CMD**
  - 컨테이너 시작 시 실행할 기본 명령어와 인수 정의
  - cmd는 단독으로 사용 가능하고, 아래와 같이 ENTRYPOINT의 인수 역할로도 사용된다

    ```yaml
    ENTRYPOINT ["java", "-jar"]
    CMD ["/app.jar"]
    ```


---

## docker-compose.yml 의 역할

여러개의 컨테이너를 띄우는 도커 애플리케이션을 정의하고 실행하는 도구

컨테이너 실행에 필요한 옵션을 넣고, 컨테이너 간의 의존을 관리할 수 있다.

- 전체 코드

    ```yaml
    version: "3"
    
    services:
      database:
        container_name: instagram
        image: mysql:8.0
        platform: linux/amd64
        environment:
          MYSQL_DATABASE: instagram
          MYSQL_ROOT_HOST: '%'
          MYSQL_ROOT_PASSWORD: ${DB_PASSWORD}
          TZ: 'Asia/Seoul'
        ports:
          - "3306:3306"
        command:
          - "mysqld"
          - "--character-set-server=utf8mb4"
          - "--collation-server=utf8mb4_unicode_ci"
        networks:
          - network
        healthcheck:
          test: [ "CMD-SHELL", "mysqladmin ping -h 127.0.0.1 -p${DB_PASSWORD} --silent" ]
          interval: 30s
          retries: 5
          start_period: 10s
          timeout: 10s
    
      application:
        container_name: main-server
        build:
          dockerfile: Dockerfile
        ports:
          - "8080:8080"
        environment:
          SPRING_DATASOURCE_URL: ${DB_URL}
          SPRING_DATASOURCE_USERNAME: ${DB_USERNAME}
          SPRING_DATASOURCE_PASSWORD: ${DB_PASSWORD}
        depends_on:
          database:
            condition: service_healthy
        networks:
          - network
        env_file:
          - .env
    
    networks:
      network:
        driver: bridge
    ```


```yaml
services:
  database:
  ....
  
  application:
  ....
```

- **services**
  - 여러 개의 Docker 컨테이너로 이루어진 서비스들을 정의하는 블록
  - 나는 두개의 서비스를 정의했다
    - database - MySQL 데이터베이스
    - application - Java 애플리케이션

database 서비스만 살펴보자면(서비스는 구조가 같아서!!)

```yaml
database:
    container_name: instagram
    image: mysql:8.0
    platform: linux/amd64
    environment:
      MYSQL_DATABASE: instagram
      MYSQL_ROOT_HOST: '%'
      MYSQL_ROOT_PASSWORD: ${DB_PASSWORD}
      TZ: 'Asia/Seoul'
    ports:
      - "3306:3306"
    command:
      - "mysqld"
      - "--character-set-server=utf8mb4"
      - "--collation-server=utf8mb4_unicode_ci"
    networks:
      - network
```

- **container_name**
  - 컨테이너 이름을 지정한다
- **ports**
  - 호스트와 컨테이너 간의 포트 연결을 설정한다
  - 3306:3306
    - 호스트의 3306 포트를 컨테이너의 3306 포트에 연결하여, 로컬에서 MySQL에 접근할 수 있도록 함
- **networks**
  - network 네트워크에 database를 연결하여, 다른 서비스와 통신할 수 있도록 설정

```yaml
networks:
  network:
    driver: bridge
```

- **networks**
  - docker-compose에서 사용할 네트워크 설정
  - **1. bridge 모드**
    - 기본 네트워크
    - 컨테이너 간 격리와 통신을 가능하게 하며, 같은 네트워크에 속한 컨테이너끼리 호스트 이름으로 접근할 수 있다
    - ex)  application 컨테이너에서 database 컨테이너에 접근할 때 database라는 이름으로 접근할 수 있다
  - **host 모드**
    - 컨테이너가 호스트 시스템과 네트워크를 공유한다
    - ex) 컨테이너에서 localhost를 사용하면 실제 호스트의 네트워크 인터페이스를 참조
    - ex) 로컬 컴퓨터에서 직접 실행 중인 데이터베이스에 연결하려는 경우, host 모드에서 localhost:5432(데이터베이스 포트)로 쉽게 접근 가능

🚨 **Spring Boot 애플리케이션이 지속적으로 종료와 재시작을 반복하는 문제 발생**

이 문제는 DB 서비스가 완전히 준비되기 전에 Spring Boot 가 먼저 실행되면서 데이터베이스에 연결할 수 없어서 발생한 것이었다.

이를 해결하기 위해 DB 서비스가 정상적으로 실행될 때까지 대기하도록 **healthy check** 를 도입했다!

```yaml
services:
  database:
    ....
    healthcheck:
      test: [ "CMD-SHELL", "mysqladmin ping -h 127.0.0.1 -p${DB_PASSWORD} --silent" ]
      interval: 30s
      retries: 5
      start_period: 10s
      timeout: 10s
      
	application:
	    ....
	    depends_on:
	      database:
	        condition: service_healthy
```

- database 서비스 - healthcheck 설정
  - **`test`**: `["CMD-SHELL", "mysqladmin ping -h 127.0.0.1 -p${DB_PASSWORD} --silent"]`
    - MySQL 서버 상태를 확인하는 명령어
    - `mysqladmin ping` 명령어를 사용하여 데이터베이스가 실행 중인지 확인
- application 서비스 - depends_on 설정
  - depends_on
    - application 서비스가 시작되기 전에 database 서비스가 시작되어야 함을 지정
  - condition: service_healthy
    - application 서비스는 database의 서비스가 헬스체크를 통과해 healthy 상태가 될 때까지 대기

---

## +) Volume 은 뭘까??

docker는 개별적인 가상화 환경인 컨테이너에서 작업을 하여 모든 데이터는 컨테이너 내부에만 존재.

→ 컨테이너를 삭제하면 컨테이너 내부에서 작업했던 데이터들이 같이 삭제된다.

→ 데이터는 살리고 싶은데 어떻게 해야 좋을까?? ⇒ **볼륨(volume)** 을 사용하자!

- 컨테이너 내부의 데이터를 외부로 링크를 걸어주는 기능



----

## 📌 수동 배포 해보기!!

### 배포란??

내 서비스를 다른 사람들이 인터넷을 통해 사용할 수 있도록 만드는 것을 의미한다.

내 컴퓨터에서 애플리케이션을 실행시키고 내 컴퓨터의 ip주소를 사람들에게 알려주면 다른 사람들이 서비스를 이용할 수 있다.

하지만 여기에는 두가지 문제점이 있다.

1. 사실상 내 컴퓨터에서 24시간동안 애플리케이션을 실행하는 것은 말이 안된다!
2. 내 컴퓨터의 Ip주소는 가정용 네트워크에서 동적으로 변할 수 있다.

→ 그래서 우리는 AWS에서 컴퓨터(인스턴스)를 빌려…해당 컴퓨터에 애플리케이션을 실행시켜 둘것이다.

그럼 우리는 빌린 컴퓨터(인스턴스)에 우리 코드를 올려야하고..실행시켜야 한다.

기존에는 내가 직접 인스턴스에 들어가 깃 클론을 받고, 빌드시키는 과정으로 배포했는데 이번에는 **Docker Hub**을 사용해 볼 것이다.

→ Docker Hub은 깃허브 처럼 도커 이미지를 저장할 수 있는 원격 저장소라고 생각하면 된다.

### 배포 과정 살펴보기

<img width="773" alt="스크린샷 2024-11-23 오후 9 43 18" src="https://github.com/user-attachments/assets/f6f97e86-0ddd-4cbf-985a-99cc4aeae563">

- Docker Image 생성 + Docker hub에 push
    - 로컬 개발 환경에서 애플리케이션을 docker 이미지로 빌드한다.
    
    ```
    docker build --platform linux/amd64 -t [도커아이디]/[리포지토리명] .
    ```
    
    - 내 docker hub에 push 한다.
    
    ```
    docker push [도커아이디]/[리포지토리명]
    ```
    
- AWS 인스턴스를 생성한다
    - DB를 사용한다면 AWS에서 제공하는 RDS도 설정해주자
    - RDS는 필수는 아니다. Mysql 이미지를 다운 받을 수도 있으니깐..
- Docker 설치 + Docker Hub에서 이미지 Pull
    - 인스턴스에 docker를 설치해준다.
    - 내 docker hub에서 원하는 이미지를 Pull 받는다.
    
    ```
    docker pull [도커아이디]/[리포지토리명]
    ```
    
- Docker 컨테이너 실행
    - pull 받은 이미지 실행한다.
    
    ```
    docker run -e .env -d -p 80:8080 [도커아이디]/[리포지토리명]
    ```
    

✅ 이렇게 첫번째 문제점 → **24시간 애플리케이션을 돌린 컴퓨터가 필요하다!** 는 해결이 되었다.

그러면 **고정적은 IP주소는 어떻게 할당받을 수 있을까???**

## 💡 Elastic IP Address (탄력적 IP)

인스턴스의 퍼블릭 주소는 유동적인 주소다. 인스턴스를 중지했다 다시 실행하면 기존의 IP주소가 변경된다는 의미이다.→ 그러면 이럴때마다 인스턴스 IP주소가 필요했던 부분을 찾아 고쳐줘야 된다는 문제점이 있다.

그래서 **탄력적 IP** 라는 것을 사용한다.

탄력적 IP는 인터넷에서 연결 가능한 퍼블릭 IPv4 주소인데, 유동적인 주소가 아닌 **고정적인 IP주소**이다.

→ 탄력적 IP를 할당하면 인스턴스를 중지했다 다시 실행해도 IP주소가 변하지 않아, 연결해제 할때까지 사용할 수 있다!!

---

## 💡 Security Group (보안 그룹)

AWS의 네트워크 보안 기술이다.

보안 그룹을 설정하면 EC2 인스턴스로 들어오거나 나가는 트래픽들을 직접 설정하여 제어할 수 있다.

**Inbound**

- 인스턴스 밖에서 안으로 들어오는

**Outbound**

- 인스턴스 안에서 밖으로 나가는

<img width="882" alt="스크린샷 2024-11-23 오전 1 04 13" src="https://github.com/user-attachments/assets/8de5c178-9dea-4ec2-87b4-049dce369636">

- 인바운드 규칙으로 허용 IP와 포트를 설정했기 때문에, 해당 IP와 port의 요청만 인스턴스에 접속할 수 있다.
- 인바운드 규칙으로 허용된 IP 혹은 port가 아니라면 접속 할 수 없다.

---

## 🚨 왜 DB와 연결되지 않는걸까??

잘 배포했다고 생각했는데 자꾸 DB와 커넥션 에러가 났다.

DB 정보들도 잘 적어주었는데…**connection refused** 에러가 왜 나는걸까??

→ 답은 RDS의 작동 방식을 이해하면 알 수 있다.

RDS는 보안그룹을 통해서 외부와 통신한다.

허용된 IP, port만 접근 가능하도록 설정하여 외부 공격이나 무단 접근을 방지한다.

→ 따라서 내 인스턴스가 DB에 접근하려면 RDS의 보안그룹에 내 인스턴스 정보를 추가해야 된다는 것!!

---

## 🌟 배포 결과

<img width="904" alt="스크린샷 2024-11-22 오후 9 41 35" src="https://github.com/user-attachments/assets/8345fbc8-4f8f-45c8-b3a0-e80ac00a2e31">
<img width="923" alt="스크린샷 2024-11-22 오후 9 41 45" src="https://github.com/user-attachments/assets/96d214ae-b6bc-4899-8ee0-452046cbd98a">
<img width="837" alt="스크린샷 2024-11-22 오후 9 42 10" src="https://github.com/user-attachments/assets/35b8f57d-f87c-41fd-a95a-aed26afcc46c">

- 스프링 컨테이너가 잘 돌아가고 있는 것을 확인할 수 있고, postman으로 잘 테스트 되는 것을 확인할 수 있다.



