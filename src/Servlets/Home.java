package Servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Pankaj on 4/5/2015.
 */
@WebServlet(name = "Home")
public class Home extends HttpServlet {


    private static final int THRESHOLD_SIZE     = 1024 * 1024 * 10;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 400; // 400MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 500; // 500MB

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        String rootPath = getServletContext().getRealPath("/");
        rootPath = rootPath.substring(0, rootPath.indexOf("TWC") + 3)+File.separator;
        String UPLOAD_DIRECTORY = rootPath+"temp"+File.separator;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        String filePath = "";
        List formItems = null;
        int orient = 90;
        String fileName = "";
        int time=1, minLimit=5, maxLimit=150;
        try {
            formItems = upload.parseRequest(request);

            Iterator iter = formItems.iterator();
            // creates the directory if it does not exist
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField() && fileName.isEmpty()) {
                    fileName = new File(item.getName()).getName();
                    filePath = UPLOAD_DIRECTORY + fileName;
                    File storeFile = new File(filePath);
                    System.out.println(filePath);
                    // saves the file on disk
                    item.write(storeFile);
                }else{
                    if(item.getFieldName().equalsIgnoreCase("Time")) {
                        time = Integer.parseInt(item.getString());
                    }
                    if(item.getFieldName().equalsIgnoreCase("Min")){
                        minLimit = Integer.parseInt(item.getString());
                    }
                    if(item.getFieldName().equalsIgnoreCase("Max")) {
                        maxLimit = Integer.parseInt(item.getString());
                    }
                    if(item.getFieldName().equalsIgnoreCase("orientation")) {
                        orient = Integer.parseInt(item.getString());
                    }
                    if(item.getFieldName().equalsIgnoreCase("dFile")) {
                        fileName = item.getString();
                        filePath = UPLOAD_DIRECTORY + fileName;
                        System.out.println(filePath);
                    }
                }
        }
            request.setAttribute("message", "Upload has been done successfully!");
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession(false);
        session.setAttribute("Time", time);
        session.setAttribute("RootPath", rootPath);
        session.setAttribute("MinLimit", minLimit);
        session.setAttribute("MaxLimit", maxLimit);
        session.setAttribute("FilePath", filePath);
        session.setAttribute("Orient", orient);
        /*System.out.println("time: " + time + "minLimit: " + minLimit + "maxLimit: " + maxLimit + "filePath: "+ filePath);*/
        response.sendRedirect("waiting");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
