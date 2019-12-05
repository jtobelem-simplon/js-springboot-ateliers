package atelier;

import java.util.Arrays;
import java.util.List;

public class AtelierModel {

	private int id;
	private String nom;
	private String description;
	private int capacite;
	private List<String> participantList;

	public AtelierModel(int id, String nom, String description, int capacite, String... participants) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.capacite = capacite;
		this.participantList = Arrays.asList(participants);
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

}
