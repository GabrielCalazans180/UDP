/*
 * Servidor.java
 *
 * Created on 17 de Maio de 2006, 11:27
 *
 * Servidor ECHO: fica em aguardo de solicita��o de algum cliente. Quando recebe
 * simplesmente devolve a mensagem.
 */

import java.net.*;
import java.io.*;

public class Servidor {
    
   public static void main(String args[]) {
        DatagramSocket s = null;
        String y;
        
        try {
            s = new DatagramSocket(6789); // cria um socket UDP
            byte[] buffer = new byte[1000];
            while (true) {
                 System.out.println("*** Servidor aguardando request");
                // cria datagrama para recepcionar solicita��o do cliente
                DatagramPacket req = new DatagramPacket(buffer, buffer.length);
                s.receive(req);
                System.out.println("*** Request recebido de: " + req.getSocketAddress());
                y = envio(new String (req.getData()));
                byte[] m = y.getBytes();
                
                // envia resposta
                DatagramPacket resp = new DatagramPacket(m, y.length(),
                        req.getAddress(), req.getPort());
                s.send(resp);
            }
            
        } catch (SocketException e) {
            System.out.println("Erro de socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro envio/recepcao pacote: " + e.getMessage());         
        } finally {
            if (s != null) s.close();
        }     
    }
     public static String envio(String x){
        String[] j = x.split(";");
        //System.out.println("\n\n" + j[2]);
        switch(Integer.parseInt(j[0])){
            
            case 1:
                //System.out.println("123");
                if(j[1].equals("5")){
                    //System.out.println("123");
                    if(j[2].equals("VVFFV"))
                    {
                        return "Certo";
                    }
                    else 
                    {
                        return "Errado";
                    }
                }
                else 
                {
                    return "Errado";   
                }
        }
        return "Errado";
    }

    
}
