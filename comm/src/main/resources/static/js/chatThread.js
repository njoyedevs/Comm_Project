/**
 * 
 */
// Add ChatThread Pop-out ---------------------------------------

// Get the modal, the add profile button, and the close button
var addChatThreadModel = document.getElementById('addChatThreadModal');
var newChatThreadBtn = document.querySelector(".btnAddChatThread");
var closeAddChatThreadButton = document.querySelector(".close-add-chatThread");

// When the user clicks the button, open the modal
newChatThreadBtn.onclick = function() {
  addChatThreadModel.style.display = "block";
}

// When the user clicks on the close button, close the modal
closeAddChatThreadButton.onclick = function() {
  addChatThreadModel.style.display = "none";
}

// Change Message Dynamically -----------------------------------
document.querySelectorAll('.selectChatThreadButton').forEach(button => {
    button.addEventListener('click', async (event) => {
		
		//  Update Messages --------
		
		// Get chatThreadId from the event.target.getAttribute for data-chatthread-id
        const chatThreadId = event.target.getAttribute('data-chatthread-id');
        
        // Update the hidden input field value with chatThreadId to persis over refresh 
        document.getElementById('selectedChatThreadId').value = chatThreadId;
        // console.log("This is the dynamic message chatThreadId: ", chatThreadId);
        
        // Make an AJAX request to set the selected chat thread ID in the session
		await fetch('/setSelectedChatThreadId', {
		    method: 'POST',
		    headers: {
		        'Content-Type': 'application/x-www-form-urlencoded',
		    },
		    body: new URLSearchParams({
		        'chatThreadId': chatThreadId
		    })
		});
        
        // Get the chat thread name from the event.target.textContent and update the
        // chatThreadTitle to presist over refresh
        const chatThreadName = event.target.textContent.trim();
        
        // Update the chatThreadTitle with the text from the chatThreadName
        document.querySelector('.chatThreadTitle').placeholder = "Chat with " + chatThreadName + " now!";

		// Use AJAX to obtain List<Message> Message message object for specific chatThread using chatThreadId
        const response = await fetch(`/chatThreads/${chatThreadId}/messages`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
            },
        });
        
        // Add error handling here
        // console.log("This is the raw AJAX promise: ", response); 
        
        // Log the status of the response
		// console.log("Response status: ", response.status);
        
        // Set List<Message> Message message object with the AJAX response
        const messages = await response.json();
        
        // Add error handling here
        // console.log("This is the accepted AJAX promise response messages object: " + JSON.stringify(messages, null, 2));

		updateMessages(messages);

		function updateMessages(messages) {
		    const messagesContainer = document.querySelector('.messages-container');
		    // Clear existing messages
		    messagesContainer.innerHTML = '';
		
		    messages.forEach(message => {
		        // Create the message structure
		        const messageDiv = document.createElement('div');
		        messageDiv.classList.add('message');
		
		        const avatarImage = document.createElement('img');
		        avatarImage.classList.add('chatAvatar');
		
		        // Use the default avatar if it's not provided in the MessageDTO
		        if (message.messageCreatorAvatar) {
		            avatarImage.src = `/images/${message.messageCreatorId}/${message.messageCreatorAvatar}`;
		        } else {
		            avatarImage.src = '/images/testImage.png';
		        }
		
				// Add a Unicode non-breaking space space between the avatar and the username
        		const space = '\u00A0';

		        // Set the inner HTML of the messageDiv to include the message content
        		messageDiv.innerHTML = `${space}${message.messageCreatorUserName}: ${message.message}`;
		
		        messageDiv.prepend(avatarImage);
		
		        // Append the message to the messages container
		        messagesContainer.appendChild(messageDiv);
		    });
		}
		
		// Update Participantes
		
		const participantsResponse = await fetch(`/chatThreads/${chatThreadId}/participants`, {
	        method: 'GET',
	        headers: {
	            'Accept': 'application/json',
	        },
	    });
	
	    // Add error handling here
	    // console.log("This is the raw AJAX promise: ", participantsResponse);
	
	    // Log the status of the participantsResponse
	    // console.log("ParticipantsResponse status: ", participantsResponse.status);
	
	    // Set List<User> participants with the AJAX response
	    const participants = await participantsResponse.json();
	
	    // Add error handling here
	    // console.log("This is the accepted AJAX promise response participants object: " + JSON.stringify(participants, null, 2));
	
	    updateParticipants(participants);
	    
	    function updateParticipants(participantUsernames) {
		    const participantsContainer = document.querySelector('.contactsContainers .form-container.participantsContainer');
		    
		    // Clear existing participants
		    let newParticipantsContainerHTML = '<h1>Participants</h1>';
		    
		    participantUsernames.forEach(username => {
		        newParticipantsContainerHTML += `
		            <div class="participantGroup">
		                <div class="participantUserName">
		                    <p class="participantText" >${username}</p>
		                </div>
		            </div>`;
		    });
		    
		    // Update the participantsContainer with the new participants
		    participantsContainer.innerHTML = newParticipantsContainerHTML;
		}

    });
});

