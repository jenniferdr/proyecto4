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
    boolean simple;
    boolean satisfVerificada = false;
    boolean satisfacible;
    boolean[] esUnitario; //tamaño numSimbolos

    /**
     * Crea una nueva expresión a partir de una lista de cláusulas.
     * @param clausulas Lista de cláusulas d ela expresión
     * @param numSimbolos Cantidad de símbolos distintos en la lista de cláusulas.
     *                    Debe ser igual o mayor a la cantidad real de símbolos,
     *                    Si se provee un número menor, se devuelve null.
     */
    public Expresion (List<Clausula> clausulas, int numSimbolos) {
	this.numSimbolos = numSimbolos;

	/* Qué hacer aquí...
	  
	   Construir un grafo con numero de nodos = numSimbolos
	   Hay que iterar sobre la lista de cláusulas y para cada una:
	     -Chequear la validez de los simbolos
	     (Si uno delos simbolos de la cláusula es <numSimbolos, devolver null)
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

	/* Qué hacer aqui...

	   Para cada elemento x de la lista 'unitarios' hacer:
	      Eliminar todos los arcos incidentes en x
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
	return x mod 2 == 0;
    }

    private DiGraph generarGrafoImplicacion() {

    }

    private boolean chequearComponentes(List<List<Integer>> componentes) {

    }

    /**
     * Permite determinar si la expresión representada es satisfacible.
     * Si la expresión no ha sido simplificada, se simplifcará.
     * @return true si es satisfacible, false si no lo es.
     */
    public boolean determinarSatisf() {
	if (this.satisVerificada) {
	    return this.satisfacible;
	}
	
	//Si la expresión no estuviera simplificada
	//Nuestro algoritmo fallaría
	if (!this.simple) {
	    this.simplificar();
	}

	
	
	this.satisVerificada = true;
	return this.satisfacibilidad;
    }

}
    