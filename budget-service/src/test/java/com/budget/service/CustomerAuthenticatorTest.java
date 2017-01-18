package com.budget.service;

import com.budget.model.dto.Customer;
import com.budget.model.repo.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by a579295 on 18.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class CustomerAuthenticatorTest {

    @Autowired
    private CustomerAuthenticator authenticator;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Customer existingCustomer;
    private Customer nonExistingCustomer;

    private final static String password = "password";
    private final static String non_existing_user_password = "non_existing_user_password";

    @Before
    public void setUp() throws Exception {
        customerRepository.deleteAll();
        existingCustomer = new Customer("username",  bCryptPasswordEncoder.encode(password), "kibarayhan@gmail.com");
        customerRepository.save(existingCustomer);
        nonExistingCustomer = new Customer("non_existing_user", non_existing_user_password, "kibarayhan@gmail.com");
    }

    @Test
    public void Register_GivenEmptyUsernameAndPassword_ShouldRegisterFailed(){
        boolean registerSuccess = authenticator.register("", nonExistingCustomer.getPassword(),
                nonExistingCustomer.getPassword(),
                nonExistingCustomer.getEmailAddress());
        assertEquals(false, registerSuccess);
    }

    @Test
    public void Register_GivenUsernameAndEmptyPassword_ShouldRegisterFailed(){
        boolean registerSuccess = authenticator.register(nonExistingCustomer.getName(), "",
                nonExistingCustomer.getPassword(),
                nonExistingCustomer.getEmailAddress());
        assertEquals(false, registerSuccess);
    }

    @Test
    public void Register_GivenUsernamePasswordEmptyPasswordAgain_ShouldRegisterFailed(){
        boolean registerSuccess = authenticator.register(nonExistingCustomer.getName(),
                nonExistingCustomer.getPassword(),
                "",
                nonExistingCustomer.getEmailAddress());
        assertEquals(false, registerSuccess);
    }

    @Test
    public void Register_GivenUsernamePasswordNotMatchPasswordAgain_ShouldRegisterFailed(){
        boolean registerSuccess = authenticator.register(nonExistingCustomer.getName(),
                nonExistingCustomer.getPassword(),
                "randomPassword",
                nonExistingCustomer.getEmailAddress());
        assertEquals(false, registerSuccess);
    }

    @Test
    public void Register_GivenUsernamePasswordPasswordAgainEmptyEmail_ShouldRegisterFailed(){
        boolean registerSuccess = authenticator.register(nonExistingCustomer.getName(),
                nonExistingCustomer.getPassword(),
                nonExistingCustomer.getPassword(),
                "");
        assertEquals(false, registerSuccess);
    }


    @Test
    public void Register_GivenUsernameAndPasswordForExistingCustomer_ShouldRegisterFailed(){
        boolean registerSuccess = authenticator.register(existingCustomer.getName(), password,
                password,
                existingCustomer.getEmailAddress());
        assertEquals(false, registerSuccess);
    }

    @Test
    public void Register_GivenUsernameAndPasswordForNonExistingCustomer_ShouldRegister(){
        boolean registerSuccess = authenticator.register(nonExistingCustomer.getName(), nonExistingCustomer.getPassword(),
                nonExistingCustomer.getPassword(), nonExistingCustomer.getEmailAddress());
        assertEquals(true, registerSuccess);

        Optional<Customer> registeredCustomer = customerRepository.findByName(nonExistingCustomer.getName());
        assertEquals(true, registeredCustomer.isPresent());
        assertEquals(nonExistingCustomer, registeredCustomer.get());
    }

    @Test
    public void Login_GivenUsernameAndPassword_ShouldLogin(){
        boolean loginSuccess = authenticator.login("username", "password");
        assertEquals("login failed", true, loginSuccess);
    }

    @Test
    public void Login_GivenEmptyUsernameAndPassword_ShouldLoginFailed(){
        boolean loginSuccess = authenticator.login("", "password");
        assertEquals("login failed", false, loginSuccess);
    }

    @Test
    public void Login_GivenUsernameAndEmptyPassword_ShouldLoginFailed(){
        boolean loginSuccess = authenticator.login("username", "");
        assertEquals("login failed", false, loginSuccess);
    }

    @Test
    public void Login_GivenUsernameAndPasswordForExistingCustomer_ShouldLogin(){
        boolean loginSuccess = authenticator.login(existingCustomer.getName(), password);
        assertEquals("login failed for existing customer", true, loginSuccess);
    }

    @Test
    public void Login_GivenUsernameAndWrongPasswordForExistingCustomer_ShouldLoginFailed(){
        boolean loginSuccess = authenticator.login(existingCustomer.getName(), "wrong_password");
        assertEquals(false, loginSuccess);
    }
    
    @Test
    public void Login_GivenUsernameAndPasswordForNonExistingCustomer_ShouldLoginFailed(){
        boolean loginSuccess = authenticator.login("non_existing_user", non_existing_user_password);
        assertEquals("login should fail for non existing customer", false, loginSuccess);
    }

}
