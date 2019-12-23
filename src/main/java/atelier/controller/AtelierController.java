package atelier.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import atelier.model.Atelier;

//ce mapping permet indique que toutes les adresses de ce controller commencent par atelier
@RequestMapping("/atelier")
@RestController
public class AtelierController {
	
	// Note : une Map serait plus adaptée ici
	private List<Atelier> atelierListe;
	
	/**
	 * Le rôle du constructeur est d'initialiser les attributs de la classe
	 */
	public AtelierController() {
		initAtelier();
	}
	
	/**
	 * Crée des ateliers qui seront stockés dans une liste (pour faire office de base de données.. non persistente)
	 */
	private void initAtelier() {
		String desc1 = "Le Hatha-Yoga étant une discipline qui s'adresse à tout le monde, "
				+ "il existe différents niveaux selon que vous soyez débutant ou plus expérimenté dans la discipline.\n" + 
				"\n" + 
				"Contrairement à un enchaînement de gymnastique, "
				+ "les postures doivent être maintenues suffisamment longtemps : environ 3 minutes par posture.";
		
		String desc2 = "Les aspects les plus complets du jazz – harmonie (grilles, gammes, accords...), "
				+ "improvisation, histoire du jazz (et ses grands noms) – seront abordés en s'appuyant sur une pratique intensive et collective de votre instrument.\n" + 
				"2 à 3 années d'instrument conseillées. Cours d'harmonie jazz conseillé.\n" + 
				"Le dernier cours de chaque trimestre pourra être remplacé par une Jam Session.";
		
		Atelier yoga = new Atelier("yoga", desc1, 8, "Simone", "Alberto", "Pandi");
		Atelier jazz = new Atelier("jazz", desc2, 12, "Louis", "Django", "Ella");
		
		// Arrays.asList crée une liste dans laquelle on ne peut pas ajouter, 
		// il faut donc wrapper dans une new ArrayList
		atelierListe = new ArrayList<Atelier>(Arrays.asList(yoga, jazz));
	}
	
	/**
	 * Mapping simple qui retourne un String
	 * 
	 * @return un message de bienvenue
	 */
	// le mapping permet d'associer un endpoint avec une méthode : ex
	// localhost:8080/atelier/hello
	@RequestMapping("/hello")
	public String hello() {
		return "simplon";
	}
	
	/**
	 * Mapping simple qui retourne un objet, le controller va sérialiser cet objet
	 * en json pour pouvoir l'envoyer via le protocole http
	 * 
	 * @return un objet de type List<Atelier>
	 */
	@GetMapping("/all")
	public List<Atelier> liste() {
		return atelierListe;
	}
	
	/**
	 * On peut aussi changer la signature de la méthode pour wrapper la réponse (ici
	 * un atelier) à l'intérieur d'une responseEntity, ce qui permet de selectionner
	 * le code http (404, 201, etc...) qui sera attaché à la réponse.
	 * 
	 * L'id est fourni dans l'url, ex : localhost:8080/atelier/get/{3}
	 * 
	 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-responseentity
	 * 
	 * @return un message de bienvenue
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<Atelier> get(@PathVariable int id) {
		try {
			return ResponseEntity.ok(getById(id));
		}
		catch (NoSuchElementException ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Mapping paramétré, on liste le nom de tous les paramètres (dans l'ordre) dans RequestParam.
	 * Ce paramètre sera à fournir dans l'url, ex : 
	 * localhost:8080/atelier/new1?nom=yoga,description=yoga,capacite=8
	 * 
	 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-requestparam
	 * 
	 * @return true si l'atelier a pu être ajouté
	 */
	@RequestMapping(value = "/new1", params = {"nom","description", "capacite"})
	public boolean newAtelier1(String nom, String description, int capacite) {
		Atelier newAtelier = new Atelier(nom, description, capacite);
		return atelierListe.add(newAtelier);
	}

	/**
	 * Seconde manière d'ajouter un parmètre, en envoyant directement un objet au
	 * format json. Utilisez postman pour tester.
	 * 
	 * https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
	 * 
	 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-requestmapping
	 * 
	 * @return true si l'atelier a pu être ajouté
	 */
	@PostMapping("/new2")
	public boolean newAtelier2(@RequestBody Atelier newAtelier) {
		return atelierListe.add(newAtelier);
	}
	
	
	@RequestMapping(value = "/inscription", params = {"atelierID","participant"})
	public boolean inscriptionParticipant(int atelierID, String participant) {
		
		try {
			Atelier atelier = getById(atelierID);
			
			if (atelier.getCapacite()>atelier.getParticipantList().size()) {
				return atelier.getParticipantList().add(participant);
			}
		} catch (NoSuchElementException ex) {
			// TODO
		}
		
		return false;
	}
	
	
	/**
	 * Une méthode pratique pour retourner un element de la liste atelierListe qui a l'id spécifié.
	 * @param id l'id à chercher
	 */
	private Atelier getById(int id) {
		for (Atelier atelier : atelierListe) {
			if (atelier.getId() == id) {
				return atelier;
			}
		}
		throw new NoSuchElementException();
	}
	
	
}
