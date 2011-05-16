/*
* To Compile from the command line use: /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/bin/javac -classpath /Users/username/Downloads/vidarr/Vidarr-1.6.6/Vidarr.app/Contents/MacOS/support/java/lib/activation.jar:/Users/username/Downloads/vidarr/Vidarr-1.6.6/Vidarr.app/Contents/MacOS/support/java/lib/mail.jar:/Users/username/Downloads/vidarr/Vidarr-1.6.6/Vidarr.app/Contents/MacOS/support/java/lib/max.jar:/Users/username/Downloads/vidarr/Vidarr-1.6.6/Vidarr.app/Contents/MacOS/support/java/classes/ /Users/username/Downloads/vidarr/Vidarr-1.6.6/Vidarr.app/Contents/MacOS/support/java/classes/MXJGmail3.java
*/

import com.cycling74.max.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MXJGmail3 extends MaxObject
{
    private static final int    NORMAL_OUTLET       = 0;
    private static final int    EXCEPTION_OUTLET    = 1;
    //private static final String SMTP_HOST_NAME      = "smtp.gmail.com";
    //private static final String SMTP_HOST_NAME      = "smtp.live.com";
    //private static final int    SMTP_HOST_PORT      = 25;
    //private static final int    SMTP_HOST_PORT      = 465;
    //private static final int    SMTP_HOST_PORT      = 587;

    private enum HostSettings {
        AIM, AOL, CUSTOM, GMAIL, HOTMAIL, YAHOO 
    }
    private int[] hostPorts = {587, 587, 0, 465, 587, 465};
    private String[] hostNames = {"smtp.aim.com", "smtp.aol.com", "", "smtp.gmail.com", "smtp.live.com", "smtp.mail.yahoo.com.sg"};
    private int smtpHostPort;
    private String smtpHostName;
    private boolean withSsl;
    
    private String username     = null;
    private String password     = null;
    private String to           = null;
    private String cc           = null;
    private String bcc          = null;
    private String from         = "nobody";
    private String subject      = null;
    private String text         = null;
    private String contentType  = "text/html";

    public MXJGmail3(Atom args[])
    {
        declareIO(1, 2);
        declareAttribute("username");
        declareAttribute("password");
        declareAttribute("to");
        declareAttribute("cc");
        declareAttribute("bcc");
        declareAttribute("subject");
        declareAttribute("text");
        //from = username + "@gmail.com";
    }

    public void bang()
    {
        deliver();
    }

    public void clear(String message, Atom args[])
    {
        from = "nobody";
        username = password = to = cc = bcc = subject = text = null;
    }

    public void deliver()
    {
        // set the HostSettings as HostSettings.HOST (choose from the enum)
        setHostSettings(HostSettings.HOST);
        setDefaultMessageAttributes(HostSettings.HOST);
        Properties props = createPropertiesWithSSL();

        try {
            // Get a mail Session object
            // Pass SMTP properties to private constructor of new
            // Session object
            //Session session = Session.getDefaultInstance(props);
            Session session = Session.getInstance(props);

            // Deal with transport directly because of TLS & SSL
            // which are required for gmail
            Transport transport = session.getTransport();
            // Create a new Message object
            MimeMessage message = new MimeMessage(session);

            // Pass the Mail Session as an argument and construct
            // a MimeMessage with the Session. Populate Message object
            if (from != "nobody") {
                message.setFrom( new InternetAddress(from));
            } else {
                message.setFrom();
            }
            if (to != null) {
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            }
            if (cc != null) {
                message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            }
            if (bcc != null) {
                message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
            }
            if (subject != null) {
                message.setSubject(subject);
            }

            // Create message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Specify that we want a Part interface for a new MIME object; 
            // construct object and assign it to messageBodyPart
            if (text != null) {
                messageBodyPart.setText(text);
            }
            
            //{
            // create a Multipart object to hold the BodyPart
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // pass the textset BodyPart as an argument to the
            // addBodyPart method of the multipart object
            // Create the attachment body part
            messageBodyPart = new MimeBodyPart();

            // pass the BodyPart to the addBodyPart() method of the
            // Multipart object so it can be added;
            // insert the multipart into the message
            message.setContent(multipart);

            // send the message with mail transport object
            transport.connect(smtpHostName, smtpHostPort, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            //transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();

            outlet(NORMAL_OUTLET, "sent");
        } catch (AddressException ae) {
            ae.printStackTrace();
            showException(null, ae);
            outlet(EXCEPTION_OUTLET, "AddressException");
        } catch (MessagingException me) {
            me.printStackTrace();
            showException(null, me);
            outlet(EXCEPTION_OUTLET, "MessagingException");
        }
    }
    
    private Properties createPropertiesWithSSL()
    {
        //Properties props = System.getProperties();
        Properties props = new Properties();
        if (withSsl) {
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.auth", "true");
            props.put("mail.smtps.ehlo","true"); //smtp:
            props.put("mail.smtps.host", smtpHostName);
            props.put("mail.smtps.quitwait", "false");
            props.put("mail.smtps.starttls.enable","true"); //smtp?
        } else {
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ehlo","true"); //smtp:
            props.put("mail.smtp.host", smtpHostName);
            props.put("mail.smtp.quitwait", "false");
            props.put("mail.smtp.starttls.enable","true"); //smtp?
        }
        // To see what is going on behind the scene
        props.put("mail.debug", "true");
        System.out.println(props.toString());
        return props;
    }
    
    /*
     * Use for testing different hosts.
     */
    private void setDefaultMessageAttributes(HostSettings hs)
    {
        int index = hs.ordinal();
        switch(hs) {
            case AIM:
                from        = "from@host.com";
                password    = "password";
                subject     = "Test from Vidarr";
                text        = "This message was sent from ... Mail Servers.";
                to          = "to@host.com";
                username    = "username@aim.com";
                break;
            case AOL:
                from        = "from@host.com";
                password    = "password";
                subject     = "Test from Vidarr";
                text        = "This message was sent from ... Mail Servers.";
                to          = "to@host.com";
                username    = "username@aol.com";
                break;
            case CUSTOM:
                from        = "from@host.com";
                password    = "password";
                subject     = "Test from Vidarr";
                text        = "This message was sent from ... Mail Servers.";
                to          = "to@host.com";
                username    = "username@custom.com";
                break;
            case GMAIL:
                from        = "from@host.com";
                password    = "password";
                subject     = "Test from Vidarr";
                text        = "This message was sent from ... Mail Servers.";
                to          = "to@host.com";
                username    = "username@gmail.com";
                break;
            case HOTMAIL:
                from        = "from@host.com";
                password    = "password";
                subject     = "Test from Vidarr";
                text        = "This message was sent from ... Mail Servers.";
                to          = "to@host.com";
                username    = "username@hotmail.com"; // "username@msn.com";
                break;
            case YAHOO:
                from        = "from@host.com";
                password    = "password";
                subject     = "Test from Vidarr";
                text        = "This message was sent from ... Mail Servers.";
                to          = "to@host.com";
                username    = "username@yahoo.com";
                break;
            default:
                break;
        }
    }
    
    private void setHostSettings(HostSettings hs)
    {
        int index = hs.ordinal();
        switch(hs) {
            case AIM:
                smtpHostPort = hostPorts[index];
                smtpHostName = hostNames[index];
                withSsl      = false;
                break;
            case AOL:
                smtpHostPort = hostPorts[index];
                smtpHostName = hostNames[index];
                withSsl      = false;
                break;
            case CUSTOM:
                smtpHostPort = hostPorts[index];
                smtpHostName = hostNames[index];
                withSsl      = true;
                break;
            case GMAIL:
                smtpHostPort = hostPorts[index];
                smtpHostName = hostNames[index];
                withSsl      = true;
                break;
            case HOTMAIL:
                smtpHostPort = hostPorts[index];
                smtpHostName = hostNames[index];
                withSsl      = false;
                break;
            case YAHOO:
                smtpHostPort = hostPorts[index];
                smtpHostName = hostNames[index];
                withSsl      = true;
                break;
            default:
                smtpHostPort = hostPorts[2];
                smtpHostName = hostNames[2];
                withSsl      = false;
                break;
        }
    }
}