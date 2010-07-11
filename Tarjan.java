public class Tarjan {
	int index = 0;
	int[] indice;
	int[] lowlink;
	Pila pila = new Pila();
	boolean[] enPila;
	DiGraph grafo;
	List<List<Integer>> componentes;
	
	public Tarjan (DiGraph grafo) {
	    int numNodos = grafo.getNumberOfNodes();
	    indice = new int[numNodos];
	    lowlink = new int[numNodos];
	    enPila = new boolean[numNodos];

	    for (int i=0; i<numNodos; i++) {
		indice[i] = -1;
		lowlink[i] = -1;
		enPila[i] = false;
	    }

	    this.grafo = grafo;
	}
	
	public List<List<Integer>> obtComponentes() {
	    componentes = new Lista<List<Integer>>();
 	    for (int i=0; i<grafo.getNumberOfNodes(); i++) {
		if (indice[i]==-1) {		    
		    //Coloca una componente en la pila
		    this.tarjanAux(i);
		}
	    }

	    return this.componentes;
	}
	
	public void tarjanAux(int nodo) {
	    indice[nodo] = index;
	    lowlink[nodo] = index;
	    index++;
	    pila.empilar(new Integer(nodo));
	    enPila[nodo] = true;
	    
	    List<Integer> sucesores = grafo.getSucesors(nodo);
	    for (int i=0; i<sucesores.size(); i++) {
		int nodoTmp = sucesores.get(i).intValue();
		if (indice[nodoTmp] == -1) {
		    tarjanAux(nodoTmp);
		    lowlink[nodo] = minimo(lowlink[nodo],lowlink[nodoTmp]);
		} else if (enPila[nodoTmp]) {
		    lowlink[nodo] = minimo(lowlink[nodo],indice[nodoTmp]);
		}
	    }
	    if (lowlink[nodo] == indice[nodo]) {
		//Componente terminada
		List<Integer> nuevaComp = new Lista<Integer>();
		boolean ok = this.componentes.add(nuevaComp);
		Integer nodoComp;
		do {
		    nodoComp = (Integer) pila.desempilar();
		    enPila[nodoComp.intValue()] = false;
		    ok = nuevaComp.add(nodoComp);
		} while (nodo != nodoComp.intValue());
	    }
	}
	
	private int minimo(int a, int b) {
	    if (a<=b) {
		return a;
	    } else {
		return b;
	    }
	}
    }