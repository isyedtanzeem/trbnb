package com.travelbnb.controller;



import com.travelbnb.payload.AppUserDto;
import com.travelbnb.payload.JWTTokenDto;
import com.travelbnb.payload.LoginDto;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private AppUserRepository appUserRepository;
    private UserService userService;

    public UserController(AppUserRepository appUserRepository, UserService userService) {
        this.appUserRepository = appUserRepository;
        this.userService = userService;
    }
//localhost:8080/api/v1/createUser
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody AppUserDto appUserDto){
if (appUserRepository.existsByEmail(appUserDto.getEmail())){
    return new ResponseEntity<>("Email Exist", HttpStatus.BAD_REQUEST);

}
if (appUserRepository.existsByUserName(appUserDto.getUserName())){
    return new ResponseEntity<>("User Name Exist", HttpStatus.BAD_REQUEST);

}

        AppUserDto ud = userService.createUserService(appUserDto);

        return new ResponseEntity<>(ud, HttpStatus.CREATED);
    }
@PostMapping("/login")
public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto) {

    String token = userService.verifyLogin(loginDto);
    if (token != null) {
        JWTTokenDto jwtTokenDto = new JWTTokenDto();
        jwtTokenDto.setType("JWT Token");
        jwtTokenDto.setToken(token);
        return new ResponseEntity<>(jwtTokenDto, HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Invalid Username/Password", HttpStatus.BAD_REQUEST);
    }
}


}
