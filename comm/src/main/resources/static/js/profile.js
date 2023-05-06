/**
 * 
 */
// Add Profile Image Pop-out ---------------------------------------

// Test Profile Button
// var profileButton = document.getElementById('profileImageButton');
// profileButton.onclick = function() {
//   console.log("This works");
// }

// Get the modal, the add image button, and the close button
var addImageModal = document.getElementById('addImageModal');
var addImageButton = document.getElementById('profileImageButton');
var closeAddImageButton = document.querySelector('.close-add-image');

// When the user clicks the button, open the modal
addImageButton.onclick = function(event) {
  event.preventDefault();
  addImageModal.style.display = "block";
}

// When the user clicks on the close button, close the modal
closeAddImageButton.onclick = function() {
  addImageModal.style.display = "none";
}
 
// Add Profile Pop-out ---------------------------------------

// Get the modal, the add profile button, and the close button
var addModal = document.getElementById('addProfileModal');
var addBtn = document.querySelector(".btnAddProfile");
var closeAddButton = document.querySelector(".close-add-profile");

// When the user clicks the button, open the modal
addBtn.onclick = function() {
  addModal.style.display = "block";
}

// When the user clicks on the close button, close the modal
closeAddButton.onclick = function() {
  addModal.style.display = "none";
}

// Edit Profile Pop-out ---------------------------------------

// Get the modal, the edit profile button, and the close button
var editModal = document.getElementById('editProfileModal');
var editBtn = document.querySelector(".btnEditProfile");
var closeEditButton = document.querySelector(".close-edit-profile");

// When the user clicks the button, open the modal
editBtn.onclick = function() {
  editModal.style.display = "block";
}

// When the user clicks on the close button, close the modal

// When the user clicks on the close button, close the modal
if (closeEditButton) {
	closeEditButton.onclick = function() {
	  editModal.style.display = "none";
	}
}