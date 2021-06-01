/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import static java.lang.Thread.sleep;
import java.text.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
  
// Server class
public class Server extends TimerTask implements Runnable
{
    
    private boolean terminarJogo;
    private boolean jogoIniciado;
    private GSalaGUI GestorGUI;
    private HashMap<Integer,ServerCommunication> jogadoresSocket;
    final  Timer  timer;
    ServerSocket serverSocket;
    
    public Server( GSalaGUI gestorDeSala) throws IOException{
        super();
        
        GestorGUI = gestorDeSala;
        serverSocket = new ServerSocket(ServerCommunication.PORTA);
        terminarJogo = false;
        timer = new Timer(true);

        jogadoresSocket = new HashMap<>();
    }
    
    @Override
    public void run() {
            System.out.println("Thread Iniciou");
            if(terminarJogo){
                timer.cancel();
                
                for(int b: jogadoresSocket.keySet())
                    jogadoresSocket.get(b).terminarJogo = true;
                return;  
            }
               
            if(jogoIniciado)
                checarClientes();
            else
                try {
                    receberClientes();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
               
        
   }


    public void enviarMSG(String msg) {
        for(int i: jogadoresSocket.keySet())
            jogadoresSocket.get(i).enviarMSG(msg);
    }
    


    private void checarClientes(){
        boolean alguemTerminou = false;
        ArrayList<String[]> finalistas = new ArrayList<>();
        System.out.println("comecou a checar clientes");
        for(int i: jogadoresSocket.keySet()){
            ServerCommunication cliente = jogadoresSocket.get(i);
       
            if(cliente.terminouCartao()){
                try {
                    //tempo para que os outros clientes se atualizem caso queiram se declarar como vencedores tambÃ©m
                    sleep(10000);

                    String numerosEncript = JogosLotoJogador.EncriptacaoAES.decrypt(cliente.cartaoNumerosEncoded, cliente.chaveDecriptar);
                    if(numerosEncript == null)
                        System.out.println("Chave de Encriptacao Incorreta");
                    else{
                        alguemTerminou = true;
                        finalistas.add(new String[]{Integer.toString(cliente.jogadorID),numerosEncript});
                    }
                } catch (InterruptedException ex) {
                        for(int b: jogadoresSocket.keySet())
                            jogadoresSocket.get(b).terminarJogo = true;
                        this.terminarJogo = true;
                        System.out.println("temrinou jogo durante o timer checar clientes");

                }

            }
        }
        if(alguemTerminou)
            if(this.GestorGUI.finalizarJogo(finalistas)){
                this.terminarJogo = true;
                this.cancel();
                System.out.println("jogo terminou");
            }
    }
    private void receberClientes() throws InterruptedException{
    // referencia: https://www.baeldung.com/java-measure-elapsed-time
    long start = System.currentTimeMillis();

    long finish = System.currentTimeMillis(); 
        try {
            serverSocket.setSoTimeout(12000);
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        while( finish - start < 12000){
            System.out.println("Tempo restante antes de iniciar" +   (finish - start));
            try {
              
                Socket socket;
                
                System.out.println("a espera" );
                
                socket = serverSocket.accept();
                if( finish - start > 12000) break;
                
                int jogadorID = jogadoresSocket.size()+1;
                ServerCommunication  cliente_novo = new ServerCommunication(socket, jogadorID, GestorGUI);

                if(cliente_novo.iniciarComunicacao()) 
                    jogadoresSocket.put(jogadorID , cliente_novo);
              

                

            } catch (IOException ex) {
                System.out.println("Erro ao aceitar novo cliente, mas o programa continua");
            }
            finish = System.currentTimeMillis(); 
        }
        System.out.println("Parou de aceitar novas conexoes");
        Thread.currentThread().interrupt();
    }
    public void iniciar_jogo(){
        this.enviarMSG("jogoIniciado->true");
        
        jogoIniciado= true;
        timer.schedule(this, 1000);
//        Thread.currentThread().interrupt();
    }
    public String getJogadorNome(int jogadorID){
        if(!this.jogadoresSocket.containsKey(jogadorID))
            return null;
        return this.jogadoresSocket.get(jogadorID).nomeJogador;
        
    }
    
 
    
    public static void main(String[] args) throws IOException 
    {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);
          
        // running infinite loop for getting
        // client request
        while (true) 
        {
            Socket s = null;
              
            try 
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                  
                System.out.println("A new client is connected : " + s);
                  
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                  
                System.out.println("Assigning new thread for this client");
  
                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos);
  
                // Invoking the start() method
                t.start();
                  
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }

 


}
  
// ClientHandler class
class ClientHandler extends Thread 
{
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    PrintWriter saida;
      
  
    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        saida  = new PrintWriter(dos, true);
    }
  
    @Override
    public void run() 
    {
        String received;
        String toreturn;
        Integer vp = 0;
        while (true) 
        {
            try {
                
                if(vp == 0){
                    saida.println("numIdentificacao:102");
                    System.out.println("enviou num id");
                }
                
                
                vp++;
                if(vp !=0){
                  
                // receive the answer from client
                if(vp == 3){
                    try {
                        sleep(3000);
                        saida.println("jogoTerminou:true;");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                received = dis.readLine();
                System.out.println(received);
                  
                if(received.equals("Exit"))
                { 
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }
                  
                // creating Date object
                Date date = new Date();
                  
                // write on output stream based on the
                // answer from the client
                switch (received) {
                  
                    case "Date" :
                        toreturn = fordate.format(date);
                        dos.writeUTF(toreturn);
                        break;
                          
                    case "Time" :
                        toreturn = fortime.format(date);
                        dos.writeUTF(toreturn);
                        break;
                          
                    default:
                        saida.println("jogoIniciado:true;");
                        System.out.println("respondeu");
                        break;
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
          
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();
              
        }catch(IOException e){
            e.printStackTrace();
        }
        
        
    }
}
