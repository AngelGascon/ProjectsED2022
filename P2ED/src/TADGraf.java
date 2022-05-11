import java.util.List;

public interface TADGraf<K,V,E> {
    /**
     * Constructor per inicialitzar la taula.
     */
    void crearGraf();

    /**
     * - Funció per a afegir una aresta.
     * - L’operació llença una excepció en cas que no es pugui afegir.
     * @param v1 node1
     * @param v2 node1
     * @param e nova aresta
     */
    void afegirAresta(Node<K,V,E> v1, Node<K,V,E> v2, E e);

    /**
     * - Funció que ens diu si una aresta existeix.
     * @param v1 node1
     * @param v2 node2
     * @return existeix(true) / no existeix(false)
     */
    boolean existeixAresta(Node<K,V,E> v1, Node<K,V,E> v2);

    /**
     * - Funció que retorna el valor d'una aresta.
     * - L’operació llença una excepció en cas que no existeixi.
     * @param v1 node1
     * @param v2 node2
     * @return E valor aresta
     */
    E valorAresta(Node<K,V,E> v1, Node<K,V,E> v2);

    /**
     * - Funció que retorna una llista que conté tots els nodes adjacents al node passat per
     * paràmetre.
     * - L’operació llença una excepció en cas que no es pugui crear aquesta llista.
     * @param v node d'adjacencies
     * @return llista d'adjacencies
     */
    List<Node<K,V,E>> adjacents(Node<K,V,E> v);
}
