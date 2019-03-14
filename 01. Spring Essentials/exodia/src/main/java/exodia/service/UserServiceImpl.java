package exodia.service;

import exodia.domain.entities.User;
import exodia.domain.models.service.UserServiceModel;
import exodia.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(UserServiceModel serviceModel) {
        User user = this.modelMapper.map(serviceModel, User.class);
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));

        try {
            this.userRepository.saveAndFlush(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserServiceModel loginUser(UserServiceModel serviceModel) {
        User user = this.userRepository.findUserByUsername(serviceModel.getUsername()).orElse(null);

        if (user == null || !user.getPassword().equals(DigestUtils.sha256Hex(serviceModel.getPassword()))){
            return null;
        }

        return serviceModel;
    }
}
