package com.nuvissoft.notifications.notifications.Services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import Utilities.Environment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppSender {


    public final String ACCOUNT_SID = Environment.env.get("TWILIO_ACCOUNT_SID");
    public final String AUTH_TOKEN = Environment.env.get("TWILIO_AUTH_TOKEN");

    private PhoneNumber senderPhone = new PhoneNumber(Environment.env.get("TWILIO_SENDER_PHONE"));
    private PhoneNumber receiverPhone;
    private String messageBody;

    public void send() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                receiverPhone,
                senderPhone,
                messageBody)
                .create();

        System.out.println(message.getSid());
    }

}
