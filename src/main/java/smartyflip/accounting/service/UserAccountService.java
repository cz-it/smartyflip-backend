package smartyflip.accounting.service;

import jakarta.servlet.http.Cookie;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import smartyflip.accounting.dto.*;
import smartyflip.utils.PagedDataResponseDto;

import java.util.List;


public interface UserAccountService {

    UserResponseDto registerUser(UserRegistrationRequestDto data);

    UserResponseDto loginUser(UserLoginRequestDto data);

    List<Cookie> logoutUser();

    UserResponseDto findUserByUsername(String username);

    UserResponseDto findUserById(Integer userId);

    UserResponseDto deleteUserById(Integer userId);

    PagedDataResponseDto<UserResponseDto> findAll(PageRequest pageRequest);

    UserResponseDto updateUser(Integer userId, UserEditRequestDto data);

    UserResponseDto activateUserEmail(String key);

    ChangeRoleResponseDto changeRoleList(Integer userId, String roleName, Boolean isAddRole);

    void changePassword(Integer userId, String password);

    void uploadAvatar(Integer userId, MultipartFile file);

    void removeAvatar(Integer userId);

}
