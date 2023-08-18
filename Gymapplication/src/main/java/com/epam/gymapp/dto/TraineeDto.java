package com.epam.gymapp.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeDto {

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@Nullable
	private String dob;
	@Nullable
	private String address;
//	@Email
	private String email;
	@Override
	public String toString() {
		return "firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", address=" + address
				+ ", email=" + email + "";
	}
	
	

}
