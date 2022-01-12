package me.donghun.guestbook.service;

import me.donghun.guestbook.dto.GuestbookDTO;
import me.donghun.guestbook.dto.PageRequestDTO;
import me.donghun.guestbook.dto.PageResultDTO;
import me.donghun.guestbook.entity.Guestbook;
import me.donghun.guestbook.repository.GuestbookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GuestbookServiceTest {

    @Autowired
    private GuestbookService guestbookService;

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    @DisplayName("목록 조회")
    void getList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(17)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> pageResultDTO = guestbookService.getList(pageRequestDTO);

        assertThat(pageResultDTO.isPrev()).isTrue();
        assertThat(pageResultDTO.isNext()).isTrue();
        assertThat(pageResultDTO.getPageList().get(0)).isEqualTo(11);
        assertThat(pageResultDTO.getPageList().get(9)).isEqualTo(20);

        List<GuestbookDTO> dtoList = pageResultDTO.getDtoList();

        assertThat(dtoList.get(0)).isInstanceOf(GuestbookDTO.class);
        assertThat(dtoList.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("등록")
    @Transactional
    // @Transactional을 붙이면 sql이 날아가더라도(콘솔에 찍히더라도) DB에는 아예 반영되지 않는다
    void register() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        Long id = guestbookService.register(guestbookDTO);

        assertThat(guestbookRepository.findById(id)).isPresent();
    }

    @Test
    @DisplayName("엔티티 클래스를 DTO로 변환")
    void entityToDto() {
        Guestbook entity = Guestbook.builder()
                .gno(1L)
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(1L)
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        GuestbookDTO afterConvert = guestbookService.entityToDto(entity);
        assertThat(afterConvert.getGno()).isEqualTo(dto.getGno());
        assertThat(afterConvert.getTitle()).isEqualTo(dto.getTitle());
        assertThat(afterConvert.getContent()).isEqualTo(dto.getContent());
        assertThat(afterConvert.getWriter()).isEqualTo(dto.getWriter());
        assertThat(afterConvert.getModDate()).isEqualTo(dto.getModDate());
        assertThat(afterConvert.getRegDate()).isEqualTo(dto.getRegDate());
    }

    @Test
    @DisplayName("DTO를 엔티티 클래스로 변환")
    void dtoToEntity() {
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(1L)
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        Guestbook entity = Guestbook.builder()
                .gno(1L)
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        Guestbook afterConvert = guestbookService.dtoToEntity(dto);
        assertThat(afterConvert.getGno()).isEqualTo(entity.getGno());
        assertThat(afterConvert.getTitle()).isEqualTo(entity.getTitle());
        assertThat(afterConvert.getContent()).isEqualTo(entity.getContent());
        assertThat(afterConvert.getWriter()).isEqualTo(entity.getWriter());
        assertThat(afterConvert.getModDate()).isNull();
        assertThat(afterConvert.getRegDate()).isNull();
    }

}