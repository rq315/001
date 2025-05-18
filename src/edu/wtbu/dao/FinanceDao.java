package edu.wtbu.dao;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.helper.MySqlHelper;

public class FinanceDao {
	
	// ��ȡ��������������������
    public static int getFinanceCount() {
        String sql = "SELECT COUNT(*) as total FROM fund_information";
        List<HashMap<String, Object>> result = MySqlHelper.executeQuery(sql, null);
        
        // �����ѯ���
        if (result != null && !result.isEmpty()) {
            // ��ȡ��һ������е�"total"ֵ
            Object count = result.get(0).get("total");
            
            // ȷ��count����������
            if (count instanceof Number) {
                return ((Number) count).intValue();
            }
        }
        return 0; // Ĭ�Ϸ���0
    }
    
    // ��ȡ�ܷ���
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
    
    // ��ȡ��ҳ�����б���������ƣ�
    public static List<HashMap<String, Object>> getFinanceList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT f.*, a.activity_name " +
                     "FROM fund_information f " +
                     "LEFT JOIN activity_information a " +
                     "ON f.associated_activity_number = a.activity_id " +
                     "LIMIT ?, ?";
        return MySqlHelper.executeQuery(sql, new Object[]{offset, pageSize});
    }
	
	//��Ӿ�����Ϣ
		public static int addFinance(int fund_amount,String fund_type, String fund_record_date, String fund_description,
				int associated_activity_number) {
			// ��֤���ڸ�ʽ
			if (fund_record_date == null || !fund_record_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
			throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
			}
			String sql = "INSERT INTO fund_information (fund_amount,fund_type, fund_record_date,fund_description,"
					+ "associated_activity_number) VALUES (?, ?, ?,?,?)";
			return MySqlHelper.executeUpdate(sql, new Object[]{fund_amount,fund_type, fund_record_date,fund_description,associated_activity_number});
		}
	
		
		
	//������ ������ѯ�����
//	public static List<HashMap<String, Object>> getFinanceList() {
//        String sql = "SELECT\r\n" + 
//        		"fund_information.*,activity_information.activity_name\r\n" + 
//        		"FROM fund_information\r\n" + 
//        		"JOIN activity_information\r\n" + 
//        		"on fund_information.associated_activity_number=activity_information.activity_id";
//        return MySqlHelper.executeQuery(sql, null);
//    }
}
