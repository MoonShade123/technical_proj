package com.example.technical_proj.service;

import com.example.technical_proj.model.Role;
import com.example.technical_proj.dto.UserDto;
import com.example.technical_proj.dto.dtoConverter.ToDtoConverter;
import com.example.technical_proj.exceptions.UserException;
import com.example.technical_proj.model.User;
import com.example.technical_proj.repository.RoleRepository;
import com.example.technical_proj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository, final RoleRepository roleRepository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signUp(final User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findRoleByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        this.userRepository.save(user);
    }

    public void createAdmin() {
        User adminAccount = new User();
        adminAccount.setUsername("admin");
        adminAccount.setPassword(bCryptPasswordEncoder.encode(("admin")));
        adminAccount.setEmail("email@admin.com");
        Role adminRole = roleRepository.findRoleByName("ADMIN");
        Role userRole = roleRepository.findRoleByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        roles.add(userRole);
        adminAccount.setRoles(roles);
        this.userRepository.save(adminAccount);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserDto> getAll() {
        Collection<User> users = this.userRepository.findAll();
        return users.stream()
                .map(ToDtoConverter::userToDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    public UserDto getById(final Long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new UserException("Can't get. User not found!")
        );
        return ToDtoConverter.userToDto(user);
    }

    @PreAuthorize("hasRole('USER')")
    public UserDto update(final User user) {
        this.userRepository.findById(user.getId()).orElseThrow(
                () -> new UserException("Can't update. User not found!")
        );
        this.userRepository.save(user);
        return ToDtoConverter.userToDto(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDto deactivate(final Long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new UserException("Can't deactivate. User not found!")
        );
        user.setActive(false);
        return ToDtoConverter.userToDto(user);
    }


    public User findOne(final String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(final User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

}
