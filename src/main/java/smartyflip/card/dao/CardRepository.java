/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartyflip.card.model.Card;

import java.util.stream.Stream;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Stream<Card> findCardsByModuleId(Long moduleId);


}
