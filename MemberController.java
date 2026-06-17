package com.example.demo.controller;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody MemberDTO memberDTO, BindingResult result) {
        // 1. Check for validation errors
        // If there are errors, return the error message with a 400 status
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        // 2. If validation passes, proceed to register the member
        try {
            return ResponseEntity.ok(memberService.registerMember(memberDTO));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            return ResponseEntity.ok("Member deleted successfully.");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Member>> getActiveMembers() {
        return ResponseEntity.ok(memberService.getAllActiveMembers());
    }
}


