$(document).ready(function () {
    var codeMirror = CodeMirror($('#editor').get()[0], {
        value: "function myScript()\n{\n   return 100;\n}\n",
        mode: "clike",
        matchBrackets: true,
        autoCloseBrackets: true,
        lineNumbers: true
    })
    
    console.log(codeMirror);
    
    // Todo:
    // Listen for change events and send them to the server
    // Start an interval to check for new changes
    
});