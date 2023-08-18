package com.epam.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private String userName;
	private String password;
	@Override
	public String toString() {
		return " userName=" + userName + ",\n password=" + password + "";
	}
	
	
}
