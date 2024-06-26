/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dto.FindModuleByNameRequestDto;
import smartyflip.modules.dto.ModuleInfoResponseDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.service.ModuleService;
import smartyflip.modules.service.TagService;
import smartyflip.utils.PagedDataResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/modules")
public class ModuleController {

    private final ModuleService moduleService;

    private final TagService tagService;

    @PostMapping
    public ModuleInfoResponseDto addModule(@Valid @RequestBody NewModuleDto newModuleDto) {
        return moduleService.addModule(newModuleDto);
    }

    @GetMapping("/{moduleId}")
    public ModuleInfoResponseDto findModuleById(@PathVariable Long moduleId) {
        return moduleService.findModuleById(moduleId);
    }

    @PutMapping("/{moduleId}")
    public ModuleInfoResponseDto editModule(@Valid @RequestBody NewModuleDto newModuleDto, @PathVariable Long moduleId) {
        return moduleService.editModule(newModuleDto, moduleId);
    }


    @DeleteMapping("/{moduleId}")
    public ModuleInfoResponseDto deleteModule(@PathVariable Long moduleId) {
        return moduleService.deleteModule(moduleId);
    }


    @GetMapping("/user/{userId}")
    public PagedDataResponseDto<ModuleInfoResponseDto> findModulesByUserId(@PathVariable Integer userId,
                                                                           @RequestParam(required = false, defaultValue = "25", name = "size") Integer size,
                                                                           @RequestParam(required = false, defaultValue = "0", name = "page") Integer page) {
        return moduleService.findModulesByUserId(userId, PageRequest.of(page, size));
    }

    @PostMapping("/period")
    public PagedDataResponseDto<ModuleInfoResponseDto> findModulesByPeriod(@Valid @RequestBody DatePeriodDto datePeriodDto,
                                                                           @RequestParam(required = false, defaultValue = "25", name = "size") Integer size,
                                                                           @RequestParam(required = false, defaultValue = "0", name = "page") Integer page) {
        return moduleService.findModulesByPeriod(datePeriodDto, PageRequest.of(page, size));
    }

    @PostMapping("/name")
    public PagedDataResponseDto<ModuleInfoResponseDto> findModulesByName(@Valid @RequestBody FindModuleByNameRequestDto moduleNameRequestDto,
                                                                         @RequestParam(required = false, defaultValue = "25", name = "size") Integer size,
                                                                         @RequestParam(required = false, defaultValue = "0", name = "page") Integer page) {
        return moduleService.findModulesByName(moduleNameRequestDto, PageRequest.of(page, size));
    }

    @GetMapping("/stacks/{id}")
    public PagedDataResponseDto<ModuleInfoResponseDto> findModulesByStack(@RequestParam(required = false, defaultValue = "25", name = "size") Integer size,
                                                                          @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                                                          @PathVariable Long id) {
        return moduleService.findModulesByStackId(id, PageRequest.of(page, size));
    }

    @GetMapping
    public PagedDataResponseDto<ModuleInfoResponseDto> findAllModules(
            @RequestParam(required = false, defaultValue = "25", name = "size") Integer size,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page
    ) {
        return moduleService.findAllModules(PageRequest.of(page, size));
    }

    @GetMapping("/tags")
    public Iterable<String> findAllTags() {
        return tagService.findAllTags();
    }

    @GetMapping("/{moduleId}/cards/amount")
    public int cardsCountByModuleId(@PathVariable Long moduleId) {
        return moduleService.cardsAmountByModuleId(moduleId);
    }
}
