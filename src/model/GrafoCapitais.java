package model;

import java.util.*;

public class GrafoCapitais {
    public Map<String, Capital> capitais;
    
    public GrafoCapitais() {
        this.capitais = new HashMap<>();
    }
    
    public void adicionarCapital(String nome) {
        capitais.put(nome, new Capital(nome));
    }
    
    public void adicionarConexaoTerrestre(String origem, String destino, int distancia) {
        capitais.get(origem).conexoes.add(new Aresta(destino, distancia));
        capitais.get(destino).conexoes.add(new Aresta(origem, distancia));
    }
    
    public ResultadoRota calcularMenorRotaTerrestre(String origem, String destino) {
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecessores = new HashMap<>();
        PriorityQueue<Aresta> filaPrioridade = new PriorityQueue<>(Comparator.comparingInt(a -> a.distanciaTerrestre));
        
        for (String capital : capitais.keySet()) {
            distancias.put(capital, Integer.MAX_VALUE);
        }
        distancias.put(origem, 0);
        filaPrioridade.add(new Aresta(origem, 0));
        
        while (!filaPrioridade.isEmpty()) {
            Aresta atual = filaPrioridade.poll();
            String capitalAtual = atual.destino;
            
            for (Aresta vizinha : capitais.get(capitalAtual).conexoes) {
                int novaDistancia = distancias.get(capitalAtual) + vizinha.distanciaTerrestre;
                
                if (novaDistancia < distancias.get(vizinha.destino)) {
                    distancias.put(vizinha.destino, novaDistancia);
                    predecessores.put(vizinha.destino, capitalAtual);
                    filaPrioridade.add(new Aresta(vizinha.destino, novaDistancia));
                }
            }
        }

        List<String> caminho = new ArrayList<>();
        String atual = destino;
        
        if (predecessores.get(atual) == null && !origem.equals(destino)) {
            return null;
        }
        
        while (atual != null) {
            caminho.add(atual);
            atual = predecessores.get(atual);
        }
        
        Collections.reverse(caminho);
        
        return new ResultadoRota(caminho, distancias.get(destino));
    }
}
