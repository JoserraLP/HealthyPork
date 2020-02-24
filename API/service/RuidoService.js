'use strict';

var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'healthypork'
});
connection.connect();

var options = {
    clientId: 'mqtthp'
}

var mqtt = require('mqtt');
var client = mqtt.connect('mqtt://localhost:1883', options);

/**
 * Eliminado de datos de ruido.
 * Eliminado un dato de ruido en la base de datos.
 *
 * idNoise Integer Id del dato de ruido
 * returns String
 **/
module.exports.deleteNoise = function(req, res, next) {
    //Parameters
    console.log("Mostramos peticion", req);
    var query = "DELETE FROM Noise WHERE id = " + req.idNoise.originalValue
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Devuelve todos los datos relacionados con el ruido.
 * Devuelve todos los datos relacionados con el ruido.
 *
 * date String Fecha de la recogida de la información
 * returns String
 **/
module.exports.getNoise = function(req, res, next) {
    //Parameters
    console.log(req.date.originalValue);
    var query = "SELECT * FROM Noise WHERE date = '" + req.date.originalValue + "'"
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de ruido.
 * Creacion de nuevos datos asociados al ruido.
 *
 * noise Noise 
 * returns String
 **/
module.exports.postNoise = function(req, res, next) {
    //Parameters

    /**
     * MySQL
     */

    console.log(req.undefined.originalValue.amount);
    var query = 'INSERT INTO Noise SET ?'
    var date = new Date();
    
    var data = {
        amount: req.undefined.originalValue.amount,
        date: date
    }
    connection.query(query, [data], function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
    
    /**
    * MQTT
    */

   client.on('connect', function () {

    let options = {
        retain: true,
        qos: 1
    };
    if (client.connected == true) {
        client.publish('noise', req.undefined.originalValue.amount.toString(), options);
    }
});

client.on('error', function (error) {
    console.log('Error, cannot connect to MQTT ' + error);
});

};


/**
 * Modifica un dato de ruido previamente registrado
 * Modifica un dato de ruido previamente registrado
 *
 * noise Noise 
 * returns String
 **/
module.exports.putNoise = function(req, res, next) {
    //Parameters
    console.log(req);
    var query = 'UPDATE Noise SET ? WHERE id = ' + req.undefined.originalValue.idNoise
    var data = {
        amount: req.undefined.originalValue.amount,
        date: req.undefined.originalValue.date
    }
    connection.query(query, [data], function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};




