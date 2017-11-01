/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.CategoryDAO;
import DAO.CompetitorDAO;
import DAO.ProductDAO;
import DAO.ProductItemDAO;
import DTO.CompetitorDTO;
import entity.ProductCategory;
import entity.ProductItem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import jaxb.productsparsed.ListCategory;
import jaxb.productsparsed.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author MYNVTSE61526
 */
public class ParseProductService implements Serializable {

    private ListCategory listJAXB;
    private Map<String, String> link;

    public ParseProductService() {
        this.listJAXB = new ListCategory();
        this.link = new HashMap<String, String>();
    }

    public void getCompetitorData() {
        CompetitorDAO competitorDAO = new CompetitorDAO();
        List<CompetitorDTO> competitorList = competitorDAO.getAllCompetitor();
        String filePath = "";
        String cateList = null;
        String parsedDate = null;
        for (CompetitorDTO competitorDTO : competitorList) {
            if (competitorDTO.getName().equals("Dù Vàng")) {
                filePath = ManageConstantService.downloadDuVangHTMLFile;
                parsedDate = parseHTML(filePath, competitorDTO.getWebsiteAddress());
                cateList = parseHTML(ManageConstantService.downloadDuVangCateHTMLFile, "http://duvangcoffee.com/api/data.php?type=category");
                saveParsedDateToDb(competitorDTO, parsedDate, cateList, filePath);
            }
            if (competitorDTO.getName().equals("Uni Space")) {
                filePath = ManageConstantService.downloadUniSpaceHTMLFile;
                parsedDate = parseHTML(filePath, competitorDTO.getWebsiteAddress());
                saveParsedDateToDb(competitorDTO, parsedDate, cateList, filePath);
            }
        }
        File f = new File(ManageConstantService.listProductMashalled);
        if (f.exists() && f.isDirectory() && f.length() == 0) {
            f.delete();
        }
    }

    public void saveParsedDateToDb(CompetitorDTO competitorDTO, String parsedDate, String cateList, String filePath) {
        JSONArray jSONArray = null;
        JSONArray jSONCateArray = null;
        String competitorName = competitorDTO.getName();
        if (competitorName.equals("Dù Vàng") && filePath != null) {
            jSONArray = new JSONArray(parsedDate);
            jSONCateArray = new JSONArray(cateList);
        }
        if (competitorName.equals("Uni Space") && filePath != null) {
            JSONObject objectTemp = new JSONObject(parsedDate);
            jSONArray = objectTemp.getJSONArray("products");
            jSONCateArray = new JSONArray();
            JSONObject jSONObject123 = new JSONObject();
            jSONObject123.put("id", 123);
            jSONObject123.put("name", "Cà phê");
            jSONCateArray.put(jSONObject123);
            JSONObject jSONObject93 = new JSONObject();
            jSONObject93.put("id", 93);
            jSONObject93.put("name", "Cà phê");
            jSONCateArray.put(jSONObject93);
            JSONObject jSONObject94 = new JSONObject();
            jSONObject94.put("id", 94);
            jSONObject94.put("name", "Đặc biệt");
            jSONCateArray.put(jSONObject94);
            JSONObject jSONObject96 = new JSONObject();
            jSONObject96.put("id", 96);
            jSONObject96.put("name", "Trà");
            jSONCateArray.put(jSONObject96);
            JSONObject jSONObject97 = new JSONObject();
            jSONObject97.put("id", 97);
            jSONObject97.put("name", "Soda");
            jSONCateArray.put(jSONObject97);
            JSONObject jSONObject113 = new JSONObject();
            jSONObject113.put("id", 113);
            jSONObject113.put("name", "Trà sữa");
            jSONCateArray.put(jSONObject113);
        }
        addDateToDb(jSONArray, jSONCateArray, competitorDTO);
    }

