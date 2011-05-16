import com.cycling74.max.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MXJGmail3 extends MaxObject {

private static final String SMTP_HOST_NAME = "smtp.gmail.com";
private static final int SMTP_HOST_PORT = 465;
private static final int NORMAL_OUTLET = 0;
private static final int EXCEPTION_OUTLET = 1;

private String username = null;
private String password = null;
private String to = null;
private String cc = null;
private String bcc = null;
private String from = "nobody";
private String subject = null;
private String text = null;
private String contentType = "text/html";

public MXJGmail3( Atom args[] ) {
declareIO( 1,2 );
declareAttribute("username");
declareAttribute("password");
declareAttribute("to");
declareAttribute("cc");
declareAttribute("bcc");
declareAttribute("subject");
declareAttribute("text");

from = username + "@gmail.com";
}

public void bang ()
{
deliver();
}

public void clear( String message, Atom args[] ) {
username = password = to = cc = bcc = subject = text = null;
from = "nobody";
}

public void deliver() {

//Get System Properties object
Properties props = System.getProperties();

// Define properties
// it MUST be smtpS for gmail
props.put( "mail.transport.protocol", "smtps" );
props.put( "mail.smtps.host", SMTP_HOST_NAME );
props.put( "mail.smtps.auth", "true" );

// tells the system NOT to wait for verification
// of sent status from gmail
props.put( "mail.smtps.quitwait", "false" );

try {

// Get a mail Session object
// Pass SMTP properties to private constructor of new
// Session object
Session session = Session.getDefaultInstance( props );

// deal with transport directly because of tls and ssl
// which are required for gmail
Transport transport = session.getTransport();

// Create a new Message object
MimeMessage message = new MimeMessage( session );

//Pass the Mail Session as an argument and construct
//a MimeMessage with the Session
// Populate Message object
if ( from != "nobody" ) {
message.setFrom( new InternetAddress( from ) );
}
else
{
message.setFrom();
}
if ( to != null )
{
message.addRecipients( Message.RecipientType.TO,
InternetAddress.parse( to ) );
}
if ( cc != null )
{
message.addRecipients( Message.RecipientType.CC,
InternetAddress.parse( cc ) );
}
if ( bcc != null )
{
message.addRecipients( Message.RecipientType.BCC,
InternetAddress.parse( bcc ));
}
if ( subject != null )
{
message.setSubject( subject );
}

//Create message body part
BodyPart messageBodyPart = new MimeBodyPart();

//Specify that we want a Part interface for a new
//MIME object; construct object and assign it to
//messageBodyPart
if ( text != null )
{
messageBodyPart.setText( text );
}

{

//create a Multipart object to hold the BodyPart
Multipart multipart = new MimeMultipart();
multipart.addBodyPart( messageBodyPart );

//pass the textset BodyPart as an argument to the
//addBodyPart method of the multipart object
//Create the attachment body part
messageBodyPart = new MimeBodyPart();

//pass the BodyPart to the addBodyPart() method of the
//Multipart object so it can be added;
//insert the multipart into the message
message.setContent( multipart );

}

//send the message with mail transport object
transport.connect( SMTP_HOST_NAME, SMTP_HOST_PORT,
username, password );
transport.sendMessage( message,
message.getRecipients( Message.RecipientType.TO ) );
transport.close();

outlet(NORMAL_OUTLET, "sent");

} catch ( AddressException ae ) {
showException( null, ae );
outlet( EXCEPTION_OUTLET, "AddressException" );
} catch ( MessagingException me ) {
showException( null, me );
outlet( EXCEPTION_OUTLET, "MessagingException" );
}
}
}

