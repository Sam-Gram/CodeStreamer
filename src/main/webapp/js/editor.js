$(document).ready(function () {
    codeMirror = CodeMirror($('#editor').get()[0], {
        value: "function myScript()\n{\n   return 100;\n}\n",
        mode: "clike",
        matchBrackets: true,
        autoCloseBrakets: true,
        lineNumbers: true
    });

    if (isHost) {
        setHost();
    } else {
        setViewer();
    }
    
    console.log(codeMirror);
});

/**
 * Use after the page has loaded to let the client know that it's a host.
 * We need this function because we don't currently have a way for the client to decide if it's a host or a viewer.
 */
function setHost() {
    registerChangeListener();
}

/**
 * Use after the page has loaded to let the client know that it's a viewer.
 * We need this function because we don't currently have a way for the client to decide if it's a host or a viewer.
 */
function setViewer() {
    startEventSource();
}

/**
 * Tells the browser to send a POST every time the user make a change in the text editor.
 */
function registerChangeListener() {
    codeMirror.on("changes", function(editor, change) {
        $.ajax({
            type: "POST",
            url: "Update",
            data: {change: JSON.stringify(change)}
        });
    });
}

/**
 * Sets up an Event Source, which will allow the server to 'push' information to the client.
 */
function startEventSource() {
    // Check if the browser supports Server-Sent Events
    if (typeof(EventSource) !== "undefined") {
        // Create a new Event Source to receive updates from /Update
        var source = new EventSource("Update");

        // When the server sends info...
        source.onmessage = function(event) {
            // Get the changes from the request.
            var change = JSON.parse(event.data);

            // Loop through all the change objects (usually one, but sometimes there's two).
            for (var i = 0; i < change.length; i++) {
                codeMirror.replaceRange(change[i].text, change[i].from,
                                        change[i].to, change[i].origin);
            }
        };
    } else {
        $("#editor").empty();
        $("#editor").append("<h1>Your browser is not supported!</h1>");
    }
}

