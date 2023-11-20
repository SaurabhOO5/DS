import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.util.*;

//cltThread hanles chat with the client
class cltThread extends Thread{
    private Socket soc;
    private String username;

    public cltThread(Socket soc) throws IOException{
        this.soc = soc;
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        username = fromSocket.readLine();
        B1b_Server_3317.cltUsername.add(username);
        onlineStatus();
        alreadyOnline();
        this.start();
    }

    @Override
    public void run(){
        try {
            handlingChat();
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    public void alreadyOnline() throws IOException{
        for(int i=0;i<B1b_Server_3317.cltList.size();i++){
            if(soc!=B1b_Server_3317.cltList.get(i)){
                PrintWriter toSocket = new PrintWriter(soc.getOutputStream(),true);
                toSocket.println(B1b_Server_3317.cltUsername.get(i)+" is already online");
            }
        }
    }

    public void onlineStatus() throws IOException{
        for(int i=0;i<B1b_Server_3317.cltList.size();i++){
            if(soc!=B1b_Server_3317.cltList.get(i)){
                PrintWriter toSocket = new PrintWriter(B1b_Server_3317.cltList.get(i).getOutputStream(),true);
                toSocket.println(username+" is online");
            }
        }
    }


    void broadcast(String msg) throws IOException{
        for(int i=0;i<B1b_Server_3317.cltList.size();i++){
            if(soc!=B1b_Server_3317.cltList.get(i)){
                PrintWriter toSocket = new PrintWriter(B1b_Server_3317.cltList.get(i).getOutputStream(),true);
                toSocket.println(msg);
            }
        }
    }

    void handlingChat() throws IOException{
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));    /* System.in -> Denotes byte stream connected to keyboard
                                                                                     InputStreamReader -> byte stream to character stream
                                                                                     BufferedReader -> read stream of characters from
                                                                                                       specified source and store it in internal
                                                                                                       buffer for fast processing.  */
            //user input
        PrintWriter toSocket = new PrintWriter(soc.getOutputStream(),true);/* PrintWriter -> Outputs characters to specified stream
                                                                                 in the parameters.
                                                                                 getOuputStream -> denotes ouput sream of socket  */
        
    
        while(true){
            fromSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            String msg = fromSocket.readLine();
            if(msg!=null){
                if(B1b_Server_3317.cltList.size()==1 && msg.equalsIgnoreCase("Leave")){
                    B1b_Server_3317.cltList.remove(soc);
                    B1b_Server_3317.cltUsername.remove(username);
                }
                else if(B1b_Server_3317.cltList.size()>1){
                    if(msg.equalsIgnoreCase("Leave")){
                        msg = "Left the chat";
                        broadcast(username +" "+msg);
                        B1b_Server_3317.cltList.remove(soc);
                        B1b_Server_3317.cltUsername.remove(username);
                        break;
                    }
                    else{
                        broadcast(username+": "+ msg);
                    }
                }
                else{
                        toSocket.println("-->Sorry, No other users online");
                }
            }
        }       
        soc.close();
    }
}




public class B1b_Server_3317{
    private static int port = 9999;
    public static ArrayList<Socket> cltList = new ArrayList<>();
    public static ArrayList<String> cltUsername = new ArrayList<>();
    
    public static void main(String []args) throws IOException {
        ServerSocket serSocket = new ServerSocket(port,2);          // ServerSocket listens for incoming clients on port 9999
         
        
        while(true){
            System.out.println("[+] Waiting for Clients...");
            Socket soc = serSocket.accept();                            /* accept() function process the incoming requests and returns a socket
                                                                        which is used for further communication to client. */
            cltList.add(soc);                                                            
            cltThread t = new cltThread(soc);
        }
    }
}
