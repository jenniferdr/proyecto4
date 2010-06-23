import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * DiGraphMatrix es una clase que representa un grafo a traves
 * de la estructura concreta de la matriz de adyacencias.
 * Extiende a DiGraph.
 * 
 * @author Les profs
 * Modificado por Jennifer Dos Reis y José A. Goncalves
 * @version 2.0
 * @since 1.6
**/
 
public class DiGraphMatrix extends DiGraph {

    // estructura de la matriz de adyacencias que se debe utilizar
   private boolean matrix[][];

   /**
    * Precondicion: n>0 
    * Crea un DiGraphMatrix con n nodos y sin arcos
    * @param n
    */
   public DiGraphMatrix(int n) {
	matrix = new boolean[n][n];
        numArcs = 0;
	numNodes = n;
   }

   /** 
    * Precondicion: El número de nodos debe ser mayor a 0, y el número de arcos
    * reportados por el archivo deben coincidir con el número de arcos
    * en el archivo.
    * Crea un DiGraphMatrix a partir del contenido del archivo.
    *
    * @param fileName nombre del archivo
    * @throws FileNotFoundException, IOException, NumberFormatException
    * Arroja NumberFormatException si en el archivo se encuentra algun
    * caracter que no sea numero. 
    */
   
   public DiGraphMatrix(String fileName) throws FileNotFoundException, IOException, NumberFormatException{
	BufferedReader in= new BufferedReader(new FileReader(fileName));
	String linea= in.readLine();
	String[] tokens= linea.split(" ");

	//Inicializar variables 
	this.numNodes= Integer.parseInt(tokens[0]);
	this.matrix = new boolean[numNodes][numNodes];
	this.numArcs= Integer.parseInt(tokens[1]);
	

	for (int i=0;i<this.numArcs && (linea = in.readLine())!=null;i++) {  
	    tokens= linea.split(" ");
	    int nodoOrigen= Integer.parseInt(tokens[0]);
	    int nodoFin= Integer.parseInt(tokens[1]);
	    if(nodoOrigen<numNodes && nodoOrigen>=0 
				   && nodoFin<numNodes && nodoFin>=0){
		if(matrix[nodoOrigen][nodoFin]){ 
		    numArcs--;
		}else{ 
		    matrix[nodoOrigen][nodoFin]= true;
		}
	    }
	    
	}
	
	
   }

   /**
    * Precondicion: El grafo de entrada debe ser diferente de null.
    * Crea un DiGraphMatrix a partir del DiGraph g
    * 
    * @param g el grafo fuente.
    */
   public DiGraphMatrix(DiGraph g) {
	this.numArcs= g.numArcs;
	this.numNodes= g.numNodes;

	for(int i=0; i<numNodes; i++){
	    for(int k=0; k<numNodes;k++){
		this.matrix[i][k]= g.isArc(i,k) ? true : false ; 
	    }
	}
   }

   /**
     * Genera una copia de este DiGraph.
     * @return una copia de este DiGraph.
     */
   @Override
   public DiGraphMatrix clone() {
	DiGraphMatrix g= new DiGraphMatrix(this.numNodes);

	for (int i=0; i<numNodes; i++) {
	    for (int j=0; j<numNodes; j++) {
		g.matrix[i][j] = this.matrix[i][j] ;
	    }
	}
	
	g.numNodes = this.numNodes;
	g.numArcs = this.numArcs;
	return g;
	
   }

    /**
     * Permite agregar <i>num</i> nuevos nodos a este DiGraph.
     * Si el <i>num</i> <=0 entonces no realiza cambios al grafo
     *
     * @param num numero de nodos a agregar
     */ 
    public void addNodes(int num) {
	if(num<0)
	    return;

        int n= this.numNodes + num;
	boolean matrixAux[][] = new boolean[n][n];

	for(int i=0; i<n-num; i++){
	    for(int k=0; k<n-num;k++){
		matrixAux[i][k]= this.matrix[i][k] ; 
	    }
	}
	this.numNodes = n;
	this.matrix= matrixAux;
    }

    /**
     * Agrega un arco a este DiGraph
     *
     * @param src nodo fuente del arco
     * @param dst nodo destino del arco
     * @return El arco agregado, null si los nodos especificados no 
     * son validos o si el arco ya existia
     *
     */
    public Arc addArc(int src, int dst) {
	if (src<0 || dst <0 || src>=this.numNodes || dst>=this.numNodes
		 || matrix[src][dst] ) 
	    return null;
	
        Arc arco= new Arc(src,dst);
	matrix[src][dst]= true; 
	this.numArcs++;
	return arco;
    }

