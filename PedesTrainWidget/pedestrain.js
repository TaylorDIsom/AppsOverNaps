console.log('RUNNING PEDESTRAIN JAVASCRIPT');
console.log($('#list_comments_page'));
console.log($('#add_comment_page'));
console.log($('#edit_comment_page'));

$(function() {
 // Handler for .ready() called.
	console.log('ready');

	//Bind to the create so the page gets updated with the listing
	$('#favorites_page').bind('pagebeforeshow',function(event, ui){
		console.log('pagebeforeshow');
	
		//Remove the old rows
		$( ".favorites_list_row" ).remove();
		
		//JQuery Fetch The New Ones
		$.ajax({
			url: "api/favorites",
			dataType: "json",
	        async: false,
	        success: function(data, textStatus, jqXHR) {
				console.log(data);
	        	//Create The New Rows From Template
	        	$( "#favorites_list_row_template" ).tmpl( data ).appendTo( "#favorites_list" );
	        },
	        error: ajaxError
		});

		$('#favorites_list').listview('refresh');
	});

	//Bind to the create so the page gets updated with the listing
	$('#schedule_page').bind('pagebeforeshow',function(event, ui){
		console.log('pagebeforeshow');
	
		//Remove the old rows
		$( ".schedule_list_row" ).remove();
		
		//JQuery Fetch The New Ones
		$.ajax({
			url: "api/schedule",
			dataType: "json",
	        async: false,
	        success: function(data, textStatus, jqXHR) {
				console.log(data);
	        	//Create The New Rows From Template
	        	$( "#schedule_list_row_template" ).tmpl( data ).appendTo( "#schedule_list" );
	        },
	        error: ajaxError
		});

		$('#schedule_list').listview('refresh');
	});

	//Bind to the create so the page gets updated with the listing
	$('#list_comments_page').bind('pagebeforeshow',function(event, ui){
		console.log('pagebeforeshow');
	
		//Remove the old rows
		$( ".comment_list_row" ).remove();
		$( ".user_list_row" ).remove();
		
		//JQuery Fetch The New Ones
		$.ajax({
			url: "api/comment",
			dataType: "json",
	        async: false,
	        success: function(data, textStatus, jqXHR) {
				console.log(data);
	        	//Create The New Rows From Template
	        	$( "#comment_list_row_template" ).tmpl( data ).appendTo( "#comments_list" );
	        },
	        error: ajaxError
		});

		//JQuery Fetch The New Ones
		$.ajax({
			url: "api/users",
			dataType: "json",
	        async: false,
	        success: function(data, textStatus, jqXHR) {
				console.log(data);
	        	//Create The New Rows From Template
	        	$( "#user_list_row_template" ).tmpl( data ).appendTo( "#users_list" );
	        },
	        error: ajaxError
		});
		
		$('#comments_list').listview('refresh');
		$('#users_list').listview('refresh');
	});

	//Bind the add page clear text
	$('#login_page').bind('pagebeforeshow', function() {
		console.log("Login Page");
		//$('#add_comment_text')[0].value = "";
	});

	//Bind the add page clear text
	$('#add_user_page').bind('pagebeforeshow', function() {
		console.log("Add User Page");
		//$('#first_name')[0].value = "";
		// $('#last_name')[0].value = "";
		// $('#email')[0].value = "";
		// $('#phone')[0].value = "";
	});
	
	//Bind the add page clear text
	$('#add_comment_page').bind('pagebeforeshow', function() {
		console.log("Add Comment Page");
		$('#add_comment_text')[0].value = "";
	});
		
	//Bind the add user page button
	$('#add_user_button').bind('click', function() {
		console.log("Add Button");
		$.ajax({
			url: "api/users",
			dataType: "json",
	        async: false,
			data: 	{
					'firstName': $('#firstName')[0].value,
					'lastName': $('#lastName')[0].value,
					'email': $('#email')[0].value,
					'phone': $('#phone')[0].value
					},
			type: 'POST',
	        error: ajaxError
		});
	});
	
	//Bind the add page clear text
	$('#add_user_page').bind('pagebeforeshow', function() {
		console.log("Add User Page");
		$('#firstName')[0].value = "";
		$('#lastName')[0].value = "";
		$('#email')[0].value = "";
		$('#phone')[0].value = "";
	});
		
	//Bind the add user page button
	$('#add_favorite_button').bind('click', function() {
		console.log("Add Button");
		$.ajax({
			url: "api/favorites",
			dataType: "json",
	        async: false,
			data: 	{
					'name': $('#name')[0].value,
					'address': $('#address')[0].value,
					'userId': $('#userId')[0].value
					},
			type: 'POST',
	        error: ajaxError
		});
	});
	
	//Bind the add page clear text
	$('#add_favorites_page').bind('pagebeforeshow', function() {
		console.log("Add User Page");
		$('#name')[0].value = "";
		$('#address')[0].value = "";
		$('#userId')[0].value = "";
	});
		
	//Bind the add user page button
	$('#add_schedule_button').bind('click', function() {
		console.log("Add Button");
		$.ajax({
			url: "api/schedule",
			dataType: "json",
	        async: false,
			data: 	{
					'date': $('#date')[0].value,
					'time': $('#time')[0].value,
					'trainId': $('#trainId')[0].value
					},
			type: 'POST',
	        error: ajaxError
		});
	});
	
	//Bind the add page clear text
	$('#add_schedule_page').bind('pagebeforeshow', function() {
		console.log("Add User Page");
		$('#date')[0].value = "";
		$('#time')[0].value = "";
		$('#trainId')[0].value = "";
	});
		
	//Bind the add page button
	$('#add_comment_button').bind('click', function() {
		console.log("Add Button");
		$.ajax({
			url: "api/comment",
			dataType: "json",
	        async: false,
			data: {'comment': $('#add_comment_text')[0].value},
			type: 'POST',
	        error: ajaxError
		});
	});
		
	//Bind the edit page init text
	$('#edit_user_page').bind('pagebeforeshow', function() {
		console.log("Edit User Page");
		var user_id = $.url().fparam("user_id");
		
		//Instead of passing around in JS I am doing AJAX so direct links work
		//JQuery Fetch The Comment
		$.ajax({
			url: "api/users/"+user_id,
			dataType: "json",
	        async: false,
	        success: function(data, textStatus, jqXHR) {
				console.log(data);
	       		//$('#edit_user_text')[0].value = data;//.comment;
	        },
	        error: ajaxError
		});
	});
	
	//Bind the edit page init text
	$('#edit_comment_page').bind('pagebeforeshow', function() {
		console.log("Edit Comment Page");
		var comment_id = $.url().fparam("comment_id");
		
		//Instead of passing around in JS I am doing AJAX so direct links work
		//JQuery Fetch The Comment
		$.ajax({
			url: "api/comment/"+comment_id,
			dataType: "json",
	        async: false,
	        success: function(data, textStatus, jqXHR) {
				console.log(data);
	       		$('#edit_comment_text')[0].value = data.comment;
	        },
	        error: ajaxError
		});
	});
	
	//Bind the edit page save button
	$('#save_button').bind('click', function() {
		console.log("Save Button");
		var comment_id = $.url().fparam("comment_id");
		$.ajax({
			url: "api/comment/"+comment_id,
			dataType: "json",
	        async: false,
			data: {'commentText': $('#edit_comment_text')[0].value},
			headers: {'X-HTTP-Method-Override': 'PUT'},
			type: 'POST',
	        error: ajaxError
		});
	});
	
	//Bind the edit page remove button
	$('#remove_button').bind('click', function() {
		console.log("Remove Button");
		var comment_id = $.url().fparam("comment_id");
		$.ajax({
			url: "api/comment/"+comment_id,
			dataType: "json",
	        async: false,
			type: 'DELETE',
	        error: ajaxError
		});
	});
	
	//Cleanup of URL so we can have better client URL support
	$('#edit_comment_page').bind('pagehide', function() {
		$(this).attr("data-url",$(this).attr("id"));
		delete $(this).data()['url'];
	});
	
	//Cleanup of URL so we can have better client URL support
	$('#edit_user_page').bind('pagehide', function() {
		$(this).attr("data-url",$(this).attr("user_id"));
		delete $(this).data()['url'];
	});

});

/******************************************************************************/

function ajaxError(jqXHR, textStatus, errorThrown){
	console.log('ajaxError '+textStatus+' '+errorThrown);
	$('#error_message').remove();
	$("#error_message_template").tmpl( {errorName: textStatus, errorDescription: errorThrown} ).appendTo( "#error_dialog_content" );
	$.mobile.changePage($('#error_dialog'), {
		transition: "pop",
		reverse: false,
		changeHash: false
	});
}
