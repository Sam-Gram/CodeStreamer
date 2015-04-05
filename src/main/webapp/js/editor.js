$(document).ready(function () {
    codeMirror = CodeMirror($('#editor').get()[0], {
        value: "#include <iostream>\n\nusing namespace std;\n\nint main()\n{\n   cout << \"Hello World\\n\" << endl;\n   return 0;\n}\n",
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
    codeMirror.setOption("readOnly", true);
}

/**
 * Tells the browser to send a POST every time the user make a change in the text editor.
 */
function registerChangeListener() {
    codeMirror.on("change", function(editor, change) {
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
    counter = 0;

    // Check if the browser supports Server-Sent Events
    if (typeof(EventSource) !== "undefined") {
        // Create a new Event Source to receive updates from /Update
        var source = new EventSource("Update?actionNum=" + counter);
        source.addEventListener('message', messageHandler, false);
    } else {
        $("#editor").empty();
        $("#editor").append("<h1>Your browser is not supported!</h1>");
    }
}

function messageHandler(event) {
    // Get the changes from the request.
    var change = JSON.parse(event.data);

    if (change.length === undefined) {
        codeMirror.replaceRange(change.text, change.from,
            change.to, change.origin);
        counter++;
    } else {
        // Loop through all the change objects (usually one, but sometimes there's two).
        for (var i = 0; i < change.length; i++) {
            codeMirror.replaceRange(change[i].text, change[i].from,
                change[i].to, change[i].origin);
            counter++;
        }
    }
    event.target.close();
    var source = new EventSource("Update?actionNum=" + counter);
    source.addEventListener('message', messageHandler, false);


    source.url = event.target.URL = event.target.url = source.url.substr(0, source.url.length-1) + counter;
    console.log(source.url);
};


