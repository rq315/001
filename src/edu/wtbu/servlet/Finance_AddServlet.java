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
 * Servlet implementation class AddFinanceServlet
 */
@WebServlet("/addFinance")
public class Finance_AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Finance_AddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		int fund_amount=0;
		try {
			fund_amount=Integer.parseInt(request.getParameter("fund_amount"));
		} catch (Exception e) {
			// TODO: handle exception
			fund_amount=0;
		}
		int associated_activity_number=0;
		try {
			associated_activity_number=Integer.parseInt(request.getParameter("associated_activity_number"));
		} catch (Exception e) {
			// TODO: handle exception
			associated_activity_number=0;
		}
		String fund_type=request.getParameter("fund_type");
		String fund_record_date=request.getParameter("fund_record_date");
		String fund_description=request.getParameter("fund_description");
		System.out.println("Establishment Date: " + fund_record_date);
		// 验证日期格式
	    if (fund_record_date == null || !fund_record_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
	        response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid date format. Expected format: YYYY-MM-DD\"}");
	        return;
	    }
		Result result=FinanceService.addFinance(fund_amount, fund_type, fund_record_date, fund_description, associated_activity_number);
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
