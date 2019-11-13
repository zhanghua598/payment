package com.sba.payment.apicontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sba.payment.mapper.PaymentMapper;
import com.sba.payment.model.Payment;
import com.sba.payment.rspmodel.RspModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(description = "SBA Payment Interface")
public class PaymentApiController {
	
	@Autowired
	private PaymentMapper paymentmapper;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "SBA Add a Payment")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> AddCoursePay(@ApiParam(name = "body", required = true) @RequestBody Payment payment) {

		try {
			
			paymentmapper.addPayment(payment);
			
			RspModel rsp = new RspModel();
			rsp.setCode(200);
			rsp.setMessage("Ok");
			return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@RequestMapping(value = "/checkcost/{courseid}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "SBA Get a Course Cost")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> GetCourseCost(@ApiParam(name = "courseid", required = true) @PathVariable("courseid") Integer courseid) {

		try {
			
			Float cost = paymentmapper.checkCost(courseid);
			
			if (cost != null) {
				Map<String, Float> costdata = new HashMap<String, Float>();
				costdata.put("cost", cost);
				
				RspModel rsp = new RspModel();
				rsp.setCode(200);
				rsp.setMessage("Ok");
				rsp.setData(costdata);
				return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);
				
			}else {
				Map<String, Float> costdata = new HashMap<String, Float>();
				costdata.put("cost", 0F);
				
				RspModel rsp = new RspModel();
				rsp.setCode(404);
				rsp.setMessage("Not found payment");
				rsp.setData(costdata);
				return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);
			}

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
