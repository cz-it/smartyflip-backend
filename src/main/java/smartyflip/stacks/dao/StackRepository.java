/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartyflip.stacks.model.Stack;

import java.util.Optional;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
    Optional<Stack> findByStackNameIgnoreCase(String stackName);

}
