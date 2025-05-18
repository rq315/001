package edu.wtbu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.pojo.Result;
import edu.wtbu.service.UsersService;

/**
 * Servlet implementation class EnterCIServlet
 */
@WebServlet("/enterCI")
public class Club_EnterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Club_EnterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		int community_id=0;
		try {
			community_id=Integer.parseInt(request.getParameter("community_id"));
		} catch (Exception e) {
			// TODO: handle exception
			community_id=0;
		}

		String community_name=request.getParameter("community_name");
		String community_type=request.getParameter("community_type");
		String founder_name=request.getParameter("founder_name");
		String founder_id=request.getParameter("founder_id");
		String create_date=request.getParameter("create_date");
		String community_introduction=request.getParameter("community_introduction");
		String community_status=request.getParameter("community_status");
		System.out.println("Establishment Date: " + create_date);
		Result result=UsersService.enterCI(community_id,community_name, community_type, 
				founder_name, founder_id, create_date, community_introduction, community_status);
		response.getWriter().append(JSON.toJSONString(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
