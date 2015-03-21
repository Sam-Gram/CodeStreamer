sam = "editor.js loaded";



$(document).ready(function () {
    var codeMirror = CodeMirror($('#editor').get()[0], {
        value: "function myScript()\n{\n   return 100;\n}\n",
        mode: "javascript",
        lineNumbers: true
    });
    debugger;
    console.log(codeMirror);
});