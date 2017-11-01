/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entity.BillItem;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author MYNVTSE61526
 */
public class BillItemDAO implements Serializable{
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("coffeeShopManagementPU");
    public Boolean addNewBillItem(BillItem billItem) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(billItem);
             em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }
    
}
