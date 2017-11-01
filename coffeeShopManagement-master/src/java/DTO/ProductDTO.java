/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.List;

/**
 *
 * @author MYNVTSE61526
 */
public class ProductDTO {

    private int id;
    private String name;
    private String description;
    private String imgLink;
    private String material;
    private List<ProductItemDTO>  listProductItemDto;

    public ProductDTO() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<ProductItemDTO> getListProductItemDto() {
        return listProductItemDto;
    }

    public void setListProductItemDto(List<ProductItemDTO> listProductItemDto) {
        this.listProductItemDto = listProductItemDto;
    }

}
