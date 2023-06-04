package com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.UserDTO;
import com.entity.Role;
import com.entity.User;
import com.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	
	public UserDTO login(String email, String password) {
		Optional<User> result = userRepository.findByEmail(email);
		if (result.isPresent()) {
			User user = result.get();
			if (user.getPassword().equals(password)) {	// Nếu khớp mật khẩu thì trả về user đó
				return new UserDTO(user);
			} else {
				return null;
			}
		} else {
			return null;	// Nếu k có user nào khớp email thì trả về null
		}
	}
	
	public UserDTO getUser(int userId) {
		Optional<User> result = userRepository.findById(userId);
		if (result.isPresent()) {
			return new UserDTO(result.get());
		} else return null;
	}
	
	public List<UserDTO> getAllUsers() {
		return listUserTolistUserDTO(userRepository.findAll());
	}
	
	public void addNewUser(UserDTO newUserDTO) {
		Role role = roleService.getRole(newUserDTO.getRoleId());
		User newUser = new User(newUserDTO);
		
		newUser.setRole(role);
		userRepository.save(newUser);
	}
	
	public void lockUser(int userId) {
		Optional<User> result = userRepository.findById(userId);
		if (result.isPresent()) {
			User user = result.get();
			
			int status = user.getStatus() == 0 ? 1 : 0;
			user.setStatus(status);
			
			userRepository.save(user);
		}
	}
	
	public void updateUser(int userId, String fullName, String phoneNumber, String address, int roleId) {
		Optional<User> result = userRepository.findById(userId);
		if (result.isPresent()) {
			User user = result.get();
			user.setFullName(fullName);
			user.setPhoneNumber(phoneNumber);
			user.setAddress(address);
			
			// Kiểm tra role có thay đổi không, nếu thay đổi thì update lại
			if (user.getRole().getId() != roleId) {
				Role role = roleService.getRole(roleId);
				user.setRole(role);
			}
			userRepository.save(user);
		}
	}
	
	public void deleteUser(int userId) {
		userRepository.deleteById(userId);	
	}
	
	public List<UserDTO> listUserTolistUserDTO(List<User> users) {
		return users.stream().map((user) -> new UserDTO(user)).collect(Collectors.toList());
	}
}
