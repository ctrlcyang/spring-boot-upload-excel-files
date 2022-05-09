package com.bezkoder.spring.files.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.files.excel.helper.ExcelHelper;
import com.bezkoder.spring.files.excel.model.DcReturnList;
import com.bezkoder.spring.files.excel.repository.DcReturnListRepository;

@Service
public class ExcelService {
  @Autowired
  DcReturnListRepository repository;

  public void save(MultipartFile file) {
    try {
      List<DcReturnList> dcReturnLists = ExcelHelper.excelToDcReturnLists(file.getInputStream());
      repository.saveAll(dcReturnLists);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<DcReturnList> dcReturnLists = repository.findAll();

    ByteArrayInputStream in = ExcelHelper.dcReturnListsToExcel(dcReturnLists);
    return in;
  }

  public List<DcReturnList> getAllDcReturnLists() {
    return repository.findAll();
  }
}
