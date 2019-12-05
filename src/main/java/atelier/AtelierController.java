package atelier;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

@RestController
public class AtelierController {
	
	private List<AtelierModel> atelierListe;
	
	public AtelierController() {
		initAtelier();
	}
	
	public void initAtelier() {
		String desc1 = "Le Hatha-Yoga étant une discipline qui s'adresse à tout le monde, "
				+ "il existe différents niveaux selon que vous soyez débutant ou plus expérimenté dans la discipline.\n" + 
				"\n" + 
				"Contrairement à un enchaînement de gymnastique, "
				+ "les postures doivent être maintenues suffisamment longtemps : environ 3 minutes par posture.";
		
		String desc2 = "Les aspects les plus complets du jazz – harmonie (grilles, gammes, accords...), "
				+ "improvisation, histoire du jazz (et ses grands noms) – seront abordés en s'appuyant sur une pratique intensive et collective de votre instrument.\n" + 
				"2 à 3 années d'instrument conseillées. Cours d'harmonie jazz conseillé.\n" + 
				"Le dernier cours de chaque trimestre pourra être remplacé par une Jam Session.";
		
		AtelierModel yoga = new AtelierModel(1,"yoga", desc1, 8, "Simone", "Alberto", "Pandi");
		AtelierModel jazz = new AtelierModel(2,"jazz", desc2, 12, "Louis", "Django", "Ella");
		
		atelierListe = Arrays.asList(yoga, jazz);
	}
	
//	@RequestMapping("/")
//	public String index() {
//		return "Salut les gars!";
//	}
	
	@RequestMapping("/ecole")
	public String nom() {
		return "simplon";
	}
	
	@GetMapping("/ateliers")
	public List<AtelierModel> liste() {
		return atelierListe;
	}
	
	// TODO : ajoute un atelier
	@RequestMapping("/nv")
	public ResponseEntity<?> nouveau() {
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping("/inscription")
	public boolean inscription(
			@RequestParam(value="atelier", defaultValue="yoga") String atelierNom, 
			@RequestParam(value="nom", defaultValue="bob") String nom) {
		
		for (AtelierModel atelier : atelierListe) {
			if (atelier.getNom() == atelierNom) {
				if (atelier.getCapacite()>atelier.getParticipantList().size()) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	
	
}
