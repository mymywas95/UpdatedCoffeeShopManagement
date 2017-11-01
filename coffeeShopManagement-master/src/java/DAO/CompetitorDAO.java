/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.CompetitorDTO;
import entity.Competitor;
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
public class CompetitorDAO  implements Serializable{
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("coffeeShopManagementPU");
    public List<CompetitorDTO> getAllCompetitor() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery( 
                "SELECT c " +
                "FROM Competitor c "); 
        List<Competitor> resEnt  = q.getResultList();
        List<CompetitorDTO> result = new ArrayList<CompetitorDTO>();
        for (Competitor com: resEnt){
            result.add(new CompetitorDTO(com.getId(), com.getName(), com.getWebsiteAddress()));
        }
        return result;
    }
}
