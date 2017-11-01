/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DAO.ProductItemDAO;
import DTO.CategoryDTO;
import DTO.MenuDTO;
import DTO.ProductDTO;
import DTO.ProductItemDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import jaxb.listcategory.Category;
import jaxb.listcategory.ListCategory;
import jaxb.listcategory.Product;
import jaxb.listcategory.ProductItem;

/**
 *
 * @author MYNVTSE61526
 */
public class MenuService implements Serializable {

    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;
    private ProductItemDAO productItemDAO;

    public List<MenuDTO> getAllMenuItem() {
        
        List<MenuDTO> listMenu = new ArrayList<MenuDTO>();
        File f = new File(ManageConstantService.listProductMashalled);
        if (!f.exists() && !f.isDirectory() && f.length() == 0) {
            categoryDAO = new CategoryDAO();
            productDAO = new ProductDAO();
            productItemDAO = new ProductItemDAO();
            List<CategoryDTO> listCategory = categoryDAO.getAllCategory();
            for (CategoryDTO categoryDTO : listCategory) {
                MenuDTO menuDTO = new MenuDTO();
                List<ProductDTO> listProduct = productDAO.getAllproductByCateId(categoryDTO.getId());
                for (ProductDTO productDTO : listProduct) {
                    List<ProductItemDTO> listProductItemDTO = productItemDAO.getAllPricebyProductId(productDTO.getId());
                    productDTO.setListProductItemDto(listProductItemDTO);
                }
                menuDTO.setListProduct(listProduct);
                menuDTO.setCategoryName(categoryDTO.getName());
                menuDTO.setCategoryId(categoryDTO.getId());
                listMenu.add(menuDTO);
            }
            boolean smarshalled = marshalMenuToXMl(listMenu, ManageConstantService.listProductMashalled);
            if (smarshalled == false) {
                return null;
            }
        }
        List<MenuDTO> listmDTO = UnMarshalXMLToMenu(ManageConstantService.listProductMashalled);
        if (listmDTO.size() > 0) {
            return listmDTO;
        } else {
            return null;
        }
    }

    public List<MenuDTO> UnMarshalXMLToMenu(String filepath) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(ListCategory.class);
            Unmarshaller unmarshallerObj = contextObj.createUnmarshaller();
            File f = new File(filepath);
            JAXBElement<ListCategory> root = unmarshallerObj.unmarshal(new StreamSource(
                    f), ListCategory.class);
            ListCategory listCategory = root.getValue();
            List<MenuDTO> listmDTO = new ArrayList<MenuDTO>();
            for (Category category : listCategory.getCategory()) {
                MenuDTO menuDTO = new MenuDTO();
                menuDTO.setCategoryId(Integer.parseInt(category.getId() + ""));
                menuDTO.setCategoryName(category.getName());
                List<ProductDTO> list = new ArrayList<>();
                for (Product product : category.getProduct()) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(Integer.parseInt(product.getId() + ""));
                    productDTO.setName(product.getName() + "");
                    if (product.getImgLink() == null) {
                        productDTO.setImgLink("updating");
                    } else {
                        productDTO.setImgLink(product.getImgLink());
                    }
                    if (product.getMaterial() == null) {
                        productDTO.setMaterial("updating");
                    } else {
                        productDTO.setMaterial(product.getMaterial());
                    }
                    if (product.getDescription() == null) {
                        productDTO.setDescription("updating");
                    } else {
                        productDTO.setDescription(product.getDescription());
                    }
                    List<ProductItemDTO> listproductItemDTO = new ArrayList<ProductItemDTO>();
                    for (ProductItem productItem : product.getProductItem()) {
                        ProductItemDTO productItemDTO = new ProductItemDTO();
                        productItemDTO.setCompetitorName(productItem.getCompetitorName());
                        productItemDTO.setPrice(Double.parseDouble(productItem.getPrice() + ""));
                        listproductItemDTO.add(productItemDTO);
                    }
                    productDTO.setListProductItemDto(listproductItemDTO);
                    list.add(productDTO);
                }
                menuDTO.setListProduct(list);
                listmDTO.add(menuDTO);
            }
            return listmDTO;
        } catch (JAXBException ex) {
            Logger.getLogger(MenuService.class.getName()).log(Level.SEVERE, null, ex);
             return null;
        } 
    }

    public Boolean marshalMenuToXMl(List<MenuDTO> listmMenuDTO, String filePath) {
        ListCategory listCategory = new ListCategory();
        for (MenuDTO menuDTO : listmMenuDTO) {
            Category category = new Category();
            category.setId(new BigInteger(menuDTO.getCategoryId() + ""));
            category.setName(menuDTO.getCategoryName());
            for (ProductDTO productDTO : menuDTO.getListProduct()) {
                Product product = new Product();
                product.setId(new BigInteger(productDTO.getId() + ""));
                product.setName(productDTO.getName());
                product.setImgLink(productDTO.getImgLink());
                product.setDescription(productDTO.getDescription());
                product.setMaterial(productDTO.getMaterial());
                for (ProductItemDTO productItemDTO : productDTO.getListProductItemDto()) {
                    ProductItem productItem = new ProductItem();
                    productItem.setCompetitorName(productItemDTO.getCompetitorName());
                    productItem.setPrice(new BigDecimal(productItemDTO.getPrice() + ""));
                    product.getProductItem().add(productItem);
                }
                category.getProduct().add(product);
            }
            listCategory.getCategory().add(category);
        }
        try {
            JAXBContext contextObj = JAXBContext.newInstance(ListCategory.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            QName qName = new QName("jaxb.listcategory", "listCategory");
            JAXBElement<ListCategory> root = new JAXBElement<>(qName, ListCategory.class, listCategory);
            marshallerObj.marshal(root, new FileOutputStream(filePath));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
