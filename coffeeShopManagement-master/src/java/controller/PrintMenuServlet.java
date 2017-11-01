/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import service.ManageConstantService;
import service.PrintToPdfService;

/**
 *
 * @author MYNVTSE61526
 */
@WebServlet(name = "PrintMenuServlet", urlPatterns = {"/PrintMenuServlet"})
public class PrintMenuServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            PrintToPdfService printToPdfService = new PrintToPdfService();
//            String headername = request.getParameter("menu");
//            JSONObject js = new JSONObject(headername);
//            Boolean menuXMLFile = printToPdfService.createMenuXMLFile(js);
            Boolean menuXMLFile = true;
            if (menuXMLFile == true) {
                String path = getServletContext().getRealPath("/");
                String xslPath = path + "WEB-INF/menuFO.xsl";
                String xmlPath = ManageConstantService.menuFile;
                String foPath = path + "WEB-INF/menuFO.fo";
                createFoFile(xslPath, xmlPath, foPath, path);

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                FopFactory fact = FopFactory.newInstance();
                fact.setUserConfig(path + "/WEB-INF/config.xml");
                FOUserAgent fua = fact.newFOUserAgent();

                Fop fop = fact.newFop(org.apache.fop.apps.MimeConstants.MIME_PDF, fua, out);

                TransformerFactory trFact = TransformerFactory.newInstance();
                Transformer trans = trFact.newTransformer();

                File fo = new File(foPath);
                Source foSource = new StreamSource(fo);
                Result saxResult = new SAXResult(fop.getDefaultHandler());
                trans.transform(foSource, saxResult);

                byte[] content = out.toByteArray();
                response.setContentType("application/pdf");
                response.setContentLength(content.length);

                response.getOutputStream().write(content);
                response.getOutputStream().flush();
            }

        } catch (IOException ex) {
            Logger.getLogger(PrintToPdfService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PrintToPdfService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(PrintToPdfService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(PrintToPdfService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void createFoFile(String xslPath, String xmlPath, String foPath, String path) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            StreamSource xsltFile = new StreamSource(xslPath);
            Transformer trans = tf.newTransformer(xsltFile);
            trans.setParameter("pathFile", path);
//            InputStream is = new ByteArrayInputStream(xmlPath.getBytes(StandardCharsets.UTF_8));

            StreamSource xmlSource = new StreamSource(xmlPath);
            StreamResult foFile = new StreamResult(
                    new FileOutputStream(foPath));
            trans.transform(xmlSource, foFile);
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }
}
