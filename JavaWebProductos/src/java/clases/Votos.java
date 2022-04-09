package clases;
import java.io.Serializable;


public class Votos implements Serializable {
    String delegado;
    Integer valor;

    public Votos() {
    }

    public Votos(String delegado, Integer valor) {
        this.delegado = delegado;
        this.valor = valor;
    }

    public String getDelegado() {
        return delegado;
    }

    public void setDelegado(String delegado) {
        this.delegado = delegado;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Votos{" + "delegado=" + delegado + ", valor=" + valor + '}';
    }
    
    
    
}
