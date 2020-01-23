'use strict';

const superagent = require('superagent');

var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'healthypork'
});
connection.connect();

function calculateSeason(date) {
    var month = date.getMonth()+1;
    if (month > 2 && month < 6)
        return 'Primavera';
    else if (month > 5 && month < 9)
        return 'Verano';
    else if (month > 8 && month < 12)
        return 'Otoño';
    else if (month < 3 || month > 11)
        return 'Invierno';
}

function calculateCelsius(tempk) {
    return Number(((tempk - 273,15).toFixed(2)));
}

module.exports.postWeather = function() {
    superagent
        .get('https://api.openweathermap.org/data/2.5/weather')
        .query({lat: 39.4789255, lon: -6.3423358, APPID: '0b1e5f660c2801400ed549b858907691'})
        .end((err, res) => {
            if (err) { return console.log(err);}
            var date = new Date();
            var query = 'INSERT INTO weather SET ?';
            var weather = {
                state: res.body.weather[0].main,
                temp: calculateCelsius(res.body.main.temp),
                temp_feel: calculateCelsius(res.body.main.feels_like),
                temp_min: calculateCelsius(res.body.main.temp_min),
                temp_max: calculateCelsius(res.body.main.temp_max),
                pressure: res.body.main.pressure,
                humidity: res.body.main.humidity,
                wind: res.body.wind.speed,
                date: date,
                season: calculateSeason(date)
            }
            connection.query(query, [weather], function (error, results, fields) {
                if (error) throw error;
            });
        });
};