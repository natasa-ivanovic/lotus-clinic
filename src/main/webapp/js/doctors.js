function getDoctors() {
	$.ajax({
		type: "GET",
		url: "/doctors",
		success: function(e) {
			renderList(e);
		},
		error : function(e) {
			alert("Couldn't fetch doctors!");
		}
	})
}

getDoctors();

function renderList(list) {
	
	list.forEach(function(doctor) {
		var tr = $('<tr></tr>');
		var link = "edit_doctor.html#" + doctor.id;
		tr.append('<td>' + doctor.email + '</td>' +
				  '<td>' + doctor.password + '</td>' +
				  '<td>' + doctor.name + '</td>' +
				  '<td>' + doctor.surname + '</td>' +
				  '<td>' + doctor.address + '</td>' +
				  '<td>' + doctor.city + '</td>' +
				  '<td>' + doctor.country + '</td>' +
				  '<td>' + doctor.phone + '</td>' +
				  '<td>' + doctor.id + '</td>' +
				  '<td>' + '<a href="' + link + '">Edit</a></td>' + 
				  '<td><a href="javascript:deleteDoctor(' + doctor.id + ')">Delete</a></td>');
		$("#doctors").append(tr);
	});
}

function deleteDoctor(id) {
	$.ajax({
		type: 'DELETE',
		url: '/doctors/' + id,
		success: function(msg) {
			window.location.replace("doctors.html");
		},
		error: function(msg) {
			alert("Couldn't delete!");
		}
		
	})
}

$(document).on('submit', '#addDoctor', function(e) {
	e.preventDefault();
	
	var form = document.forms['addDoctor'];
	
	var doc = {
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
	
	var doctor = JSON.stringify(doc);
	
	$.ajax({
		type: 'POST',
		url: '/doctors',
		data: doctor,
		contentType: 'application/json',
		success: function(msg) {
			window.location.replace("doctors.html");
		},
		error: function(msg) {
			alert("Couldn't add doctor!");
		}
		
	})
	
})