    public void addDateToDb(JSONArray jSONArray, JSONArray jSONCateArray, CompetitorDTO competitorDTO) {
        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        ProductItemDAO productItemDAO = new ProductItemDAO();
        String competitorName = competitorDTO.getName();
        String productName = "";
        String productPriceTemp = "";
        int cateId = 0;
        int checkExist = 0;
        int cateExistId = 0;
        int productPrice = 0;
        Boolean isProduct = false;
        for (int i = 0; i < jSONArray.length(); i++) {
            cateExistId = 0;
            checkExist = 0;
            cateId = 0;
            productPrice = 0;
            productName = "";
            productPriceTemp = "";
            if (competitorName.equals("Dù Vàng")) {
                productName = jSONArray.getJSONObject(i).getString("description");
                if (productName.equals("")) {
                    productName = jSONArray.getJSONObject(i).getString("product_name");
                }
                if (productName.equals("Phụ Phí")) {
                    productPriceTemp = jSONArray.getJSONObject(i).getInt("base_price") + "";
                } else {
                    productPriceTemp = jSONArray.getJSONObject(i).getString("base_price");
                }
                cateId = jSONArray.getJSONObject(i).getInt("categ_id");
            }
            if (competitorName.equals("Uni Space")) {
                productName = jSONArray.getJSONObject(i).getString("name");
                productPriceTemp = jSONArray.getJSONObject(i).getInt("price") + "";
                cateId = jSONArray.getJSONObject(i).getInt("category");
            }
            productName = productName.toUpperCase();
            if (Integer.parseInt(productPriceTemp) > 10000) {
                productPrice = Integer.parseInt(productPriceTemp.substring(0, 2));
            } else {
                productPrice = Integer.parseInt(productPriceTemp.substring(0, 1));
            }
            ProductCategory productCategory = new ProductCategory();
            entity.Product product = new entity.Product();
            ProductItem productItem = new ProductItem();
            checkExist = productDAO.checkExistProduct(productName);
            if (checkExist == 0) {
                for (int j = 0; j < jSONCateArray.length(); j++) {
                    isProduct = false;
                    if (cateId == jSONCateArray.getJSONObject(j).getInt("id")) {
                        String cateNameTemp = jSONCateArray.getJSONObject(j).getString("name");
                        String cateName = cateNameTemp;
                        if (cateNameTemp.contains("(") || cateNameTemp.contains(")")) {
                            cateName = cateNameTemp.substring(cateNameTemp.indexOf("(") + 1, cateNameTemp.indexOf(")"));
                        }
                        cateName = cateName.toUpperCase();
                        cateExistId = categoryDAO.checkExistCate(cateName);
                        if (cateExistId == 0) {
                            productCategory.setName(cateName);
                        } else {
                            productCategory.setId(cateExistId);
                        }
                        isProduct = true;
                        break;

                    }
                }
                if (isProduct == false) {
                    System.out.println("not product");
                }
                if (isProduct == true) {
                    if (productCategory.getId() != null) {
                        product.setCategoryId(productCategory.getId());

                    } else {
                        int newCateId = categoryDAO.addNewCategory(productCategory);
                        product.setCategoryId(newCateId);
                    }
                    if (product.getCategoryId() != 0) {
                        product.setName(productName);
                        int productId = productDAO.addNewProduct(product);
                        if (productId != 0) {
                            productItem.setCompetitorId(competitorDTO.getId());
                            productItem.setProductId(productId);
                            productItem.setPrice(productPrice);
                            productItemDAO.addNewProductItem(productItem);
                        }
                    }
                }
            } else {
                ProductItem productItem1 = productItemDAO.findProductItembyProductIdAndcompetitorId(productPrice, competitorDTO.getId());
                if (productItem1 != null) {
                    Boolean createdProductItem = productItemDAO.updatePricebyId(productItem1.getId(), productPrice);
                    if (createdProductItem == false) {
                        System.out.println("fail");
                    }
                }
            }
        }
    }

    public String parseHTML(String filePath, String uri) {
        Writer writer = null;
        try {
            int BUFFER_SIZE = 8192;
            URL url = new URL(uri);
            URLConnection yc = url.openConnection();
            yc.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream is = yc.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"), BUFFER_SIZE);
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8"), BUFFER_SIZE);
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.replaceAll("&", "");
                writer.write(inputLine + "\n");
            }
            writer.close();
            in.close();
            is.close();
            String content = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
            return content;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void validate(String xsdPath, String xmlData) throws SAXException, IOException {
        SchemaFactory sf = SchemaFactory.newInstance(
                XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(xsdPath));
        Validator valid = schema.newValidator();
        StringReader reader = new StringReader(xmlData);
        valid.validate(new StreamSource(reader));

    }

}
