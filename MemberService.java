package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member registerMember(MemberDTO dto) {
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        Member member = new Member(dto.getName(), dto.getEmail());
        return memberRepository.save(member);
    }

    // 2. Soft Delete Concept
    public void deleteMember(Long id) {
        // Step 1. Fetch the result from the database. 
        Optional<Member> optionalMember = memberRepository.findById(id);

        // Step 2. Check if the box is empty
        if (!optionalMember.isPresent()) {
            // Step 3. If it is empty, throw the error
            throw new RuntimeException("Member not found");
        }

        Member member = optionalMember.get();
        
        member.setDeleted(true);
        memberRepository.save(member);
    }

    // 3. Get Active Members
    public List<Member> getAllActiveMembers() {
        return memberRepository.findAllActiveMembers();
    }
}


