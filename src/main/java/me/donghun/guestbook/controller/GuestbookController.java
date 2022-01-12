package me.donghun.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.donghun.guestbook.dto.GuestbookDTO;
import me.donghun.guestbook.dto.PageRequestDTO;
import me.donghun.guestbook.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list....." + pageRequestDTO);
        model.addAttribute("result", guestbookService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get.....");
        // UrlBasedViewResolver
    }

    @PostMapping("/register")
    public String register(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto....." + dto);
        Long gno = guestbookService.register(dto);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

}
