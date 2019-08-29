

document.addEventListener('DOMContentLoaded', function () {
	console.log('DOM recherche construit');

	$('#date').datepicker({dateFormat:"yy-mm-dd"});
	
	$('#date2').datepicker({dateFormat:"yy-mm-dd"});
});