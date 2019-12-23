package atelier.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Atelier {
	
	private static final AtomicInteger ID_GEN = new AtomicInteger(0);

	private int id;
	private String nom;
	private String description;
	private int capacite;
	private List<String> participantList;
	
	// nécessaire pour la méthode newAtelier2 du controller qui a besoin de construire un atelier
	public Atelier() {
		this.id = ID_GEN.getAndIncrement();
		this.participantList = new ArrayList<>();
	}

	public Atelier(String nom, String description, int capacite, String... participants) {
		this(); // appelle le constructeur sans paramètre (au-dessus)
		
		this.nom = nom;
		this.description = description;
		this.capacite = capacite;
		participantList.addAll(Arrays.asList(participants));
	}

	public String getNom() {
		return nom;
	}
	
	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public int getCapacite() {
		return capacite;
	}

	public List<String> getParticipantList() {
		return participantList;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	
	

}
