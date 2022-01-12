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

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository guestbookRepository;

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = guestbookRepository.findById(dto.getGno());

        result.ifPresent(guestbook -> {
            guestbook.changeTitle(dto.getTitle());
            guestbook.changeContent(dto.getContent());
            guestbookRepository.save(guestbook);
        });
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = guestbookRepository.findById(gno);
        return result.map(this::entityToDto).orElse(null);
    }

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
