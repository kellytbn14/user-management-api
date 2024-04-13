package com.example.usermanagementapi.facades;

import com.example.usermanagementapi.dtos.*;
import com.example.usermanagementapi.entities.Phone;
import com.example.usermanagementapi.entities.User;
import com.example.usermanagementapi.handlers.exceptions.BusinessException;
import com.example.usermanagementapi.mappers.PhoneMapper;
import com.example.usermanagementapi.mappers.UserMapper;
import com.example.usermanagementapi.security.jwt.TokenUtils;
import com.example.usermanagementapi.services.PhoneService;
import com.example.usermanagementapi.services.UserService;
import com.example.usermanagementapi.utils.CustomUtilService;
import com.example.usermanagementapi.utils.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.usermanagementapi.security.Constants.BEARER;

@Service
@Transactional
public class UserFacade {
    private final Logger log = LoggerFactory.getLogger(UserFacade.class);
    private final UserMapper userMapper;
    private final UserService userService;
    private final PhoneService phoneService;
    private final PhoneMapper phoneMapper;
    private final AuthenticationManager authenticationManager;

    public UserFacade(UserMapper userMapper, UserService userService, PhoneService phoneService, PhoneMapper phoneMapper, AuthenticationManager authenticationManager) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.phoneService = phoneService;
        this.phoneMapper = phoneMapper;
        this.authenticationManager = authenticationManager;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getName());
        CustomUtilService.ValidateRequired(request.getEmail());
        CustomUtilService.ValidateRequired(request.getPassword());

        CustomUtilService.ValidateEmail(request.getEmail());
        CustomUtilService.ValidatePassword(request.getPassword());

        userService.validateEmailExists(request.getEmail());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .isActive(Boolean.TRUE)
                .build();

        User userCreated = userService.save(user);

        if (Objects.nonNull(request.getPhones()) && !request.getPhones().isEmpty()) {
            List<Phone> phones = getPhones(request, userCreated);
            phoneService.saveAll(phones);
        }

        return CreateUserResponse.builder()
                .id(userCreated.getId())
                .name(userCreated.getName())
                .email(userCreated.getEmail())
                .password(userCreated.getPassword())
                .isActive(userCreated.getIsActive())
                .createdDate(userCreated.getCreatedDate())
                .lastUpdate(userCreated.getLastUpdate())
                .lastLogin(userCreated.getLastLogin())
                .build();
    }

    private static List<Phone> getPhones(CreateUserRequest request, User userCreated) {
        return request.getPhones().stream()
                .peek(phoneDto -> {
                    CustomUtilService.ValidateRequired(phoneDto.getNumber());
                    CustomUtilService.ValidateRequired(phoneDto.getCityCode());
                    CustomUtilService.ValidateRequired(phoneDto.getCountryCode());
                })
                .map(phoneDto -> Phone.builder()
                        .number(phoneDto.getNumber())
                        .cityCode(phoneDto.getCityCode())
                        .countryCode(phoneDto.getCountryCode())
                        .userId(userCreated.getId())
                        .createdDate(LocalDateTime.now())
                        .lastUpdate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
    }

    public UserDto changeActiveStatus(ChangeActiveStatusUserRequest request) {
        CustomUtilService.ValidateRequired(request.getUserId());
        CustomUtilService.ValidateRequired(request.getActive());

        User user = userService.findById(request.getUserId());
        user.setIsActive(request.getActive());
        User userUpdated = userService.update(user);
        return userMapper.toDto(userUpdated);
    }

    public Page<FilterUserResponse> filterUsersPage(FilterUserRequest filter, Pageable pageable) {
        return userService.filterUsersPage(filter, pageable);
    }

    public UserDto getUserWithPhones(UUID userId) {
        CustomUtilService.ValidateRequired(userId);

        User user = userService.findById(userId);
        List<Phone> phones = phoneService.findAllByUserId(user.getId());

        UserDto userDto = userMapper.toDto(user);
        List<PhoneDto> phonesDto = phoneMapper.toDto(phones);
        userDto.setPhones(phonesDto);

        return userDto;
    }

    public UserAuthDto authentication(UserAuthRequest request) {
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getEmail());
        CustomUtilService.ValidateRequired(request.getPassword());
        User user = userService.findByEmail(request.getEmail());

        if (!user.getIsActive()) {
            throw new BusinessException(MessageResponse.USER_NOT_ACTIVE);
        }

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final var jwt = TokenUtils.generateToken(authentication);

        UserAuthDto userAuth = new UserAuthDto();
        userAuth.setTokenType(BEARER);
        userAuth.setToken(jwt);

        user.setLastLogin(LocalDateTime.now());
        userService.update(user);
        return userAuth;
    }
}
