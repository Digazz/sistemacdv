import java.io.Serializable;

public abstract class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String placa;
    protected String modelo;
    protected int ano;

    public Veiculo(String placa, String modelo, int ano) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public abstract String getTipo();

    public abstract int getExtra();

    public String exibirDados() {
        return "Placa: " + placa + "\nModelo: " + modelo + "\nAno: " + ano;
    }
}