package com.cenk.service;

import com.cenk.dto.request.UserFindRequestDto;
import com.cenk.dto.request.*;
import com.cenk.dto.response.FindResponseDto;
import com.cenk.exception.ErrorType;
import com.cenk.exception.UserException;
import com.cenk.mapper.IUserMapper;
import com.cenk.rabbitmq.model.CreateUserModel;
import com.cenk.repository.IUserRepository;
import com.cenk.repository.entity.UserProfile;
import com.cenk.utility.JwtTokenManager;
import com.cenk.utility.ServiceManager;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserRepository iUserRepository;
    private final JwtTokenManager jwtTokenManager;

    public UserProfileService(IUserRepository iUserRepository,
                              JwtTokenManager jwtTokenManager){
        super(iUserRepository);
        this.iUserRepository = iUserRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public void save(UserSaveRequestDto dto){
        save(IUserMapper.INSTANCE.toUser(dto));
    }

    public void save(CreateUserModel model){
        save(IUserMapper.INSTANCE.toUserProfile(model));
    }

    public List<FindResponseDto> findNameOrSurname(UserFindRequestDto dto){
        List<UserProfile> users = iUserRepository.findByNameStartsWithIgnoreCaseOrSurnameStartsWithIgnoreCase(
                dto.getName(),dto.getName());
        if(users.isEmpty())
            throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
        List<FindResponseDto> foundUserlist = new ArrayList<>();
        users.forEach(user -> {
            foundUserlist.add(IUserMapper.INSTANCE.toFindResponseDto(user));
        });
        return foundUserlist;
    }
    public void update(UserUpdateRequestDto dto){
        Optional<Long> authid = jwtTokenManager.getIdFromToken(dto.getToken());
        if(authid.isEmpty())
            throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
        Optional<UserProfile> userProfile = iUserRepository.findOptionalByAuthid(authid.get());
        if(userProfile.isPresent()){
            UserProfile profile = userProfile.get();
            profile.setAddress(dto.getAddress());
            profile.setAvatar(dto.getAvatar());
            profile.setGender(dto.getGender());
            profile.setName(dto.getName());
            profile.setPhone(dto.getPhone());
            profile.setSurname(dto.getSurname());
            update(profile);
        }
    }

}
