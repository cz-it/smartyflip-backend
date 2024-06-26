/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import smartyflip.card.dto.CardDto;
import smartyflip.card.dto.NewCardDto;
import smartyflip.card.service.CardService;
import smartyflip.utils.PagedDataResponseDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    final CardService cardService;

    @PostMapping("")
    public CardDto addCard(@Valid @RequestBody NewCardDto newCardDto) {
        return cardService.addCard(newCardDto);
    }

    @GetMapping("/{cardId}")
    public CardDto findCardById(@PathVariable Long cardId) {
        return cardService.findCardById(cardId);
    }

    @PutMapping("/{cardId}")
    public CardDto editCard(@PathVariable Long cardId, @Valid @RequestBody NewCardDto newCardDto) {
        return cardService.editCard(cardId, newCardDto);
    }

    @DeleteMapping("/{cardId}")
    public CardDto deleteCard(@PathVariable Long cardId) {
        return cardService.deleteCard(cardId);
    }

    @GetMapping("/modules/{moduleId}")
    public Iterable<CardDto> findCardsByModuleId(@PathVariable Long moduleId) {
        return cardService.findCardsByModuleId(moduleId);
    }

    @GetMapping
    public PagedDataResponseDto<CardDto> findAllCards(
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page
    ) {
        return cardService.findAllCards(PageRequest.of(page, size));
    }

}
