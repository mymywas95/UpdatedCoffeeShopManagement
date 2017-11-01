/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.BillDTO;
import DTO.OrderDTO;
import entity.Bill;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author MYNVTSE61526
 */
public class BillDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("coffeeShopManagementPU");

    public int addNewBill(Bill bill) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(bill);
            em.getTransaction().commit();
            return bill.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public List<Bill> getAllBill() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery(
                "SELECT b"
                + " FROM Bill b");
        List<Bill> resEnt = q.getResultList();
        return resEnt;
    }

    public List<BillDTO> getAllBillFromDateToDate(Date paymentDateFrom, Date paymentDateTo) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery(
                "SELECT b.paymentDate, SUM(b.total)"
                + " FROM Bill b "
                + " Where b.paymentDate >= :paymentDateFrom AND b.paymentDate <= :paymentDateTo "
                + " group by b.paymentDate");
        q.setParameter("paymentDateFrom", paymentDateFrom);
        q.setParameter("paymentDateTo", paymentDateTo);
        List<Object[]> resEnt = q.getResultList();
        List<BillDTO> result = new ArrayList<BillDTO>();
        for (Object[] pi : resEnt) {
            BillDTO billDTO = new BillDTO();
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sm.format(pi[0]);
            Double d = new Double((double) pi[1]);
            int totalPrice = d.intValue();
            billDTO.setPaymentDate(strDate);
            billDTO.setTotal(totalPrice);
            result.add(billDTO);
        }
        return result;
    }

}
