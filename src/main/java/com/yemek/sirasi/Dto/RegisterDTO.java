package com.yemek.sirasi.Dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private String username;
    private String empName;
    private String email;
    private String password;
    private String confirmPass;


}

