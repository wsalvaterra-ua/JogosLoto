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
public class Server implements Runnable
{
    
    private boolean terminarJogo;
    private boolean jogoIniciado;
    private final GSalaGUI GestorGUI;
    private final HashMap<Integer,ServerCommunication> jogadoresSocket;

    ServerSocket serverSocket;
    
    public Server( GSalaGUI gestorDeSala) throws IOException{
        super();
        
        GestorGUI = gestorDeSala;
        serverSocket = new ServerSocket(ServerCommunication.PORTA);
        terminarJogo = false;


        jogadoresSocket = new HashMap<>();
    }
    
    @Override
    public void run() {
    
        receberClientes();
        if(jogadoresSocket.size()<1){
            System.out.println("Nnenhum cliente ingressou se");
            return;
        }   
        while(!terminarJogo){
                    
                   ArrayList<String[]> finalistas = new ArrayList<>();
        
        for(int i: jogadoresSocket.keySet()){
            ServerCommunication cliente = jogadoresSocket.get(i);
       
            if(cliente.terminouCartao()){
                try {

                    System.out.println("Classe Server Detetou que um cartao terminou");
                    String numerosDecript = JogosLotoJogador.EncriptacaoAES.decrypt(cliente.cartaoNumerosEncoded, cliente.chaveDecriptar);
                    if(numerosDecript == null)
                        System.out.println("Chave de Encriptacao Incorreta");
                    else{
                        finalistas.add(new String[]{Integer.toString(cliente.jogadorID),numerosDecript});
                        GestorGUI.estado_BotaoSortear(false);
                    //tempo para que os outros clientes se atualizem caso queiram se declarar como vencedores também
                        Thread.sleep(3000);
                    }
                } catch (InterruptedException ex) {
                        for(int b: jogadoresSocket.keySet())
                            jogadoresSocket.get(b).terminarJogo = true;
                        this.terminarJogo = true;
                        System.out.println("temrinou jogo durante o timer checar clientes");
                }

            }
        }
            System.out.println("finallistas:" + finalistas.size());
        if(finalistas.size() > 0)
            if(this.GestorGUI.finalizarJogo(finalistas)){
                this.terminarJogo = true;
                for(int b: jogadoresSocket.keySet())
                    jogadoresSocket.get(b).terminarJogo = true;
                 break;
            }else{
                GestorGUI.estado_BotaoSortear(true);
            }
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
   }


    public void enviarMSG(String msg) {
        for(int i: jogadoresSocket.keySet())
            jogadoresSocket.get(i).enviarMSG(msg);
    }
    


    public void iniciar_jogo(){
        this.enviarMSG("jogoIniciado->true");
    }
    public String getJogadorNome(int jogadorID){
        if(!this.jogadoresSocket.containsKey(jogadorID)){
            System.out.println("nome vazio?");
            return null;
        }
        return this.jogadoresSocket.get(jogadorID).nomeJogador;
        
    }
    

    public void receberClientes() {
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