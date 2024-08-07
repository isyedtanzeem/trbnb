package com.travelbnb.service;

import com.travelbnb.payload.AppUserDto;
import com.travelbnb.payload.LoginDto;

public interface UserService {

    AppUserDto createUserService(AppUserDto appUserDto);

    String verifyLogin(LoginDto loginDto);
}
