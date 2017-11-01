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
@Table(name = "ProductItem", catalog = "CoffeeShopDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductItem.findAll", query = "SELECT p FROM ProductItem p"),
    @NamedQuery(name = "ProductItem.findById", query = "SELECT p FROM ProductItem p WHERE p.id = :id"),
    @NamedQuery(name = "ProductItem.findByProductId", query = "SELECT p FROM ProductItem p WHERE p.productId = :productId"),
    @NamedQuery(name = "ProductItem.findByCompetitorId", query = "SELECT p FROM ProductItem p WHERE p.competitorId = :competitorId"),
    @NamedQuery(name = "ProductItem.findByPrice", query = "SELECT p FROM ProductItem p WHERE p.price = :price"),
    @NamedQuery(name = "ProductItem.findByDescription", query = "SELECT p FROM ProductItem p WHERE p.description = :description")})
public class ProductItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "productId", nullable = false)
    private int productId;
    @Basic(optional = false)
    @Column(name = "competitorId", nullable = false)
    private int competitorId;
    @Basic(optional = false)
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "description", length = 2147483647)
    private String description;

    public ProductItem() {
    }

    public ProductItem(Integer id) {
        this.id = id;
    }

    public ProductItem(Integer id, int productId, int competitorId, double price) {
        this.id = id;
        this.productId = productId;
        this.competitorId = competitorId;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(int competitorId) {
        this.competitorId = competitorId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        if (!(object instanceof ProductItem)) {
            return false;
        }
        ProductItem other = (ProductItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductItem[ id=" + id + " ]";
    }
    
}
