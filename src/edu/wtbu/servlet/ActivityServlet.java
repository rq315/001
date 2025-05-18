package edu.wtbu.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.pojo.Result;
import edu.wtbu.service.ActivityService;

/**
 * Servlet implementation class ActivityServlet
 */
@WebServlet("/activity")
public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.setContentType("text/html;charset=utf-8");
//		int page =1;
//		try {
//			page =Integer.parseInt(request.getParameter("page"));
//		} catch (Exception e) {
//			// TODO: handle exception
//			page =1;
//		}
//		int pageSize =10;
//		try {
//			pageSize =Integer.parseInt(request.getParameter("size"));
//		} catch (Exception e) {
//			// TODO: handle exception
//			pageSize =10;
//		}
//		 String activityName = request.getParameter("activityName");
//        String activityType = request.getParameter("activityType");
//        Result result;
//        
//        if((activityName == null || activityName.isEmpty()) && 
//           (activityType == null || activityType.isEmpty())) {
//        	//当活动名称和活动类型均为空时
//            result = ActivityService.getActivityListByPage(page, pageSize);
//        } else {
//        	//当活动名称或活动类型任一有值时
//            result = ActivityService.searchActivities(activityName, activityType, null, page, pageSize);
//        }
//        
//        // 统一响应格式
//        HashMap<String, Object> responseData = new HashMap<>();
//        responseData.put("flag", result.getFlag());
//        responseData.put("data", result.getData());
//        
//        response.getWriter().write(JSON.toJSONString(responseData));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
