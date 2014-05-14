var contextRoot = getContextRoot();

function getContextRoot() {
	var rootUrl = location.protocol;
	rootUrl = rootUrl + "//" + location.host;
	rootUrl = rootUrl + "/" + location.pathname.split("/")[1];
	return rootUrl;
}

function clearFormInputs(form) {
	$(form).find('input[type="text"], textarea, select').val('');
	$(form).find('input[type="checkbox"], input[type="radio"]').removeAttr('checked');
}