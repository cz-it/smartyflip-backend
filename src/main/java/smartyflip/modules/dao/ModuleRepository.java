/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartyflip.modules.model.Module;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    Module findByModuleId(Long moduleId);

    //Page<Module> findModulesByModuleNameIgnoreCase(String moduleName, PageRequest pageRequest);

    Page<Module> findByStackNameIgnoreCase(String stackName, PageRequest pageRequest);

    @Query("select m from Module m inner join m.tags t where lower(t.tag) = lower(:tagName)")
    Stream<Module> findAllByTags(Set<String> tagName);

    Page<Module> findModulesByUserAccountId(Integer userId, PageRequest pageRequest);

    Page<Module> findAllByDateCreatedBetween(LocalDateTime from, LocalDateTime to, PageRequest pageRequest);

    Page<Module> findModulesByModuleNameLikeIgnoreCase(String moduleName, PageRequest pageRequest);

    @Query("SELECT m FROM Module m WHERE m.stack.stackId = :stackId")
    Page<Module> findModulesByStackId(@Param("stackId") Long stackId, PageRequest pageRequest);
}
