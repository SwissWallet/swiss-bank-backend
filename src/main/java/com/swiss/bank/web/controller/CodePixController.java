package com.swiss.bank.web.controller;

import com.swiss.bank.entity.CodePix;
import com.swiss.bank.jwt.JwtUserDetails;
import com.swiss.bank.service.CodePixService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/codes")
public class CodePixController {

    private final CodePixService codePixService;

    public CodePixController(CodePixService codePixService) {
        this.codePixService = codePixService;
    }

    @PostMapping("/payment")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> paymentCodePix(@RequestParam String code, @AuthenticationPrincipal JwtUserDetails userDetails){
        codePixService.paymentCodePix(code, userDetails.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CodePix> findByCode(@PathVariable String code){
        CodePix codeValue = codePixService.findByCode(code);
        return ResponseEntity.ok().body(codeValue);
    }
}
