package com.bezkoder.spring.files.excel.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.files.excel.model.DcReturnList;

public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "dc_no", "sup_no", "box_qty", "created_date" };
  static String SHEET = "dc_return_list";

  public static boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static ByteArrayInputStream dcReturnListsToExcel(List<DcReturnList> dcReturnLists) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (DcReturnList dcReturnList : dcReturnLists) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(dcReturnList.getDcNo());
        row.createCell(1).setCellValue(dcReturnList.getSupNo());
        row.createCell(2).setCellValue(dcReturnList.getBoxQty());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        row.createCell(3).setCellValue( sdf.format(dcReturnList.getCreatedDate()));
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

  public static List<DcReturnList> excelToDcReturnLists(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();

      List<DcReturnList> dcReturnLists = new ArrayList<DcReturnList>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        DcReturnList dcReturnList = new DcReturnList();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
          case 0:
            dcReturnList.setDcNo((int) currentCell.getNumericCellValue());
            break;

          case 1:
            dcReturnList.setSupNo((int) currentCell.getNumericCellValue());
            break;

          case 2:
            dcReturnList.setBoxQty((int) currentCell.getNumericCellValue());
            break;

          case 3:
            dcReturnList.setCreatedDate(currentCell.getDateCellValue());
            break;

          default:
            break;
          }

          cellIdx++;
        }

        dcReturnLists.add(dcReturnList);
      }

      workbook.close();

      return dcReturnLists;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