    /**
     * Agrega un arco a este DiGraph
     *
     * @param src nodo fuente del arco
     * @param dst nodo destino del arco
     * @param costo del arco
     * @return El arco agregado.Si los nodos especificados no son validos
     * retorna null. 
     *
     */
    public Arc addArc(int src, int dst, double costo) {
	if (src<0 || dst <0 || src>=this.numNodes || dst>=this.numNodes) 
	    return null;

        Arc arco= new Arc(src,dst);
	arco.setCost(costo);
	matrix[src][dst]= true; 
	this.numArcs++;
	return arco;
    }
	
    /**
     * Retorna la lista de predecesores del nodo nodeId
     * 
     * @param nodeId el id del nodo del que se quieren los predecesores
     * 
     * @return lista de predecesores de nodeId. Si algun elemento no se
     * pudo agregar o nodeId no es un nodo del grafo devuelve  null. 
     */ 
    public List<Integer> getPredecesors(int nodeId) {
	if(nodeId<0 || nodeId>=numNodes)
	    return null;

	boolean ok=true;
 	Lista<Integer> lista= new Lista<Integer>(); 

	// Recorre la columna del nodeId y si hay arcos agrega 
	// el predecesor a la lista
	for(int i=0; i<numNodes; i++){
	    if(matrix[i][nodeId]){
		ok= ok && lista.add(i);
	    }
	 }

	if(ok){
		return lista;
	}else{ 
		return null;
	}
	
    }
    /**
     * Retorna la lista de sucesores del nodo nodeId
     *
     * @param nodeId el id del nodo del que se quieren los sucesores
     *
     * @return lista de sucesores de nodeId. Si algun elemento no se
     * pudo agregar o nodeId no es un nodo del grafo devuelve  null. 
     */ 
    public List<Integer> getSucesors(int nodeId) {
	if(nodeId<0 || nodeId>=numNodes)
	    return null;

	boolean ok= true;
 	Lista<Integer> lista= new Lista<Integer>(); 

	// Recorre la fila del nodeId y si hay arcos agrega 
	// el sucesor a la lista
	for(int i=0; i<numNodes; i++){
		if(matrix[nodeId][i]){
			ok= ok &&lista.add(i);
		}
	}
	if(ok){
	    return lista;
	}else{ 
	    return null;
	}
    }

    /**
     * Retorna el Arco cuyo nodo fuente es nodoSrc y nodo destino es nodoDst.
     *
     * @param nodoSrc nodo fuente
     * @param nodoDst nodo destino
     *
     * @return lista de sucesores de nodeId. Devuelve null si nodoSrc o nodoDst
     * no pertenecen al grafo o si el arco no pertenece al grafo.
     */
    public Arc getArc(int nodoSrc, int nodoDst) {
	return new Arc(nodoSrc,nodoDst);
    }

    /**
     * Carga en este DiGraph, el grafo contenido en el archivo
     * Borra el grafo anterior y le asigna la informacion del grafo
     * contenido en el archivo fileName.
     * El número de nodos debe ser mayor a 0, y el número de arcos
     * reportados por el archivo deben coincidir con el número de arcos
     * en el archivo.
     * @param fileName nombre del archivo que contiene la representacion del
     * grafo a cargar
     * 
     * @throws java.io.IOException, FileNotFoundException, NumberFormatException
     */
    public void read(String fileName) throws FileNotFoundException, 
					IOException, NumberFormatException {
        BufferedReader in= new BufferedReader(new FileReader(fileName));
	String linea= in.readLine();
	String[] tokens= linea.split(" ");

	this.numNodes= Integer.parseInt(tokens[0]);
	this.numArcs= Integer.parseInt(tokens[1]);
	this.matrix = new boolean[numNodes][numNodes];
	
	for (int i=0;i<this.numArcs && (linea = in.readLine())!=null;i++) {  
	    tokens= linea.split(" ");
	    int nodoOrigen= Integer.parseInt(tokens[0]);
	    int nodoFin= Integer.parseInt(tokens[1]);
	    if(nodoOrigen<numNodes && nodoOrigen>=0 
				   && nodoFin<numNodes && nodoFin>=0){
		if(matrix[nodoOrigen][nodoFin]){ 
		    numArcs--;
		}else{ 
		    matrix[nodoOrigen][nodoFin]= true;
		}
	    }
	    
	}
	
    }

    /**
     * Escribe este DiGraph en un archivo en el formato establecido en el enunciado
     *
     * @param fileName nombre del archivo donde se escribira la representacion
     * del grafo
     *
     * @throws java.io.IOException
     */
    public void write(String fileName) throws IOException {
        PrintStream out= new PrintStream(fileName);

	out.print(numNodes + " "+ numArcs+ "\n");

	for(int i=0; i<numNodes;i++){
	    for(int k=0; k<numNodes; k++){
		if(matrix[i][k]){
		    out.print(i + " "+ k+ "\n");
		} 
	    }
	}
    }

