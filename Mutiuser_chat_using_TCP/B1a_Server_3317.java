import java.io.*;
import java.net.*;

public class B1a_Server_3317{
    public static void main(String []args) throws IOException{
        int port = 9999;
        ServerSocket serv = new ServerSocket(port);
        System.out.println("Waiting for clients...");
        Socket soc = serv.accept();
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));    /* System.in -> Denotes byte stream connected to keyboard
                                                                                     InputStreamReader -> byte stream to character stream
                                                                                     BufferedReader -> read stream of characters from
                                                                                                       specified source and store it in internal
                                                                                                       buffer for fast processing.  */
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));    //user input
        PrintWriter toSocket = new PrintWriter(soc.getOutputStream(),true);   /* PrintWriter -> Outputs characters to specified stream
                                                                                 in the parameters.
                                                                                 getOuputStream -> denotes ouput sream of socket  */
        while(true){
            String str;
            str = fromSocket.readLine();
            if(str.equals("Bye")){
                System.out.println("--From Client: "+str);
                System.out.print(" --To Client: ");
                str = userInput.readLine();
                toSocket.println(str);
                break;
            }

            System.out.println("--From Client: "+str);
            System.out.print(" --To Client: ");
            str = userInput.readLine();
            if(str.equals("Bye")){
                toSocket.println(str);
                str = fromSocket.readLine();
                System.out.println("--From Client: "+str);
                break;
            }
            toSocket.println(str);            
        }
        soc.close();
        serv.close();
    }
}
