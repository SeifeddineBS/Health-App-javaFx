/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;



  import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author SeifBS
 */
public class SendSms {

    public void sendSms(String message_, String number) throws UnsupportedEncodingException, MalformedURLException, IOException {

   String AUTH_TOKEN = "27a534f5e1c63d55c51d674aa4d8fe44";
    String ACCOUNT_SID = "AC0f2cab8f2c99346b7a89584abc1ebb23";


    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message message = Message.creator(new PhoneNumber("+216"+number),
        new PhoneNumber("+18482259834"), 
        message_).create();

    System.out.println(message.getSid());
}

    
}
