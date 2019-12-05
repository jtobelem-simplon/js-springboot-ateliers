$(document).ready(function() {
	$.ajax({
		url : "http://localhost:8080/ateliers"
	}).then(function(data) {

		$('#ateliers-table').bootstrapTable({
			data : data,
			onClickRow : selectionnerLigne,
			idField : "id"
		});

		// selectionne la première ligne par defaut
		selectionnerLigne(data[0], null);

	});
});

function selectionnerLigne(row, $element) {
	console.log(row, row.id);
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
