   /**
   * Lista implementa la interfaz List <E>
   *  
   * @author José A Goncalves y Jennifer Dos Reis
   * @version 1.0
   * @since 1.6
   */
public class Lista <E> implements List <E>,Cloneable {
   /*
    * Atributos de la clase
    * objeto[0] : elemento contenido en esta 'caja'
    * siguiente: apuntador a la siguiente 'caja'
    * tam : numero de elementos en la lista
    *
    * Cuando se crea una lista la primera 'caja' guarda el tamaño de la
    * lista y apunta al primer elemento de la lista, si lo hay
    *
    * Se puede ver a la instancia de Lista que se cree desde fuera como
    * un 'controlador' de la Lista, luego las siguientes instancias que 
    * se creen con el método add() se comportarán como simples registros.
    *
    * Cuando la lista es vacía, o recién creada
    * tam=0, objeto y siguiente = null
    *
    */
    private E[] objeto = (E[]) new Object[1];
    private Lista<E> siguiente = null;
    private int tam = 0;

    /**
     * Agrega el elemento de tipo E a la lista
     *
     * @param element Elemento a agregar. Debe ser no nulo
     * 
     * @return True si fue agregado, false si es null
     */
    public boolean add(E element) {
	//Llamamos al otro constructor con index=tam porque esto es
	//equivalente a agregar el elemento al final de la lista
	return this.add(tam, element);
    }

    /**
     * Agrega el elemento de tipo E a la lista
     * @param index Posición en la lista donde se insertará el elemento.
     *	      Debe ser mayor a 0. Si es mayor a this.size() se agrega al final de la lista.
     * @param element Elemento a agregar. Debe ser no nulo
     * @return True si fue agregado, false si es null
     */
    public boolean add(int index, E element) {
	if (element==null || index<0) {
	    return false;
	}
	//Nos despalazamos hasta la caja anterior a la que se quiere
	//eliminar, luego se acomodan los apuntadores	
	Lista<E> ubicacion = this;
	for (int i=0; i<index && ubicacion.siguiente!=null; i++) {
	    ubicacion = ubicacion.siguiente;
	}
	Lista<E> next = ubicacion.siguiente;
	ubicacion.siguiente = new Lista<E>();
	ubicacion.siguiente.siguiente = next;
	ubicacion.siguiente.objeto[0] = element;
	this.tam++;
	return true;
    }

    /**
     * Elimina todos los elementos de la lista
     */
    public void clear() {
	this.objeto[0] = null;
	this.siguiente = null;
	this.tam = 0;
    }

    /**
     * Crea una copia idéntica de esta lista
     * 
     * @return La copia de esta lista
     */
    @Override
    public List clone() {
	//return new Lista<E>();
	Lista<E> clon = new Lista<E>();
	Lista<E> temporal = null;
	Lista<E> ubicacionThis = this.siguiente;
	Lista<E> ubicacionClon = clon;
	clon.tam = this.tam;
	for (int i=0; i<this.tam; i++) {
	    temporal = new Lista<E>();
	    temporal.objeto = ubicacionThis.objeto.clone();
	    ubicacionClon.siguiente = temporal;
	    ubicacionClon = ubicacionClon.siguiente;
	    ubicacionThis = ubicacionThis.siguiente;
	}
	return clon;
    }

    /**
     * Verifica si un elemento se encuentra en la lista.
     *
     * @param o Objeto a buscar, el cual debe implementar un método equals apropiado.
     * 
     * @return True si está, false en caso contrario
     */
    public boolean contains(Object o) {
        boolean esta = false;
	Lista<E> ubicacion = this.siguiente;
	while (ubicacion!=null && !esta) {
	    esta = ubicacion.objeto[0].equals(o);
	    ubicacion = ubicacion.siguiente;
	}
	return esta;
    }

