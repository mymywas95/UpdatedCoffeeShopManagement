/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.BillDAO;
import DTO.BillDTO;
import entity.Bill;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author MYNVTSE61526
 */
public class StatisticService implements Serializable {

    BillDAO billDAO;

    public JSONObject getStatisticFromDateToDate(String fromDate, String toDate) {
        try {
            billDAO = new BillDAO();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDateParsed = format.parse(fromDate);
            Date toDateParsed = format.parse(toDate);
            java.sql.Date fromDateSql = new java.sql.Date(fromDateParsed.getTime());
            java.sql.Date toDateSql = new java.sql.Date(toDateParsed.getTime());
            List<BillDTO> listSql = billDAO.getAllBillFromDateToDate(fromDateSql, toDateSql);
            JSONObject json = new JSONObject();
            JSONArray array = new JSONArray();
            for (BillDTO billDTO : listSql) {
                JSONObject item = new JSONObject();
                item.put("date", billDTO.getPaymentDate());
                item.put("total", billDTO.getTotal());
                array.put(item);
            }
            json.put("statictis", array);
            return json;
        } catch (ParseException ex) {
            Logger.getLogger(StatisticService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
