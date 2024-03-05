package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Autômatos de Pilha Determinísticos (P)
0,a;x;y,1 -- (transição de q0 para q1 lendo a da fita, x da pilha e escrevendo y na pilha) */

public class MT extends Leitor{
    
    private String initialStateAP;
    private String[] finalStatesAP;
    private List<String[]> conditionsAP;
    private List<String[]> inputFormatted;
    private List<String> message;
    private String output;
    private String estadoAtual;

/*

Maquina de Turing

-Lê a fita
-Executa a função de transição
-Escreve
-Movimenta para a esquerda ou direita
-Vai para o proximo estado

- aN bN - n >= 0

-Quando ler um a ou quando ler um B vai para a finalização
-Escreve A
-Procura b
    -Vai para a direita enquanto ler a
    -Até ler b
    -Se ler b, escreve B
-Procura A
    -Vai para a esquerda enquanto ler b
    -Até ler A
### Repete o ciclo ###

---Finalização---
-Vai para a direita enquanto ler B, até ler "_"

    

*/


    public MT(String initialStateAP, String[] finalStatesAP, List<String[]> conditionsAP, String inputIn) throws IOException{
        System.out.println("Inicializando o MT..");
        this.initialStateAP = initialStateAP;
        this.finalStatesAP = finalStatesAP;
        this.conditionsAP = conditionsAP;
        message= new ArrayList<String>();
        inputFormatted = new ArrayList<String[]>();
        this.lerEntrada(inputIn);
    }

    public String toString(String[] input){
        String list = Arrays.toString(input).replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
        return list;
    }

    public void lerEntrada(String path) throws IOException{
        Path arquivo = Paths.get(path);
        if(!Files.exists(arquivo)){
            System.out.println("Não existe");
        }
        List<String> input = Files.readAllLines(arquivo);
        for(int i  = 0; i < input.size(); i++ ){
            //System.out.println(input.get(i).split(""));
            inputFormatted.add(input.get(i).concat("_").split(""));
        }
        /*for(int i=0;i<inputFormatted.size();i++){
             for(String linhas:inputFormatted.get(i)){
                System.out.println(linhas);
             }   
        }*/
    }

    public void verificarCondicao(String outputOut) throws IOException{
        String direction = "R";
        for(int i=0;i<this.inputFormatted.size();i++){
            int j=-1;
            String[] tape = inputFormatted.get(i);
            //Setando o estadoAtual como o primeiro estado
            estadoAtual = initialStateAP;
            
            while(true){
                
                if(direction.equals("R")){
                    j++;
                }
                else if(direction.equals("L")){
                    j--;
                }

                if(j==tape.length){
                    break;
                }

                //encontra todos as opções do estado atual
                //verifico a entrada com as condições de cada opção, se não bater já para. Se bater trocar
                //System.out.println(this.conditionsAFD.size());
                int achou=0;
                //Achar as condições do estadoAtual
                for(String[] aux:conditionsAP){
                //Split no aux[1]
                    String[] aux2 = aux[1].split(";");
                    if(estadoAtual.equals(aux[0])){
                        //Achamos as condições, agora teremos que verificar se bate com o input

                        //0,a;A;R,1
                        //aux2[0] a
                        if(tape[j].equals(aux2[0])){
                            //Escrevendo na fita
                            //aux2[1] A
                            tape[j] = aux2[1];

                            //aux2[2] = R
                            direction = aux2[2];
                            
                            //aux[2] = 1
                            estadoAtual=aux[2];   
                                                    
                            achou=1;
                            break;
                        }
                    }
                }
                if(achou==0){
                    
                    estadoAtual = "ERRO";
                    break;
                }
                // System.out.println(estadoAtual);

                
            } 
            
            for(String aux:this.finalStatesAP){
                
                if(estadoAtual.equals(aux)){
                    output = "1";
                    break;
                }
                else{
                    output="0";
                }
            }
            message.add(output+";"+toString(tape));
            

        }

        escreverArquivo(outputOut, message);
        System.out.println("Finalizando MT...\nFIM");

    }


}

