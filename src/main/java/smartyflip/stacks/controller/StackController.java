/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smartyflip.stacks.dto.NewStackRequestDto;
import smartyflip.stacks.dto.StackInfoResponseDto;
import smartyflip.stacks.service.StackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stacks")
public class StackController {
    final StackService stackService;

    @PostMapping("")
    public StackInfoResponseDto addStack(@Valid @RequestBody NewStackRequestDto stackDto) {
        return stackService.addStack(stackDto);
    }

    @GetMapping("/{stackId}")
    public StackInfoResponseDto findStackById(@PathVariable("stackId") Long stackId) {
        return stackService.findStackById(stackId);
    }

    @PutMapping("/{stackId}")
    public StackInfoResponseDto editStack(@PathVariable String stackId, @Valid @RequestBody NewStackRequestDto stackDto) {
        return stackService.editStack(Long.parseLong(stackId), stackDto);
    }

    @DeleteMapping("/{stackId}")
    public StackInfoResponseDto deleteStack(@PathVariable Long stackId) {
        return stackService.deleteStack(stackId);
    }

    @GetMapping("")
    public Iterable<StackInfoResponseDto> getAllStacks() {
        return stackService.getAllStacks();
    }

    @GetMapping("/{stackName}/modulesAmount")
    public int getModulesAmount(@PathVariable String stackName) {
        return stackService.getModulesAmount(stackName);
    }
}
