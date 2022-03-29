/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kartaca.challenge.dto;

import com.kartaca.challenge.Enums.Roles;

public class NewUserData extends UserData{
    private String password;

    @Override
    public Roles getRole() {
        return super.getRole();
    }

    @Override
    public void setRole(Roles role) {
        super.setRole(role);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId( Long id) {
        super.setId(id);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername( String username) {
        super.setUsername(username);
    }

    @Override
    public Long getPhone() {
        return super.getPhone();
    }

    @Override
    public void setPhone( Long phone) {
        super.setPhone(phone);
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public void setAddress( String address) {
        super.setAddress(address);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail( String email) {
        super.setEmail(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}