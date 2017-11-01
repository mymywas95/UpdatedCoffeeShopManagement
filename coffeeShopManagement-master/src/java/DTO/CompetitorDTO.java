/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author MYNVTSE61526
 */
public class CompetitorDTO {

    private int id;
    private String name;
    private String websiteAddress;

    public CompetitorDTO() {
    }

    public CompetitorDTO(int id, String name, String websiteAddress) {
        this.id = id;
        this.name = name;
        this.websiteAddress = websiteAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    
}
