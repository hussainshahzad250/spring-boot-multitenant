package com.sas.multitenant.request;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String password;

}
