package me.donghun.guestbook.service;

import me.donghun.guestbook.dto.GuestbookDTO;

public interface GuestbookService {
    Long register(GuestbookDTO dto);
}
