import java.util.*;

public class GrafGeneric<K,V,E> implements TADGraf<K,V,E>{

    private Hashtable<K, Node<K,V,E>> hashtable;

    GrafGeneric(){
        crearGraf();
    }

    public void afegirNode(K k, Node<K,V,E> nodeNou){ hashtable.put(k, nodeNou); }
    public Node<K,V,E> getHashValue(K k){ return hashtable.get(k); }

    @Override
    public void crearGraf() { hashtable = new Hashtable<>(); }

    @Override
    public void afegirAresta(Node<K,V,E> v1, Node<K,V,E> v2, E e) {
        Aresta<K,E> novArestaCol = new Aresta<>(e);
        //Aresta<K,E> novArestaFil = new Aresta<>(e);
        if(v1.primeraFila==null){
            v1.primeraFila = novArestaCol;
            v2.primeraColumna = novArestaCol;
            novArestaCol.setRefs(null, null, v1.ref, v2.ref);
            /*
            v1.primeraColumna = novArestaFil;
            v2.primeraFila = novArestaFil;
            novArestaCol.setRefs(null, null, v2.ref, v1.ref);*/
        }else{
            novArestaCol.setRefs(v1.primeraFila, v2.primeraColumna, v1.ref, v2.ref);
            v1.primeraFila = novArestaCol;
            v2.primeraColumna = novArestaCol;
            /*
            novArestaFil.setRefs(v2.primeraFila, v1.primeraColumna, v2.ref, v1.ref);
            v2.primeraFila = novArestaFil;
            v1.primeraColumna = novArestaFil;*/
        }
    }

    @Override
    public boolean existeixAresta(Node<K,V,E> v1, Node<K,V,E> v2) {
        boolean trobat = false;
        Aresta<K,E> aux;
        if((Integer)v1.ref < (Integer)v2.ref){
            aux = v1.primeraFila;
            while (aux!=null){
                if (aux.refFila == v1.ref && aux.refCol == v2.ref) {
                    trobat = true;
                    break;
                }
                aux = aux.segFila;
            }
        }else{
            aux = v2.primeraFila;
            while (aux!=null){
                if (aux.refCol == v1.ref && aux.refFila == v2.ref) {
                    trobat = true;
                    break;
                }
                aux = aux.segFila;
            }
        }
        return trobat;
    }

    @Override
    public E valorAresta(Node<K,V,E> v1, Node<K,V,E> v2) {
        E value = null;
        Aresta<K,E> aux;
        if((Integer)v1.ref < (Integer)v2.ref){
            aux = v1.primeraFila;
            while (aux!=null){
                if(aux.refFila == v1.ref && aux.refCol == v2.ref) value = aux.infoAresta;
                aux = aux.segFila;
            }
        }else{
            aux = v2.primeraFila;
            while (aux!=null){
                if(aux.refCol == v1.ref && aux.refFila == v2.ref) value = aux.infoAresta;
                aux = aux.segFila;
            }
        }
        return value;
    }

    @Override
    public List<Node<K,V,E>> adjacents(Node<K,V,E> v) {
        List<Node<K,V,E>> value = new LinkedList<>();
        Aresta<K,E> aux;
        aux = v.primeraFila;
        while (aux!=null){ value.add(this.getHashValue(aux.refCol)); aux = aux.segFila; }
        aux = v.primeraColumna;
        while (aux!=null){ value.add(this.getHashValue(aux.refFila)); aux = aux.segCol; }
        return value;
    }
    ///Algorismes
    List<String> camiOptim(K identificador_origen, K
            identificador_desti, int autonomia){

        ArrayList<K> noVisitats = new ArrayList<>();
        Hashtable<K, Boolean> visitats = new Hashtable<>();
        Hashtable<K, Double> distancies = new Hashtable<>();
        Hashtable<K,K> camiNodes = new Hashtable<>();
        //
        Enumeration<K> e = hashtable.keys();
        // Iterating through the Hashtable
        //Setting elements
        while (e.hasMoreElements()) {
            K key = e.nextElement();

            noVisitats.add(key);
            camiNodes.put(key, key);
            visitats.put(key, false);

            if(key.equals(identificador_origen)){
                distancies.put(key,0.0);
            }else{
                distancies.put(key,Double.MAX_VALUE);
            }

        }

        K currentVertex = null;
        K key = null;
        Aresta<K,E> inici = null;
        Double shortestDistance;

        while (noVisitats.size()!=0){//mentre no s'hagin visitat tots els nodes

            //Get currentVertex: unvisited && shortestDistance
            e = visitats.keys();
            shortestDistance = Double.MAX_VALUE;
            while (e.hasMoreElements()){
                key = e.nextElement();
                if (!visitats.get(key) && distancies.get(key) < shortestDistance){
                    currentVertex = key;
                    shortestDistance = distancies.get(key);
                }
            }

            //Visit NeighBours
            inici = hashtable.get(currentVertex).primeraFila;
            while (inici!=null){//For each unvisited NeighBours

                //Calculate distance from origin vertex: inici.infoAresta
                //if inici.infoAresta < distancies.get(inici.refCol)
                if((Double) inici.infoAresta+distancies.get(inici.refFila) < distancies.get(inici.refCol)){
                    // Update shortest distance to this vertex -> distancies
                    // Update the previous vertex with the current vertex -> camiNodes
                    distancies.put(inici.refCol, (Double) inici.infoAresta+distancies.get(inici.refFila));
                    camiNodes.put(inici.refCol, inici.refFila);
                }

                inici = inici.segFila;
            }

            inici = hashtable.get(currentVertex).primeraColumna;
            while (inici!=null){//For each unvisited NeighBours

                //Calculate distance from origin vertex: inici.infoAresta+
                //if inici.infoAresta < distancies.get(inici.refCol)
                if((Double) inici.infoAresta+distancies.get(inici.refCol) < distancies.get(inici.refFila)){
                    // Update shortest distance to this vertex -> distancies
                    // Update the previous vertex with the current vertex -> camiNodes
                    distancies.put(inici.refFila, (Double) inici.infoAresta+distancies.get(inici.refCol));
                    camiNodes.put(inici.refFila, inici.refCol);
                }

                inici = inici.segCol;
            }

            //Current vertex is visited -> visitats
            //Current vertex remove from noVisitats
            visitats.put(currentVertex, true);
            noVisitats.remove(currentVertex);
        }

        //Calcul cami optim
        ArrayList<String> cami = new ArrayList<>();
        K aux = identificador_desti;
        while (!aux.equals(identificador_origen)){
            cami.add(camiNodes.get(aux)+" - "+distancies.get(aux)+" km -> "+aux);
            aux = camiNodes.get(aux);
        }
        ArrayList<String> camiOrdenat = new ArrayList<>();
        for (int i = cami.size()-1; i>=0; i--){
            camiOrdenat.add(cami.get(i));
        }
        return camiOrdenat;
    }
}