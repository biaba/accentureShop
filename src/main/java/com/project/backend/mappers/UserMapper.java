package com.project.backend.mappers;

import com.project.backend.models.UserActivity;
import com.project.backend.models.modelsFront.UserFront;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserFront getUserFrontFromUserActivity(UserActivity userActivity);
}
