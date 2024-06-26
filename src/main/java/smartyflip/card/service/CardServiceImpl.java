/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.card.dao.CardRepository;
import smartyflip.card.dto.CardDto;
import smartyflip.card.dto.NewCardDto;
import smartyflip.card.model.Card;
import smartyflip.card.model.enums.Level;
import smartyflip.card.service.exceptions.CardNotFoundException;
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.modules.model.Module;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;
import smartyflip.utils.PagedDataResponseDto;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final ModuleRepository moduleRepository;

    private final ModelMapper modelMapper;


    @Transactional
    @Override
    public CardDto addCard(NewCardDto newCardDto) {
        Module module = moduleRepository.findById(newCardDto.getModuleId()).orElseThrow(ModuleNotFoundException::new);
        Card card = modelMapper.map(newCardDto, Card.class);
        card.setLevel(Level.fromString(newCardDto.getLevel()));
        card.setModuleName(module.getModuleName());
        card.setModule(module);
        cardRepository.save(card);
        return mapToDto(card);
    }


    @Override
    public CardDto findCardById(Long cardId) {
        return mapToDto(getCardById(cardId));
    }

    @Transactional
    @Override
    public CardDto editCard(Long cardId, NewCardDto newCardDto) {
        if (!moduleRepository.existsById(newCardDto.getModuleId())) {
            throw new ModuleNotFoundException();
        }
        Card card = getCardById(cardId);
        updateCard(newCardDto, card);
        cardRepository.save(card);
        return mapToDto(card);
    }

    @Transactional
    @Override
    public CardDto deleteCard(Long cardId) {
        Card card = getCardById(cardId);
        cardRepository.deleteById(cardId);
        return mapToDto(card);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<CardDto> findCardsByModuleId(Long moduleId) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new ModuleNotFoundException();
        }
        return cardRepository.findCardsByModuleId(moduleId)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PagedDataResponseDto<CardDto> findAllCards(PageRequest pageRequest) {
        Page<Card> cardPage = cardRepository.findAll(pageRequest);
        PagedDataResponseDto<CardDto> pagedDataResponseDto = new PagedDataResponseDto<>();
        pagedDataResponseDto.setData(cardPage.getContent().stream()
                .map((Card card) -> modelMapper.map(card, CardDto.class))
                .collect(Collectors.toList()));
        pagedDataResponseDto.setTotalElements(cardPage.getTotalElements());
        pagedDataResponseDto.setTotalPages(cardPage.getTotalPages());
        return pagedDataResponseDto;
    }


    private Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);
    }


    private CardDto mapToDto(Card card) {
        return modelMapper.map(card, CardDto.class);
    }

    private void updateCard(NewCardDto newCardDto, Card card) {
        card.setQuestion(newCardDto.getQuestion());
        card.setAnswer(newCardDto.getAnswer());

        Level level = Level.valueOf(newCardDto.getLevel().toUpperCase());
        card.setLevel(level);

        Long moduleId = newCardDto.getModuleId();
        Module module = moduleRepository.findById(moduleId).orElseThrow(ModuleNotFoundException::new);
        card.setModuleId(module.getModuleId());
        card.setModuleName(module.getModuleName());
    }

}
