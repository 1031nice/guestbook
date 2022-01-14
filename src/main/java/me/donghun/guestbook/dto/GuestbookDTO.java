package me.donghun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestbookDTO {

    private Long gno;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String writer;
    private LocalDateTime regDate, modDate;

}