    /**
     * Compara dos listas
     *
     * @param o La lista con la cual se va a comparar
     * 
     * @return True si las listas son idénticas, falso en caso contrario.
     */
    public boolean equals(List<E> o){
	if (this.tam!=o.size()) {
	    return false;
	} else {
	    boolean igual = true;
	    Lista<E> ubicacion = this.siguiente;
	    for (int i=0; i<this.tam && igual; i++) {
		igual = ubicacion.objeto[0].equals(o.get(i));
		ubicacion = ubicacion.siguiente;
	    }
	    return igual;
	}
    }

    /**
     * Devuelve el elemento almacenado en la posicion index de la lista.
     *
     * @param index Posicion del elemento a devolver.
     * 
     * @return null si index &gt; size()
     */
    public E get(int index){
        if (index<0 || index>=this.tam) {
	    return null;
	} else {
	    Lista<E> ubicacion = this.siguiente;
	    for (int i=0; i<index; i++) {
		ubicacion = ubicacion.siguiente;
	    }
	    return ubicacion.objeto[0];
	}
    }

    /**
     * Determina la posicion del elemento <i>o</i> en la lista
     * 
     * @param o el objeto
     * @return Si el elemento esta en la lista retorna su posicion, sino -1
     */
    public int indexOf(Object o){
	Lista<E> ubicacion = this.siguiente;
	for (int i=0; i<this.tam; i++) {
	    if (ubicacion.objeto[0].equals(o)) {
		return i;
	    }
	    ubicacion = ubicacion.siguiente;
	}
	return -1;
    }

    /**
     * Determina si la lista no tiene elementos.
     *
     * @return true si size() &eq; 0. falso en caso contrario
     */
    public boolean isEmpty(){
        return this.tam==0;
    }

    /**
     * Elimina el elemento en la posicion index.
     *
     * @param index la posicion del elemento a eliminar, 0 &le; index &lt; size()
     * @return el elemento eliminado, si no se elimino ningun elemento retorna
     * null
     */
    public E remove(int index){
	if (index<0 || index>=this.tam) {
	    return null;
	}
	Lista<E> ubicacion = this;
	for (int i=0; i<index; i++) {
	    ubicacion = ubicacion.siguiente;
	}
	E elemento = ubicacion.siguiente.objeto[0];
	ubicacion.siguiente = ubicacion.siguiente.siguiente;
	this.tam--;
	return elemento;
    }
    
    /**
     * Elimina el elemento <i>o</i>.
     *
     * @param o el elemento a eliminar.
     * @return true si el elemento existia y fue eliminado, false en caso contrario.
     * 
     */
    public boolean remove(Object o){
	Lista<E> ubicacion = this;
	for (int i=0; i<this.tam; i++) {
	    if (ubicacion.siguiente!=null && ubicacion.siguiente.objeto[0].equals(o)) {
		ubicacion.siguiente = ubicacion.siguiente.siguiente;
		this.tam--;
		return true;
	    }
	    ubicacion = ubicacion.siguiente;
	}
	return false;
    }

    /**
     * Retorna el numero de elementos enla lista
     *
     * @return el numero de elementos en la lista
     */
    public int size(){
        return this.tam;
    }

    /**
     * Retorna un nuevo arreglo que contiene todos los elementos
     * en esta lista {@code List}.
     *
     * @return an array of the elements from this {@code LinkedList}.
     */

    public Object[] toArray(){
        Object arreglo[] = new Object[this.tam];
	Lista<E> ubicacion = this.siguiente;
	for (int i = 0; i<this.tam; i++) {
	    arreglo[i] =  ubicacion.objeto[0];
	    ubicacion = ubicacion.siguiente;
	}
	return arreglo;
    }
    public int[] toArray(){
	int[] array= new E[this.size()];
	for(int i=0; i< this.size() ; i++){
	    array[i]= this.get(i).intValue();
	}
	return array
    }

    /**
     * Retorna la retpresentacion en String de esta {@code List}
     *
     * @return la retpresentacion en String de esta {@code List}
     */
    @Override
    public String toString(){
        String salida = "";
	Lista<E> ubicacion = this.siguiente;
	for (int i = 0; i<this.tam; i++) {
	    salida += ubicacion.objeto[0].toString() + " -> ";
	    ubicacion = ubicacion.siguiente;
	}
	return salida+"null";
    }
		
}
