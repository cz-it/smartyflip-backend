/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.modules.model.Module;
import smartyflip.stacks.dao.StackRepository;
import smartyflip.stacks.dto.NewStackRequestDto;
import smartyflip.stacks.dto.StackInfoResponseDto;
import smartyflip.stacks.model.Stack;
import smartyflip.stacks.service.exceptions.StackAlreadyExistException;
import smartyflip.stacks.service.exceptions.StackNotFoundException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StackServiceImpl implements StackService, Runnable {

    private final StackRepository stackRepository;

    private final ModelMapper modelMapper;

    private boolean isStackExistsByName(String stackName) {
        return stackRepository.findByStackNameIgnoreCase(stackName.trim().toLowerCase().replaceAll("\\s+", "_")).isPresent();
    }


    @Transactional
    @Override
    public StackInfoResponseDto addStack(NewStackRequestDto stackDto) {
        if (isStackExistsByName(stackDto.getStackName())) {
            throw new StackAlreadyExistException("Stack with this name already exists");
        }

        Stack stack = modelMapper.map(stackDto, Stack.class);
        stackRepository.save(stack);
        return modelMapper.map(stack, StackInfoResponseDto.class);
    }


    @Transactional
    @Override
    public StackInfoResponseDto deleteStack(Long stackId) {
        Stack stack = stackRepository.findById(stackId)
                .orElseThrow(StackNotFoundException::new);
        stackRepository.delete(stack);
        return modelMapper.map(stack, StackInfoResponseDto.class);
    }

    @Override
    public StackInfoResponseDto findStackById(Long stackId) {
        Stack stack = stackRepository.findById(stackId)
                .orElseThrow(StackNotFoundException::new);
        return modelMapper.map(stack, StackInfoResponseDto.class);
    }

    @Override
    public StackInfoResponseDto editStack(Long stackId, NewStackRequestDto stackDto) {
        Stack existingStack = stackRepository.findById(stackId)
                .orElseThrow(StackNotFoundException::new);

        // Checking stackName for unique
        if (isStackExistsByName(stackDto.getStackName())) {
            throw new StackAlreadyExistException("Stack with this name already exists");
        }

        // Preserve the id of the existing entity
        long existingId = existingStack.getStackId();
        // Preserve the modules
        Set<Module> existingModules = existingStack.getModules();
        // Update the existingStack with StackDto's values
        modelMapper.map(stackDto, existingStack);
        // Replace the existing stack id and modules
        existingStack.setStackId(existingId);
        if (existingModules != null) {
            existingStack.setModules(existingModules);
        }
        stackRepository.save(existingStack);
        return modelMapper.map(existingStack, StackInfoResponseDto.class);
    }

    @Override
    public Iterable<StackInfoResponseDto> getAllStacks() {
        return stackRepository
                .findAll()
                .stream()
                .map(s -> modelMapper.map(s, StackInfoResponseDto.class))
                .toList();
    }

    @Override
    public int getModulesAmount(String stackName) {
        Stack stack = stackRepository.findByStackNameIgnoreCase(stackName).orElseThrow(StackNotFoundException::new);
        return stack.getModulesAmount();
    }

    @Override
    public void run() {

    }
}
