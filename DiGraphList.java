import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * DiGraphList es una clase que representa un grafo a traves de la
 * estructura concreta de dos arreglos de listas de adyacencias.
 * La lista inArcs asocia cada nodo con sus predecesores, y la
 * lista outArcs asocia cada nodo con sus sucesores.
 *
 * @author Les profs
 * Modificado por Jose A Goncalves y Jennifer Dos Reis
 * @version 1.0
 * @since 1.6
**/

public class DiGraphList extends DiGraph {

    // Arreglo de lista de los arcos, inArc[i] contiene la lista
    // de los arcos que cuyo destino es el nodo i
   private List<Arc> inArcs[];
    // arreglo de lista de los arcos, outArc[i] contiene la lista
    // de los arcos que cuyo origen es el nodo i
   private List<Arc> outArcs[];

    /**
     * Crea un DiGraphList con n nodos y sin arcos.
     * @param n Numero de nodos iniciales del grafo. Tiene que ser mayor a 0
     */
    public DiGraphList(int n) {
	inArcs = new List[n];
	outArcs = new List[n];
	this.numNodes = n;
	this.numArcs = 0;
	for (int i=0; i<n; i++) {
	    inArcs[i]  = new Lista<Arc>();
	    outArcs[i] = new Lista<Arc>();
	}
    }

    /**
     * Crea un DiGraphList a partir del contenido de un archivo.
     * Si el archivo no existe lanza IOException.
     * Si el archivo no cumple el formato, lanza NumberFormatException
     * El número de nodos debe ser mayor a 0, y el número de arcos
     * reportados por el archivo deben coincidir con el número de arcos
     * en el archivo.
     * @param fileName Nombre del archivo.
     * @throws IOException, NumberFormatException
     */
    public DiGraphList(String fileName) throws IOException, NumberFormatException {
	BufferedReader in = new BufferedReader(new FileReader(fileName));
	String linea = in.readLine();
	String[] tokens = linea.split(" ");
	boolean ok = true;

	this.numNodes = Integer.parseInt(tokens[0]);
	this.numArcs  = Integer.parseInt(tokens[1]);
	inArcs  = new List[numNodes];
	outArcs = new List[numNodes];
	
	for (int i=0; i<numNodes; i++) {
 	   inArcs[i]  = new Lista<Arc>();
	   outArcs[i] = new Lista<Arc>();
	}
	
	for (int i=0;i<this.numArcs && (linea = in.readLine())!=null ;i++ ) { 
	    tokens= linea.split(" ");
	    int nodoInic= Integer.parseInt(tokens[0]);
	    int nodoFin = Integer.parseInt(tokens[1]);
	    if(nodoInic<numNodes && nodoInic>=0 
				   && nodoFin<numNodes && nodoFin>=0){
	        if(this.outArcs[nodoInic].contains(new Arc(nodoInic,nodoFin))){
	    	    numArcs--;
		}else{
		    ok =       outArcs[nodoInic].add(new Arc(nodoInic,nodoFin))
		   	 && inArcs[nodoFin].add(new Arc(nodoInic,nodoFin));
		}	    
	    }
	}
	
    }

    /**
     * Crea un DiGraphList a partir del DiGraph g
     * @param g Digrafo que se va a copiar. Debe ser distinto de null.
     */
    public DiGraphList(DiGraph g) {
	this.numNodes= g.getNumberOfNodes();
	this.numArcs = g.getNumberOfArcs();
	inArcs  = new List[numNodes];
	outArcs = new List[numNodes];
	boolean ok;
	
	for (int i=0; i<numNodes; i++) {
	    outArcs[i] = new Lista<Arc>();
	    for(int k=0; k<numNodes; k++){
		if (inArcs[k]==null) {
		    inArcs[k] = new Lista<Arc>();
		}
		if (g.isArc(i,k)) {
		    ok = outArcs[i].add(new Arc(i,k));
		    ok = inArcs[k].add(new Arc(i,k));
		}
	    }
	}	
    }

