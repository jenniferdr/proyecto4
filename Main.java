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
		int num_calles= Integer.parseInt(items[0]);
	    	int num_avenidas = Integer.parseInt(items[1]);
		int num_pares= Integer.parseInt(items[2]);

		int[] calles= new int[num_calles];
		int[] avenidas= new int[num_avenidas];
		int prox_simbol=0;

		Lista<Clausula> clausulas= new Lista<Clausula>();
		
		
	    	for(int j=0; j<num_pares; j++){
		    String linea= in.readLine();
		    String[] coordenada= linea.split(" ");
		    int x1= Integer.parseInt(coordenada[0]);
		    int y1= Integer.parseInt(coordenada[1]);
		    int x2= Integer.parseInt(coordenada[2]);
		    int y2= Integer.parseInt(coordenada[3]);
		    		    
		    // Asignar simbolos
		    for(int z=0; z<4; z++){
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
		    }
		  
		    // Ver orientacion de las calles y avenidas
		    if(x1 < x2){ 
			int a= calles[y1];

			if(y1 != y2){
			    int b= calles[y2];
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
		    }
		    if(x1 > x2){ 
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
		    }
		    if (x1==x2){   // Estan en la misma avenida
			if(y1 < y2){
				int c= avenidas[x1];
				boolean ok= clausulas.add(new Clausula(c));
			}
			if (y1 > y2){
				c= avenidas[x1] + 1;
				boolean ok= clausulas.add(new Clausula(c));
			}
			if(y1==y2){
			// Mmm bueno se puede obviar esto, no se hace clausula y ya 
			}
		    }
		}
		// Aqui termina la iteracion para los pares de puntos
		// Si la lista de clausulas no es vacia hacer... sino satis= false 
		// new Expresion(clausulas,prox_simbol)
		// Simplificar
		// boolean satis= Verificar satisf
		PrintStream out = new PrintStream(args[1]);
		if(satis){
		    out.println("Si.");
		}else{
		    out.println("No.");
		}
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
	}


    }

}
