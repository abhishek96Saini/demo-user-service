package com.abhishek.demouserservice.controller;

import com.abhishek.demouserservice.dto.UserDto;
import com.abhishek.demouserservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
	public void shouldCreateNewUser() throws Exception {

		UserDto userDto = new UserDto();
		userDto.setAge(23);
		userDto.setExperience(1);
		userDto.setName("Test Person");

		UserDto responseDto = new UserDto();
		responseDto.setId(1);
		responseDto.setName(userDto.getName());
		responseDto.setAge(userDto.getAge());
		responseDto.setExperience(userDto.getExperience());

		when(userService.createUser(any(UserDto.class))).thenReturn(responseDto);
		mockMvc
			.perform(MockMvcRequestBuilders.post("/api/v1/user")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(userDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Test Person"))
			.andExpect(jsonPath("$.age").value(userDto.getAge()));
	}

	@Test
	public void getAllUsersSortedByAgeDesc() throws Exception {
		UserDto userDto1 = new UserDto();
		userDto1.setAge(23);
		userDto1.setExperience(1);
		userDto1.setName("Test Person");
		UserDto userDto2 = new UserDto();
		userDto2.setAge(45);
		userDto2.setExperience(12);
		userDto2.setName("Test Person 2");

		List<UserDto> userDtoList = new ArrayList<>();
		userDtoList.add(userDto1);
		userDtoList.add(userDto2);

		userDtoList.sort((u1, u2) -> u2.getAge() - u1.getAge());

		when(userService.getAllUsers(any(Sort.class))).thenReturn(userDtoList);

		mockMvc
			.perform(MockMvcRequestBuilders.get("/api/v1/user")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.param("sortBy", "age")
				.param("sortDirection", "DESC"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value(userDto2.getName()));
	}

	@Test
	public void getAllUsersNoSorting() throws Exception {
		UserDto userDto1 = new UserDto();
		userDto1.setAge(23);
		userDto1.setExperience(1);
		userDto1.setName("Test Person");
		UserDto userDto2 = new UserDto();
		userDto2.setAge(45);
		userDto2.setExperience(12);
		userDto2.setName("Test Person 2");

		List<UserDto> userDtoList = new ArrayList<>();
		userDtoList.add(userDto1);
		userDtoList.add(userDto2);

		when(userService.getAllUsers(any(Sort.class))).thenReturn(userDtoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user").contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value(userDto1.getName()));
	}

}