    /**
     * Genera una copia de este DiGraphList
     * @return Una copia de este DiGraphList
     */
    @Override
    public DiGraphList clone() {
	DiGraphList digrafo = new DiGraphList(this.numNodes);
	digrafo.numArcs = this.numArcs;
	for (int i=0; i<this.numNodes; i++) {
	    digrafo.inArcs[i]  = this.inArcs[i].clone();
	    digrafo.outArcs[i] = this.outArcs[i].clone();
	}
	return digrafo;
    }

    /**
     * Agrega al grafo la cantidad de nodos especificada
     * Estos nuevos nodos no tienen lados incidentes
     * @param num Cantidad de nodos nuevos. No puede ser negativo.
     */
    public void addNodes(int num) {
	List<Arc>[] inArcsTemp  = new List[num+this.numNodes];
	List<Arc>[] outArcsTemp = new List[num+this.numNodes];
	for (int i=0; i<this.numNodes; i++) {
	    inArcsTemp[i]  = this.inArcs[i];
	    outArcsTemp[i] = this.outArcs[i];
	}
	for (int i=this.numNodes; i<this.numNodes+num; i++) {
	    inArcsTemp[i]  = new Lista<Arc>();
	    outArcsTemp[i] = new Lista<Arc>();
	}
	this.inArcs  = inArcsTemp;
	this.outArcs = outArcsTemp;
	this.numNodes += num;
    }

    /**
     * Agrega al grafo un arco entre los nodos especificados
     * @param src Nodo inicial
     * @param dst Nodo terminal
     * @return Un objeto Arc cuyos nodos inicial y terminal son src y dst.
     *         Si los nodos no existen o si el arco ya existe devuelve null
     */
    public Arc addArc(int src, int dst) {
        if (src<0 || dst <0 || src>=this.numNodes || dst>=this.numNodes) {
	    return null;
	} else {
	    Arc arco = new Arc(src,dst);
	    if (this.outArcs[src].contains(arco)) {
		return null;
	    } else {
		this.outArcs[src].add(arco);
		this.inArcs[dst].add(arco);
		this.numArcs++;
		return arco;
	    }
	}
    }

    /**
     * Agrega al grafo un arco con un costo asociado.
     * @param src Nodo inicial
     * @param dst Nodo terminal
     * @param costo Costo asociado al arco
     * @return Un objeto Arc cuyos nodos inicial y terminal son src y dst.
     *         Si uno de los nodos no existe devuelve null.
     */
    public Arc addArc(int src, int dst, double costo) {
        if (src<0 || dst <0 || src>=this.numNodes || dst>=this.numNodes) {
	    return null;
	} else {
	    Arc arco = new Arc(src,dst);
	    arco.setCost(costo);
	    if (this.outArcs[src].contains(arco)) {
		return null;
	    } else {
		this.outArcs[src].add(arco);
		this.inArcs[dst].add(arco);
		this.numArcs++;
		return arco;
	    }
	}
    }

    /**
     * Devuelve una lista cuyos elementos son los predecesores del
     * nodo especificado
     * @param nodeId Nodo del que se obtendrán sus predecesores
     * @return Una lista con los predecesores del nodo especificado;
     *         Devuelve null si el nodo no existe o hubo algún
     *         problema construyendo la lista.
     */
    public List<Integer> getPredecesors(int nodeId) {
        if (nodeId<0 || nodeId>=this.numNodes) {
	    return null;
	} else {
            Lista<Integer> predecesores = new Lista<Integer>();
	    Arc arco;
	    int elemento;
	    boolean estado;
	    for (int i=0; i<this.inArcs[nodeId].size(); i++) {
		arco = this.inArcs[nodeId].get(i);
		elemento = arco.getSource();		
		if ( !predecesores.add(elemento) ) {
		    return null;
		}
	    }
	    return predecesores;
	}
    }

    /**
     * Devuelve una lista cuyos elementos son los sucesores del
     * nodo especificado
     * @param nodeId Nodo del que se obtendrán sus sucecesores
     * @return Una lista con los sucesores del nodo especificado;
     *         Devuelve null si el nodo no existe o hubo algún
     *         problema construyendo la lista.
     */
    public List<Integer> getSucesors(int nodeId) {
        if (nodeId<0 || nodeId>=this.numNodes) {
	    return null;
	} else {
            Lista<Integer> sucesores = new Lista<Integer>();
	    Arc arco;
	    int elemento;
	    boolean estado;
	    for (int i=0; i<this.outArcs[nodeId].size(); i++) {
		arco = this.outArcs[nodeId].get(i);
		elemento = arco.getDestination();		
		if ( !sucesores.add(elemento) ) {
		    return null;
		}
	    }
	    return sucesores;
	}
    }

