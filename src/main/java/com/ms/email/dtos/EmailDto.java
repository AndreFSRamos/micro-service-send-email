package com.ms.email.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class EmailDto {

	@NotBlank(message = "O campo ownerRef é obrigatório.")
    private String ownerRef;
	
	@NotBlank(message = "O campo emailFrom é obrigatório.")
    @Email(message = "Formato de email invalido.")
    private String emailFrom;
	
	@NotBlank(message = "O campo emailTo é obrigatório.")
    @Email(message = "Formato de email invalido.")
    private String emailTo;
	
	@NotBlank(message = "O campo subject é obrigatório.")
    private String subject;
	
	@NotBlank(message = "O campo origin é obrigatório.")
	private String origin;
	
	@NotNull(message = "O campo username é obrigatório.")
    private String username;
	
	@NotBlank(message = "O campo text é obrigatório.")
    private String text;

	@NotBlank(message = "O campo jwtToken é obrigatório.")
	private String jwtToken;

}
