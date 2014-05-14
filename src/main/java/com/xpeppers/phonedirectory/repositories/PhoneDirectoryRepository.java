package com.xpeppers.phonedirectory.repositories;

import com.xpeppers.phonedirectory.domain.PhoneDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface PhoneDirectoryRepository extends JpaRepository<PhoneDirectory, Long>, QueryDslPredicateExecutor {
}
