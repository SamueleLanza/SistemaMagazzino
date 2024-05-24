package org.generation.italy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.generation.italy.model.Movimento;

public class Main {

	public static void main(String[] args) {
		HashMap<String, String> clienti = new HashMap<String, String>();
		HashMap<String, String> fornitori = new HashMap<String, String>();
		HashMap<String, String> prodotti = new HashMap<String, String>();
		HashMap<String, String> tipologieMovimentoE = new HashMap<String, String>();
		HashMap<String, String> tipologieMovimentoU = new HashMap<String, String>();
		HashMap<String, Integer> giacenzaProd = new HashMap<String, Integer>();
		ArrayList<Movimento> listaMovimenti = new ArrayList<Movimento>();
		HashMap<String, List<String>> movimentiCliente = new HashMap<String, List<String>>();
		HashMap<String, List<String>> movimentiFornitore = new HashMap<String, List<String>>();
		Scanner sc = new Scanner(System.in);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Movimento m = new Movimento();
		String risposta, rispostaRestart, risp;
		boolean sceltaSbagliata = false;
		boolean uscita = false, entrata = false, giacenza = false, ricerca = false;
		int k = 1, l = 1;
		// inserimento clienti
		clienti.put("01", "Bar Dante");
		clienti.put("02", "Bacio Bar");
		clienti.put("03", "Bar Anni 20");
		clienti.put("04", "Bar Stazione");
		// inserimento fornitori
		fornitori.put("01", "San Carlo");
		fornitori.put("02", "Algida");
		fornitori.put("03", "Panificio");
		fornitori.put("04", "Ingrosso Alimenti");
		// inserimento prodotti
		prodotti.put("01", "Patatine");
		prodotti.put("02", "Acqua");
		prodotti.put("03", "Pane");
		prodotti.put("04", "Prosciutto Cotto");
		// inserimento giacenza prodotti
		giacenzaProd.put("01", 50);
		giacenzaProd.put("02", 30);
		giacenzaProd.put("03", 100);
		giacenzaProd.put("04", 20);
		// inserimento movimenti
		tipologieMovimentoE.put("E01", "Acquisto da fornitore");
		tipologieMovimentoE.put("E02", "Reso da cliente");
		tipologieMovimentoE.put("E03", "Produzione interna");
		tipologieMovimentoE.put("E04", "Spostamento da altro magazzino");
		tipologieMovimentoU.put("U01", "Vendita a cliente");
		tipologieMovimentoU.put("U02", "Reso a fornitore");
		tipologieMovimentoU.put("U03", "Sostituzione in garanzia");
		tipologieMovimentoU.put("U04", "Spostamento a altro magazzino");
		
		//Ciclo restart
		do {
			// Ciclo  controllo ripsosta 
			do {
				sceltaSbagliata = false;
				System.out.println(
						" Movimento in entrata (1)  Movimento in uscita (2)  Giacenzaza prodotto (3)  Ricerca movimento (4)?");
				risposta = sc.nextLine();
				if (risposta.equals("1")) {
					System.out.println("Hai selezionato movimento in entrata");
					entrata = true;
				} else if (risposta.equals("2")) {
					System.out.println("Hai selezionato movimento in uscita");
					uscita = true;
				} else if (risposta.equals("3")) {
					System.out.println("Hai selezionato giacenza prodotto");
					giacenza = true;
				} else if (risposta.equals("4")) {
					System.out.println("Hai selezionato ricerca movimento");
					ricerca = true;
				} else {
					System.out.println("Hai inserito una scelta sbagliata, riprova");
					sceltaSbagliata = true;
				}
			} while (sceltaSbagliata == true);
			// Blocco movimento in entrata
			if (entrata == true) {
				for (int i = 0; i <= listaMovimenti.size(); i++) {
					System.out.print("Inserisci la data del movimento in entrata: ");
					m.dataMov = LocalDate.parse(sc.nextLine(), df);
					System.out.print("Inserisci il codice prodotto: ");
					m.codiceprod = sc.nextLine();
					System.out.print("Inserisci la quantità del prodotto: ");
					m.quantita = sc.nextInt();
					sc.nextLine();
					giacenzaProd.put(m.codiceprod, (giacenzaProd.get(m.codiceprod) + m.quantita));
					System.out.println(
							"Inserisci il codice del movimento in entrata tra i seguenti: \n" + tipologieMovimentoE);
					m.codiceMov = sc.nextLine();
					if (m.codiceMov.equals("E01")) {
						System.out.println("Inserire il codice fornitore");
						m.codiceForn = sc.nextLine();
						if (!movimentiFornitore.containsKey(m.codiceForn)) {
							movimentiFornitore.put(m.codiceForn, new ArrayList<String>());
						}
						movimentiFornitore.get(m.codiceForn).add("E" + l);
						l++;
					} else if (m.codiceMov.equals("E02")) {
						System.out.println("Inserire il codice cliente");
						m.codiceCliente = sc.nextLine();
						if (!movimentiCliente.containsKey(m.codiceCliente)) {
							movimentiCliente.put(m.codiceCliente, new ArrayList<String>());
						}
						movimentiCliente.get(m.codiceCliente).add("E" + l);
						l++;
					}
					listaMovimenti.add(m);
					if (tipologieMovimentoE.containsKey(m.codiceMov)) {
						System.out.println("Il movimento effettuato è: " + tipologieMovimentoE.get(m.codiceMov));
						System.out.println("Effettuato in data " + listaMovimenti.get(i).dataMov.format(df));
						System.out.println("Per l'articolo " + prodotti.get(m.codiceprod));
						entrata = false;
						break;
					}
				}
				// Blocco movimento in uscita
			} else if (uscita == true) {
				for (int i = 0; i <= listaMovimenti.size(); i++) {
					System.out.print("Inserisci la data del movimento in uscita: ");
					m.dataMov = LocalDate.parse(sc.nextLine(), df);
					System.out.print("Inserisci il codice prodotto: ");
					m.codiceprod = sc.nextLine();
					System.out.print("Inserisci la quantità del prodotto: ");
					m.quantita = sc.nextInt();
					sc.nextLine();
					giacenzaProd.put(m.codiceprod, (giacenzaProd.get(m.codiceprod) - m.quantita));
					System.out.println(
							"Inserisci il codice del movimento in uscita tra i seguenti: \n" + tipologieMovimentoU);
					m.codiceMov = sc.nextLine();
					if (m.codiceMov.equals("U02")) {
						System.out.println("Inserire il codice fornitore");
						m.codiceForn = sc.nextLine();
						if (!movimentiFornitore.containsKey(m.codiceForn)) {
							movimentiFornitore.put(m.codiceForn, new ArrayList<String>());
						}
						movimentiFornitore.get(m.codiceForn).add("U" + k);
						k++;
					} else if (m.codiceMov.equals("U01")) {
						System.out.println("Inserire il codice cliente");
						m.codiceCliente = sc.nextLine();
						if (!movimentiCliente.containsKey(m.codiceCliente)) {
							movimentiCliente.put(m.codiceCliente, new ArrayList<String>());
						}
						movimentiCliente.get(m.codiceCliente).add("U" + k);
						k++;
					}
					listaMovimenti.add(m);
					if (tipologieMovimentoU.containsKey(m.codiceMov)) {
						System.out.println("Il movimento effettuato è: " + tipologieMovimentoU.get(m.codiceMov));
						System.out.println("Effettuato in data " + listaMovimenti.get(i).dataMov.format(df));
						System.out.println("Per il prodotto " + prodotti.get(m.codiceprod));
						uscita = false;
						break;
					}
				}
				//Blocco verifica giacenza
			} else if (giacenza == true) {
				System.out.println("Inserire il codice del prodotto del quale si vuole verificare la giacenza:");
				m.codiceprod = sc.nextLine();
				if (prodotti.containsKey(m.codiceprod)) {
					System.out.println("La giacenza in magazzino del prodotto " + prodotti.get(m.codiceprod) + " è di "
							+ giacenzaProd.get(m.codiceprod));
					giacenza = false;
				}
			} else if (ricerca == true) {
				do {
					sceltaSbagliata = false;
					System.out.println("Vuoi ricercare tra i clienti(1) o fornitori(2)?");
					risp = sc.nextLine();
					if (risp.equals("1")) {
						System.out.println("Inserisci il codice cliente:");
						m.codiceCliente = sc.nextLine();
						if (movimentiCliente.containsKey(m.codiceCliente)) {
							System.out.println(
									"I movimenti relativi al cliente " + clienti.get(m.codiceCliente) + " sono:");
							for (int j = 0; j < movimentiCliente.get(m.codiceCliente).size(); j++) {
								System.out.println("Il movimento: " + movimentiCliente.get(m.codiceCliente).get(j));

							}
						}

					} else if (risposta.equals("2")) {
						System.out.println("Inserisci il codice fornitore:");
						m.codiceForn = sc.nextLine();
						if (movimentiCliente.containsKey(m.codiceForn)) {
							System.out.println(
									"I movimenti relativi al fornitore " + fornitori.get(m.codiceForn) + " sono:");
							for (int j = 0; j < movimentiFornitore.size(); j++) {
								System.out.println("il movimento: " + movimentiFornitore.get(m.codiceForn).get(j));
							}
						}
					} else {
						System.out.println("Inserire un valore valido");
						sceltaSbagliata = true;
					}
				} while (sceltaSbagliata == true);
				ricerca = false;
			}
			// richiesta restart
			System.out.println("Desideri fare un'altra operazione?");
			rispostaRestart = sc.nextLine();
			if (rispostaRestart.equalsIgnoreCase("no")) {
				System.out.println("Alla prossima !");
			}
		} while (rispostaRestart.equalsIgnoreCase("si") || rispostaRestart.equalsIgnoreCase("sì"));

	}

}
