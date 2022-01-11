package me.donghun.guestbook.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class GuestbookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("목록 화면")
    void list() throws Exception {
        mockMvc.perform(get("/guestbook/list"))
                .andExpect(view().name("/guestbook/list"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}