import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;
      BufferedReader br;//for data reading 
    PrintWriter out; //for data writing

    public Client(){
        try{
            System.out.println("sending request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection done.");

             br = new BufferedReader(new InputStreamReader(socket.getInputStream()));// inputstreamreader convert the daata in charcter and it being handled by the buffereader
                out=new PrintWriter(socket.getOutputStream());

                    startReading();
                    startWriting();

        }catch(Exception e){
            
        }
    }
    public void startReading() {
        //thread1-to read
        Runnable r1=()->{
            System.out.println("reader started");
            try{
            while(true){
                
                String msg = br.readLine();
                if(msg.equals("exit")){
                    System.out.println("server Stopped the chat");
                    socket.close();
                    break;
                }
                System.out.println("server :-" +msg);
            
            }
        }catch(Exception e){
            //e.printStackTrace();
            System.out.println("connection closed");
        }
        };
        new Thread(r1).start();
        }

        public void startWriting() {
            //thread2- to send
            Runnable r2=()->{
                System.out.println("writer started");
                try{
                while( !socket.isClosed()){
                    
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                        String content= br1.readLine();
                        out.println(content);
                        out.flush();
                        if(content.equals("exit")){
                            socket.close();
                            break;
                        }
                   
                }
                System.out.println("connection closed");
            }catch(Exception e){
                e.printStackTrace();
            }
            };
            new Thread(r2).start();
            }
        

    public static void main(String []args){
        System.out.println("client");
        new Client();
    }
}
