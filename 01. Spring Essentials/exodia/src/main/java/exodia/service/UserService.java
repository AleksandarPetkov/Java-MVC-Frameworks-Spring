package exodia.service;

import exodia.domain.models.service.UserServiceModel;

public interface UserService {

    boolean registerUser(UserServiceModel serviceModel);

    UserServiceModel loginUser(UserServiceModel serviceModel);
}
