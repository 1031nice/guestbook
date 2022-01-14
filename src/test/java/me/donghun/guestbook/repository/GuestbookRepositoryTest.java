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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    @DisplayName("방명록 저장")
    @Transactional
    void save() {
        int startInclusive = 499;
        int endInclusive = 599;
        IntStream.rangeClosed(startInclusive, endInclusive).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title" + i)
                    .content("Content" + i)
                    .writer("User" + (i % 10))
                    .build();
            guestbookRepository.save(guestbook);
        });

        IntStream.rangeClosed(startInclusive, endInclusive).forEach(i -> {
            assertThat(guestbookRepository.findByTitle("Title" + i)).isNotEmpty();
        });
    }

    @Test
    @DisplayName("방명록 수정")
    void update() {
        Guestbook guestbook = Guestbook.builder()
                .title("test title")
                .content("test content")
                .writer("user")
                .build();
        guestbookRepository.save(guestbook);

        String newTitle = "new title";
        String newContent = "new content";

        guestbook.changeTitle(newTitle);
        guestbook.changeContent(newContent);
        guestbookRepository.save(guestbook);

        Optional<Guestbook> optionalGuestbook = guestbookRepository.findById(guestbook.getGno());
        assertThat(optionalGuestbook.get().getTitle()).isEqualTo(newTitle);
        assertThat(optionalGuestbook.get().getContent()).isEqualTo(newContent);
    }

    @Test
    @DisplayName("제목으로 방명록 찾기")
    void findByTitle() {
        Guestbook guestbook = Guestbook.builder()
                .title("test title")
                .content("test content")
                .writer("user")
                .build();
        guestbookRepository.save(guestbook);

        Optional<Guestbook> optionalGuestbook = guestbookRepository.findByTitle(guestbook.getTitle());
        assertThat(optionalGuestbook).isNotEmpty();
        assertThat(optionalGuestbook.get().getTitle()).isEqualTo(guestbook.getTitle());
    }

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