package com.nuvissoft.notifications.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nuvissoft.notifications.notifications.Services.CreditServices;
// import com.nuvissoft.notifications.notifications.Services.WhatsAppSender;
import com.nuvissoft.notifications.notifications.Services.WhatsAppSender;
import com.twilio.type.PhoneNumber;

import Utilities.Environment;
// import Data.Fake.FakeDataExamples;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class NotificationsApplication {
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(NotificationsApplication.class, args);
		WhatsAppSender whatsApp = new WhatsAppSender();
		demoWhatsAppMessageWithExcel(whatsApp);
	}

	static void demoWhatsAppMessageWithExcel(WhatsAppSender whatsApp) {
		CreditServices service = new CreditServices();
		String messageTemplate = Environment.env.get("TWILIO_CUSTOM_MESSAGE");

		service
				.getAllCreditsWithLate()
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
									"Este mensaje duró en llegar:  " + (e.getTerm()) + " Sec");

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

						Thread.sleep(Long.valueOf((1000 * e.getTerm())));

					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					whatsApp
							.send();
							System.out.println(finalMessage);
				});

	}
}