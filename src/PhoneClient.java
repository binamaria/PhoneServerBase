import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.String;

public class PhoneClient {
    public static void main(String[] args) throws Exception {

        String host = "localhost";
        int port = 2009;
        int choice;
        Scanner userInput = new Scanner(System.in);
        Socket socket=new Socket(host,port);

        try{
            DataInputStream inStream=new DataInputStream(socket.getInputStream());
            DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String clientName="",clientPhoneNumber="",serverMessage="",clientMessage="";

//            System.out.println("Please type HALO to connect to the server");
//            if((userInput.nextLine()).equals("HALO"))
//            {
//                outStream.writeUTF("HALO");
//                        outStream.flush();
//                        serverMessage = inStream.readUTF();
//                        System.out.println(serverMessage);
//            }

            do {
                System.out.println("Enter the choice below:");
                System.out.println("Type 1 to STORE the phone number");
                System.out.println("Type 2 to GET the phone number");
                System.out.println("Type 3 to REMOVE the phone number");
                System.out.println("Type 4 to EXIT");



                choice = userInput.nextInt();


                switch (choice){
                    case 1:
                        clientMessage="STORE";
                        outStream.writeUTF(clientMessage);
                        System.out.println("Name:");
                        clientName = br.readLine();
                        outStream.writeUTF(clientName);
                        System.out.println("Phone Number:");
                        clientPhoneNumber = br.readLine();
                        outStream.writeUTF(clientPhoneNumber);
                        outStream.flush();
                        serverMessage = inStream.readUTF();
                        System.out.println(serverMessage);
                        break;
                    case 2:
                        clientMessage="GET";
                        outStream.writeUTF(clientMessage);
                        System.out.println("Name:");
                        clientName = br.readLine();
                        outStream.writeUTF(clientName);
                        outStream.flush();
                        serverMessage = inStream.readUTF();
                        System.out.println(serverMessage);
                        break;
                    case 3:
                        clientMessage="REMOVE";
                        outStream.writeUTF(clientMessage);
                        System.out.println("Name:");
                        clientName = br.readLine();
                        outStream.writeUTF(clientName);
                        outStream.flush();
                        serverMessage = inStream.readUTF();
                        System.out.println(serverMessage);
                        break;
                    case 4: clientMessage="4";

                        break;
                }
            }while (choice!=4);

            outStream.close();
            outStream.close();
        }catch(Exception e){
            System.out.println(e);
        }
        socket.close();

    }
}

