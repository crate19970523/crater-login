package com.crater.craterlogin.service.impl;

import com.crater.craterlogin.bean.dto.invitationCodeService.GetInvitationCodeDto;
import com.crater.craterlogin.bean.entity.db.InvitationCode;
import com.crater.craterlogin.dao.db.InvitationCodeDao;
import com.crater.craterlogin.exception.DbException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class InvitationCodeServiceImplTest {
    private final InvitationCodeServiceImpl testTarget = new InvitationCodeServiceImpl();
    private InvitationCodeDao invitationCodeDao;

    @BeforeEach
    public void setUp() {
        invitationCodeDao = Mockito.mock(InvitationCodeDao.class);
        testTarget.setInvitationCodeDao(invitationCodeDao);
    }

    @Test
    public void getInvitationCode_codeNotFound_returnNotExist() {
        // Arrange
        final var code = "INVALID_CODE";
        var getInvitationCodeDto = new GetInvitationCodeDto(code);
        
        Mockito.when(invitationCodeDao.select(Mockito.any())).thenAnswer(i -> {
            var invitationCode = i.getArgument(0, InvitationCode.class);
            Assertions.assertEquals(code, invitationCode.code());
            return new ArrayList<>();
        });
        
        // Act
        var result = testTarget.getInvitationCode(getInvitationCodeDto);
        
        // Assert
        Assertions.assertFalse(result.isExist());
        Assertions.assertNull(result.isActive());
        Assertions.assertNull(result.isExpired());
    }

    @Test
    public void getInvitationCode_codeExistsAndActive_returnExistAndActive() {
        // Arrange
        final var code = "VALID_CODE";
        var getInvitationCodeDto = new GetInvitationCodeDto(code);
        
        Mockito.when(invitationCodeDao.select(Mockito.any())).thenAnswer(i -> {
            var invitationCode = i.getArgument(0, InvitationCode.class);
            Assertions.assertEquals(code, invitationCode.code());
            return List.of(new InvitationCode()
                    .setCode(code)
                    .setActive(true)
                    .setExpirationDate(LocalDate.now().plusDays(1)));
        });
        
        // Act
        var result = testTarget.getInvitationCode(getInvitationCodeDto);
        
        // Assert
        Assertions.assertTrue(result.isExist());
        Assertions.assertTrue(result.isActive());
        Assertions.assertFalse(result.isExpired());
    }

    @Test
    public void getInvitationCode_codeExistsButInactive_returnExistAndInactive() {
        // Arrange
        final var code = "INACTIVE_CODE";
        var getInvitationCodeDto = new GetInvitationCodeDto(code);
        
        Mockito.when(invitationCodeDao.select(Mockito.any())).thenAnswer(i -> {
            var invitationCode = i.getArgument(0, InvitationCode.class);
            Assertions.assertEquals(code, invitationCode.code());
            return List.of(new InvitationCode()
                    .setCode(code)
                    .setActive(false)
                    .setExpirationDate(LocalDate.now().plusDays(1)));
        });
        
        // Act
        var result = testTarget.getInvitationCode(getInvitationCodeDto);
        
        // Assert
        Assertions.assertTrue(result.isExist());
        Assertions.assertFalse(result.isActive());
        Assertions.assertFalse(result.isExpired());
    }

    @Test
    public void getInvitationCode_codeExistsButExpired_returnExistAndExpired() {
        // Arrange
        final var code = "EXPIRED_CODE";
        var getInvitationCodeDto = new GetInvitationCodeDto(code);
        
        Mockito.when(invitationCodeDao.select(Mockito.any())).thenAnswer(i -> {
            var invitationCode = i.getArgument(0, InvitationCode.class);
            Assertions.assertEquals(code, invitationCode.code());
            return List.of(new InvitationCode()
                    .setCode(code)
                    .setActive(true)
                    .setExpirationDate(LocalDate.now().minusDays(1)));
        });
        
        // Act
        var result = testTarget.getInvitationCode(getInvitationCodeDto);
        
        // Assert
        Assertions.assertTrue(result.isExist());
        Assertions.assertTrue(result.isActive());
        Assertions.assertTrue(result.isExpired());
    }

    @Test
    public void getInvitationCode_codeExistsWithNullExpirationDate_returnExistAndNotExpired() {
        // Arrange
        final var code = "NO_EXPIRY_CODE";
        var getInvitationCodeDto = new GetInvitationCodeDto(code);
        
        Mockito.when(invitationCodeDao.select(Mockito.any())).thenAnswer(i -> {
            var invitationCode = i.getArgument(0, InvitationCode.class);
            Assertions.assertEquals(code, invitationCode.code());
            return List.of(new InvitationCode()
                    .setCode(code)
                    .setActive(true)
                    .setExpirationDate(null));
        });
        
        // Act
        var result = testTarget.getInvitationCode(getInvitationCodeDto);
        
        // Assert
        Assertions.assertTrue(result.isExist());
        Assertions.assertTrue(result.isActive());
        Assertions.assertFalse(result.isExpired());
    }

    @Test
    public void getInvitationCode_databaseError_throwDbException() {
        // Arrange
        final var code = "ERROR_CODE";
        var getInvitationCodeDto = new GetInvitationCodeDto(code);
        
        Mockito.when(invitationCodeDao.select(Mockito.any())).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        var exception = Assertions.assertThrows(DbException.class, () -> testTarget.getInvitationCode(getInvitationCodeDto));
        Assertions.assertEquals("query invitation code failed", exception.getMessage());
    }
}