import java.net.*;
import java.io.*;
class Server{

    ServerSocket server;
    Socket socket;

    BufferedReader br;//for data reading 
    PrintWriter out; //for data writing

    //constructor
    public Server(){
        try{
              server=new ServerSocket(7777);//port number which the program need to run in server
              System.out.println("server is ready to aceept connection");
              System.out.println("server is waiting");
              socket=server.accept();

                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));// inputstreamreader convert the daata in charcter and it being handled by the buffereader
                out=new PrintWriter(socket.getOutputStream());

                    startReading();
                    startWriting();

            }catch(Exception e){
                e.printStackTrace();//if any error it help to print in the console
        }
    }

   //creating of 2 thread wich work same time 
    public void startReading() {
    //thread1-to read
    Runnable r1=()->{
        System.out.println("reader started");

        try{
        while(true){
            
            String msg = br.readLine();
            if(msg.equals("exit")){
                System.out.println("client Stopped the chat");
                socket.close();
                break;
            }
            System.out.println("Client :-" +msg);
       
        }
    }catch(Exception e){
              //  e.printStackTrace();
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
        while(!socket.isClosed()){
            
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content= br1.readLine();

                
                out.println(content);
                out.flush();
                if(content.equals("exit")){
                    socket.close();
                    break;
                }
            
        }
        }catch(Exception e){
                //e.printStackTrace();
                System.out.println("connection closed");
            }
           
    };
    new Thread(r2).start();
    }


    public static void main(String []args){
        System.out.println("server is working");
        new Server();
    }
}