/******************************************
 * 
 * @description Socket settings
 * @author mmihic <milosmihic9@gmail.com>
 ******************************************/

const logger = require('../logger');
const profanityFilter = require('./profanity-filter');
var io;

module.exports = function (server) {
    io = require('socket.io')(server);
    io.on('connection', handleClientConnection);
};

/**
 * @desc Function handle with client connection and client messages
 * @param {Object} socket 
 */
function handleClientConnection(socket) {
    logger.chat(`new User ${socket.id} - connected `);

    socket.on('disconnect', function () {
        logger.chat(`User ${socket.id} - disconnected `);
    });

    socket.on('clientChat', handleUserMessage);

    /**
     * @desc Function handle with user messages, 
     *       checks for profanity and pass the message to everyone if does not contain any profanity
     * @param {String, Object} msgObj 
     */
    function handleUserMessage(msgObj) {
        if (typeof msgObj === "string") {
            msgObj = JSON.parse(msgObj);
        }

        if (!msgObj.msg) {
            return;
        }
        if (profanityFilter.containsProfanity(msgObj.msg)) {
            // log profanity use
            logger.profanity(`User ${socket.id} - attempted to send a message containg profanity: ${msgObj.msg}`);
            io.sockets.connected[socket.id].emit("serverMessages", {
                msg: "Please take care about profanity."
            });
            return;
        }
        // pass the message to everyone, since it does not contain any profanity
        io.emit("clientChat", msgObj);
        // log the chat msg
        logger.chat(`User ${socket.id} - ${msgObj.msg}`);
    }

}