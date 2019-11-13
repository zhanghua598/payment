package com.sba.payment.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.sba.payment.model.Payment;

@Mapper
public interface PaymentMapper {

	@Insert("insert into sba_payment.payment(courseId,userName,mentorName,startDate,endDate,fee) values(#{courseId},#{userName},#{mentorName},#{startDate},#{endDate},#{fee})")
	@SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
	void addPayment(Payment payment);

	@Select("SELECT cost FROM sba_payment.payment where courseId = #{courseid}")
	Float checkCost(@Param("courseid") Integer courseid);

}
