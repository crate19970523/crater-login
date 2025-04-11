package com.crater.craterlogin.controller;

import com.crater.craterlogin.bean.dto.editAccountService.AddAccountDto;
import com.crater.craterlogin.bean.request.editAccountController.AddAccountRequest;
import com.crater.craterlogin.exception.AccountException;
import com.crater.craterlogin.service.EditAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EditAccountControllerTest {
    private final EditAccountController testTarget = new EditAccountController();
    private EditAccountService editAccountService;

    @BeforeEach
    public void setUp() {
        editAccountService = Mockito.mock(EditAccountService.class);
        testTarget.setEditAccountService(editAccountService);
    }

    @Test
    public void addAccount_success() {
        // Arrange
        String userName = "testUser";
        String password = "testPassword";
        String email = "test@example.com";
        AddAccountRequest request = new AddAccountRequest(userName, password, email);
        
        // Act
        var response = testTarget.addAccount(request);
        
        // Assert
        Assertions.assertTrue(response.status().isSuccess());
        Assertions.assertNull(response.status().errorMessage());
        Assertions.assertNull(response.status().errorDetail());
        
        // Verify service was called with correct parameters
        Mockito.verify(editAccountService).addAccount(Mockito.argThat(dto -> 
            dto.userName().equals(userName) && 
            dto.password().equals(password) && 
            dto.email().equals(email)
        ));
    }
    
    @Test
    public void addAccount_serviceThrowsAccountException_rethrowsException() {
        // Arrange
        String userName = "testUser";
        String password = "testPassword";
        String email = "test@example.com";
        AddAccountRequest request = new AddAccountRequest(userName, password, email);
        String errorMessage = "found same user name";
        
        Mockito.doThrow(new AccountException(errorMessage))
            .when(editAccountService).addAccount(Mockito.any(AddAccountDto.class));
        
        // Act & Assert
        AccountException exception = Assertions.assertThrows(
            AccountException.class, 
            () -> testTarget.addAccount(request)
        );
        
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }
    
    @Test
    public void addAccount_serviceThrowsGenericException_wrapsInAccountException() {
        // Arrange
        String userName = "testUser";
        String password = "testPassword";
        String email = "test@example.com";
        AddAccountRequest request = new AddAccountRequest(userName, password, email);
        String originalErrorMessage = "Database connection failed";
        
        Mockito.doThrow(new RuntimeException(originalErrorMessage))
            .when(editAccountService).addAccount(Mockito.any(AddAccountDto.class));
        
        // Act & Assert
        AccountException exception = Assertions.assertThrows(
            AccountException.class, 
            () -> testTarget.addAccount(request)
        );
        
        Assertions.assertEquals("add account have unknown error", exception.getMessage());
        Assertions.assertEquals(originalErrorMessage, exception.getCause().getMessage());
    }
}