/**
 * 
 */
 // Add a Contact Pop-out ---------------------------------------

// Get the Add Contacts modal
var addContactsModal = document.getElementById('addContactsModal');

// Get the Add Contacts button
var addContactsBtn = document.querySelector('.btn-add-contacts');

// Get the close button for the Add Contacts modal
var addContactsCloseBtn = document.querySelector('.close-add-contacts');

// Show the Add Contacts modal when the button is clicked
addContactsBtn.addEventListener('click', function () {
    addContactsModal.style.display = 'block';
});

// Hide the Add Contacts modal when the close button is clicked
addContactsCloseBtn.addEventListener('click', function () {
   
   addContactsModal.style.display = 'none';
});

 // Remove a Contact Pop-out ---------------------------------------

// Get the Remove Contacts modal
var removeContactsModal = document.getElementById('removeContactsModal');

// Get the Remove Contacts button
var removeContactsBtn = document.querySelector('.btn-remove-contacts');

// Get the close button for the Remove Contacts modal
var removeContactsCloseBtn = document.querySelector('.close-remove-contacts');

// Show the Remove Contacts modal when the button is clicked
removeContactsBtn.addEventListener('click', function () {
    removeContactsModal.style.display = 'block';
});

// Hide the Remove Contacts modal when the close button is clicked
removeContactsCloseBtn.addEventListener('click', function () {
   
   removeContactsModal.style.display = 'none';
});
