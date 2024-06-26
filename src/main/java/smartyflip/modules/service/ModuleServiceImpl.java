/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.accounting.dao.UserRepository;
import smartyflip.accounting.dto.exceptions.UserNotFoundException;
import smartyflip.accounting.model.UserAccount;
import smartyflip.card.dao.CardRepository;
import smartyflip.card.service.exceptions.InvalidSubscriptionTypeException;
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.modules.dao.TagRepository;
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dto.FindModuleByNameRequestDto;
import smartyflip.modules.dto.ModuleInfoResponseDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.model.Module;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;
import smartyflip.modules.service.exceptions.UnauthorizedAccessException;
import smartyflip.modules.service.util.RegisteredModule;
import smartyflip.modules.service.util.TrialModule;
import smartyflip.stacks.dao.StackRepository;
import smartyflip.stacks.model.Stack;
import smartyflip.stacks.service.exceptions.StackNotFoundException;
import smartyflip.utils.PagedDataResponseDto;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    public static final String GUEST_TYPE = "GUEST";
    public static final String USER_TYPE = "USER";

    private final ModuleRepository moduleRepository;

    private final CardRepository cardRepository;

    private final StackRepository stackRepository;

    private final TagRepository tagRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public ModuleInfoResponseDto addModule(NewModuleDto newModuleDto) {

        String subscriptionType = "USER";

        Stack stack = stackRepository.findByStackNameIgnoreCase(newModuleDto.getStackName()).orElseThrow(StackNotFoundException::new);
        UserAccount userAccount = userRepository.findByUsernameIgnoreCase(newModuleDto.getUsername()).orElseThrow(UserNotFoundException::new);

        if ("GUEST".equalsIgnoreCase(subscriptionType) && !isUserAdmin(userAccount)) {
            throw new InvalidSubscriptionTypeException("Creating a GUEST module is restricted to ADMIN users only.");
        }

        Module module;
        if ("GUEST".equalsIgnoreCase(subscriptionType)) {
            module = modelMapper.map(newModuleDto, TrialModule.class);
            ((TrialModule) module).setSubscriptionType("GUEST");
        } else if ("USER".equalsIgnoreCase(subscriptionType)) {
            module = modelMapper.map(newModuleDto, RegisteredModule.class);
            ((RegisteredModule) module).setSubscriptionType("USER");
        } else {
            throw new InvalidSubscriptionTypeException("Invalid subscription type.");
        }

        module.setUserAccount(userAccount);
        module.setUserName(userAccount.getUsername());
        module.setStack(stack);
        module = moduleRepository.save(module);
        return moduleToModuleInfoResponseDto(module);
    }

    @Transactional
    public ModuleInfoResponseDto editModule(NewModuleDto newModuleDto, Long moduleId) {

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(ModuleNotFoundException::new);
        UserAccount userAccount = userRepository.findByUsernameIgnoreCase(newModuleDto.getUsername()).orElseThrow(UserNotFoundException::new);

        if (!isUserAdmin(userAccount) && !module.getUserAccount().getUsername().equals(newModuleDto.getUsername())) {
            throw new UnauthorizedAccessException("User is not authorized to edit this module");
        }

        Stack stack = stackRepository.findByStackNameIgnoreCase(newModuleDto.getStackName()).orElseThrow(StackNotFoundException::new);
        module.setStack(stack);
        module.setModuleName(newModuleDto.getModuleName());
        module = moduleRepository.save(module);
        return moduleToModuleInfoResponseDto(module);
    }

    @Transactional(readOnly = true)
    @Override
    public PagedDataResponseDto<ModuleInfoResponseDto> findModulesByUserId(Integer userId, PageRequest pageRequest) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        Page<Module> modulePage = moduleRepository.findModulesByUserAccountId(userId, pageRequest);
        return getPagedDataResponseDto(modulePage);
    }

    @Transactional(readOnly = true)
    @Override
    public ModuleInfoResponseDto findModuleById(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        return moduleToModuleInfoResponseDto(module);
    }

    @Transactional
    @Override
    public ModuleInfoResponseDto deleteModule(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        moduleRepository.delete(module);
        return moduleToModuleInfoResponseDto(module);
    }

    @Transactional(readOnly = true)
    @Override
    public PagedDataResponseDto<ModuleInfoResponseDto> findModulesByPeriod(DatePeriodDto datePeriodDto, PageRequest pageRequest) {
        Page<Module> modulePage = moduleRepository.findAllByDateCreatedBetween(datePeriodDto.getDateFrom().atStartOfDay(), datePeriodDto.getDateTo().atStartOfDay(), pageRequest);
        return getPagedDataResponseDto(modulePage);
    }

    @Transactional(readOnly = true)
    @Override
    public PagedDataResponseDto<ModuleInfoResponseDto> findModulesByName(FindModuleByNameRequestDto moduleName, PageRequest pageRequest) {
        Page<Module> modulePage = moduleRepository.findModulesByModuleNameLikeIgnoreCase("%" + moduleName.getName() + "%", pageRequest);
        return getPagedDataResponseDto(modulePage);
    }

    @Transactional(readOnly = true)
    @Override
    public PagedDataResponseDto<ModuleInfoResponseDto> findAllModules(PageRequest pageRequest) {
        Page<Module> modulePage = moduleRepository.findAll(pageRequest);
        return getPagedDataResponseDto(modulePage);
    }

    @Transactional(readOnly = true)
    @Override
    public PagedDataResponseDto<ModuleInfoResponseDto> findModulesByStackId(Long stackId, PageRequest pageRequest) {
        if (!stackRepository.existsById(stackId)) {
            throw new StackNotFoundException();
        }
        Page<Module> modulePage = moduleRepository.findModulesByStackId(stackId, pageRequest);
        return getPagedDataResponseDto(modulePage);
    }

    @Transactional(readOnly = true)
    @Override
    public int cardsAmountByModuleId(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        return (module.getCards() != null) ? module.getCardsAmount() : 0;
    }

//    @Override
//    public PagedDataResponseDto<ModuleInfoResponseDto> findModulesByStackId(Long id, PageRequest pageRequest) {
//        Page<Module> modulePage = moduleRepository.findModulesByStackId(id, pageRequest);
//        return getPagedDataResponseDto(modulePage);
//    }

    private boolean isUserAdmin(UserAccount userAccount) {
        return userAccount.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMINISTRATOR"));
    }

    private PagedDataResponseDto<ModuleInfoResponseDto> getPagedDataResponseDto(Page<Module> modulePage) {
        PagedDataResponseDto<ModuleInfoResponseDto> pagedDataResponseDto = new PagedDataResponseDto<>();
        pagedDataResponseDto.setData(modulePage.getContent().stream()
                .map(this::moduleToModuleInfoResponseDto)
                .collect(Collectors.toList()));
        pagedDataResponseDto.setTotalElements(modulePage.getTotalElements());
        pagedDataResponseDto.setTotalPages(modulePage.getTotalPages());
        return pagedDataResponseDto;
    }

    private Module findModuleOrThrow(Long moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(ModuleNotFoundException::new);
    }

    private ModuleInfoResponseDto moduleToModuleInfoResponseDto(Module module) {
        ModuleInfoResponseDto mapped = modelMapper.map(module, ModuleInfoResponseDto.class);
        mapped.setStackId(module.getStack().getStackId());
        return mapped;
    }

}
