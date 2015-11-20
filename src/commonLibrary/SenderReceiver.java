package commonLibrary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SenderReceiver {
	Socket s;
	public SenderReceiver(){
	}
	
	public String receiveMessageOn(Socket socket){
		try{
			if(socket.isClosed())
				throw new Exception("receiveMessageOn:"+socket.toString()+" is closed. Cannot continue");
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String msg = dis.readUTF();
			System.out.println("receiveMessageOn() Received "+msg);
			return msg;
		}
		catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().contains("receiveMessageOn"))
				System.exit(12); // exit 12 --> client to video Server abnormally disconnected
			return "No reply received";
		}
	}
	
	public void sendMesssageOn(Socket socket, String payload){
		try{
			if(socket.isClosed())
				throw new Exception("sendMesssageOn:"+socket.toString()+" is closed. Cannot continue");

			System.out.println("Trying to send |"+payload.substring(0, Math.min(100, payload.length()))+"...| to "+socket.getInetAddress()+":"+socket.getPort());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(payload);
		}
		catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().contains("sendMessageOn"))
				System.exit(11); // exit 11 --> client to video Server abnormally disconnected
		}
	}

}
