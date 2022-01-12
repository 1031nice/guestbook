package me.donghun.guestbook.repository;

import me.donghun.guestbook.entity.Guestbook;
import me.donghun.guestbook.entity.QGuestbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    @DisplayName("방명록 저장")
    @Transactional
    void save() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content...." + i)
                    .writer("User" + (i % 10))
                    .build();

            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    @DisplayName("방명록 수정")
    void update() {
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        result.ifPresent(guestbook -> {
            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content....");

            guestbookRepository.save(guestbook);
        });
    }

    // querydsl 테스트
    @Test
    @DisplayName("Querydsl 단일 항목 검색")
    void querydslTest1() {
        String keyword = "1";
        Page<Guestbook> result = guestbookRepository.findAll(
                QGuestbook.guestbook.title.contains(keyword),
                PageRequest.of(0, 10, Sort.by("gno").descending())
        );

        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("Querydsl 다중 항목 검색")
    void querydslTest2() {
        String keyword = "1";
        QGuestbook qGuestbook = QGuestbook.guestbook;
        Page<Guestbook> result = guestbookRepository.findAll(
                qGuestbook.title.contains(keyword)
                        .or(qGuestbook.content.contains(keyword))
                        .and(qGuestbook.gno.gt(0L)),
                PageRequest.of(0, 10, Sort.by("gno").descending()));

        result.stream().forEach(System.out::println);
    }

}