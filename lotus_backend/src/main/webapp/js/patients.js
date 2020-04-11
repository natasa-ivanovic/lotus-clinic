function getPatients() {
	$.ajax({
		type: "GET",
		url: "/patients",
		success: function(e) {
			renderList(e);
		},
		error : function(e) {
			alert("Couldn't fetch patients!");
		}
	})
}

getPatients();

function renderList(list) {
	list.forEach(function(patient) {
		var tr = $('<tr></tr>');
		var link = "edit_patient.html#" + patient.id;
		tr.append('<td>' + patient.email + '</td>' +
				  '<td>' + patient.password + '</td>' +
				  '<td>' + patient.name + '</td>' +
				  '<td>' + patient.surname + '</td>' +
				  '<td>' + patient.address + '</td>' +
				  '<td>' + patient.city + '</td>' +
				  '<td>' + patient.country + '</td>' +
				  '<td>' + patient.phone + '</td>' +
				  '<td>' + patient.id + '</td>' +
				  '<td><a href="' + link + '">Edit</a></td>' +
				  '<td><a href="javascript:deletePatient(' + patient.id + ')">Delete</a></td>');
		$("#patients").append(tr);
	});
}

function deletePatient(id) {
	$.ajax({
		type: 'DELETE',
		url: '/patients/' + id,
		success: function(msg) {
			window.location.replace("patients.html");
		},
		error: function(msg) {
			alert("Couldn't delete!");
		}
		
	})
}

$(document).on('submit', '#addPatient', function(e) {
	e.preventDefault();
	
	var form = document.forms['addPatient'];
	
	var patient = {
			email : form.email.value,
			password : form.password.value,
			name : form.name.value,
			surname : form.surname.value,
			address : form.address.value,
			city : form.city.value,
			country : form.country.value,
			phone : form.phone.value,
			id : form.id.value
	};
	
	$.ajax({
		type: 'POST',
		url: '/patients',
		data: JSON.stringify(patient),
		contentType: 'application/json',
		success: function(msg) {
			window.location.replace("patients.html");
		},
		error: function(msg) {
			alert("Couldn't add patient!");
		}
		
	})
	
})
