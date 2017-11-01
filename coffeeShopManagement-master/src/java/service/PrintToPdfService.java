/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import jaxb.listcategory.ListCategory;
import jaxb.menu.Category;
import jaxb.menu.Menu;
import jaxb.menu.Product;
import org.apache.bcel.generic.F2D;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author MYNVTSE61526
 */
public class PrintToPdfService implements Serializable {
    public boolean createMenuXMLFile(JSONObject jSONObject) {
        Menu menu = convertJsonToJaxb(jSONObject);
        try {
            JAXBContext contextObj = JAXBContext.newInstance(Menu.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            QName qName = new QName("jaxb.menu", "menu");
//            JAXBElement<Menu> root = new JAXBElement<>(qName, Menu.class, menu);
            marshallerObj.marshal(menu, new FileOutputStream(ManageConstantService.menuFile));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Menu convertJsonToJaxb(JSONObject jSONObject) {
        Menu menu = new Menu();
        JSONArray jSONArray = jSONObject.getJSONArray("menu");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject categoryObj = jSONArray.getJSONObject(i);
            String categoryName = categoryObj.getString("CategoryName");
            JSONArray productList = categoryObj.getJSONArray("productList");
            Category category = new Category();
            category.setName(categoryName);
            for (int j = 0; j < productList.length(); j++) {
                JSONObject productObj = productList.getJSONObject(j);
                String productId = productObj.getString("id");
                String productName = productObj.getString("name");
                String productPrice = productObj.getString("price");
                Product product = new Product();
                product.setId(new BigInteger(productId));
                product.setName(productName);
                product.setPrice(new BigDecimal(productPrice));
                category.getProduct().add(product);
            }
            menu.getCategory().add(category);
        }
        return menu;
    }
}
