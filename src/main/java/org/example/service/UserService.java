package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Customer;
import org.example.entity.User;
import org.example.exception.BusinessException;
import org.example.mapper.UserMapper;
import org.example.model.user.UserRequest;
import org.example.model.user.UserResponse;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.map(userRequest);
        return userMapper.map(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userMapper.map(userRepository.findAll());
    }

    public UserResponse findById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException("Cannot find the user with ID" + id));
        return userMapper.map(user);
    }

    public void update(Integer id, UserRequest userRequest) {
        User userToUpdate = userRepository.findById(userRequest.getId()).orElseThrow(
                () -> new BusinessException(String.format("User with id: %s is not found", id))
        );
        userToUpdate.setId(userRequest.getId());
        userToUpdate.setName(userRequest.getName());
        userToUpdate.setPassword(userRequest.getPassword());
    }

    public void deleteById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException("Cannot find the user with ID" + id));
        userRepository.delete(user);
    }


}
