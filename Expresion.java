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

     * Los símbolos de la expresión son representados a través de números naturales
     * comenzando por el 0. Los números pares representan a una variable no negada,
     * y el número impar que le sigue es el negado de esa variable. Por ejemplo:
     * Si p = 2, entonces ¬p = 3 
     */
    DiGraph expresion;
    int numSimbolos; //Un simbolo p y ¬p cuentan como 2 símbolos distintos
    List<Integer> unitarios;
    boolean simple = true; //False si la expresión puede ser simplificada
    boolean satisfVerificada = false; //True si se conoce la satisfacibilidad de la expresión
    boolean satisfacible;
    boolean[] esUnitario; //Se utiliza para evaluar si entre los unitarios están un símbolo y su negado

    /**
     * Crea una nueva expresión a partir de una lista de cláusulas.
     * @param clausulas Lista de cláusulas de la expresión
     * @param numSimbolos Un número par no negativo que es igual a la cantidad de símbolos
     *                    distintos entre todas las cláusulas multiplicado por 2.
     */
    public Expresion (List<Clausula> clausulas, int numSimbolos) throws ExcepcionSimboloInvalido {
	this.numSimbolos = numSimbolos;
	this.expresion = new DiGraphList(numSimbolos);
	this.unitarios = new Lista<Integer>();
	this.esUnitario= new boolean[numSimbolos]; 

	for (int i=0; i< clausulas.size() ; i++){
	    Clausula estaClausula = clausulas.get(i);
	    int variable1 = estaClausula.getSimbolo1();
	    int variable2 = estaClausula.getSimbolo2();
	    if( variable1 >= numSimbolos ) {
	    	//continue;
		throw new ExcepcionSimboloInvalido(variable1, estaClausula);
	    } else if (variable2 >= numSimbolos ) {
		throw new ExcepcionSimboloInvalido(variable1, estaClausula);
	    }
	    
	    //Utilizar el método esUnitaria de clausula lo hace más legible... - JoseA
	    if (!estaClausula.esUnitaria()) {
		// Relacionar las variables en el grafo
		Arc arco= this.expresion.addArc(variable1, variable2);
		arco= this.expresion.addArc(variable2, variable1);
	    } else {
		//Agregar esta variable a lista de unitarias
		boolean ok= this.unitarios.add(new Integer(variable1));
		this.esUnitario[variable1]= true;
		this.simple= false;
	    }
	}
    }

    private boolean esPar(int x) {
	return ((x%2) == 0);
    }

    /**
     * Simplifica la expresión eliminando todas las cláusulas unitarias existentes.
     */
    public void simplificar() {
	if (this.simple) {
	    return;
	}
	
	for(int i=0; i< this.unitarios.size(); i++){
	    int nodo = (this.unitarios.get(i)).intValue();

	    //Verificar si hay contradiccion
	    if (this.esUnitario[negado(nodo)]) {
		this.satisfacible = false;
		this.satisfVerificada = true;
		return;
	    }

	    List<Integer> adyacentes= this.expresion.getPredecesors(nodo);
	    // Eliminar todos los arcos del nodo unitario
	    for(int j=0; j< adyacentes.size() ;j++){
		int nodoAdy = adyacentes.get(j).intValue();
		Arc arco = this.expresion.delArc(nodo,nodoAdy);
		arco = this.expresion.delArc(nodoAdy,nodo);
            }

	    // Tomar el negado del simbolo
	    int nodo_negado= this.negado(nodo);
	    
	    // Agregar adyacentes de nodo_negado a la lista de unitarios
	    // y eliminar todos sus arcos.
	    adyacentes = this.expresion.getPredecesors(nodo_negado);

            for(int j=0; j< adyacentes.size() ;j++){
		int nodoAdy = adyacentes.get(j).intValue();
		boolean ok= this.unitarios.add(nodoAdy);
		this.esUnitario[nodoAdy]= true;

		// Eliminar arcos
		Arc arco= this.expresion.delArc(nodo_negado,nodoAdy);
		arco= this.expresion.delArc(nodoAdy,nodo_negado);
	    }
	}
	this.simple = true;
    }

    /*
     * Dado un símbolo x, este método determina cuál es su negado
     */
    private int negado(int x) {
	if (this.esPar(x)) {
	    return x+1;
	} else {
	    return x-1;
	}
    }

    private DiGraphList generarGrafoImplicacion() {
	
	DiGraphList grafoImp = new DiGraphList(numSimbolos);
	List<Arc> arcos = grafoImp.getAllArcs();
	for (int i=0; i<arcos.size(); i++) {
	    int simbolo1 = arcos.get(i).getSource();
	    int simbolo2 = arcos.get(i).getDestination();
	    Arc arco = grafoImp.addArc(negado(simbolo1),simbolo2);
	}
	return grafoImp;
    }


    public static void ordenar (Integer[] arreglo) {
	quicksort(arreglo, 0, arreglo.length);
    }

    public static void quicksort (Integer[] arreglo, int ini, int fin) {
	if ( fin-ini <= 1 ) {
	    return;
	}
	int limite = ini-1;
	Integer pivote = arreglo[fin-1];
        for (int j=ini; j<fin; j++) {
	    if (arreglo[j].compareTo(pivote) <= 0) {
		limite++;
		Integer temp = arreglo[j];
		arreglo[j] = arreglo[limite];
		arreglo[limite] = temp;
	    }
	}
        quicksort(arreglo, ini, limite);
	quicksort(arreglo, limite+1, fin);
	return;
    }


    private boolean chequearComponentes(List<List<Integer>> componentes) {
	
	if(componentes.size()==1){
	    return false;
	}
	if(componentes.size()== this.numSimbolos){
	    return true;
	}
	for (int i=0; i< componentes.size() ; i++){
	    //List<Integer> componente= componentes.get(i);
	    Integer[] compConexa= (Integer[]) componentes.get(i).toArray();
	    ordenar(compConexa);
	    for(int j=0; j< compConexa.length ; j++) {
		int nodo = compConexa[j].intValue();
		//Estamos seguros de que compConexa[j+1] existe porque su tamaño siempre es par
		if (this.esPar(nodo) && (compConexa[j+1] == nodo+1) ) {
		    return false;		
		}
	    }
	}
	return true;
    }

    /**
     * Permite determinar si la expresión representada es satisfacible.
     * Si la expresión no ha sido simplificada, se simplifcará.
     * @return true si es satisfacible, false si no lo es.
     */
    public boolean determinarSatisf() {
	this.simplificar();

	if (this.satisfVerificada) {
	    return this.satisfacible;
	}
	
        DiGraphList grafoImp = this.generarGrafoImplicacion();
	List<List<Integer>> componentes = grafoImp.tarjan();
	this.satisfacible = this.chequearComponentes(componentes);	
	this.satisfVerificada = true;

	return this.satisfacible;
    }


    public String toString() {
	String salida = "Grafo expresion: ";
	salida += expresion.toString();
	salida += "\n esUnitario:";
	for (int i=0; i<esUnitario.length; i++) {
	    salida += "\n "+esUnitario[i];
	}
	salida += "\n satisfacible: "+satisfacible;
	salida += "\n satisfVarificada: "+satisfVerificada;
	return salida;
    }
}
    
