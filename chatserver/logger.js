/******************************************************
 * 
 * @description Configures the default winston logger settings
 * @author mmihic <milosmihic9@gmail.com>
 * 
 *****************************************************/

const winston = require('winston');

const myCustomLevels = {
    levels: {
        profanity: 0,
        chat: 1,
        error: 2,
        warn: 3,
        info: 4,
        debug: 5
    }
};

const transports = {
    console: new winston.transports.Console({
        level: 'chat'
    }),
    fileErrors: new winston.transports.File({
        filename: 'app-error.log',
        level: 'error'
    }),
    fileDebug: new winston.transports.File({
        filename: 'app-debug.log',
        level: 'debug'
    }),
    fileProfanity: new winston.transports.File({
        filename: 'app-profanity.log',
        level: 'profanity'
    }),
    fileChat: new winston.transports.File({
        filename: 'app-chat.log',
        level: 'chat'
    }),
};

const logger = winston.createLogger({
    transports: Object.values(transports),
    levels: myCustomLevels.levels
});


module.exports = logger;