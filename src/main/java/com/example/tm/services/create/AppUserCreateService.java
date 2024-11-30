package com.example.tm.services.create;

import com.example.tm.dto.request.create.AppUserCreate;
import com.example.tm.dto.request.create.mappers.AppUserCreateMapper;
import com.example.tm.dto.request.update.AppUserUpdate;
import com.example.tm.dto.request.update.mappers.AppUserUpdateMapper;
import com.example.tm.entities.AppUser;
import com.example.tm.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppUserCreateService {

    private final AppUserRepository appUserRepository;
    //private static final Logger log = LoggerFactory.getLogger(AppUserCreateService.class);
    private final AppUserCreateMapper appUserCreateMapper;
    private final AppUserUpdateMapper appUserUpdateMapper;

    public AppUserCreateService(AppUserRepository appUserRepository, AppUserCreateMapper appUserCreateMapper, AppUserUpdateMapper appUserUpdateMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserCreateMapper = appUserCreateMapper;
        this.appUserUpdateMapper = appUserUpdateMapper;
    }

    @Transactional
    public AppUser createUser(AppUserCreate newUser) {
        AppUser toSave = appUserCreateMapper.toEntity(newUser);
        return appUserRepository.save(toSave);
    }

    @Transactional
    public AppUser updateUser(UUID userId, AppUserUpdate updateUserRequest) { //update and create dtos are now same
        AppUser oldUser = appUserRepository.findById(userId).orElseThrow();

        AppUser toSave = appUserUpdateMapper.toEntity(updateUserRequest);
        toSave.setId(userId);

        return appUserRepository.save(toSave);
    }

}