    /**
     * Retorna el grado de un nodo en este DiGraph.
     *
     * @param nodeId identificacion del nodo
     * @return el grado del nodo nodeId en este Grafo, -1 si el nodo no se
     * encuentra en el grafo
     */
    public int getDegree(int nodeId) {
	if(nodeId<0 || nodeId>=numNodes)
	    return -1;	

	int grado=0;

	for(int i=0; i<numNodes; i++){
	    if(matrix[nodeId][i]){
		grado= grado + 1;	
	    }
	    if(matrix[i][nodeId]){
		grado= grado + 1;
	    } 	
	}
	return grado;
    }

    /**
     * Retorna el grado externo de un nodo en este DiGraph.
     *
     * @param nodeId identificacion del nodo
     * @return el grado externo del nodo nodeId en este Grafo, -1 si el nodo no
     * se encuentra en el grafo
     */
    public int getOutDegree(int nodeId) {
	if(nodeId<0 || nodeId>=numNodes)
	    return -1;

	int gradoEx=0;

	for(int i=0; i<numNodes; i++){
		if(matrix[nodeId][i]){
			gradoEx= gradoEx + 1;
		} 	
	}
	return gradoEx;
    }

    /**
     * Retorna el grado interno de un nodo en este DiGraph.
     *
     * @param nodeId identificacion del nodo
     * @return el grado interno del nodo nodeId en este Grafo, -1 si el nodo no se
     * encuentra en el grafo
     */
    public int getInDegree(int nodeId) {
        if(nodeId<0 || nodeId>numNodes)
	    return -1;

	int gradoIn=0;

	for(int i=0; i<numNodes; i++){
	    if(matrix[i][nodeId]){
		gradoIn= gradoIn + 1;	
	    }	
	}
	return gradoIn;
    }

    /**
     * Retorna el numero de nodos en el grafo
     *
     * @return numero de nodos en el grafo
     */
    public int getNumberOfNodes() {
        return numNodes;
    }

    /**
     * Retorna el numero de arcos en el grafo
     *
     * @return numero de arcos en el grafo
     */
    public int getNumberOfArcs() {
	return numArcs;
    }

    /**
     * Retorna la lista de arcos que tienen a nodeId como fuente
     * @param nodeId identificador del nodo
     *
     * @return la lista de arcos que tienen a nodeId como fuente
     * Si el nodeId no pertenece al grafo devuelve null.
     */
    public List<Arc> getOutEdges(int nodeId) {
	 if(nodeId<0 || nodeId>=numNodes)
	    return null;

	boolean ok= true;
 	List<Arc> lista= new Lista<Arc>(); 

	// Recorre la fila del nodeId y si hay arcos los agrega 
	// a la lista
	for(int i=0; i<numNodes; i++){
	    if(matrix[nodeId][i]){
		ok= ok &&lista.add(new Arc(nodeId,i));
	    }
	}

	if(ok){
	    return lista;
	}else{ 
	    return null;
	}
        
    }

    /**
     * Retorna la lista de arcos que tienen a nodeId como destino
     * @param nodeId identificador del nodo
     *
     * @return la lista de arcos que tienen a nodeId como destino
     * Si el nodeId no pertenece al grafo devuelve null.
     */
    public List<Arc> getInEdges(int nodeId) {
        if(nodeId<0 || nodeId>=numNodes)
	    return null;

	boolean ok= true;
 	List<Arc> lista= new Lista<Arc>(); 

	// Recorre la columna del nodeId y si hay arcos los agrega 
	// a la lista
	for(int i=0; i<numNodes; i++){
	    if(matrix[i][nodeId]){
		ok= ok && lista.add(new Arc(i,nodeId));
	    }
	}
	if(ok){
	    return lista;
	}else{ 
	    return null;
	}
    }

    /**
     * Remueve un arco de este DiGraph
     *
     * @param nodeIniId nodo fuente del arco a eliminar
     * @param nodeFinId nodo destino del arco a eliminar
     *
     * @return arco eliminado, null en caso de que el arco no exista, no haya
     * sido eliminado o los nodos no pertenezcan al grafo
     */
    public Arc delArc(int nodeIniId, int nodeFinId) {
	if (nodeIniId<0 || nodeFinId <0 || nodeIniId>=this.numNodes 
		|| nodeFinId>=this.numNodes) 
	    return null;

	if(matrix[nodeIniId][nodeFinId]){
       	    matrix[nodeIniId][nodeFinId]= false;
	    this.numArcs--;
	    return (new Arc(nodeIniId,nodeFinId));
	}else{
	    return null;
	}
    }

    /**
     * remueve todos los arcos de este grafo
     *
     * @return lista de arcos eliminados
     */
    public List<Arc> removeAllArcs() {

	List<Arc> lista= new Lista<Arc>();
	boolean ok= true;

	for(int i=0; i<numNodes;i++){
	    for(int k=0; k<numNodes; k++){
		if(matrix[i][k]){
		    matrix[i][k]= false;
			ok= ok && lista.add(new Arc(i,k));
		} 
	    }
	}

	this.numArcs=0;
	return lista;   
    }

