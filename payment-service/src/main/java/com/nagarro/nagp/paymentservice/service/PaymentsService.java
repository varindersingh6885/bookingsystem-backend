package com.nagarro.nagp.paymentservice.service;

public interface PaymentsService {
	public String paymentReceivedFlight(String bookingId);

	public void mockPaymentFailureFlight(String bookingId);
	
	public String paymentReceivedHotel(String bookingId);

	public void mockPaymentFailureHotel(String bookingId);
}
