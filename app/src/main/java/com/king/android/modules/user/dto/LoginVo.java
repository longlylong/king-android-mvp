package com.king.android.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginVo {
    private String account;

    private String psw;
}
