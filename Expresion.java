import java.lang.Integer;
/**
 * Esta clase representa a una expresión en 2CNF. Si la expresión está en <2CNF esta clase
 * provee un método para simplificar dicha expresión a 2CNF. Además la clase provee un método
 * para determinar satisfacibilidad de la expresión representada.
 */
public class Expresion {

    /*
     * La expresión se representa a través de un grafo no orientado donde
     * el lado {x,y} está si y solo sí la cláusula {x,y} está en la expresión.
     * Las cláusulas unitarias se guardan aparte en una lista de enteros.
     */
    DiGraph expresion;
    int numSimbolos;
    List<Integer> unitarios;
    boolean simple=true;
    boolean satisfVerificada = false;
    boolean satisfacible=false;
    boolean[] esUnitario; //tamaño numSimbolos

    /**
     * Crea una nueva expresión a partir de una lista de cláusulas.
     * @param clausulas Lista de cláusulas de la expresión
     * @param numSimbolos Cantidad de símbolos distintos en la lista de cláusulas.
     *                    Debe ser igual o mayor a la cantidad real de símbolos,
     *                    Si se provee un número menor, el atributo expresion sera 
     *                    un grafo nulo.
     */
    public Expresion (List<Clausula> clausulas, int numSimbolos) {
	this.numSimbolos = numSimbolos;
	this.expresion =new DiGraphList(numSimbolos);
	this.unitarios= new Lista<Integer>();
	this.esUnitario= new boolean[numSimbolos]; 

	for(int i=0; i< clausulas.size() ; i++){
	    int variable1= clausulas.get(i).getSimbolo1();
	    int variable2= clausulas.get(i).getSimbolo2();
	    if( variable1 >= numSimbolos || variable2 >= numSimbolos )
	    	// Pienso q seria bueno lanzar una excepcion aki
		this.expresion= null;

	    if(variable2!=-1){
		// Relacionar las variables en el grafo
		Arc arco= this.expresion.addArc(variable1, variable2);
		arco= this.expresion.addArc(variable2, variable1);
	    }else{
		// La clausula es unitaria 
		boolean ok= this.unitarios.add(new Integer(variable1));
		this.esUnitario[variable1]= true;
		this.simple= false;
	    }
	}
	/* Construir un grafo con numero de nodos = numSimbolos
	   Hay que iterar sobre la lista de cláusulas y para cada una:
	     -Chequear la validez de los simbolos
	     (Si uno delos simbolos de la cláusula es >=numSimbolos, devolver null)
	     -Agregar los arcos en ambas direcciones al grafo
	     -Si es una cláusula unitaria, agregarla a la lista de unitarios, y poner en false
	     el booleano 'simple'
	   Listo	  
	 */
	
    }

    /**
     * Simplifica la expresión eliminando todas las cláusulas unitarias existentes.
     */
    public void simplificar() {
	if (this.simple) {
	    return;
	}
	
	for(int i=0; i< this.unitarios.size(); i++){
	    int nodo= (this.unitarios.get(i)).intValue();
	    List<Integer> adyacentes= this.expresion.getPredecesors(nodo);
	    // Eliminar todos los arcos del nodo unitario
	    for(int j=0; j< adyacentes.size() ;j++){
		int nodoAdy = adyacentes.get(j).intValue();
		Arc arco= this.expresion.delArc(nodo,nodoAdy);
		arco= this.expresion.delArc(nodoAdy,nodo);
            }

	    // Tomar el negado del simbolo
	    int nodo_negado= this.negado(nodo);
	    
	    // Agregar adyacentes de nodo_negado a la lista de unitarios
	    // y eliminar todos sus arcos.
	    adyacentes= this.expresion.getPredecesors(nodo_negado);

            for(int j=0; j< adyacentes.size() ;j++){
		int nodoAdy = adyacentes.get(j).intValue();
		boolean ok= this.unitarios.add(nodoAdy);
		this.esUnitario[nodoAdy]= true;

		// Verificar si existe contradiccion
		if(this.esPar(nodoAdy) && this.esUnitario[nodoAdy+1]){
		    this.satisfVerificada = false;
		    return;	  
		}
		if(!(this.esPar(nodoAdy)) && this.esUnitario[nodoAdy-1]){
		    satisfVerificada = false;
		    return;	
		}
  		// Eliminar arcos
		Arc arco= this.expresion.delArc(nodo_negado,nodoAdy);
		arco= this.expresion.delArc(nodoAdy,nodo_negado);
	    }
	}
	/* Qué hacer aqui...

	   Para cada elemento x de la lista 'unitarios' hacer:
	      Eliminar todos los arcos adyacentes a x
	      Si x es Par (es no negado)
		 Para cada sucesor y de x+1 -> ver si hay contradiccion(y),agregar a unitarios
		 Eliminar todos los arcos incidentes en x+1
	      Si x es Impar (negado)
		 Para cada sucesor de x-1 -> ver si hay contradiccion(y),agregar a unitarios
		 Eliminar todos los arcos incidentes en x-1

	  Donde si hay contradiccion(y) es
	  Si y es Par (no negado), hay contradicción si esUnitario[y+1]=true
	  Si y es Impar (negado), hay contradicci+on si esUnitario[y-1]=true
	 */  

	this.simple = true;
    }

