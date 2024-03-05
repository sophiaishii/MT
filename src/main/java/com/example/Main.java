package com.example;

import java.io.IOException;


/*Autômatos de Pilha Determinísticos (P)
0,a;x;y,1 -- (transição de q0 para q1 lendo a da fita, x da pilha e escrevendo y na pilha) */

public class Main {
    public static void main(String[] args) throws IOException{

        String specsPath = "src/main/java/com/example/Exemplo/specs.json";
        String inputPath = "src/main/java/com/example/Exemplo/input.in";
        String outputPath = "src/main/java/com/example/Exemplo/output.txt";
        Leitor l = new Leitor();
        l.lerArquivoJSON(specsPath);

        switch(l.getTypeMachine()){

            case "T":{
                MT machine = new MT(l.getInitialState(), l.getFinalStates(), l.getConditions(), inputPath);
                machine.verificarCondicao(outputPath);
                break;
            }

            default :{
                System.out.println("Máquina não reconhecida, desligando...\n FIM");
                break;
            }

        }
        
     }
}

