/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;


public interface TagService {

    Iterable<String> addTagsToModule(Long moduleId, Iterable<String> tags);

    boolean deleteTag(Long moduleId, String tag);

    Iterable<String> findTagsByModuleId(Long moduleId);

    Iterable<String> findAllTags();

    boolean deleteTag(long tagId);
}
