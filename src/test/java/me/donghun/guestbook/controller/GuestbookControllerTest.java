package me.donghun.guestbook.controller;

import me.donghun.guestbook.repository.GuestbookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
    @DisplayName("조회")
    void read() throws Exception {
        mockMvc.perform(get("/guestbook/read")
                        .param("gno", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dto"))
                .andExpect(model().attributeExists("requestDTO"))
                .andDo(print());
    }

    @Test
    @DisplayName("등록")
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
    @DisplayName("등록 화면")
    void registerView() throws Exception {
        mockMvc.perform(get("/guestbook/register"))
                .andExpect(view().name("guestbook/register"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("루트 요청시 목록 화면으로 redirect")
    void index() throws Exception {
        mockMvc.perform(get("/guestbook/"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("목록 조회")
    void listView() throws Exception {
        mockMvc.perform(get("/guestbook/list"))
                .andExpect(view().name("guestbook/list"))
                .andExpect(model().attributeExists("result"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}