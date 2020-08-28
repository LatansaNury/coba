var functions = require('firebase-functions');
var admin = require('firebase-admin');
 
admin.initializeApp(functions.config().firebase);
exports.sendNotification = functions.database.ref('/sensor/{detection}')
        .onUpdate((change, context) => {
	// const detection = event.data.val();
	const detection = change.after.val();
	// const detection = newData.detection();
 	// const eventSnapshot = change.after;
	// const detection = eventSnapshot.child("detection").val();
	// const detection = newData.detection()
        // Grab the current value of what was written to the Realtime Database.
        // var eventSnapshot = event.data;
        // var str1 = "Author is ";
        // var str = str1.concat(eventSnapshot.child("author").val());
        // console.log(str);
 
        const topic = "android";
        const payload = {
            data: {
                title: "tes",
                body: "tesss"
            }
        };

	if (detection == "Fall") {
	// Send a message to devices subscribed to the provided topic.
        return admin.messaging().sendToTopic(topic, payload)
            .then(function (response) {
                // See the MessagingTopicResponse reference documentation for the
                // contents of response.
                console.log("Successfully sent message:", response);
		console.log(detection);
            })
            .catch(function (error) {
                console.log("Error sending message:", error);
            });
	}
        });
