package clientSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Receiver extends Thread
{
   private ServerSocket serverSocket;
   
   public Receiver(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
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
         Thread t = new Receiver(port);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}