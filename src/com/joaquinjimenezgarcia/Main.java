package com.joaquinjimenezgarcia;

import java.util.Arrays;
import java.util.Random;

public class Main {

	// El bote con las bolas de la partida.
	private static int[] pot;

	// La bola actual dentro del array pot.
	private static int currentBall = 0;

	public static void main(String[] args) {



	}

	//////////// PARTE DE MARCO ////////////


	////////////////////////////////////////

	//////////// PARTE DE PABLO ////////////

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

	////////////////////////////////////////

	//////////// PARTE DE ALE //////////////


	////////////////////////////////////////

	//////////// PARTE DE FRAN /////////////


	////////////////////////////////////////

	//////////// PARTE DE JORGE ////////////


	////////////////////////////////////////

	//// PARTE DEL PUTO AMO DE JOAKI ////
	static void createPaper(){
		int cantidad = 15; //numeros aleatorio
		int aux = cantidad;  //auxiliar;
		int[] numeros = new int[cantidad];
		int[] resultado = new int[cantidad];
		Random rnd = new Random();
		int res;


		//se rellena una array del 1 al 90
		for(int i=0;i<cantidad;i++){
			numeros[i]=(int)((1 + Math.random() * 90));
		}

		for(int i=0;i<cantidad;i++){
			res=rnd.nextInt(aux);
			resultado[i]=numeros[res];
			numeros[res]=numeros[aux-1];
			aux--;

		}

		//Ordena el array
		Arrays.sort(resultado);

		//se imprime el resultado;
		System.out.println("CARTON");
		System.out.println("======");

		for(int i=0;i<cantidad;i++){
			System.out.print(resultado[i] + " ");

			if(i == 4 || i == 9){
				System.out.println();
			}
		}

	}

	////////////////////////////////////////
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
