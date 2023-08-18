package com.epam.gymapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.dto.UserDtoForPasswordChange;
import com.epam.gymapp.exception.UserNotFoundException;
import com.epam.gymapp.model.User;
import com.epam.gymapp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userService;

	User user;
	UserDto userDto;

	@BeforeEach
	void setup() {
		user = new User(1, "sai", "sai", "sai@gmail.com", "123456", true, null, null, null);
		userDto = new UserDto("sai", "123456");
	}

	@Test
	void testSave() {
		when(userRepository.save(any())).thenReturn(user);
		User result = userService.save(user);
		
		assertEquals(user, result);
	}

	@Test
	void testSaveNullUser() {
	
		when(userRepository.save(any())).thenThrow(NullPointerException.class);
		assertThrows(NullPointerException.class, () -> userService.save(user));

	}

	@Test
	void testUpdate() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
		when(userRepository.save(any())).thenReturn(user);
		User result = userService.update(user);
		
		assertEquals(user, result);
	}

	@Test
	void testUpdateNullUser() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.update(user));

	}

	@Test
	void testView() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
		User result = userService.view("sai");
		assertEquals(user, result);
	}

	@Test
	void testViewNullUser() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.view("sai"));

	}

	@Test
	void testDelete() {
		userService.delete("hello");

		Mockito.verify(userRepository).deleteByUserName(anyString());

	}

	@Test
	void testLogin() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
		boolean result = userService.login(userDto);
		assertTrue(result);
		
	}

	@Test
	void testUnsucessfullLogin() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
		UserDto userDto = new UserDto("sai", "123"); 
		boolean result = userService.login(userDto);
		assertFalse(result);
		
	}
	
	@Test
	void testUnsucessfulLogin() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		UserDto userDto = new UserDto("sai", "123"); 
		assertThrows(UserNotFoundException.class, () -> userService.login(userDto));

		
	}
	
	@Test
	void testChangePassword() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
		UserDtoForPasswordChange userDto = new UserDtoForPasswordChange("sai", "123456", "123");
		boolean result = userService.changeLogin(userDto);
		assertTrue(result);
		
	}
	@Test
	void testChangePasswordunsucessfull() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
		UserDtoForPasswordChange userDto = new UserDtoForPasswordChange("sai", "123", "123");
		boolean result = userService.changeLogin(userDto);
		assertFalse(result);
		
	}
	
	@Test
	void testChangePasswordunsucessful() {
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		UserDtoForPasswordChange userDto = new UserDtoForPasswordChange("sai", "123", "123");
		assertThrows(UserNotFoundException.class, () -> userService.changeLogin(userDto));

	}
	
	@Test
	void testSetStatus() {
		user.setActive(false);
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
		assertTrue(userService.setStatus(userDto));
	}
	
	@Test
	void testSetStatusUnsucessfull() {
		user.setActive(false);
		when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.setStatus(userDto));

	}
	@Test
	void testSetStatusUnsucessful() {
		user.setActive(false);
		when(userRepository.findByUserName(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> userService.setStatus(userDto));

	}

}
