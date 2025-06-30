import java.util.ArrayList;
import java.util.List;

public class SistemaCadastro {
    private List<Veiculo> veiculos;
    private BancoDeDados banco;

    public SistemaCadastro() {
        banco = new BancoDeDados();
        veiculos = banco.carregarVeiculos();
    }

    public void adicionarVeiculo(Veiculo v) {
        veiculos.add(v);
        banco.salvarVeiculos(veiculos);
    }

    public Veiculo buscarPorPlaca(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    public boolean atualizarVeiculo(String placa, Veiculo novo) {
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getPlaca().equalsIgnoreCase(placa)) {
                veiculos.set(i, novo);
                banco.salvarVeiculos(veiculos);
                return true;
            }
        }
        return false;
    }

    public boolean excluirVeiculo(String placa) {
        Veiculo v = buscarPorPlaca(placa);
        if (v != null) {
            veiculos.remove(v);
            banco.salvarVeiculos(veiculos);
            return true;
        }
        return false;
    }

    public List<Veiculo> listarTodos() {
        return new ArrayList<>(veiculos);
    }
}