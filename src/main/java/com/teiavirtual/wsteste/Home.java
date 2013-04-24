package com.teiavirtual.wsteste;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/home")
public class Home extends HttpServlet {
    @Override

    public void init() throws ServletException {
       // super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      // resp.getWriter().println("teste servlet");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