    /**
     * Invierte la direccion de un arco si existe
     * @param nodeIniId nodo fuente del arco antes de invertirlo
     * @param nodeFinId nodo destino del arco antes de invertirlo
     * @return true si el arco fue invertido, false en caso contrario
     */
    public boolean reverseArc(int nodeIniId, int nodeFinId) {
        if(nodeIniId<0 || nodeIniId>=numNodes || nodeFinId<0 || nodeFinId>=numNodes){
	    return false;
	}else{
	    if(matrix[nodeIniId][nodeFinId]){
		matrix[nodeIniId][nodeFinId]=false;
		matrix[nodeFinId][nodeIniId]=true;
		return true;	
	    }else{
		return false;
	    }
	}
    }

    /**
     * Invierte todos los arcos del DiGraph.
     *
     *
     * @return true si todos los arcos fueron invertidos, false en caso 
     * contrario. En caso de que algun nodo no puede ser invertido, el grafo
     * debe quedar sin alteraciones.
     */
    public boolean reverseArcs() {
        DiGraphMatrix aux= new DiGraphMatrix(numNodes);
	boolean ok= true;

	for(int i=0; i<numNodes; i++){
	    for(int k=0; k<numNodes; k++){
		if(i!=k && matrix[i][k]){			
			aux.matrix[i][k]= false;
			aux.matrix[k][i]= true;
			ok= true;
		}
	    }
	}

	if(ok){
	    this.matrix= aux.matrix;	
	    return true;
	}else{
	    return false;
	}
	
    }

    /**
     * Determina si el DiGraph g es igual a este DiGraph
     *
     * @param g el grafo con el que se quiere comparar
     * @return true si los dos DiGraph contienen los mismos nodos y los mismos
     * arcos
     */
    public boolean equals(DiGraph g) {
	boolean equal= true;
	if (  this.numNodes!=g.getNumberOfNodes()
	    || this.numArcs!=g.getNumberOfArcs()) {
	    return false;
	} else {
	    for(int i=0; i<numNodes && equal; i++){
		for(int k=0; k<numNodes && equal; k++){
		    equal = equal && (matrix[i][k]== g.isArc(i,k));	
		}
	    }
	    return equal;
	}
    }
    

    /**
     * Retorna un Digraph que es la clausura transitiva de este DiGraph
     * calculada usando el algoritmo Roy-Warshal
     *
     * @return un Digraph que es la clausura transitiva de este DiGraph
     * calculada usando el algoritmo Roy-Warshal
     */
    public DiGraph royWarshall() {
	int n= 0;
        DiGraphMatrix g = new DiGraphMatrix(numNodes);
	g.numArcs= this.numArcs;

	// M:= M + I
	for(int i=0; i<numNodes; i++){
	    for(int k=0; k<numNodes; k++){
		if(i==k && !(this.matrix[i][k])){
		    g.matrix[i][k]= true;
		}else{
		    g.matrix[i][k]= this.matrix[i][k];
		}	
	    }
	}

	for( int k = 0; k < numNodes; ++k ) {
	    for( int i = 0; i < numNodes; ++i ) {
		if(i!=k && g.matrix[i][k]){
		    for(int j=0; j<numNodes; j++){
			g.matrix[i][j]=  g.matrix[i][j] || g.matrix[k][j];;
		    }
		}
	    }
	}

	//Este ciclo mientras no vemos una mejor manera de actualizar
	//el número de arcos del grafo salida
	int counter = 0;
	for (int i=0; i<this.numNodes; i++) {
	    for (int k=0; k<this.numNodes; k++) {
		if (g.matrix[i][k]) {
		    counter++;
		}
	    }
	}
	g.numArcs = counter;
	return g;
    }

    /**
     * Retorna la representacion en String de este DiGraph.
     * @return la representacion en String de este DiGraph.
     */
    @Override
    public String toString() {
	String salida = this.numNodes +" "+this.numArcs;
	for (int i=0; i<this.numNodes; i++) {
	    for (int j=0; j<this.numNodes; j++) {
		if ( this.matrix[i][j] ) {
		    salida += "\n"+i+" "+j;
		}
	    }
	}
	return salida;
    }

    /**
     * Indica si un arco existe en este DiGraph
     * 
     * @param src el id del nodo origen del arco
     * @param dst el id del nodo destino del arco
     * @return true si exite un arco desde el nodo src hasta el nodo dst.
     * false en caso contrario
     */
    @Override
    public boolean isArc(int src, int dst) {
	if (src<0 || dst <0 || src>=this.numNodes || dst>=this.numNodes) 
	    return false;

        return matrix[src][dst];
    }

}
