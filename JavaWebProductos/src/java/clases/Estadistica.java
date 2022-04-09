package clases;
import java.io.Serializable;
import java.util.ArrayList;

public class Estadistica implements Serializable {
    ArrayList<Votos> lista = new ArrayList<>();

    public Estadistica() {
    }

    public ArrayList<Votos> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Votos> lista) {
        this.lista = lista;
    }
    
    public void addVoto(Votos voto) {
        this.lista.add(voto);
    }    

    @Override
    public String toString() {
        return "Estadistica{" + "lista=" + lista + '}';
    }

    
    
}
