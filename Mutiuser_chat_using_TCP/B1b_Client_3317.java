import java.io.*;
import java.net.*;
import java.lang.Thread;

class sendThread extends Thread{
    private Socket cltSocket;

    public sendThread(Socket cltSocket){
        this.cltSocket=cltSocket;
    }

    @Override
    public void run() {
        try{
            send();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    //send function
    void send() throws IOException{
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter toSocket = new PrintWriter(cltSocket.getOutputStream(),true);
        while(true){
                if(userInput.ready()){
                    String str = userInput.readLine();
                    toSocket.println(str);
                    if(str.equals("Leave")){
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                    }
                }
        }
    }
}

//-----------------------------------------------------------------------------------------------------------------

class recvThread extends Thread{
    private Socket cltSocket;

    public recvThread(Socket cltSocket){
        this.cltSocket=cltSocket;
    }

    @Override
    public void run() {
        try{
            recv();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    //send function
    void recv() throws IOException{
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(cltSocket.getInputStream()));
        while(true){
            if(fromSocket.ready()){
               String str = fromSocket.readLine();
               String token[] = str.split(":");
                if(token.length==2){
                    System.out.println(token[0]);
                    System.out.println("  -->"+token[1]);
                }
                else{
                    System.out.println(token[0]);
                }

               //System.out.println(str);
            }
        }
    }
}



//---------------------------------------------------------------------------------------------------------------
public class B1b_Client_3317{
    private static int port = 9999;
    public static sendThread t1;
    public static recvThread t2;
    public  static String username;
    public static Socket cltSocket;
    //private static String str;
    public static void main(String []args) throws IOException,InterruptedException{
        System.out.println("[+] Client Started...");
        cltSocket = new Socket("localhost",port);                            /* Tries to connect to Server on port 9999 */
        System.out.println("[+] Connection established...");

        PrintWriter toSocket = new PrintWriter(cltSocket.getOutputStream(),true);   /* PrintWriter -> Outputs characters to specified stream
                                                                                                      in the parameters.
                                                                                        getOuputStream -> denotes ouput sream of socket */
        //BufferedReader fromSocket = new BufferedReader(new InputStreamReader(cltSocket.getInputStream()));


        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));   /* System.in -> Denotes byte stream connected to keyboard
                                                                                       InputStreamReader -> byte stream to character stream
                                                                                       BufferedReader -> read stream of characters from
                                                                                                         specified source and store it in internal
                                                                                                         buffer for fast processing. */
        System.out.print("Enter username: ");
        username = userInput.readLine();
        toSocket.println(username);
        t1 = new sendThread(cltSocket);
        t2= new recvThread(cltSocket);
        t1.start();
        t2.start();
                                                                  //closing socket.
    }
}
