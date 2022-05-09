package com.bezkoder.spring.files.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.files.excel.model.DcReturnList;

public interface DcReturnListRepository extends JpaRepository<DcReturnList, Long> {
}
