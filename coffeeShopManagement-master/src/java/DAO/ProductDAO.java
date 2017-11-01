/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ProductDTO;
import entity.Product;
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
public class ProductDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("coffeeShopManagementPU");

    public List<ProductDTO> getAllproductByCateId(int categoryId) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery(
                "SELECT p "
                + "FROM Product p "
                + "Where p.categoryId = :categoryId");
        q.setParameter("categoryId", categoryId);
        List<Product> resEnt = q.getResultList();
        List<ProductDTO> result = new ArrayList<ProductDTO>();
        for (Product pro : resEnt) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(pro.getId());
            productDTO.setName(pro.getName());
            productDTO.setImgLink(pro.getImgLink());
            productDTO.setDescription(pro.getDescription());
            productDTO.setMaterial(pro.getMaterial());
            result.add(productDTO);
        }
        return result;
    }

    public int checkExistProduct(String productName) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery(
                    "SELECT p "
                    + "FROM Product p "
                    + "Where p.name = :name");
            q.setParameter("name", productName);
            Product product = (Product) q.getSingleResult();
            if (product != null) {
                return product.getId();
            } else {
                return 0;
            }
        } catch (Exception e) {
              System.out.println("not exit product name " + productName);
            return 0;
        }
    }

    public int addNewProduct(Product product) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            return product.getId();
        } catch (Exception e) {
             System.out.println("fail to create " + product.getName());
            return 0;

        }
    }
}
