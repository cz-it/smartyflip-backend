/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smartyflip.modules.service.ModuleService;
import smartyflip.modules.service.TagService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/modules")
public class TagController {

    final ModuleService moduleService;

    final TagService tagService;

    @PatchMapping("/{moduleId}/tags")
    public Iterable<String> addTagsToModule(@PathVariable Long moduleId, @RequestBody List<String> tagNames) {
        return tagService.addTagsToModule(moduleId, tagNames);
    }


    @DeleteMapping("/{moduleId}/tags/{tag}")
    public boolean deleteTag(@PathVariable Long moduleId, @PathVariable String tag) {
        return tagService.deleteTag(moduleId, tag);
    }

    @PostMapping("/{moduleId}/tags")
    public Iterable<String> findTagsByModuleId(@PathVariable Long moduleId) {
        return tagService.findTagsByModuleId(moduleId);
    }

    @DeleteMapping("/tags/{tagId}")
    public boolean deleteTag(@PathVariable long tagId) {
        return tagService.deleteTag(tagId);
    }
}
