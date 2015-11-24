package requestReceiver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class RequestReceiver extends Thread
{
   private ServerSocket serverSocket;
   
   public RequestReceiver(int port) throws IOException
   {
	   serverSocket = new ServerSocket(port);
	   System.out.println("Request Receiver Running on "+serverSocket.getInetAddress()+":"+serverSocket.getLocalPort());
	   serverSocket.setSoTimeout(0);
   }

   public void run()
   {
      while(true)
      {
         try
         {
            //System.out.println("Waiting for client on port " +serverSocket.getLocalPort() + "...");
            Socket socketFromPeer = serverSocket.accept();
            System.out.println("Request received from "+ socketFromPeer.getRemoteSocketAddress()+":"+socketFromPeer.getPort());
            
            // Start new 'Servant' thread and make it serve the peer
            new Servant(socketFromPeer).start();
            //socketFromPeer.close();
            
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   public static void main(String [] args)
   {
      int port = Integer.parseInt(args[0]);
      try
      {
         Thread t = new RequestReceiver(port);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}