package me.donghun.guestbook.service;

import me.donghun.guestbook.dto.GuestbookDTO;
import me.donghun.guestbook.entity.Guestbook;
import me.donghun.guestbook.repository.GuestbookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GuestbookServiceTest {

    @Autowired
    private GuestbookService guestbookService;

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    @DisplayName("등록")
    @Transactional
    void register() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .gno(999L)
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        guestbookService.register(guestbookDTO);

        assertThat(guestbookRepository.findById(guestbookDTO.getGno())).isPresent();
    }

    @Test
    @DisplayName("DTO를 엔티티 클래스로 변환")
    void dtoToEntity() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .gno(1L)
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        Guestbook guestbook = Guestbook.builder()
                .gno(1L)
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        Guestbook afterConvert = guestbookService.dtoToEntity(guestbookDTO);
        assertThat(afterConvert.getGno()).isEqualTo(guestbook.getGno());
        assertThat(afterConvert.getTitle()).isEqualTo(guestbook.getTitle());
        assertThat(afterConvert.getContent()).isEqualTo(guestbook.getContent());
        assertThat(afterConvert.getWriter()).isEqualTo(guestbook.getWriter());
        assertThat(afterConvert.getModDate()).isNull();
        assertThat(afterConvert.getRegDate()).isNull();
    }

}