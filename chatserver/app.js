/******************************************************
 * 
 * @description Configures the application server
 * @author mmihic <milosmihic9@gmail.com>
 * 
 *****************************************************/
// Imported modules
var express = require('express');
var http = require('http');
var lib = require('./lib/profanity-filter.js');

var app = express();
var server = http.Server(app);
var chatServer = require('./lib/chat-server')(server);

// require once in app main, in order to configure winston
const logger = require('./logger.js');

app.use(express.static('../webclient'));

server.listen(3000, '0.0.0.0', function () {
    logger.debug("Express listeningon *:3000");
});


// catch the uncaught errors that weren't wrapped in a domain or try catch statement
// do not use this in modules, but only in applications, as otherwise we could have multiple of these bound
process.on('uncaughtException', function (err) {
    // handle the error safely
    logger.error('uncaughtException', err);
});

module.exports = app;