package io.github.amenski.digafmedia.infrastructure.converter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringToListAttributeConverter implements AttributeConverter<List<String>, String> {

    private static final String ATTRIBUTE_SEPARATOR = "#"; // persistence concern, not domain

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if(attribute == null || attribute.isEmpty()) return StringUtils.EMPTY;

        StringBuilder sb = new StringBuilder();
        attribute.forEach(val -> sb.append(val).append(ATTRIBUTE_SEPARATOR));
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return StringUtils.isNotBlank(dbData)
                ? Arrays.asList(dbData.split(ATTRIBUTE_SEPARATOR))
                : new ArrayList<>(0);
    }
}
