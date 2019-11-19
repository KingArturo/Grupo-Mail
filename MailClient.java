/**
 * A class to model a simple email client. The client is run by a
 * particular user, and sends and retrieves mail via a particular server.
 * 
 * @author David J. Barnes and Michael KÃ¶lling
 * @version 2011.07.31
 */
public class MailClient
{
    // The server used for sending and receiving.
    private MailServer server;
    // The user running this client.
    private String user;
    private MailItem ultimoMensaje;

    /**
     * Create a mail client run by user and attached to the given server.
     */
    public MailClient(MailServer server, String user)
    {
        this.server = server;
        this.user = user;
    }

    /**
     * Return the next mail item (if any) for this user.
     */
    public MailItem getNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        String cuerpoMensaje = item.getMessage();
        String asuntoMensaje = item.getSubject();
        if(item == null) {
            item = server.getNextMailItem(user);
        }
        else {
            ultimoMensaje = item;
            if (asuntoMensaje.contains(user)) {
            }
            else{
                if (cuerpoMensaje.contains("viagra")||cuerpoMensaje.contains("loteria")){
                    item = null;
                }
            }
        }

        return item;
    }

    /**
     * Print the next mail item (if any) for this user to the text 
     * terminal.
     */
    public void printNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        String cuerpoMensaje = item.getMessage();
        String asuntoMensaje = item.getSubject();
        if(item == null) {
            System.out.println("No new mail.");
        }
        else {
            ultimoMensaje = item;
            if (asuntoMensaje.contains(user)) {
                item.print();
            }
            else{
                if (cuerpoMensaje.contains("viagra")||cuerpoMensaje.contains("loteria")){
                    System.out.println("Mensaje recibido de spam"); 
                }
                else {
                    item.print();
                }
            }
        }
    }

    /**
     * Send the given message to the given recipient via
     * the attached mail server.
     * @param to The intended recipient.
     * @param message The text of the message to be sent.
     */
    public void sendMailItem(String to, String subject, String message)
    {
        MailItem item = new MailItem(user, to, subject, message);
        server.post(item);
    }

    public int getNumberOfMessage()
    {
        int correos = server.howManyMailItems(user);
        return correos;
    }

    public void getLastReceivedMail()
    {
        ultimoMensaje.print();
    }

    /**
     * Responde automáticamente a un mensaje que le ha sido enviado.
     */
    public void receiveAndAutorespond()
    {
        MailItem item = server.getNextMailItem(user);
        if(item != null) {
            String reFrom = item.getFrom();
            String reSubject = "RE: " + item.getSubject();
            String reMessage = "Gracias por su mensaje. Le contestaré lo antes posible. " + item.getMessage();
            sendMailItem(reFrom, reSubject, reMessage);
        }
    }
}
