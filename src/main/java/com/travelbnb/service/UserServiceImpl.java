package com.travelbnb.service;

import com.travelbnb.entity.AppUser;
import com.travelbnb.payload.AppUserDto;
import com.travelbnb.payload.LoginDto;
import com.travelbnb.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private AppUserRepository appUserRepository;

    private JWTservice jwtService;

    public UserServiceImpl(AppUserRepository appUserRepository, JWTservice jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }


    @Override
    public AppUserDto createUserService(AppUserDto appUserDto) {

        AppUser appUser = dtoToEntity(appUserDto);
        appUser.setPassword(BCrypt.hashpw(appUserDto.getPassword(), BCrypt.gensalt(10)));
        AppUser savedUser = appUserRepository.save(appUser);
        AppUserDto aud = entityToDto(savedUser);
        return aud;
    }

    @Override
    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> byUserName = appUserRepository.findByUserName(loginDto.getUserName());
        if (byUserName.isPresent()) {
            AppUser appUser = byUserName.get();
            if (BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword())) {
                String token = jwtService.generateToken(appUser);
                return token;
            }
        }
        return null;
    }

    private AppUser dtoToEntity(AppUserDto aud) {
        AppUser au = new AppUser();
        au.setName(aud.getName());
        au.setUserName(aud.getUserName());
        au.setEmail(aud.getEmail());
        au.setPassword(aud.getPassword());
        return au;
    }

    private AppUserDto entityToDto(AppUser au) {
        AppUserDto aud = new AppUserDto();
        aud.setId(au.getId());
        aud.setName(au.getName());
        aud.setUserName(au.getUserName());
        aud.setEmail(au.getEmail());
        aud.setPassword(au.getPassword());
        return aud;

    }
}
