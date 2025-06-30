public class Moto extends Veiculo {
    private static final long serialVersionUID = 1L;

    private int cilindradas;

    public Moto(String placa, String modelo, int ano, int cilindradas) {
        super(placa, modelo, ano);
        this.cilindradas = cilindradas;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }

    @Override
    public int getExtra() {
        return cilindradas;
    }

    @Override
    public String exibirDados() {
        return super.exibirDados() + "\nCilindradas: " + cilindradas + "cc";
    }
}