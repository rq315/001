package edu.wtbu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.pojo.Result;
import edu.wtbu.service.FinanceService;

/**
 * Servlet implementation class FinanceServlet
 */
@WebServlet("/finance")
public class FinanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		int page =1;
		try {
			page =Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			// TODO: handle exception
			page =1;
		}
		int pageSize =10;
		try {
			pageSize =Integer.parseInt(request.getParameter("size"));
		} catch (Exception e) {
			// TODO: handle exception
			pageSize =10;
		}
		Result result = FinanceService.getFinanceList(page, pageSize);
		response.getWriter().append(JSON.toJSONString(result));
		
//		List<HashMap<String,Object>> result = UsersService.getFinanceList();
//		Result res = new Result();
//		res.setData(result);
//		response.getWriter().append(JSON.toJSONString(res));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