    /**
     * Regresa un objeto Arc cuyos nodos inicial y terminal son los especificados.
     * Este método es equivalente a llamar al constructor de Arc.
     * @param nodoSrc Nodo inicial
     * @param nodoDst Nodo terminal
     * @return Construye el Arc con nodos inicial y terminal iguales a nodoSrc y nodoDst
     */
    public Arc getArc(int nodoSrc, int nodoDst) {
	return new Arc(nodoSrc, nodoDst);
    }

    /**
     * Reemplaza el grafo por el grafo contenido en un archivo
     * Si el archivo no existe lanza IOException.
     * Si el archivo no cumple el formato, lanza NumberFormatException
     * El número de nodos debe ser mayor a 0, y el número de arcos
     * reportados por el archivo deben coincidir con el número de arcos
     * en el archivo.
     * @param fileName Nombre del archivo
     * @throws IOException, NumberFormatException
     */
    public void read(String fileName) throws IOException, NumberFormatException{
	BufferedReader in= new BufferedReader(new FileReader(fileName));
	String linea= in.readLine();
	String[] tokens= linea.split(" ");
	boolean ok;

	this.numNodes= Integer.parseInt(tokens[0]);
	this.numArcs = Integer.parseInt(tokens[1]);
	inArcs  = new List[numNodes];
	outArcs = new List[numNodes];
	
	for (int i=0; i<numNodes; i++) {
	    inArcs[i] = new Lista<Arc>();
	    outArcs[i] = new Lista<Arc>();
	}
	
	for (int i=0;i<this.numArcs && (linea = in.readLine())!=null;i++) { 
	    tokens= linea.split(" ");
	    int nodoInic= Integer.parseInt(tokens[0]);
	    int nodoFin = Integer.parseInt(tokens[1]);
	    ok =    outArcs[nodoInic].add(new Arc(nodoInic,nodoFin))
	         && inArcs[nodoFin].add(new Arc(nodoInic,nodoFin));
	}
	
    }

    /**
     * Escribe este grafo en un archivo
     * Si hubo un problema al escribir el archivo (ej. si no existe el archivo)
     * lanza IOException
     * @param fileName Nombre del archivo
     * @throws IOException
     */
    public void write(String fileName) throws IOException {
        PrintStream out= new PrintStream(fileName);
	out.print(numNodes + " "+ numArcs+ "\n");

	for(int i=0; i<numNodes;i++){
		int t= outArcs[i].size();
		for(int k=0; k<t; k++){
			Arc arco= outArcs[i].get(k);
			out.print(i+" "+ arco.getDestination() + "\n");
		}
	}
    }

    /**
     * Obtiene el grado de un nodo.
     * @param nodeId Nodo del grafo
     * @return El grado del nodo especificado, -1 si el nodo no existe
     */
    public int getDegree(int nodeId) {
        if (nodeId<0 || nodeId>=this.numNodes) {
	    return -1;
	} else {
	    return this.outArcs[nodeId].size()+this.inArcs[nodeId].size();
	}
    }

    /**
     * Obtiene el grado externo de un nodo.
     * @param nodeId Nodo del grafo
     * @return El grado externo del nodo especificado, -1 si el nodo no existe
     */
    public int getOutDegree(int nodeId) {
        if (nodeId<0 || nodeId>=this.numNodes) {
	    return -1;
	} else {
	    return this.outArcs[nodeId].size();
	}
    }

    /**
     * Obtiene el grado interno de un nodo.
     * @param nodeId Nodo del grafo
     * @return El grado interno del nodo especificado, -1 si el nodo no existe
     */
    public int getInDegree(int nodeId) {
        if (nodeId<0 || nodeId>=this.numNodes) {
	    return -1;
	} else {
	    return this.inArcs[nodeId].size();
	}
    }

    /**
     * Obtiene la cantidad de nodos del grafo
     * @return La cantidad de nodos del grafo
     */
    public int getNumberOfNodes() {
        return this.numNodes;
    }

