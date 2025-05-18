package edu.wtbu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StuId {
	//以年份开头的10位随机学号生成
    public static String generateStudentId() {
    	
    	SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year = yearFormat.format(new Date());
         
        // 生成6位随机数
        Random random = new Random();
        int randomNum = random.nextInt(900000) + 100000;
         
        return year + randomNum;
    }
}