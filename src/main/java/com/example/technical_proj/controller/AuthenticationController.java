package com.example.technical_proj.controller;

import com.example.technical_proj.dto.dtoConverter.ToDtoConverter;
import com.example.technical_proj.dto.UserDto;
import com.example.technical_proj.model.AuthToken;
import com.example.technical_proj.model.LoginUser;
import com.example.technical_proj.security.TokenProvider;
import com.example.technical_proj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class AuthenticationController {
    public String currentUsername;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        currentUsername = jwtTokenUtil.getUsernameFromToken(token);
        return ResponseEntity.ok(new AuthToken(token));
    }

    //    @PreAuthorize("hasRole('USER')")
    @GetMapping("/current-user")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto userDto = ToDtoConverter.userToDto(userService.findOne(currentUsername));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
