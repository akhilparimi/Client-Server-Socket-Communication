import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.security.Security;
import java.io.IOException;
import java.lang.Object;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server1 
{
    public static void main(String args[]) throws IOException
    {
        int pnumber = Integer.parseInt(args[0]);
        System.setProperty("javax.net.ssl.keyStore","../mainKeyStore.jks");
        //The password of the main keystore file
        System.setProperty("javax.net.ssl.keyStorePassword","123456");
        try
        {
            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(pnumber);
            System.out.println("The server has started and client connection is setup");
            SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
            DataInputStream inpMsgs = new DataInputStream(sslSocket.getInputStream());
            DataOutputStream opMsgs = new DataOutputStream(sslSocket.getOutputStream());
            opMsgs.writeUTF("Messaging client and connection is setup");
            while(true) {
                String msgRecv = inpMsgs.readUTF();
                Path path = Paths.get("Server1.java");
                if (msgRecv.equals("exit")) {
                    break; 
                } else if (msgRecv.equals("pwd")) {
                    String messageToSend = path.toAbsolutePath().toString();
                    opMsgs.writeUTF(messageToSend);
                } else if (msgRecv.equals("ls")) {
                    Path path2 = Paths.get(path.toAbsolutePath().toString());
                    String maindirpath = path2.getParent().toString();
                    String messageToSend = maindirpath;
                    opMsgs.writeUTF(messageToSend);
                } else {
                    break;
                }
            }
            opMsgs.close();
            inpMsgs.close();
            sslSocket.close();
            sslServerSocket.close();    
        }
        catch(Exception ex)
        {
            System.err.println("Error Happened : "+ex.toString());
        }
    }
}
