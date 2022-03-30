/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kartaca.challenge.restController;

import com.kartaca.challenge.Enums.Roles;
import com.kartaca.challenge.Service.AccountService;
import com.kartaca.challenge.dto.NewUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping(value="/api/register",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void createUserJson(NewUserData data){
        data.setRole(Roles.ROLE_USER);
        accountService.saveUser(data);
    }
}
