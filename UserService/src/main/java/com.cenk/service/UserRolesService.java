package com.cenk.service;

import com.cenk.repository.IUserRolesRepository;
import com.cenk.repository.entity.UserRoles;
import com.cenk.utility.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class UserRolesService extends ServiceManager<UserRoles,String> {

    private final IUserRolesRepository repository;

    public UserRolesService(IUserRolesRepository repository){
        super(repository);
        this.repository = repository;
    }


}
