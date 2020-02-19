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
 * Eliminado de datos de la calidad del aire.
 * Eliminado un dato de la calidad del aire guardado en la base de datos.
 *
 * idAirQuality Integer Id de la calidad del aire
 * returns String
 **/
module.exports.deleteAirQuality = function (req, res, next) {
    //Parameters
    console.log("Mostramos peticion", req);
    var query = "DELETE FROM airquality WHERE id = " + req.idAirQuality.originalValue
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Devuelve todos los datos relacionados con la calidad del aire.
 * Devuelve todos los datos relacionados con la calidad del aire.
 *
 * date String Fecha de la recogida de la información
 * returns String
 **/
module.exports.getAirQuality = function (req, res, next) {
    //Parameters
    console.log(req.date.originalValue);
    var query = "SELECT * FROM AirQuality WHERE date = '" + req.date.originalValue + "'"
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de calidad del aire.
 * Creacion de nuevos datos asociados a la calidad del aire.
 *
 * airQuality AirQuality 
 * returns String
 **/
module.exports.postAirQuality = function (req, res, next) {
    //Parameters

    /**
     * MySQL
     */

    console.log(req.undefined.originalValue.amount);
    var query = 'INSERT INTO AirQuality SET ?'
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
        console.log('Succesfull connected to MQTT');

        let options = {
            retain: true,
            qos: 1
        };
        if (client.connected == true) {
            client.publish('airquality', req.undefined.originalValue.amount.toString(), options);
        }
    });

    client.on('error', function (error) {
        console.log('Error, cannot connect to MQTT ' + error);
    });
};


/**
 * Modifica un dato de calidad del aire previamente registrado
 * Modifica un dato de calidad del aire previamente registrado
 *
 * airQuality AirQuality 
 * returns String
 **/
module.exports.putAirQuality = function (req, res, next) {
    //Parameters
    console.log(req);
    var query = 'UPDATE AirQuality SET ? WHERE id = ' + req.undefined.originalValue.idAirQuality
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




