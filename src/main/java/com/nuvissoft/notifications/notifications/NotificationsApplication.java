package com.nuvissoft.notifications.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nuvissoft.notifications.notifications.Services.CreditServices;
// import com.nuvissoft.notifications.notifications.Services.WhatsAppSender;
import com.nuvissoft.notifications.notifications.Services.WhatsAppSender;
import com.twilio.type.PhoneNumber;

// import Data.Fake.FakeDataExamples;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class NotificationsApplication {

	static Dotenv environment = Dotenv.load();

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(NotificationsApplication.class, args);
		WhatsAppSender whatsApp = new WhatsAppSender();
		demoWhatsAppMessageWithExcel(whatsApp);
	}

	static void demoWhatsAppMessageWithExcel(WhatsAppSender whatsApp) {
		CreditServices service = new CreditServices();
		String messageTemplate = "Estimado sr(a). NOMBRE se informa que tiene un saldo pendiente de C$ MONTO. Por lo que se le invita a cancelar el mismo lo antes posible. OBSERVACION";

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