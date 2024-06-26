/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service.util;

import org.modelmapper.AbstractConverter;
import smartyflip.modules.model.Tag;

public class TagToStringConverter extends AbstractConverter<Tag, String> {
    @Override
    protected String convert(Tag source) {
        return source.getTag().trim().toLowerCase().replaceAll("\\s+", "_");
    }

    public String convertToDatabaseColumn(String tag) {
        return tag;
    }
}