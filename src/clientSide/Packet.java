package clientSide;

public class Packet {
	int option;
	String data;
	
	Packet(int option, String data){
		this.option = option;
		this.data = data;
	}
	
	Packet(String payload){
		
	}
	
	String getPayload(){
		return "|option|"+option+"|/option||data|"+data+"|data|";
	}
	
}
