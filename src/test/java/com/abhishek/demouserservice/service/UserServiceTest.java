package com.abhishek.demouserservice.service;

import com.abhishek.demouserservice.dao.UserRepository;
import com.abhishek.demouserservice.dto.UserDto;
import com.abhishek.demouserservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private final ModelMapper modelMapper = new ModelMapper();

	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void init() {
		userService = new UserService(modelMapper, userRepository);
	}

	@Test
	public void testCreateUserShouldBeSuccessful() {
		UserDto userDto = new UserDto(1, "John Doe", 36, 12);
		User user = new User(1, "John Doe", 36, 12, LocalDate.now(), LocalDate.now());

		Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

		UserDto savedUserDto = userService.createUser(userDto);

		assertEquals(userDto.getId(), savedUserDto.getId());
		assertEquals(userDto.getName(), savedUserDto.getName());
		assertEquals(userDto.getExperience(), savedUserDto.getExperience());
	}

	@Test
	public void testGetAllUsersWillBeEmptyForZeroUsers() {
		Mockito.when(userRepository.findAll(Sort.unsorted())).thenReturn(Collections.emptyList());

		List<UserDto> users = userService.getAllUsers(Sort.unsorted());

		assertTrue(users.isEmpty());
	}

	@Test
	public void testGetAllUsersWithUnsortedData() {
		List<User> users = Arrays.asList(new User(1, "Alice Smith", 23, 4, LocalDate.now(), LocalDate.now()),
				new User(2, "Bob Jones", 23, 4, LocalDate.now(), LocalDate.now()));
		List<UserDto> userDtoList = Arrays.asList(new UserDto(1, "Alice Smith", 23, 4),
				new UserDto(2, "Bob Jones", 23, 4));

		Mockito.when(userRepository.findAll(any(Sort.class))).thenReturn(users);

		List<UserDto> result = userService.getAllUsers(Sort.unsorted());

		assertEquals(userDtoList.size(), result.size());
	}

}