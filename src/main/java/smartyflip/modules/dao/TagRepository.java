/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartyflip.modules.model.Tag;
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByTag(String tag);

}
