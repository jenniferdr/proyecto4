/**
 * Arc es una clase que permite almancenar la informacion de los arcos de un
 * grafo
 *
 * @author Les profs
 * @version 1.0
 * @since 1.6
 */

public class Arc {
    private String id = "";
    private double cost;
    private int src = -1;
    private int dst = -1;

    /**
     * Crea un arco entre los nodos src y dst
     *
     * @param src nodo origen del arco
     * @param dst nodo destino del arco
     */
    public Arc(int src, int dst) {
       this.src = src;
       this.dst = dst;
    }

    /**
     * Retorna un nuevo {@code Arc} con el mismo fuente y el mismo destino que
     * este Arc.
     *
     * @return una lista con los mismos elementosque esta lista
     * @see java.lang.Cloneable
     */
    @Override
    public Object clone() {
       return new Arc(src, dst);
    }

    /**
     * Indica si el Arc a es igual a este Arc
     *
     * @param a Arc con el que se desea comparar.
     * @return true si los fuentes y destinos de los dos arcos son iguales.
     */
    @Override
    public boolean equals(Object a) {
	/*
	Reimplementamos equals para que sea un override del equals() de la
	clase Object. Esto porque las Listas genéricas llaman al equals
	heredado por Object y no al que estaba implementado en Arc, 
	arrojando resultados no deseados.
	*/
	if (a instanceof Arc) {
	    Arc otro = (Arc) a;
	    return src == otro.src && dst == otro.dst;
	} else {
	    return false;
	}
    }

    /**
     * Pertmite obtener el costo de un arco: de ir de archo fuente al arco
     * destino.
     *
     * @return costo del Arco.
     */
    public double getCost() {
       return cost;
    }

    /**
     * Pertmite establecer el costo a este Arc.
     *
     * @param el costo a ser asignado a este Arco.
     */
    public void setCost(double c) {
       cost = c;
    }

    /**
     * Pertmite obtener el origen de este arco
     * 
     *
     * @return origen del Arco.
     */
    public int getSource() {
       return src;
    }

    /**
     * Pertmite obtener el destino de este arco
     * 
     *
     * @return destino del Arco.
     */
    public int getDestination() {
       return dst;
    }
    /**
     * Retorna la representacion en String del Arc
     * 
     * @return la representacion en String de este Arc
     */
    @Override
    public String toString() {
       return "(" + src + ", "+ dst+")";
    }
}
