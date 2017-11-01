/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DTO.BillItemDTO;
import DTO.OrderDTO;
import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import jaxb.orders.ListBillItemDTO;
import jaxb.orders.OrderItem;
import jaxb.orders.Orders;

/**
 *
 * @author MYNVTSE61526
 */
public class IndexService implements Serializable {

    public int checkDataBeforeLoad() {
        try {
            File orderUnsaveFile = new File(ManageConstantService.orderUnSavedFilePath);
            if (orderUnsaveFile.exists() == true) {
                insertorderUnsaveFileDatetoDB(ManageConstantService.orderUnSavedFilePath);
            }
            File productListFile = new File(ManageConstantService.listProductMashalled);
            if (productListFile.exists() == false) {
                return 0;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 2;

        }
    }

    public void ValidateOrderUnsaveFileDateBeforeSave(String filePath) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertorderUnsaveFileDatetoDB(String filepath) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(Orders.class);
            Unmarshaller unmarshallerObj = contextObj.createUnmarshaller();
            File f = new File(filepath);
            JAXBElement<Orders> root = unmarshallerObj.unmarshal(new StreamSource(
                    f), Orders.class);
            Orders orders = root.getValue();
            if (orders.getOrderItem().size() > 0) {
                File file = new File(ManageConstantService.orderUnSavedFilePath);
                file.delete();
                List<OrderDTO> listOrderDTO = new ArrayList<OrderDTO>();
                OrderService orderService = new OrderService();
                for (OrderItem orderItem : orders.getOrderItem()) {
                    OrderDTO orderDTO = convertJaxbToDto(orderItem);
                    Boolean created = orderService.createNewBill(orderDTO);
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(MenuService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OrderDTO convertJaxbToDto(OrderItem orderItem) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNote(orderItem.getNote());
        String date = orderItem.getPaymentDate();
        orderDTO.setPaymentDate(date);
        orderDTO.setPromotion(Integer.parseInt(orderItem.getPromotion() + ""));
        orderDTO.setTableName(orderItem.getTableName() + "");
        orderDTO.setTotal(Integer.parseInt(orderItem.getTotal() + ""));
        List<BillItemDTO> billItemDTOs = new ArrayList<BillItemDTO>();
        for (ListBillItemDTO listBillItemDTO : orderItem.getListBillItemDTO()) {
            BillItemDTO billItemDTO = new BillItemDTO();
            billItemDTO.setBillId(Integer.parseInt(listBillItemDTO.getBillId() + ""));
            billItemDTO.setProductId(Integer.parseInt(listBillItemDTO.getProductId() + ""));
            billItemDTO.setQuantity(Integer.parseInt(listBillItemDTO.getQuantity() + ""));
            billItemDTOs.add(billItemDTO);
        }
        orderDTO.setListBillItemDTO(billItemDTOs);
        return orderDTO;
    }
}
