package model;

import java.util.ArrayList;
import java.util.List;

public class Capital {
    String nome;
    List<Aresta> conexoes;
    
    public Capital(String nome) {
        this.nome = nome;
        this.conexoes = new ArrayList<>();
    }
}
