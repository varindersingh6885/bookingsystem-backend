package com.nagarro.nagp.paymentservice.service;

public interface PaymentsService {
	public String paymentReceived(String bookingId);

	public void mockPaymentFailure(String bookingId);
}
