package com.joaquinjimenezgarcia;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings({"ForLoopReplaceableByForEach", "ManualArrayCopy"})
public class Main {

	// El bote con las bolas de la partida.
	private static int[] pot;

	// Array que contiene todos los cartones generados.
	private static int[][][] papers;

	// La bola actual dentro del array pot.
	private static int currentBall = 0;

	// Está en false mientras no haya salido línea en ningún cartón.
	private static boolean hasLineComeOut = false;

	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		int numPapers = 0;

		do {
			try {
				System.out.print("Introduzca el número de cartones a generar: ");
				numPapers = input.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("Por favor, introduzca un número válido.");
				input.next(); // Desatascar el scanner
			}
		} while(numPapers <= 0);

		papers = createPapers(numPapers);

		for (int i = 0; i < papers.length; i++) {
			int[][] paper = papers[i];

			System.out.println("\nCARTÓN " + (i+1));
			System.out.println("========");
			System.out.println(paperToString(paper));
		}

		System.out.println("Pulse ENTER para empezar.\n" +
							   "Tras ello, pulse ENTER para sacar una bola.");
		input.nextLine();
		input.nextLine();

		pot = createPot();
		nextTurn();
	}

	/**
	 *	Función que coge las bolas del bote generado y las va sacando de 1 en 1 y mostrando por pantalla.
	 *
	 */
	private static void nextTurn(){

		System.out.println("Bola #" + (currentBall+1) + ": " + pot[currentBall]);
		currentBall++;

		input.nextLine();

		// Antes de la quinta bola sacada no se puede producir una línea,
		// por lo tanto comprobamos la función checkpapers a partir de
		// la quinta bola.
		if (currentBall >= 4){
			checkPapers(papers);
		}else {
			nextTurn();
		}
	}

	/**
	 * Desordena el array dado de forma aleatoria
	 *
	 * @param array Un array de enteros
	 * @return El array con los elementos desordenados
	 */
	private static int[] shuffleArray(int[] array) {
		/*
		 *	Implementación de la versión de Richard Durstenfeld de la mezcla de Fisher–Yates
		 *	https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
		 */
		for (int i = 0; i < array.length - 1; i++) {
			int j = randomInteger(i, array.length - 2);

			// Intercambiar array[i] con array[j]
			// Para ello, guardamos array[j] en una variable temporal
			int tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
		}

		return array;
	}

	/**
	 * @param min El número mínimo, incluido.
	 * @param max El número máximo, incluido.
	 * @return Un número aleatorio entre min y max
	 */
	private static int randomInteger(int min, int max) {
		return (int) (Math.random() * (((max - min) + 1) + min));
	}

	private static String paperToString(int[][] paper) {
		String result = "";

		for (int i = 0; i < paper.length; i++) {
			int[] row = paper[i];

			for (int j = 0; j < row.length; j++) {
				int number = row[j];
				result += number + (j != row.length - 1 ? " " : "");
			}

			result += "\n";
		}

		return result;
	}

	/**
	 * Funcion Que crea un bote con numeros del 1 al 90 desordenados
	 * @return Devuelve el bote desordenado
	 */
	private static int [] createPot(){
		int array [] = new int [90];

		for (int i = 0; i < array.length; i++){
			array [i] = i + 1;

		}
		//Llamada a la función de desordenación del array
		return shuffleArray(array);
	}

	/**
	 * Funcion que comprueba si un carton tiene linea o bingo,
	 * copiando el carton en un array paperCopy y poniendo los
	 * numeros que ya salieron en el pot a 0 en dicho array.
	 * Si la suma de todos los numeros de una fila es 0, hay linea en ese carton.
	 * Si la suma de todos los numeros del carton es 0, hay bingo en ese carton.
	 * @param paper int Array de dos dimensiones con los valores a comprobar.
	 * @return String Devuelve bingo, line o nothing dependiendo de las comprobaciones.
	 */
	private static String checkPaper(int paper[][]){
		int potNumber, bingo = 0;
		int paperCopy[][] = new int[3][5];
		boolean checkLine=false;

		//Copia el array paper en paperCopy
		for (int i = 0; i < paper.length; i++) {
			for (int j = 0; j < paper[i].length; j++) {
				paperCopy[i][j] = paper[i][j];
			}
		}

		//Bucle que itera por cada numero del pot que haya salido.
		for (int i = 0; i < currentBall; i++) {
			potNumber = pot[i];

			//Pone a 0 los numeros de paperCopy que ya hayan salido en el pot.
			for (int j = 0; j < paperCopy.length; j++) {
				for (int k = 0; k < paperCopy[j].length; k++) {
					if (potNumber == paperCopy[j][k]){
						paperCopy[j][k] = 0;
					}
				}
			}
		}

		//Comprueba si hay linea en cada fila del carton.
		for (int i = 0, line = 0; i < paperCopy.length && !checkLine && !hasLineComeOut; i++) {
			for (int j = 0; j < paperCopy[i].length; j++) {
				line += paperCopy[i][j];

			}

			if (line == 0){
				checkLine = true;

			}
		}

		//Comprueba si hay bingo en el carton.
		for (int i = 0; i < paperCopy.length; i++) {
			for (int j = 0; j < paperCopy[i].length; j++) {
				bingo += paperCopy[i][j];
			}
		}

		if (bingo == 0){
			return "bingo";
		}else if (checkLine){
			return "line";
		}else{
			return "nothing";
		}
	}

	/**
	 * Funcion que comprueba si hay linea o bingo en cada uno de los cartones con cada bola que va saliendo en el pot.
	 * @param papers int Array papers en el que la primera dimension es el numero de carton y las otras dos los calores que se comprueban.
	 */
	private static void checkPapers(int papers[][][]){
		// Es remotamente posible que hayan dos o más cartones idénticos que tengan bingos
		// a la vez. Por lo tanto almacenamos en esta variable si al menos uno tiene bingo.
		boolean hasBingoComeOut = false;

		//Bucle que comprueba si hay linea o bingo en cada uno de los cartones.
		for (int i = 0; i < papers.length; i++) {
			switch (checkPaper(papers[i])){
				case "bingo":
					hasBingoComeOut = true;
					System.out.println("Hay bingo en el cartón " + (i+1) + "!!!!");
					break;
				case "line":
					if (!hasLineComeOut) {
						System.out.println("Hay linea en el cartón " + (i+1) + "!\n");
						hasLineComeOut = true;
					}
					break;
			}
		}

		if (hasBingoComeOut) {
			return; // Este return no llama a nextTurn(), por lo tanto el juego se termina.
		}

		nextTurn();
	}

	/**
	 * función que coge el número de cartones que quieres jugar.
	 * @param num numero de cartones que se van a crear
	 */
	private static int[][][] createPapers(int num){

		int arrayCreatePaper[][][] = new int [num][3][5];


		for (int i = 0; i < num ; i++) {
			arrayCreatePaper[i] = createPaper();
		}
		return arrayCreatePaper;
	}

	/**
	 * First creates a single array with no-repeated numbers and orders it.
	 * Then, places them in a two dimensions array for an easier checking of lines
	 * @return paper array
	 */
	private static int[][] createPaper(){
		int quantity = 5; // random numbers
		int lines = 3; // lines
		int [] array = new int [quantity*lines]; // single array for order
		int [][] paper = new int[lines][quantity]; // new array with lines

		// Fill the first array
		for (int i = 0; i < quantity*lines ; i++) {
			int number;
			do{
				number = (int)((1 + Math.random() * 90));
			}while(exists(number, array)); // Call the function to check numbers

			array[i] = number;
		}

		// Order it
		Arrays.sort(array);

		// Place numbers in the new array
		for (int i = 0, j = 0; i < lines ; i++) {
			for (int k = 0; k < quantity; k++, j++) { // "j" is for the single array
				paper[i][k] = array[j];
			}
		}
		return paper; // Returns the new array
	}

	/**
	 * Function that checks if the number exists in the array
	 * @param number
	 * @param array
	 * @return true or false (depens on the existence of the number in the array)
	 */
	private static boolean exists(int number, int[] array){
		for (int i = 0; i < array.length; i++) {
			// Checks if number exists at "i" in the array
			if(number == array[i]){
				return true; // If exists, returns true
			}
		}
		return false; // If doesn't exist, returns false
	}
}

/*
Pregunta por cartones
Bolas del 1 al 90
Cartones con 3 filas de 5 números (15 nums)
Generar bolas (descartando las que sales)
Los cartones presentan los números ordenados de menor a mayor
Hay que mostrar qué cartón ha ganado con los números que han salido
Y el total de bolas que han salido con sus números
 */

