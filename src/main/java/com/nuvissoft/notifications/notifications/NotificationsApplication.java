package com.nuvissoft.notifications.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nuvissoft.notifications.notifications.Services.CreditServices;
import com.nuvissoft.notifications.notifications.Services.WhatsAppSender;
import com.twilio.type.PhoneNumber;

import Data.Domain.Credits;
import Data.Fake.FakeDataExamples;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class NotificationsApplication {

	static Dotenv environment = Dotenv.load();

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(NotificationsApplication.class, args);
		// demoWhatsAppMessage();
		// demoWhatsAppMessageWithExcel();
		System.out.println(
			environment.get("TWILIO_DEFAULT_MESSAGE")
		);
	}

	static void demoWhatsAppMessage() throws InterruptedException {
		WhatsAppSender wsp = new WhatsAppSender();
		FakeDataExamples data = new FakeDataExamples();

		while (true) {

			Thread.sleep(3000);

			for (Credits crdt : data.getAllCredits()) {

				wsp.setReceiverPhone(new PhoneNumber(
						"whatsapp:" + crdt.getPhoneNumber()));

				wsp.setMessageBody(
						"Estimado "
								+ ((crdt.getGender() == 'M') ? "sr. " : "sra. ")
								+ crdt.getFirstName()
								+ ", se le informa que tiene un monto pendiente de cancelación de "
								+ crdt.getAmount()
								+ " que venció en la fecha: "
								+ crdt.getDueDate().toString()
								+ "\nPorfavor le recomendamos realizar la cancelación o abono del mismo lo antes posible para evitar cargos moratorios."
								+ "\nRemitente: Gasolinera Jireh");
				wsp.send();
			}

		}
	}

	static void demoWhatsAppMessageWithExcel(){
		CreditServices service = new CreditServices();

		service.getAllCreditsWithLate().forEach(e -> {
			System.out.println( "\n" + e);
		});;

	}
}
