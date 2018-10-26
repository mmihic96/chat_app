/************************************************
 * 
 * @description Profanity filter
 * @author mmihic <milosmihic9@gmail.com>
 ***********************************************/

const fs = require('fs');
const path = require('path');
const words = require(path.join(__dirname, '..', 'data', 'words.json'));

/**
 * Checks if the user's message contains any profanity 
 * @param {String} message - user's chat message to be checked
 * @return {boolean} - true if any profanity is found in the user's message, false otherwise
 */
function containsProfanity(message) {
    for (let word of words) {
        if (message.indexOf(word) !== -1) {
            return true;
        }
    }
    return false;
}

module.exports.containsProfanity = containsProfanity;