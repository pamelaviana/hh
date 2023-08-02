let retryCount = 0; // Counter for retry attempts
let maxRetries = 5; // Maximum retry attempts
let receivedNotifications = {}; // Store the received notifications
const notificationCounter = document.getElementById('notificationCounter');
notificationCounter.innerText = '';

function fetchNotifications() {
    var csrfToken = document.querySelector("[name='_csrf']").content;
    $.ajax({
        url: '/check/notifications',
        type: 'GET',
        headers: {
            'X-CSRF-TOKEN': csrfToken
        },
        success: function(response) {
            console.log(response);
            retryCount = 0; // Reset retry count on a successful response
            if (response == null || response == undefined || response.length == 0) {
                console.log('No notifications');
                return;
            }
            console.log(response);
            for (let i = 0; i < response.length; i++) {
                let notification = response[i];
                // Check if the notification was already received
                if (!receivedNotifications[notification.id]) {
                    receivedNotifications[notification.id] = notification;
                    notificationCounter.innerText = i + 1;
                    let notificationHTML = `
                    <div>
                        <a class="dropdown-item d-flex align-items-center" href="${notification.url || '/'}">
                            <div class="me-3">
                                <div class="${notification.type.level} icon-circle">
                                    <i class="${notification.type.icon} text-white"></i>
                                </div>
                            </div>
                            <div>
                                <span class="small text-gray-500">${notification.date}</span>
                                <span class="float-end small text-gray-500">${notification.time}</span>
                                <p>${notification.message}</p>
                            </div>
                        </a>
                    </div>
                    `;
                    $('#notification-container').append(notificationHTML);
                }
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Error getting notifications:', textStatus, ', Details:', errorThrown);
            console.error('Response:', jqXHR.responseText);
            retryCount++;
            if(retryCount >= maxRetries){
                clearInterval(intervalID); // Stop the interval after maxRetries failed attempts
                console.error('Stopped checking notifications after', maxRetries, 'failed attempts.');
            }
        }
    });
}

// Set an interval to fetch notifications every 30 seconds
let intervalID = setInterval(fetchNotifications, 30000);
fetchNotifications(); // Fetch notifications on page load