    /**
     * Obtiene la cantidad de arcos en el grafo
     * @return La cantidad de arcos en el grafo
     */
    public int getNumberOfArcs() {
	return this.numArcs;
    }

    /**
     * Devuelve una lista con los arcos que parten de un nodo del grafo
     * @param nodeId Nodo del grafo
     * @return Una lista con los arcos que parten del nodo especificado;
     *         Devuelve null si el nodo no existe
     */
    public List<Arc> getOutEdges(int nodeId) {
	if (nodeId<0 || nodeId>=this.numNodes) {
	    return null;
	} else {
	    return this.outArcs[nodeId].clone();	    
	}	 
    }

    /**
     * Devuelve una lista con los arcos que apuntan a un nodo del grafo
     * @param nodeId Nodo del grafo
     * @return Una lista con los arcos que apuntan al nodo especificado;
     *         Devuelve null si el nodo no existe
     */
    public List<Arc> getInEdges(int nodeId) {
	if (nodeId<0 || nodeId>=this.numNodes) {
	    return null;
	} else {
	    return this.inArcs[nodeId].clone();	    
	}
    }

    /**
     * Borra un arco del grafo
     * @param nodeIniId Nodo inicial
     * @param nodeFinId Nodo terminal
     * @return Un Arc correspondiente al arco eliminado, null si alguno
     *         de los nodos o el arco no existe.
     */
    public Arc delArc(int nodeIniId, int nodeFinId) {
        if (    nodeIniId<0 || nodeFinId<0 
	     || nodeIniId >= this.numNodes 
	     || nodeFinId >= this.numNodes
	     || !this.outArcs[nodeIniId].contains(new Arc(nodeIniId,nodeFinId))) {
	    return null;
	} else {
	    Arc arco = new Arc(nodeIniId, nodeFinId);
	    if (   this.outArcs[nodeIniId].remove(arco) 
		&& this.inArcs[nodeFinId].remove(arco) ) {
		this.numArcs--;
		return arco;
	    } else {
		return null;
	    }
	}
    }

    /**
     * Borra todos los arcos del grafo
     * @return Una lista con todos los arcos del grafo
     */
    public List<Arc> removeAllArcs() {
	boolean error;
	Lista<Arc> salida = new Lista<Arc>();
	for (int i=0; i<this.numNodes; i++) {
	    for(int j=0; j<this.outArcs[i].size(); j++) {
		error = salida.add(this.outArcs[i].get(j));
	    }
	    outArcs[i].clear();
	    inArcs[i].clear();
	}
	this.numArcs=0;
	return salida;
    }

    /**
     * Invierte la orientación de un arco del grafo
     * @param nodeIniId Nodo inicial
     * @param nodeFinId Nodo terminal
     * @return true si el arco fue eliminado, false si el arco no
     *         existe o si hubo un problema al invertir el arco.
     */
    public boolean reverseArc(int nodeIniId, int nodeFinId) {
	if (   nodeIniId<0 || nodeFinId<0 || nodeIniId >= this.numNodes
	    || nodeFinId >= this.numNodes 
	    || !this.isArc(nodeIniId,nodeFinId)) {
	    return false;
	} else {
	    boolean error;
	    Arc nuevo = new Arc(nodeFinId,nodeIniId);
	    error = this.outArcs[nuevo.getSource()].add(nuevo);
	    error = this.inArcs[nuevo.getDestination()].add(nuevo);
	    error = this.outArcs[nodeIniId].remove(new Arc(nodeIniId,nodeFinId));
	    error = this.inArcs[nodeFinId].remove(new Arc(nodeIniId,nodeFinId));
	    return error;
	}
    }

    /**
     * Invierte la orientación de todos los arcos del grafo.
     * @return Esta operación siempre es exitosa y devuelve true.
     */
    public boolean reverseArcs() {
	int arcos = this.numArcs;
	List<Arc> arcosOriginales = this.removeAllArcs();
	Arc arco;
	int destinoNuevo;
	int origenNuevo;
	for (int i=0; i<arcosOriginales.size(); i++) {
	    arco = arcosOriginales.get(i);
	    destinoNuevo =  arco.getSource();
	    origenNuevo =  arco.getDestination();
	    this.numArcs--;
	    arco = this.addArc(origenNuevo,destinoNuevo);
	}
	this.numArcs = arcos;
	return true;
    }

