package com.revature.RevPay.controllers;

import com.revature.RevPay.entities.AccountType;
import com.revature.RevPay.entities.User;
import com.revature.RevPay.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;



    @Test
    void testRegister() throws Exception {
        Integer testId = 1;
        AccountType testType = AccountType.PERSONAL;
        String testUsername = "test user";
        String testPassword = "test pass";
        String testEmail = "test@email.com";
        String testPhoneNumber = "test-phone-number";
        User testUser = new User(testId, testType, testUsername, testPassword, testEmail, testPhoneNumber);
        when(userService.findByUsername(testUsername)).thenReturn(testUser);

        this.mockMvc.perform(get("/user/" + testUsername)).andDo(print()).andExpect(status().isOk());
    }
}
