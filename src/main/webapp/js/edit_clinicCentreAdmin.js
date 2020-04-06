/**
 * 
 */

var userID = "";

$(document).ready(function() {
	var str = window.location.hash.substring(1).replace(/%20/g, ' ');
	userID = str;
	getUser(userID);
})

function getUser(userID) {	
		$.ajax({
			type: 'GET',
			url : "clinicCentreAdmins/" + userID,
			contentType: "application/json",
			success : function(e) {
				renderUser(e);
			},
			error: function(e) {
				alert("Couldn't fetch user!");
			}
		})	
}

$(document).on('submit', '#editClinicCentreAdmin', function(e) {
	e.preventDefault();
	
	var form = document.forms['editClinicCentreAdmin'];
	
	var edited = {
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
		type: "PUT",
		url: "clinicCentreAdmins/" + userID,
		data: JSON.stringify(edited),
		contentType: "application/json",
		success: function(data)
		{
			window.location.replace("clinicCentreAdmins.html");
		},
		error: function(data) {
			alert("Couldn't update user!");
		}
	})
});


function renderUser(user) {
	$('input[name="email"]').val(user.email);
	$('input[name="password"]').val(user.password);
	$('input[name="name"]').val(user.name);
	$('input[name="surname"]').val(user.surname);
	$('input[name="address"]').val(user.address);
	$('input[name="city"]').val(user.city);
	$('input[name="country"]').val(user.country);
	$('input[name="phone"]').val(user.phone);
	$('input[name="id"]').val(user.id);
	
}