    /**
     * Evalua si dos grafos son iguales
     * @param g Grafo con el que se va a comparar
     * @return True si son iguales, false en caso contrario
     */
    public boolean equals(DiGraph g) {
        if (g==null || g.getNumberOfNodes()!=this.numNodes) {
	    return false;
	} else {
	    boolean igual = true;
	    Arc arco = null;
	    //Evaluamos si todos los arcos en this están en g
	    //Y si el grado de los nodos en this es igual al de los de g
	    for (int i=0; i<this.numNodes && igual; i++) {
		igual = this.getDegree(i)==g.getDegree(i);
		for (int j=0; j<this.outArcs[i].size() && igual; j++) {
		    arco = this.outArcs[i].get(j);
		    igual = g.isArc(arco.getSource(),arco.getDestination());
		}
	    }
	    return igual;
	}
    }

    /**
     * Retorna un Digraph que es la clausura transitiva de este DiGraph
     * @return un Digraph que es la clausura transitiva de este DiGraph
     */

    public DiGraph royWarshall() {
	Lista<Arc> nuevos = new Lista<Arc>();
	boolean error;
	Arc arco;
	DiGraph salida = new DiGraphList(this.numNodes);
	//Extraer todos los arcos del grafo
	for (int i=0; i<this.numNodes; i++) {
	    for (int j=0; j<this.outArcs[i].size(); j++) {
		arco = (Arc) this.outArcs[i].get(j).clone();
		error = nuevos.add(arco);
	    }
	    //Agrega los arcos reflexivos al grafo salida
	    arco = salida.addArc(i,i);
	}

	int origen, intermedio, destino;
	//Generar los arcos transitivos
	for (int i=0; i<nuevos.size(); i++) {
	    origen = nuevos.get(i).getSource();
	    intermedio = nuevos.get(i).getDestination();
	    arco = salida.addArc(origen,intermedio);
	    for (int j=0; j<outArcs[intermedio].size(); j++) {
		destino = outArcs[intermedio].get(j).getDestination();
		arco = new Arc(origen,destino);
		if (   origen!=destino && intermedio!=destino 
		    && !nuevos.contains(arco)
		    && !outArcs[origen].contains(arco)) {
			error = nuevos.add(arco);
		}
	    }
	}
	return salida;
    }

    /**
     * Retorna la representación en String de este DiGraph
     * @return La representación en String de este grafo
     */
    @Override
    public String toString() {
        String salida = this.numNodes + " " + this.getNumberOfArcs();
	for (int i=0; i<this.numNodes; i++) {
	    for (int j=0; j<this.outArcs[i].size(); j++) {
		    salida += "\n"+i+" "+this.outArcs[i].get(j).getDestination();
	    }
	}
	return salida;
    }

    /**
     * Determina si existe un arco en el grafo
     * @param src Nodo inicial
     * @param dst Nodo terminal
     * @return True si existe el arco, false si no existe o alguno de los nodos
     *         no existe
     */
    @Override
    public boolean isArc(int src, int dst) {
	if (src<0 || dst<0 || src>=this.numNodes || dst>=this.numNodes) {
	    return false;
	} else {
	    return this.outArcs[src].contains(new Arc(src,dst));
	}
    }

    /**
     * Aplica el algoritmo de Tarjan al grafo para obtener las componentes fuertemente conexas.
     * @return Una lista de listas de enteros. Cada lista de enteros es el conjunto de nodos de una componente fuertemente conexa del grafo.
     */    
    public List<List<Integer>> tarjan() {
	List<List<Integer>> lista = new Lista<Lista<Integer>>();
	return lista;
    }


    /**
     * Devuelve una lista con todos los arcos pertenecientes a este grafo.
     * @return Una lista de arcos.
     */
    public List<Arc> getAllArcs() {
	List<Arc> arcos = new Lista<Arc>();
	for (int i=0; i<this.numNodes; i++) {
	    for (int j=0; j<outArcs[i].size(); j++) {
		boolean ok = arcos.add(outArcs[i].get(j));
	    }
	}
	return arcos;
    }

}
