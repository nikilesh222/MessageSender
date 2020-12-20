package com.example.emailsender;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import javax.mail.Session;
import android.view.View;
import android.widget.EditText;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.util.Properties;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText fromEmail = (EditText) findViewById(R.id.fromEmail);
        EditText toEmail = (EditText) findViewById(R.id.toEmail);
        String message = fromEmail.getText().toString() + " " + toEmail.getText().toString();
//String message;
        // Recipient's email ID needs to be mentioned.

//        new Thread(() -> {
//            SendEmail();
//        }).start();
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                SendEmail();
            }
        });

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void SendEmail(){
        String to = "nikileshsai.rokkam@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "nikileshsai.rokkam@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.user", "dsfaf");
        properties.setProperty("mail.password", "fdassfd");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");
            String body = "<h1>This is actual message</h1>";
            // Send the actual HTML message, as big as you like
            message.setContent(body, "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
}