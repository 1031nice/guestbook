package me.donghun.guestbook;

import me.donghun.guestbook.entity.Guestbook;
import me.donghun.guestbook.repository.GuestbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	GuestbookRepository guestbookRepository;

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			IntStream.rangeClosed(1, 300).forEach(i -> {
				Guestbook guestbook = Guestbook.builder()
						.title("Title...." + i)
						.content("Content...." + i)
						.writer("User" + (i % 10))
						.build();

				guestbookRepository.save(guestbook);
			});
		};
	}

}
