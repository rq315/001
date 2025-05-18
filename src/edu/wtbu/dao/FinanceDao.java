package edu.wtbu.dao;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.helper.MySqlHelper;

public class FinanceDao {
	
	// 获取经费总数（基本方法）
    public static int getFinanceCount() {
        String sql = "SELECT COUNT(*) as total FROM fund_information";
        List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql, null);
        
        // 处理查询结果
        if (result != null && !result.isEmpty()) {
            // 获取第一个结果中的"total"值
            Object count = result.get(0).get("total");
            
            // 确保count是数字类型
            if (count instanceof Number) {
                return ((Number) count).intValue();
            }
        }
        return 0; // 默认返回0
    }
    
    // 获取总费用
    public static double getTotalFundAmount() {
        String sql = "SELECT SUM(fund_amount) as total_sum FROM fund_information";
        List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql, null);
        if (result != null && !result.isEmpty()) {
            Object sum = result.get(0).get("total_sum");
            if (sum instanceof Number) {
                return ((Number) sum).doubleValue();
            }
        }
        return 0.0;
    }
    
    // 获取分页经费列表（关联活动名称）
    public static List<HashMap<String, Object>> getFinanceList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT f.*, a.activity_name " +
                     "FROM fund_information f " +
                     "LEFT JOIN activity_information a " +
                     "ON f.associated_activity_number = a.activity_id " +
                     "LIMIT ?, ?";
        return MySqlHelper.executeQuery(sql, new Object[]{offset, pageSize});
    }
	
	//添加经费信息
		public static int addFinance(int fund_amount,String fund_type, String fund_record_date, String fund_description,
				int associated_activity_number) {
			// 验证日期格式
			if (fund_record_date == null || !fund_record_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
			throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
			}
			String sql = "INSERT INTO fund_information (fund_amount,fund_type, fund_record_date,fund_description,"
					+ "associated_activity_number) VALUES (?, ?, ?,?,?)";
			return MySqlHelper.executeUpdate(sql, new Object[]{fund_amount,fund_type, fund_record_date,fund_description,associated_activity_number});
		}
	
		
		
	//管理经费 关联查询活动名称
//	public static List<HashMap<String, Object>> getFinanceList() {
//        String sql = "SELECT\r\n" + 
//        		"fund_information.*,activity_information.activity_name\r\n" + 
//        		"FROM fund_information\r\n" + 
//        		"JOIN activity_information\r\n" + 
//        		"on fund_information.associated_activity_number=activity_information.activity_id";
//        return MySqlHelper.executeQuery(sql, null);
//    }
}
