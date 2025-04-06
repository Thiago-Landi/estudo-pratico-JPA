package com.Thiago_landi.libraryapi.controller.mappers;

import org.mapstruct.Mapper;

import com.Thiago_landi.libraryapi.controller.dto.UserDTO;
import com.Thiago_landi.libraryapi.model.UserClass;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserClass toEntity(UserDTO dto);
}