// Add Emojis! ------------------------------------------------------

// chatThread.js
import { EmojiButton } from 'https://cdn.jsdelivr.net/npm/@joeattardi/emoji-button/dist/index.js?module';

// Identify the input button and emoji picker buttons by id in dashboard.js
const inputField = document.querySelector('#message');
const pickerButton = document.querySelector('#emoji-picker-button');

// Declare variable picker as a new EmojiButton()
const picker = new EmojiButton({
	theme: 'dark'
});

// Create arrow function using picker.on to add each emoji to inputField.value and send to id="message"
picker.on('emoji', (emoji) => {
  	// Append the selected emoji to the input field
 	inputField.value += emoji.emoji;
});

// Create an on click event listener 
pickerButton.addEventListener('click', (event) => {
	
	// This prevents the pickerButton from submitting the form	
	event.preventDefault();
  
	// This allows the picker(EmojiButton()) to toggle via the id="emoji-picker-button"
	picker.togglePicker(pickerButton);
});

// Add Media ----------------------------------------------------------------

// Get the model, the add media button
const mediaButton = document.getElementById('mediaButton');
const mediaInputForm = document.getElementById('mediaInputForm');
const addMediaButton = document.getElementById('addMediaButton');
const uploadImage = document.getElementById('uploadImage');

mediaButton.addEventListener('click', () => {
  mediaInputForm.style.display = mediaInputForm.style.display === 'none' ? 'block' : 'none';
});

addMediaButton.addEventListener('click', () => {
	// console.log("Add Media Button Clicked");
	const fileName = uploadImage.files[0].name;
	// console.log(fileName);
})

// Add Gif Pop-out ---------------------------------------

window.onload = function() {
  // Get the modal, the add Gif button, and the close button
  var addGifModal = document.getElementById('addGifModal');
  var newGifBtn = document.querySelector(".btnAddGif");
  var closeAddGifButton = document.querySelector(".close-add-gif");

  // When the user clicks the button, open the modal
  newGifBtn.onclick = function() {
    addGifModal.style.display = "block";
  }

  // When the user clicks on the close button, close the modal
  closeAddGifButton.onclick = function() {
    addGifModal.style.display = "none";
  }
}


// Add Gifs -----------------------------------------------------------------------------

// Get the GIF button element
const gifButton = document.querySelector('button[type="button"]:nth-child(3)');

// Get the GIF input form element
const gifInputForm = document.getElementById('gifInputForm');

// Toggle the display of the GIF input form when the GIF button is clicked
gifButton.addEventListener('click', () => {
  gifInputForm.style.display = gifInputForm.style.display === 'none' ? 'block' : 'none';
});

// Get the GIF search button and input field elements
const gifSearchButton = document.getElementById('gifSearchButton');
const gifSearchInput = document.getElementById('gifSearchInput');
const gifResultsContainer = document.getElementById('gifResultsContainer');

// Add the event listener for the GIF search button
gifSearchButton.addEventListener('click', async () => {
  const searchTerm = gifSearchInput.value.trim();

  if (searchTerm) {
    const url = `/api/giphy-search?q=${encodeURIComponent(searchTerm)}`;

    const response = await fetch(url);
    const data = await response.json();
    const gifs = data.data;

    gifResultsContainer.innerHTML = '';

    gifs.forEach(gif => {
      const img = document.createElement('img');
      img.src = gif.images.fixed_height_small.url;
      img.alt = gif.title;
      img.classList.add('gifResult');

      img.addEventListener('click', () => {
        const messageInput = document.getElementById('message');
        const imgToAdd = document.createElement('img');
        imgToAdd.src = img.src;
        imgToAdd.classList.add('addedGif');
        
        messageInput.value += `<img src="${imgToAdd.src}" class="${imgToAdd.classList}" />`;
        
        // Close the modal
	  	const addGifModal = document.getElementById('addGifModal');
	  	addGifModal.style.display = 'none';
        
        gifInputForm.style.display = 'none';
      });

      gifResultsContainer.appendChild(img);
    });
  }
});

// Message Submission Action ------------------------------------------------

// Add event listener to the form to handle submission
const messageForm = document.querySelector('form[action="/messages/new"]');

messageForm.addEventListener('submit', (e) => {
  e.preventDefault();

  // Add error handling

  // Submit the form
  messageForm.submit();
});