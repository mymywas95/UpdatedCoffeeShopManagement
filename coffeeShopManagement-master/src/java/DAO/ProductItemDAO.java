/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ProductItemDTO;
import entity.Competitor;
import entity.ProductItem;
import java.io.Serializable;
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
public class ProductItemDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("coffeeShopManagementPU");

    public List<ProductItemDTO> getAllPricebyProductId(int productId) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery(
                "SELECT p.price, c.name "
                + "FROM ProductItem as p, Competitor as c "
                + "WHERE p.productId = :productId and p.competitorId = c.id");
        q.setParameter("productId", productId);
        List<Object[]> resEnt = q.getResultList();
        List<ProductItemDTO> result = new ArrayList<ProductItemDTO>();
        for (Object[] pi : resEnt) {
            ProductItemDTO productItemDTO = new ProductItemDTO();
            productItemDTO.setPrice(new Double(pi[0].toString()));
            productItemDTO.setCompetitorName(pi[1].toString());
            result.add(productItemDTO);
        }
        return result;
    }

    public int addNewProductItem(ProductItem productItem) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(productItem);
            em.getTransaction().commit();
            return productItem.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    public ProductItem findProductItembyProductIdAndcompetitorId(int productId, int competitorId) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery(
                    "SELECT p "
                    + "FROM ProductItem p "
                    + "WHERE p.productId = :productId and p.competitorId = :competitorId");
            q.setParameter("productId", productId);
            q.setParameter("competitorId", competitorId);
            ProductItem productItem = (ProductItem) q.getSingleResult();
            if (productItem == null) {
                return null;
            }
            return productItem;
        } catch (Exception e) {
            return null;
        }
    }
    public Boolean updatePricebyId(int id, int price) {
     EntityManager em = emf.createEntityManager();
        try {
            ProductItem productItem = em.find(ProductItem.class, id);
            if (productItem != null) {
                em.getTransaction().begin();
                productItem.setPrice(price);
                em.merge(productItem);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
