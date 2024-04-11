package com.abhishek.demouserservice.controller;

import com.abhishek.demouserservice.dto.UserDto;
import com.abhishek.demouserservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> createUser(@RequestBody final UserDto userDto) {

		UserDto registeredUserDto = userService.createUser(userDto);

		log.info("createUser successful with user ID {}", registeredUserDto.getId());
		return ResponseEntity.ok(registeredUserDto);
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) final String sortBy,
			@RequestParam(required = false) Sort.Direction sortDirection) {
		Sort sort = Sort.unsorted();
		if (sortBy != null) {
			sort = sortDirection != null ? Sort.by(sortDirection, sortBy) : Sort.by(sortBy);
		}
		return ResponseEntity.ok(userService.getAllUsers(sort));
	}

}
