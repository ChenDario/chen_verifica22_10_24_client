package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket s = new Socket("localhost", 3000);
        System.out.println("Il client si è collegato");

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        
        Scanner scanner = new Scanner(System.in);
        
        //Dal server
        String stringaRicevuta;
        String tentativi;

        //Al server
        String numIn;

        do {
            //Gioco dell'indovina numero
            do {
                int mandare;
                boolean intero = false;
                //Per fare in modo che inserisca un intero
                do {
                    //Si inserisce il numero da mandare al server
                    System.out.println("Inserire la stringa: ");
                    numIn = scanner.nextLine(); 
                    try {
                        //Si inserisce il numero da mandare al server
                        mandare = Integer.parseInt(numIn);
                        System.out.println("\n Num inserito: " + mandare);
                        if(mandare >= 0 && mandare <= 100){
                            intero = false;
                        } else {
                            System.out.println(" - - Inserire un numero tra 0 e 100 - - ");
                            intero = true;
                        }
                            
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(" - - Inserire un numero tra 0 e 100 - - ");
                        intero = true;
                    }    
                } while (intero);
                
                out.writeBytes(numIn + "\n"); //Invio al server il numero
                //Leggo la risposta dal server
                stringaRicevuta = in.readLine();

                if(stringaRicevuta.equalsIgnoreCase("<"))
                    System.out.println("Numero troppo piccolo");
                if(stringaRicevuta.equalsIgnoreCase(">"))
                    System.out.println("Numero troppo grande");

            } while (!stringaRicevuta.equalsIgnoreCase("="));

            //Numero tentativi
            tentativi = in.readLine();
            System.out.println("HAI INDOVINATO IN " + tentativi + "\n");

            //Inserimento in classifica


            //Chiedere al giocare se si vuole fare una nuova partita
            System.out.println("Nuova Partita? (Yes/No) ");
            numIn = scanner.nextLine();

            //Messaggio al server di nuova partita o meno
            out.writeBytes(numIn);

        } while (numIn.equalsIgnoreCase("yes"));
        
    }
}