    private boolean esPar(int x) {
	return ((x%2) == 0);
    }

    private DiGraph generarGrafoImplicacion() {
	/* Problema:
	   Este método requiere que de alguna manera extraigamos todos los arcos de 
	   un grafo sin destruirlo. DiGraph provee el método removeAllArcs, pero
	   esto destruye el grafo. Un workaround sería clonar el grafo y a ese clon
	   hacerle removeAllArcs, pero esto implica en un instante tener dos veces
	   el grafo en memoria, lo cual no es necesariamente posible.
	   La solución ideal es hacer un método eficiente que devuelva los arcos
	   de un grafo. A este método lo llamaremos getAllArcs()
	 */

	/* Qué hacer..
	  
	   Crear un grafo con numero de nodos = numSimbolos
	   Lista de arcos = getAllArcs del grafo expresion
	   Para cada arco x de la lista de arcos
	       Obtener nodos (x,y) que representan los símbolos
	       Agregar al grafo los arcos (neg(x),y)
	   Devolver el grafo resultante.

	   Donde neg(x) es
	     Si  x es Par -> return x+1
	     Si  x es Impar -> return  x-1

	*/
    }

    private int negado(int x) {
	if (this.esPar(x)) {
	    return x+1;
	} else {
	    return x-1;
	}
    }

    private boolean chequearComponentes(List<List<Integer>> componentes) {
	
	if(componentes.size()==1){
	    return false;
	}
	if(componentes.size()== this.numSimbolos){
	    return true;
	}
	for(int i=0; i< componentes.size() ; i++){
	    List<Integer> componente= componentes.get(i);
	    int[] compConexa= componentes.toArray();  //Agregar esa funcion a la clase list
	    compConexa= ordenar(compConexa);	      // Hacer Quicksort
	    for(int j=0; j< compConexa.length() ; j++){
		int nodo= compConexa[j].intValue();		
		if(this.esPar(nodo) && (compConexa[j+1]== nodo+1) ){
		    return false;		
		}
	    }
	return true;
	}	
	/* Qué hacer...
	   Este método recibe una lista de listas, cada lista es una componente
	   f. conexa del grafo de implicación.

	   Si solo hay 1 componente -> es insatisfacible, listo
	    Y si la lista es del tamaño del grafo es satisfacible, listo

	   Para cada componente
	       Convertir la lista a arreglo
	       Ordenar el arreglo
	       Para cada entero x del arreglo
	           Si x es par, si el próximo elemento es x+1, entonces no es satisfacible
		   (un elemento y su negado estarían en la misma comp.)
		   Si x es impar, seguir adelante (porque si estuviera el negado de x, sería el número
		   anterior, el cual ya habrías chequeado)

	   Nota: implementar quicksort para ordenar arreglos de Integer
	 */
    }

    /**
     * Permite determinar si la expresión representada es satisfacible.
     * Si la expresión no ha sido simplificada, se simplifcará.
     * @return true si es satisfacible, false si no lo es.
     */
    public boolean determinarSatisf() {
	if (this.satisfVerificada) {
	    return this.satisfacible;
	}
	
	//Si la expresión no estuviera simplificada
	//Nuestro algoritmo fallaría
	if (!this.simple) {
	    this.simplificar();
	}

        DiGraph grafoImp = this.generarGrafoImplicacion();
	List<List<Integer>> componentes = grafoImp.Tarjan();
	this.satisfacible = this.chequearComponentes(componentes);	
	this.satisfVerificada = true;

	return this.satisfacible;
    }

}
    
