/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Date;
import java.util.List;

/**
 *
 * @author MYNVTSE61526
 */
public class OrderDTO {

    private String tableName;
    private String paymentDate;
    private float total;
    private String note;
    private int promotion;
    private List<BillItemDTO> listBillItemDTO;

    public OrderDTO() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public List<BillItemDTO> getListBillItemDTO() {
        return listBillItemDTO;
    }

    public void setListBillItemDTO(List<BillItemDTO> listBillItemDTO) {
        this.listBillItemDTO = listBillItemDTO;
    }
    
    
}
