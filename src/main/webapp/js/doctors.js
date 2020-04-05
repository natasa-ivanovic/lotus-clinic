function getDoctors() {
	$.ajax({
		type: "GET",
		url: "/doctors",
		success: function(e) {
			renderList(e);
		},
		error : function(e) {
			alert('Something bad happened!');
		}
	})
}

getDoctors();

function renderList(list) {
	
	list.forEach(function(doctor) {
		var tr = $('<tr></tr>');
		var link = "edit_doctor.html#"
		tr.append('<td>' + doctor.email + '</td>' +
				  '<td>' + doctor.password + '</td>' +
				  '<td>' + doctor.name + '</td>' +
				  '<td>' + doctor.surname + '</td>' +
				  '<td>' + doctor.address + '</td>' +
				  '<td>' + doctor.city + '</td>' +
				  '<td>' + doctor.country + '</td>' +
				  '<td>' + doctor.phone + '</td>' +
				  '<td>' + doctor.id + '</td>' +
				  '<td>' + '<a href="' + link + doctor.id + '">Edit</a></td>');
		$("#doctors").append(tr);
	});
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
			alert("Something bad happened!");
		}
		
	})
	
})
