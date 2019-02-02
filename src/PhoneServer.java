import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import  java.util.Arrays;

public class PhoneServer {
    public static void main(String[] args) throws Exception {
        int port = 2009;
        try{
            ServerSocket server=new ServerSocket(port);
            int counter=0;
            System.out.println("PhoneServer started running..");
            while(true){
                counter++;

                Socket serverClient=server.accept();  //The client connection request is accepted
                System.out.println("PhoneClient no:" + counter + " is started..!");
                ServerClientThread sct = new ServerClientThread(serverClient,counter); //The request is send to a separate thread
                sct.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
class ServerClientThread extends Thread {
    Socket serverClient;
    int clientNo;
    ServerClientThread(Socket inSocket,int counter){
        serverClient = inSocket;
        clientNo=counter;
    }
    public void run(){
        try{
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
            String clientName = "", clientPhoneNumber = "", clientMessage = "", clientAck = "";

          //  List<List<String>> al = new ArrayList<>();
            HashMap<String, String> al = new HashMap<String, String>();

            while(!clientMessage.equals("4")){

                clientMessage = inStream.readUTF();

//                if(clientMessage.equals("HALO"))
//                {
//                    outStream.writeUTF(100 + "OK");
//               //     outStream.writeUTF("msg" + "Oke");
//                }
                if (clientMessage.equals("STORE")){
                   clientName = inStream.readUTF();
                    clientPhoneNumber = inStream.readUTF();

                    al.put(clientName,clientPhoneNumber);



                    System.out.println("Hashmap stored : " + al);

                    outStream.writeUTF("100" + " OK");


                }
                if(clientMessage.equals("GET")){
                    clientName = inStream.readUTF();  //getting client name
                   // int index=  al.get(clientName).toString();  //  al.indexOf(clientName);
                  //  System.out.println(clientName+"hhhhh"+al.get(clientName).toString()+"hhhhh"+al.get(0)+"alSizeeeeeeeeeeeeee : " + al.size()+"gggggggg");
                    if(al.containsKey(clientName)) {
                        outStream.writeUTF("200" + " " + al.get(clientName).toString());
                    }
                    else
                    {
                        outStream.writeUTF("300" + " NOT FOUND");
                    }
                 //   al.get(index);
                }
                if(clientMessage.equals("REMOVE")) {

                    clientName = inStream.readUTF();
                    if(al.containsKey(clientName))
                    {
                        al.remove(clientName);
                        //  System.out.println(clientName+"dddddd"+al.get(clientName).toString());
                        outStream.writeUTF("100" + " OK");
                    }
                    else{

                        outStream.writeUTF("300" + " NOT FOUND");

                    }
                }
//                else{
//                    outStream.writeUTF("400" + " BAD REQUEST");
//                }
            }
            outStream.flush();
            inStream.close();
            outStream.close();

            serverClient.close();

        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            System.out.println("PhoneClient -" + clientNo + " exit..!! ");
        }
    }
}


