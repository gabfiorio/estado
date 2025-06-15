package model;

import java.util.List;

public class ResultadoRota {
    public List<String> caminho;
    public int distanciaTotal;
    
    public ResultadoRota(List<String> caminho, int distanciaTotal) {
        this.caminho = caminho;
        this.distanciaTotal = distanciaTotal;
    }
}
