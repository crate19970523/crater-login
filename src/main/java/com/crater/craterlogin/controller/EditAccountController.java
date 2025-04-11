package com.crater.craterlogin.controller;

import com.crater.craterlogin.bean.dto.editAccountService.AddAccountDto;
import com.crater.craterlogin.bean.request.editAccountController.AddAccountRequest;
import com.crater.craterlogin.bean.response.Status;
import com.crater.craterlogin.bean.response.editAccountController.AddAccountResponse;
import com.crater.craterlogin.exception.AccountException;
import com.crater.craterlogin.service.EditAccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditAccountController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private EditAccountService editAccountService;

    @PermitAll
    @PostMapping("/editAccountController/add")
    @Operation(summary = "add account")
    public AddAccountResponse addAccount(@RequestBody @Valid AddAccountRequest addAccountRequest) {
        try {
            editAccountService.addAccount(new AddAccountDto(addAccountRequest.userName(), addAccountRequest.password(), addAccountRequest.email()));
            return new AddAccountResponse(Status.SUCCESS);
        } catch (AccountException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AccountException("add account have unknown error", e);
        }
    }

    @Autowired
    public void setEditAccountService(EditAccountService editAccountService) {
        this.editAccountService = editAccountService;
    }
}
