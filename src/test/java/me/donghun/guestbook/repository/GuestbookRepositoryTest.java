package me.donghun.guestbook.repository;

import me.donghun.guestbook.entity.Guestbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    @DisplayName("방명록 저장")
    void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content...." + i)
                    .writer("User" + (i % 10))
                    .build();

            System.out.println(guestbookRepository.save(guestbook));
        });
    }

}