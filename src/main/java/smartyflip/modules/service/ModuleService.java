/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;

import org.springframework.data.domain.PageRequest;
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dto.FindModuleByNameRequestDto;
import smartyflip.modules.dto.ModuleInfoResponseDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.utils.PagedDataResponseDto;

public interface ModuleService {
    ModuleInfoResponseDto addModule(NewModuleDto newModuleDto);

    ModuleInfoResponseDto findModuleById(Long moduleId);

    ModuleInfoResponseDto editModule(NewModuleDto newModuleDto, Long moduleId);

    ModuleInfoResponseDto deleteModule(Long moduleId);

    PagedDataResponseDto<ModuleInfoResponseDto> findModulesByPeriod(DatePeriodDto datePeriodDto, PageRequest pageRequest);

    PagedDataResponseDto<ModuleInfoResponseDto> findModulesByName(FindModuleByNameRequestDto nameRequestDto, PageRequest pageRequest);

    PagedDataResponseDto<ModuleInfoResponseDto> findModulesByUserId(Integer userId, PageRequest pageRequest);

    PagedDataResponseDto<ModuleInfoResponseDto> findAllModules(PageRequest pageRequest);

    PagedDataResponseDto<ModuleInfoResponseDto> findModulesByStackId(Long stackId, PageRequest pageRequest);

//    Iterable<ModuleDto> finAllModulesByTags(Iterable<String> tags);

    int cardsAmountByModuleId(Long moduleId);

}
