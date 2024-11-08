package com.ceos20.instagram;

import com.ceos20.instagram.member.domain.Member;
import com.ceos20.instagram.member.repository.MemberRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InstagramApplication {

	@Autowired
	private MemberRepository memberRepository;


	public static void main(String[] args) {

		// .env load
		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(InstagramApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			if (!memberRepository.existsById(1L)) {
				Member member = Member.builder()
						.username("username")
						.nickname("nickname")
						.password("password")
						.email("email@example.com")
						.imageUrl("image_url")
						.build();

				memberRepository.save(member);
				System.out.println("Initialized member with id 1.");
			}

			if (!memberRepository.existsById(2L)) {
				Member member2 = Member.builder()
						.username("username2")
						.nickname("nickname2")
						.password("password2")
						.email("email2@example.com")
						.imageUrl("image_url2")
						.build();

				memberRepository.save(member2);
				System.out.println("Initialized member with id 2.");
			}
		};
	}
}
