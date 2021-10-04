/*
 * Cliente.java
 *
 * Created on 17 de Maio de 2006, 11:10
 *
 * Argumentos: <HostIP> <porta> <mensagem>
 * Ex. java Cliente 127.0.0.1 6789 "mensagem teste"
 * O servidor devolve a msg (echo)
 */
import java.net.*;
import java.io.*;

class ThreadCliente implements Runnable {
    protected String servidor="localhost";
    protected String msg="1;5;VVFFV;";
    protected int porta=6789;
    ThreadCliente(String args[]) {
        if (args.length > 0) servidor = args[0];
        if (args.length > 1) porta = Integer.parseInt(args[1]);
        if (args.length > 2) msg = args[2];
    }
    public void run() { // a interface Runnable exige a implementa��o do m�todo run
        Thread thread = Thread.currentThread();
        DatagramSocket s = null;
        try {
            s = new DatagramSocket(); // cria um socket UDP
            System.out.println("* " + thread.getName() + " * Socket criado na porta: " + s.getLocalPort());
            byte[] m = msg.getBytes(); // transforma arg em bytes
            InetAddress serv = InetAddress.getByName(servidor);
            
            DatagramPacket req = new DatagramPacket(m, msg.length(), serv, porta);
            s.send(req); // envia datagrama contendo a mensagem m
            System.out.println("* " + thread.getName() + " * Datagrama enviado: " + msg);
            
            byte[] buffer = new byte[1000];
            DatagramPacket resp = new DatagramPacket(buffer, buffer.length);
            s.setSoTimeout(10000); // timeout em ms
            s.receive(resp); // aguarda resposta do servidor - bloqueante
            System.out.println("* " + thread.getName() + " * Resposta do servidor:" + new String(resp.getData()));
            
        } catch (SocketException e) {
            // timeout, erro na cria��o
            System.out.println("* " + thread.getName() + " * Erro socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("* " + thread.getName() + " * Erro envio/recepcao do pacote: " + e.getMessage());
        }  finally {
            if (s != null) s.close();
        } 
    }
}
public class Cliente {
     public static void main(String args[]) {
        Thread cliente1 = new Thread(new ThreadCliente(args), "CLIENTE 1");
        //Thread cliente2 = new Thread(new ThreadCliente(args), "CLIENTE 2");
        cliente1.start();
        //cliente2.start();
    
    }


    
}
