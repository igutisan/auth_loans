package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.CreateUserDto;
import co.com.pragma.api.dto.UserResponseDto;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.UserRoles;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    UserResponseDto toResponseDto(User user);

    List<UserResponseDto> toResponseDtoList(List<User> users);

    User toModel(CreateUserDto dto);



}
