package Servlets;

import Core.GetFrequency;
import com.sun.nio.sctp.SctpSocketOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Pankaj on 4/5/2015.
 */
@WebServlet(name = "Waiting")
public class Waiting extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int time = (int) request.getSession().getAttribute("Time");
        int minLimit = (int) request.getSession().getAttribute("MinLimit");
        int maxLimit = (int) request.getSession().getAttribute("MaxLimit");
        String filePath = (String) request.getSession().getAttribute("FilePath");
        String rootPath = (String) request.getSession().getAttribute("RootPath");
        int orient = (int) request.getSession().getAttribute("Orient");
        GetFrequency gf = new GetFrequency();
//        int prefix = (int) Math.floor(Math.random() * 1000);
        int intervals = gf.startLDA(filePath, time, rootPath, minLimit, maxLimit);
        String fileName = "Outputs/topic_words";//+String.valueOf(prefix);
        request.getSession().removeAttribute("Time");
        request.getSession().removeAttribute("MinLimit");
        request.getSession().removeAttribute("MaxLimit");
        request.getSession().removeAttribute("FilePath");
        HttpSession session = request.getSession(false);
        session.setAttribute("Intervals", intervals);
        session.setAttribute("FileName", fileName);
        session.setAttribute("Orient", orient);
        System.out.println("****"+intervals+","+fileName);

//            System.out.println("FilePath: " + filePath + "\tTime: " + time + "\tMinLimit: " + minLimit + "\tMaxLimit: " + maxLimit);
            /*Thread.sleep(4000);*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*System.out.println("Welcome1");*/
        request.getRequestDispatcher("/waiting.jsp").forward(request, response);
    }
}
