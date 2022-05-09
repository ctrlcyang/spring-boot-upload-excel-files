package com.bezkoder.spring.files.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "dc_return_list")
public class DcReturnList {

  @Id
  @Column(name = "dc_no")
  private Integer dcNo;

  @Column(name = "sup_no")
  private Integer supNo;

  @Column(name = "box_qty")
  private Integer boxQty;

  @Column(name = "created_date")
  private Date createdDate;

  public Integer getDcNo() {
    return dcNo;
  }

  public void setDcNo(Integer dcNo) {
    this.dcNo = dcNo;
  }

  public Integer getSupNo() {
    return supNo;
  }

  public void setSupNo(Integer supNo) {
    this.supNo = supNo;
  }

  public Integer getBoxQty() {
    return boxQty;
  }

  public void setBoxQty(Integer boxQty) {
    this.boxQty = boxQty;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public String toString() {
    return "DcReturnList{" +
            "dcNo=" + dcNo +
            ", supNo=" + supNo +
            ", boxQty=" + boxQty +
            ", createdDate=" + createdDate +
            '}';
  }

}
