package me.donghun.guestbook.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GuestbookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("루트 요청시 목록 화면으로 redirect")
    void index() throws Exception {
        mockMvc.perform(get("/guestbook/"))
                .andExpect(view().name("/guestbook/list"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("목록 조회")
    void list() throws Exception {
        mockMvc.perform(get("/guestbook/list"))
                .andExpect(view().name("/guestbook/list"))
                .andExpect(model().attributeExists("result"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}