package com.ms.email.integrationstests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuth {
    private String UserName;
    private String password;
}
