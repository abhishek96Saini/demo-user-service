package com.abhishek.demouserservice.service;

import com.abhishek.demouserservice.dao.UserRepository;
import com.abhishek.demouserservice.dto.UserDto;
import com.abhishek.demouserservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

	private final ModelMapper modelMapper;

	private final UserRepository userRepository;

	/**
	 * create a new user. This method does not ensure if user is already registered.
	 * Please ensure user already exists.
	 * @param userDto new user details.
	 * @return saved user along with provided id field in the DTO.
	 */
	public UserDto createUser(final UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);

		user.setCreatedAt(LocalDate.now());
		user.setUpdatedAt(LocalDate.now());
		User save = userRepository.save(user);

		return modelMapper.map(save, UserDto.class);

	}

	/**
	 * get all users. List will be empty if no users exists.
	 * @return userDto list.
	 */
	public List<UserDto> getAllUsers(final Sort sort) {
		List<User> users = userRepository.findAll(sort);
		return modelMapper.map(users, new TypeToken<List<UserDto>>() {
		}.getType());
	}

}
