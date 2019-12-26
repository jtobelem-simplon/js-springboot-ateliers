chargerAteliers();
chargerNomEcole();
connecterBoutons();

// l'id de l'atelier selectionné
var id = 0;


/**
 * on associe les boutons avec les fonctions à appeler
 * 
 * TODO (1) : action à effectuer lorsqu'on clique
 */
function connecterBoutons() {
	document.getElementById('ajouter-atelier1').addEventListener('click', ajouterAtelier1);
	document.getElementById('ajouter-atelier2').addEventListener('click', ajouterAtelier2);
	document.getElementById('ajouter-participant').addEventListener('click', ajouterParticipant);
}

/**
 * on envoie les paramètres directement dans l'url
 */
function ajouterAtelier1() {
	let nom = document.getElementById('inputNom').value;
	let description = document.getElementById('inputDesc').value;
	let capacite = document.getElementById('inputCapacite').value;
	
	// TODO (3) : passage du paramètre dans l'url, comment-est récupéré dans le controller?
	let url = "atelier/new1?nom=" + nom + "&description=" + description + "&capacite=" + capacite;

	// TODO (2) : méthode asynchrone, on passe directement à ligne 33 même si la 30 n'est pas terminée. 31 sera effectuée lorsque 30 sera finie.
	$.post(url).then(function (data) {
		alert("atelier bien ajouté");
	});

}

/**
 * on envoie les paramètres à partir d'un objet json qui sera dans le body (plus propre je trouve)
 * 
 * https://stackoverflow.com/questions/20226169/how-to-pass-json-post-data-to-web-api-method-as-an-object
 */
function ajouterAtelier2() {

	let atelier = {
		nom: document.getElementById('inputNom').value,
		description: document.getElementById('inputDesc').value,
		capacite: document.getElementById('inputCapacite').value
	};

	// dommage, la syntaxe suivante ne fonctionne pas..
//		$.post("atelier/new2", data=JSON.stringify(atelier), contentType='application/json');

	// TODO (2) : méthode asynchrone
	$.ajax({
		type : "POST",
		url : "atelier/new2",
		dataType : 'json',
		data : JSON.stringify(atelier), // TODO (3) : passage du paramètre dans le body, comment-est récupéré dans le controller?
		contentType : 'application/json'
	}).then(function(data) {
		alert("atelier bien ajouté");
	});

}

function ajouterParticipant() {
	let participant = document.getElementById('inputNomPart').value;

	// TODO (3) : passage du paramètre dans l'url, comment-est récupéré dans le controller?
	let url = "atelier/inscription?atelierID=" + id + "&participant=" + participant;

	// TODO (2) : méthode asynchrone
	$.post(url).then(function (data) {
		alert("participant bien ajouté");
	});
}

function chargerAteliers() {
	// TODO (2) : méthode asynchrone
	$.get("atelier/all").then(function (data) {

		$('#ateliers-table').bootstrapTable({
			data: data,
			onClickRow: selectionnerLigne, // TODO (1) : action à effectuer lorsqu'on clique
			idField: "id"
		});

		// selectionne la première ligne par defaut
		selectionnerLigne(data[id], null);
	});
}

function chargerNomEcole() {
	// TODO (2) : méthode asynchrone
	$.get("atelier/hello").then(function (data) {
		if (data) {
			document.getElementById('nom-ecole').textContent += " de "
				+ data;
		}
	});
}


function selectionnerLigne(row, $element) {
	// mise à jour de la variable globale id
	id = row.id;

	document.getElementById('detail-nom').textContent = row.nom;
	document.getElementById('detail-desc').textContent = row.description;

	node = document.getElementById('participants-table-body');

	// vide la liste des participants
	while (node.hasChildNodes()) {
		node.removeChild(node.lastChild);
	}

	for (var i = 0; i < row.participantList.length; i++) {
		$('#participants-table-body').append(
			"<tr><td>" + row.participantList[i] + "</td></tr>");
	}
}
