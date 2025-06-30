public class Carro extends Veiculo {
    private static final long serialVersionUID = 1L;

    private int portas;

    public Carro(String placa, String modelo, int ano, int portas) {
        super(placa, modelo, ano);
        this.portas = portas;
    }

    public int getPortas() {
        return portas;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }

    @Override
    public int getExtra() {
        return portas;
    }

    @Override
    public String exibirDados() {
        return super.exibirDados() + "\nPortas: " + portas;
    }
}