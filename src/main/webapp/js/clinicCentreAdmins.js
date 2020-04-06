function getClinicCentreAdmins() {
	$.ajax({
		type: "GET",
		url: "/clinicCentreAdmins",
		success: function(e) {
			renderList(e);
		},
		error : function(e) {
			alert("Couldn't fetch clinic centre admins!");
		}
	})
}

getClinicCentreAdmins();

function renderList(list) {
	list.forEach(function(clinicCentreAdmin) {
		var tr = $('<tr></tr>');
		var link = "edit_clinicCentreAdmin.html#" + clinicCentreAdmin.id;
		tr.append('<td>' + clinicCentreAdmin.email + '</td>' +
				  '<td>' + clinicCentreAdmin.password + '</td>' +
				  '<td>' + clinicCentreAdmin.name + '</td>' +
				  '<td>' + clinicCentreAdmin.surname + '</td>' +
				  '<td>' + clinicCentreAdmin.address + '</td>' +
				  '<td>' + clinicCentreAdmin.city + '</td>' +
				  '<td>' + clinicCentreAdmin.country + '</td>' +
				  '<td>' + clinicCentreAdmin.phone + '</td>' +
				  '<td>' + clinicCentreAdmin.id + '</td>' +
				  '<td><a href="' + link + '">Edit</a></td>' +
				  '<td><a href="javascript:deleteClinicCentreAdmin(' + clinicCentreAdmin.id + ')">Delete</a></td>');
		$("#clinicCentreAdmins").append(tr);
	});
}

function deleteClinicCentreAdmin(id) {
	$.ajax({
		type: 'DELETE',
		url: '/clinicCentreAdmins/' + id,
		success: function(msg) {
			window.location.replace("clinicCentreAdmins.html");
		},
		error: function(msg) {
			alert("Couldn't delete!");
		}
		
	})
}

$(document).on('submit', '#addClinicCentreAdmin', function(e) {
	e.preventDefault();
	
	var form = document.forms['addClinicCentreAdmin'];
	
	var clinicCentreAdmin = {
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
		url: '/clinicCentreAdmins',
		data: JSON.stringify(clinicCentreAdmin),
		contentType: 'application/json',
		success: function(msg) {
			window.location.replace("clinicCentreAdmins.html");
		},
		error: function(msg) {
			alert("Couldn't add clinic centre admin!");
		}
		
	})
	
})
