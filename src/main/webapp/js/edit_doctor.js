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
			url : "doctors/" + userID,
			contentType: "application/json",
			success : function(e) {
				renderUser(e);
			},
			error: function(e) {
				alert("Something bad happened!");
				//window.location.replace("doctors.html");
			}
		})	
}

$(document).on('submit', '#editDoctor', function(e) {
	e.preventDefault();
	
	var form = document.forms['editDoctor'];
	
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
		url: "doctors/" + userID,
		data: JSON.stringify(edited),
		contentType: "application/json",
		success: function(data)
		{
			window.location.replace("doctors.html");
		},
		error: function(data) {
			alert("Something bad happened!");
			//window.location.replace("doctors.html");
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

$(document).on("click", "#del_user", function() {
	
	$.ajax({
		type : "DELETE",
		url : "rest/users/" + userID,
		success : function(msg) {
			//$("#msg")[0].innerHTML = msg.responseText;
			window.location.replace("/ManaCloud/users.html");
		},
		error : function(msg) {
			window.location.replace("/ManaCloud/users.html");
		}
	})
	
})

