import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.security.Security;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.lang.Object;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client1
{
    static void RecursiveFileList(File[] arr, int index, int level)
    {
        if (index == arr.length)
            return;
        for (int i = 0; i < level; i++)
            System.out.print("\t");
        if (arr[index].isFile())
            System.out.println(arr[index].getName());
        else if (arr[index].isDirectory()) {
            System.out.println("[" + arr[index].getName()
                               + "]");
            RecursiveFileList(arr[index].listFiles(), 0,
                           level + 1);
        }
        RecursiveFileList(arr, ++index, level);
    }
    public static void main(String args[]) throws IOException
    {
        
        int serverPort = Integer.parseInt(args[1]);
        String serverName = args[0];
        System.setProperty("javax.net.ssl.trustStore","../mainTrustStore.jts");
        //The password of the main trustStore file
        System.setProperty("javax.net.ssl.trustStorePassword","123456");
        try
        {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket)sslsocketfactory.createSocket(serverName,serverPort);
            DataOutputStream opMsgs = new DataOutputStream(sslSocket.getOutputStream());
            DataInputStream inpMsgs = new DataInputStream(sslSocket.getInputStream());
            String connectionMsg = inpMsgs.readUTF();
            System.out.print(connectionMsg + '\n');
            while(true) {
            System.out.print("ssh>:");
            String messageToSend = System.console().readLine();
            opMsgs.writeUTF(messageToSend);
            if (messageToSend.equals("exit")) {
                break; 
            } else if (messageToSend.equals("pwd")) {
                String msgRecv = inpMsgs.readUTF();
                System.out.println(msgRecv);  
            } else if (messageToSend.equals("ls")) {
                 String maindirpath = inpMsgs.readUTF();
                File maindir = new File(maindirpath);
                if (maindir.exists() || maindir.isDirectory()) {
                        File arr[] = maindir.listFiles();
                        System.out.println(
                            "**********************************************");
                        System.out.println(
                            "Following are the files from main directory " + maindir);
                        System.out.println(
                            "**********************************************");
                        RecursiveFileList(arr, 0, 0);
                    }
            } else {
                System.out.println("Invalid Command");
                break;
            }
            }
            sslSocket.close();
        }
        catch(Exception e)
        {
            System.err.println("Error has occurred: "+e.toString());
        }
    }
}