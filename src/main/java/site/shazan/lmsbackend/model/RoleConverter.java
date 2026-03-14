package site.shazan.lmsbackend.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class RoleConverter implements AttributeConverter<Role, Short> {

    @Override
    public Short convertToDatabaseColumn(Role attribute) {
        return attribute == null ? Role.USER.getDbValue() : attribute.getDbValue();
    }

    @Override
    public Role convertToEntityAttribute(Short dbData) {
        return Role.fromDbValue(dbData);
    }
}

