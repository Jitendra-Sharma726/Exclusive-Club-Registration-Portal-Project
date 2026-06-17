package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // Derived Query Method for duplicate check
    Optional<Member> findByEmail(String email);

    // Only fetch members who have NOT been soft deleted
    @Query("SELECT m FROM Member m WHERE m.isDeleted = false")
    List<Member> findAllActiveMembers();
}


