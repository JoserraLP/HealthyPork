'use strict';

var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'healthypork'
});
connection.connect();

/**
 * Eliminado de datos de temperatura.
 * Eliminado un dato de temperatura en la base de datos.
 *
 * idTemperature Integer Id del dato de temperatura
 * returns String
 **/
module.exports.deleteTemperature = function(req, res, next) {
    //Parameters
    console.log("Mostramos peticion", req);
    var query = "DELETE FROM Temperature WHERE id = " + req.idTemperature.originalValue
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Devuelve todos los datos relacionados con la temperatura.
 * Devuelve todos los datos relacionados con la temperatura.
 *
 * date String Fecha de la recogida de la información
 * returns String
 **/
module.exports.getTemperature = function(req, res, next) {
    //Parameters
    console.log(req.date.originalValue);
    var query = "SELECT * FROM Temperature WHERE date = '" + req.date.originalValue + "'"
    connection.query(query, function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de temperatura.
 * Creacion de nuevos datos asociados a la temperatura.
 *
 * temperature Temperature 
 * returns String
 **/
module.exports.postTemperature = function(req, res, next) {
    //Parameters
    console.log(req.undefined.originalValue.amount);
    var query = 'INSERT INTO Temperature SET ?'
    var date = new Date();
    var data = {
        amount: Number((req.undefined.originalValue.amount).toFixed(2)),
        date: date
    }
    connection.query(query, [data], function (error, results, fields) {
        if (error) throw error;
        console.log('The solution is: ', results);
        res.send({
            message: results
        });
    });
};


/**
 * Modifica un dato de temperatura previamente registrado
 * Modifica un dato de temperatura previamente registrado
 *
 * temperature Temperature 
 * returns String
 **/
module.exports.putTemperature = function(req, res, next) {
    //Parameters
    console.log(req);
    var query = 'UPDATE Temperature SET ? WHERE id = ' + req.undefined.originalValue.idTemperature
    var data = {
        amount: Number((req.undefined.originalValue.amount).toFixed(2)),
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




