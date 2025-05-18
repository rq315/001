package edu.wtbu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.pojo.Result;
import edu.wtbu.service.ActivityService;

/**
 * Servlet implementation class UpdateNumServlet
 */
@WebServlet("/updateNum")
public class Activity_UpdateNumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Activity_UpdateNumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
        int expectedNumber=0;
		try {
			expectedNumber=Integer.parseInt(request.getParameter("expected_number"));
		} catch (Exception e) {
			// TODO: handle exception
			expectedNumber=0;
		}
		int activityId=0;
		try {
			activityId=Integer.parseInt(request.getParameter("activity_id"));
		} catch (Exception e) {
			// TODO: handle exception
			activityId=0;
		}
		Result result = ActivityService.updateExpectedNumber(activityId, expectedNumber);
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
