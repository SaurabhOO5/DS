import java.io.*;
import java.net.*;

public class B1a_Client_3317 {
    private static int port = 9999;
    private static String str;
    public static void main(String []args) throws IOException{
        System.out.println("[+] Client Started...");
        Socket cltSocket = new Socket("localhost",port);                            /* Tries to connect to Server on port 9999 */
        System.out.println("[+] Connection established...");
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));   /* System.in -> Denotes byte stream connected to keyboard
                                                                                       InputStreamReader -> byte stream to character stream
                                                                                       BufferedReader -> read stream of characters from
                                                                                                         specified source and store it in internal
                                                                                                         buffer for fast processing. */
        
        PrintWriter toSocket = new PrintWriter(cltSocket.getOutputStream(),true);   /* PrintWriter -> Outputs characters to specified stream
                                                                                                      in the parameters.
                                                                                        getOuputStream -> denotes ouput sream of socket */
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(cltSocket.getInputStream()));                                                                                

        while(true){
            System.out.print("--To Server: ");
            str = userInput.readLine();                                              /* Stores a line in string 'str' */
            if(str.equals("Bye")){
                toSocket.println(str);
                str = fromSocket.readLine();
                System.out.println(" --From Server: "+str);
                break;                                                                  
            }
            
            toSocket.println(str);                                                   /* Print 'str' to the socket stream. */
            str = fromSocket.readLine();
            if(str.equals("Bye")){
                System.out.println(" --From Server: " + str);
                System.out.print("--To Server: ");
                str = userInput.readLine();
                toSocket.println(str);
                break;
            }
            System.out.println(" --From Server: " + str);            
        }
        cltSocket.close();                                                          //closing socket.    
    }
}
