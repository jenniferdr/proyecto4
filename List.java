   /**
   * List es una interfaz que determina los metodos basicos que debe tener una
   * lista independiente de su implementacion interna
   * 
   * @author Les profs
   * @version 1.0
   * @since 1.6
   */

public interface List <E> {
 
    /**
     * Agrega <i>element</i> a la lista.
     *
     * @param Elemento de tipo E, con el que se declaro el objeto
     * lista particular,
     * @return true si el elemento fue insertado, false en caso contrario
     */

    public boolean add(E element);
    /**
     * Agrega <i>element</i> a la lista en la posicion <i>i</i>, si <i> &gt;
     * que size() el elemento se agrega al final de lista.
     *
     * @param Elemento de tipo E, con el que se declaro el objeto
     * lista particular,
     * @return true si el elemento fue insertado, false en caso contrario
     */

    public boolean add(int index, E element);

    /**
     * Elimina todos los elementos de la lista. La lista debe quedar como recien creada.
     *
     */

    public void clear();

    /**
     * Retorna una nueva {@code List} con los mismos elementos que esta
     * {@code List}.
     *
     * @return una lista con los mismos elementosque esta lista
     * @see java.lang.Cloneable
     */

    public List clone();

    /**
     * Determina si el objeto <i>o</i> esta contenidoe n la lista. {@code Object equals}
     *
     * @see Object#equals
     */
    public boolean contains(Object o);

    /**
     * Determina si el objeto <i>o</i> es igual a la lista actual. 
     *
     * @param la lista con la que se desea comparar
     * 
     * @return true si las dos listas tienen el mismo tamaño y contienen los mismos
     * objetos en el mismo orden. false en caso contrario
     *
     */

    public boolean equals(List<E> o);

    /**
     * Devuelve el elemento almacenado en la posicion index de la lista.
     *
     * @param index posicion del elemento a devolver.
     * 
     * @return null si index &gt; size()
     */
    public E get(int index);

    /**
     * Determina la posicion del elemento <i>o</i> en la lista
     * 
     * @param o el objeto
     * @return si el elemento esta en la lista retorna suy posicion, sino -1
     */
    public int indexOf(Object o);

    /**
     * Determina si la lista no tiene elementos.
     *
     * @return true si size() &eq; 0. falso en caso contrario
     */
    public boolean isEmpty();

    /**
     * Elimina el elemento en la posicion index.
     *
     * @param index la posicion del elemento a eliminar, 0 &le; index &lt; size()
     * @return el elemento eliminado, si no se elimino ningun elemento retorna
     * null
     */
    public E remove(int index);
    
    /**
     * Elimina el elemento <i>o</i>.
     *
     * @param o el elemento a eliminar.
     * @return true si el elemento existia y fue eliminado, false en caso contrario.
     * null
     */
    public boolean remove(Object o);

    /**
     * Retorna el numero de elementos enla lista
     *
     * @return el numero de elementos en la lista
     */
    public int size();

    /**
     * Retorna un nuevo arreglo que contiene todos los elementos
     * en esta lista {@code List}.
     *
     * @return an array of the elements from this {@code LinkedList}.
     */

    public Object[] toArray();

    /**
     * Retorna la retpresentacion en String de esta {@code List}
     *
     * @return la retpresentacion en String de esta {@code List}
     */
    @Override
    public String toString();

}
