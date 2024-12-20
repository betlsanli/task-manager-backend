package com.example.tm.services;

import com.example.tm.dto.AppUser.AppUserRequestDTO;
import com.example.tm.dto.AppUser.AppUserMapper;
import com.example.tm.dto.AppUser.AppUserResponseDTO;
import com.example.tm.entities.AppUser;
import com.example.tm.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    //private static final Logger log = LoggerFactory.getLogger(AppUserService.class);

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, AppUserMapper appUserMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
    }

    public List<AppUserResponseDTO> getAll() {
        List<AppUser> users = appUserRepository.findAll();
        return appUserMapper.toDtos(users);
    }

    public AppUserResponseDTO getById(UUID id) {
        AppUser user = appUserRepository.findById(id).orElseThrow();
        return appUserMapper.toDto(user);
    }
    public AppUserResponseDTO getByEmail(String email) {
        AppUser user = appUserRepository.findByEmail(email).orElseThrow();
        return appUserMapper.toDto(user);
    }

    public List<AppUserResponseDTO> getAllByIds(List<UUID> ids) {
        List<AppUser> users = appUserRepository.findAllById(ids);
        return appUserMapper.toDtos(users);
    }

    public long getTotalCount() {
        return appUserRepository.count();
    }

    public boolean deleteById(UUID id) {
        getById(id);
        appUserRepository.deleteById(id);
        return true;
    }

    @Transactional
    public AppUserResponseDTO createUser(AppUserRequestDTO newUser) {
        AppUser toSave = appUserMapper.toEntity(newUser);
        return appUserMapper.toDto(appUserRepository.save(toSave));
    }

    @Transactional
    public AppUserResponseDTO updateUser(UUID userId, AppUserRequestDTO updateUserRequest) {
        AppUser oldUser = appUserRepository.findById(userId).orElseThrow();

        AppUser toSave = appUserMapper.toEntity(updateUserRequest);
        toSave.setId(userId);

        return appUserMapper.toDto(appUserRepository.save(toSave));
    }
}
