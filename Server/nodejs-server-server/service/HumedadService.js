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
 * MQTT
 */
client.on('connect', function(){
    console.log('Succesfull connected to MQTT');
});

client.on('error', function(error){
    console.log('Error, cannot connect to MQTT ' + error);
});




/**
 * MySQL
 */

/**
 * Eliminado de datos de humedad.
 * Eliminado un dato de humedad en la base de datos.
 *
 * idHumidity Integer Id del dato de humedad
 * returns String
 **/
module.exports.deleteHumidity = function(req, res, next) {
    //Parameters
    console.log("Mostramos peticion", req);
    var query = "DELETE FROM Humidity WHERE id = " + req.idHumidity.originalValue
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Devuelve todos los datos relacionados con la humedad.
 * Devuelve todos los datos relacionados con la humedad.
 *
 * date String Fecha de la recogida de la información
 * returns String
 **/
module.exports.getHumidity = function(req, res, next) {
    //Parameters
    console.log(req.date.originalValue);
    var query = "SELECT * FROM Humidity WHERE date = '" + req.date.originalValue + "'"
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de humedad.
 * Creacion de nuevos datos asociados a la humedad.
 *
 * humidity Humidity 
 * returns String
 **/
module.exports.postHumidity = function(req, res, next) {
    //Parameters
    console.log(req.undefined.originalValue.amount);
    var query = 'INSERT INTO Humidity SET ?'
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
    let options={
        retain:true,
        qos:1};
    if (client.connected == true){
        /*
        client.on('message', function (topic, message) {
            // message is Buffer
            console.log(message.toString())
        })
        client.subscribe('humidity', function (err) {
            if (!err) {
        */
              client.publish('humidity', req.undefined.originalValue.amount.toString(), options);
            //}
        //})
    }
};


/**
 * Modifica un dato de humedad previamente registrado
 * Modifica un dato de humedad previamente registrado
 *
 * humidity Humidity 
 * returns String
 **/
module.exports.putHumidity = function(req, res, next) {
    //Parameters
    var query = 'UPDATE Humidity SET ? WHERE id = ' + req.undefined.originalValue.idHumidity
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




