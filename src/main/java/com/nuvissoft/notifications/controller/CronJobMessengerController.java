package com.nuvissoft.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nuvissoft.notifications.Services.CreditServices;
import com.nuvissoft.notifications.Services.WhatsAppSender;
import com.nuvissoft.notifications.Utilities.Environment;
import com.twilio.type.PhoneNumber;

@Component
public class CronJobMessengerController {
	@Autowired
	WhatsAppSender whatsApp;

	
	@Scheduled(cron = "${scheduling.cron}")
	public void sendPaymentsMessages() {

		CreditServices service = new CreditServices();
		String messageTemplate = Environment.env.get("TWILIO_CUSTOM_MESSAGE");
		String paymentReportMessage = Environment.env.get("PAYMENT_REPORT_MESSAGE");
		var notificationsSended = service.getAllCreditsWithLate();

		notificationsSended
				.forEach(e -> {

					String finalMessage = messageTemplate
							.replace(
									"NOMBRE",
									e.getFirstName())
							.replace(
									"MONTO",
									String.valueOf(
											e.getAmount()))
							.replace(
									"OBSERVACION",
									"Este mensaje duró en llegar: " + (e.getTerm()) + " Sec")
							.replaceAll("[_*]", "\n");

					whatsApp
							.setReceiverPhone(
									new PhoneNumber(
											"whatsapp:+505" + e.getPhoneNumber()));

					whatsApp
							.setMessageBody(
									finalMessage);

					try {
						System.out.println(
								"Thread is sleeping... by " + (e.getTerm()) + " Sec");

						Thread.sleep(Long.valueOf(1000));

					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					whatsApp
							.send();
					System.out.println(finalMessage);
				});


		whatsApp.setReceiverPhone(
				new PhoneNumber(
						Environment.env.get("PHONE_TO_SEND_REPORT")));

		paymentReportMessage = paymentReportMessage
				.replace(
						"COBROS",
						notificationsSended.toString().replaceAll("[\\[\\]]", ""));

		whatsApp.setMessageBody(
				paymentReportMessage);

		System.out.println(paymentReportMessage);

		whatsApp.send();

	}

}
