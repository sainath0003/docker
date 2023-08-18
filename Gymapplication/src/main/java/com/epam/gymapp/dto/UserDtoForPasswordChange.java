package com.epam.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoForPasswordChange {
	private String userName;
	private String oldPassword;
	private String newPassword;
}
