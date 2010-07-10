import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.Integer;

public class Main{

public static void main(String[] args){
	
	if (args.length != 2) {
	    System.err.println("Sintaxis: java Main <fileName_entrada> <fileName_salida>");
	    return;
	}

	try {
	    BufferedReader in = new BufferedReader(new FileReader(args[0]));
	    int instancias = Integer.parseInt(in.readLine());
	    for(int i=0; i< instancias ; i++){
		String[] items= in.readLine().split(" ");
		int num_calles= Integer.parseInt(items[1]);
	    	int num_avenidas = Integer.parseInt(items[0]);
		int num_pares= Integer.parseInt(items[2]);

		int[] calles= new int[num_calles];
		int[] avenidas= new int[num_avenidas];
		int prox_simbol=0;

		//inicializar los arreglos de simbolos en -1
		for (int j=0; j<num_calles; j++) {
		    calles[j] = -1;
		}
		for (int j=0; j<num_avenidas; j++) {
		    avenidas[j] = -1;
		}
		Lista<Clausula> clausulas= new Lista<Clausula>();
		
		
	    	for(int j=0; j<num_pares; j++) {
		    String linea= in.readLine();
		    String[] coordenada= linea.split(" ");
		    int x1= Integer.parseInt(coordenada[0])-1;
		    int y1= Integer.parseInt(coordenada[1])-1;
		    int x2= Integer.parseInt(coordenada[2])-1;
		    int y2= Integer.parseInt(coordenada[3])-1;

		    System.out.println("("+x1+","+y1+") ("+x2+","+y2+")");

		    //Los -1 es porque por lo visto las calles/avenidas se empiezan a numerar por el 1

		    // Asignar simbolos
		    /*for(int z=0; z<4; z++){
		    	if(avenidas[Integer.parseInt(coordenada[z])]==-1){
			    avenidas[Integer.parseInt(coordenada[z])]= prox_simbol;
			    prox_simbol= prox_simbol+2;
		    	}
			z++;
		    }
		    
		    for(int z=1; z<4; z++){
		    	if(calles[Integer.parseInt(coordenada[z])]==-1){
			    calles[Integer.parseInt(coordenada[z])]= prox_simbol;
			    prox_simbol= prox_simbol+2;
		    	}
			z++;
			}*/
		    //Esto ^ no lo logra mucho...
		    //En el 1º bloque estas asignando símbolos como si las 4 coordenadas fueran de avenidas, lo cual
		    //no necesariamente tiene sentido y podría hasta dar en ciertos casos fuera de arreglo

		    //Asignar símbolos a avenidas
		    if (avenidas[x1]==-1) {
			avenidas[x1] = prox_simbol;
			prox_simbol+=2;
		    }
		    if (avenidas[x2]==-1) {
			avenidas[x2] = prox_simbol;
			prox_simbol+=2;
		    }
		    //Asignar simbolos a calles
		    if (calles[y1]==-1) {
			calles[y1] = prox_simbol;
			prox_simbol+=2;
		    }
		    if (calles[y2]==-1) {
			calles[y2] = prox_simbol;
			prox_simbol+=2;
		    }
		    
		    
		    /* Temp:
		       Calle Oeste-Este -> simbolo no negado
		       Avenida Norte-Sur -> Simbolo no negado		       
		     */
		  
		    //Ver orientacion de las calles y avenidas
		    //y generar cláusulas

		    //Si el punto de origen está más a la izquierda que el destino
		    if (x1 < x2) {
	
			int a = calles[y1];

			//Si las calles son distintas, hay dos orientaciones posibles
			//y se generarán 4 cláusulas
			if (y1 != y2) {
			    int b = calles[y2];
			    int c = avenidas[x1];
			    int d = avenidas[x2];

	    		    if(y1 > y2){
				// Invertir la direccion de las avenidas
				// Si la calle 'origen' está más al sur que el destino
				c++; 
				d++;
			    }
			    //Crear clausulas (a v b) (b v d) (a v c) (c v d)
			    boolean ok = clausulas.add(new Clausula(a,b));
			    ok= clausulas.add(new Clausula(a,c));
			    ok= clausulas.add(new Clausula(d,b));
			    ok= clausulas.add(new Clausula(d,c));
			} else {
			    //Crear clausula unitaria
			    boolean ok = clausulas.add(new Clausula(a));
			}
		    } else if(x1 > x2){ 
			int a= calles[y1]+1;

			if(y1 != y2){
			    int b= calles[y2]+1;
			    int c= avenidas[x1];
			    int d= avenidas[x2];

	    		    if(y1 > y2){
				// Invertir la direccion de las avenidas 
				c++; 
				d++;
			    }
			    //Crear clausulas (a v b) (b v d) (a v c) (c v d)
			    boolean ok= clausulas.add(new Clausula(a,b));
			    ok= clausulas.add(new Clausula(a,c));
			    ok= clausulas.add(new Clausula(d,b));
			    ok= clausulas.add(new Clausula(d,c));
			}else{
			    //Crear clausula unitaria
			    boolean ok= clausulas.add(new Clausula(a));
			}
		    } else {   // Estan en la misma avenida
			if(y1 < y2){
				int c = avenidas[x1];
				boolean ok= clausulas.add(new Clausula(c));
			} else if (y1 > y2){
				int c = avenidas[x1] + 1;
				boolean ok= clausulas.add(new Clausula(c));
			}
		    }
		}
		// Aqui termina la iteracion para los pares de puntos

		Expresion problema = new Expresion(clausulas, prox_simbol);
		boolean satis = problema.determinarSatisf();

		PrintStream out = new PrintStream(args[1]);
		if(satis){
		    out.println("Si.");
		}else{
		    out.println("No.");
		}
		System.out.println(satis);
	    }
	} catch(NumberFormatException e) {
	    System.err.println("Error de formato en el archivo especificado");
	    return;
	} catch (FileNotFoundException fnfe) {
	    System.err.println("Error al cargar archivo, verifique el nombre");
	    return;
	} catch(IOException ioe) {
	    System.err.println("Error: " + ioe);
	    return;
	} catch (ExcepcionSimboloInvalido exp) {
	    System.err.println(exp.getErMessage());
	    return;
	}


    }

}
