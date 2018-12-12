package com.dxc.mail;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

@RestController
public class CheckMail {

    @RequestMapping("/v1/user/{userId}/checkMail")
    public void checkMail(@PathVariable("userId") String userId){
        try {
            //create properties field
            Properties properties = new Properties();
            properties.put("mail.pop3.host", "pop.gmail.com");
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            properties.put("mail.debug", "true");

            Session emailSession = Session.getDefaultInstance(properties);
            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");
            store.connect("pop.gmail.com", "vuanhkhoa03@gmail.com", null);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            //retrieve the messsage from the folder in array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.print("messages.length : "+messages.length);
            for (int i = 0, n = messages.length; i<n; i++){
                Message message = messages[i];
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
            }
            //close the store and folder object
            emailFolder.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
