package me.donghun.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.donghun.guestbook.dto.GuestbookDTO;
import me.donghun.guestbook.dto.PageRequestDTO;
import me.donghun.guestbook.dto.PageResultDTO;
import me.donghun.guestbook.entity.Guestbook;
import me.donghun.guestbook.repository.GuestbookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        guestbookRepository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        return new PageResultDTO<>(
                guestbookRepository.findAll(requestDTO.getPageable(Sort.by("gno").descending())),
                this::entityToDto
        );
    }

}
