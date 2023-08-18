package com.epam.gymapp.service;

import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.dto.UserDtoForPasswordChange;
import com.epam.gymapp.model.User;

public interface UserService {
	public User save(User user);

	public User view(String userName);

	public Boolean login(UserDto userDto);

	public boolean changeLogin(UserDtoForPasswordChange userDto);

	public void delete(String userName);
	
	public boolean setStatus(UserDto userDto);
}
