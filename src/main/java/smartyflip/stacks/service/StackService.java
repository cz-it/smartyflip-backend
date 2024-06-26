/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.service;

import smartyflip.stacks.dto.NewStackRequestDto;
import smartyflip.stacks.dto.StackInfoResponseDto;

public interface StackService {

    StackInfoResponseDto addStack(NewStackRequestDto stackDto);

    StackInfoResponseDto deleteStack(Long stackId);

    StackInfoResponseDto findStackById(Long stackId);

    StackInfoResponseDto editStack(Long stackId, NewStackRequestDto stackDto);

    Iterable<StackInfoResponseDto> getAllStacks();

    int getModulesAmount(String stackName);
}
