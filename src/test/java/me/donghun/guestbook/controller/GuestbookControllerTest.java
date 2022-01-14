package me.donghun.guestbook.controller;

import me.donghun.guestbook.entity.Guestbook;
import me.donghun.guestbook.repository.GuestbookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GuestbookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    GuestbookRepository guestbookRepository;

    @Test
    @DisplayName("POST /guestbook/remove 방명록 삭제")
    void deleteById() throws Exception {
        Guestbook guestbook = createGuestbook();
        guestbookRepository.save(guestbook);

        mockMvc.perform(post("/guestbook/remove")
                        .param("gno", guestbook.getGno().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/guestbook/list"))
                .andExpect(flash().attributeExists("msg"))
                .andDo(print());

        assertThat(guestbookRepository.findById(guestbook.getGno())).isEmpty();
    }

    @Test
    @DisplayName("POST /guestbook/modify 방명록의 제목과 내용 수정")
    @Transactional
    void modify() throws Exception {
        Guestbook guestbook = createGuestbook();
        guestbookRepository.save(guestbook);

        String newTitle = "new_title";
        String newContent = "new_content";

        mockMvc.perform(post("/guestbook/modify")
                        .param("gno", guestbook.getGno().toString())
                        .param("title", newTitle)
                        .param("content", newContent))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("page", "gno"))
                .andDo(print());

        Optional<Guestbook> result = guestbookRepository.findById(guestbook.getGno());
        assertThat(result).isNotEmpty();
        assertThat(result.get().getTitle()).isEqualTo(newTitle);
        assertThat(result.get().getContent()).isEqualTo(newContent);
    }

    private Guestbook createGuestbook() {
        return Guestbook.builder()
                .title("test_title")
                .content("test_content")
                .writer("test_writer")
                .build();
    }

    @Test
    @DisplayName("POST /guestbook/register 방명록 등록")
    @Transactional
    void register() throws Exception {
        String title = "test_title";
        mockMvc.perform(post("/guestbook/register")
                        .param("title", title)
                        .param("content", "test_content")
                        .param("writer", "test_writer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/guestbook/list"))
                .andExpect(flash().attributeExists("msg"))
                .andDo(print());

        assertThat(guestbookRepository.findByTitle(title)).isNotEmpty();
    }

    @Test
    @DisplayName("방명록의 제목 또는 내용이 비어있을 경우 등록 실패")
    void registerFail_emptyTitleOrEmptyContent() throws Exception {
        mockMvc.perform(post("/guestbook/register")
                        .param("title", "")
                        .param("content", "test_content")
                        .param("writer", "test_writer"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("guestbookDTO", "title"))
                .andDo(print());

        mockMvc.perform(post("/guestbook/register")
                        .param("title", "test_title")
                        .param("content", "")
                        .param("writer", "test_writer"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("guestbookDTO", "content"))
                .andDo(print());
    }


    @Test
    @DisplayName("GET /guestbook/read 조회 화면으로 이동")
    void routeToReadView() throws Exception {
        mockMvc.perform(get("/guestbook/read")
                        .param("gno", "150"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dto"))
                .andExpect(model().attributeExists("requestDTO"))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /guestbook/modify 수정 화면으로 이동")
    void routeToModifyView() throws Exception {
        mockMvc.perform(get("/guestbook/modify")
                        .param("gno", "150"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dto"))
                .andExpect(model().attributeExists("requestDTO"))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /guestbook/register 등록 화면으로 이동")
    void routeToRegisterView() throws Exception {
        mockMvc.perform(get("/guestbook/register"))
                .andExpect(view().name("guestbook/register"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET / 방명록 목록 화면으로 이동")
    void routeToListView() throws Exception {
        mockMvc.perform(get("/guestbook/"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("GET /guestbook/list 방명록 목록 화면으로 이동")
    void routeToListView2() throws Exception {
        mockMvc.perform(get("/guestbook/list"))
                .andExpect(view().name("guestbook/list"))
                .andExpect(model().attributeExists("result"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}