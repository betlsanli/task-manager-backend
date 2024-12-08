package com.example.tm.dto.AppUser;
import com.example.tm.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);
    // Entity to DTO (Excluding 'password')
    @Mapping(target = "password", ignore = true)  // Exclude the 'password' field
    AppUserDTO toDto(AppUser appUser);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "lastModifiedAt", ignore = true),
    })
    AppUser toEntity(AppUserDTO dto);

    List<AppUserDTO> toDtos(List<AppUser> appUsers);
}
