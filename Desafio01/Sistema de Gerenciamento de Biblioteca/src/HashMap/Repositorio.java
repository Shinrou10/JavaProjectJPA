package HashMap;

import java.util.HashMap;
import java.util.Map;

public class Repositorio<T> {
    private Map<String, T> dados = new HashMap<>();

    public void adicionar(String chave, T objeto) {
        dados.put(chave, objeto);
    }

    public T buscar(String chave) {
        return dados.get(chave);
    }

    public void remover(String chave) {
        dados.remove(chave);
    }

    public Map<String, T> listar() {
        return dados;
    }
}
