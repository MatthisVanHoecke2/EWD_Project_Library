function addAuthor() {
	const form = document.getElementById("bookForm");
	form.action = "/books/add/author";
	form.submit();
}
function removeAuthor(index) {
	const form = document.getElementById("bookForm");
	form.action = `/books/add/author?remove=${parseInt(index)}`;
	form.submit();
}

function addLocation() {
	const form = document.getElementById("bookForm");
	form.action = "/books/add/location";
	form.submit();
}
function removeLocation(index) {
	const form = document.getElementById("bookForm");
	form.action = `/books/add/location?remove=${parseInt(index)}`;
	form.submit();
}

function saveBook() {
	const form = document.getElementById("bookForm");
	form.action = "/books/add/save";
	form.submit();
}