/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MYNVTSE61526
 */
@Entity
@Table(name = "Competitor", catalog = "CoffeeShopDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Competitor.findAll", query = "SELECT c FROM Competitor c"),
    @NamedQuery(name = "Competitor.findById", query = "SELECT c FROM Competitor c WHERE c.id = :id"),
    @NamedQuery(name = "Competitor.findByName", query = "SELECT c FROM Competitor c WHERE c.name = :name"),
    @NamedQuery(name = "Competitor.findByWebsiteAddress", query = "SELECT c FROM Competitor c WHERE c.websiteAddress = :websiteAddress"),
    @NamedQuery(name = "Competitor.findByDescription", query = "SELECT c FROM Competitor c WHERE c.description = :description")})
public class Competitor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "websiteAddress", length = 100)
    private String websiteAddress;
    @Column(name = "description", length = 2147483647)
    private String description;

    public Competitor() {
    }

    public Competitor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Competitor)) {
            return false;
        }
        Competitor other = (Competitor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Competitor[ id=" + id + " ]";
    }
    
}
