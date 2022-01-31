const express = require('express')
const app = express() //initalize express app
const mysql = require('mysql2')
const cors = require('cors')

const bodyParser = require('body-parser')

require('dotenv').config() //load the .env file
app.use(bodyParser.json())
app.use(cors()) //Allows the backend to accept connections from any clients

/*
 * Initialise la connexion à la base de données selon les valeurs fournies dans le fichier .env (voir .env.defaults)
 */
let connection

function createConnection() {
    connection = mysql.createConnection({
        host: process.env.MYSQL_HOST,
        port: process.env.MYSQL_PORT,
        database: process.env.MYSQL_DB_NAME,
        user: process.env.MYSQL_USER,
        password: process.env.MYSQL_PASSWORD
    })
}

function getCurrentDateTime() {
    return new Date().toISOString().slice(0, 19).replace('T', ' ')
}

function getFiveLastMinutesDateTime() {
    var endDate = new Date()
    var startDate = new Date(endDate)
    startDate.setMinutes(endDate.getMinutes() - 5)
    return startDate.toISOString().slice(0, 19).replace('T', ' ')
}

app.all('*', (req, res, next) => { //A chaque accès à l'API, crée une nouvelle connexion à la base de données.
    createConnection()
    next()
})

app.post('/user', (req, res) => {
    console.log(req.body)
    /*
    * Check if the user already exists. If no, inserts a new user. If yes, update the current user
    */
   var currentTime = getCurrentDateTime()
    connection.execute('INSERT INTO user(username, last_login) VALUES (?, ?)', [req.body.username, currentTime], (err, results) => {
        if(err) {
            res.status(503).json({"error": "this username already exists"})
        }
        else {
            res.status(200).json({"insertedUser": {
                "id": results.insertId,
                "username": req.body.username,
                "last_login": currentTime
            }})
        }
    })
})

app.post('/message/:touserid', (req, res) => {
    var fromUser = req.body.userid
    var msg = req.body.msg
    connection.execute('INSERT INTO message(msg, from_user_id, to_user_id, datetime) VALUES ?', [msg, fromUser, req.params.touserid, new Date().toLocaleString()], err => {
        if(err) {
            res.status(500).json({"error": err.message})
        }
        else {
            res.status(200).json({"message": "success"})
        }
    })
})

app.get('/messages/:firstuser/:seconduser', (req, res) => {
    var firstUser = parseInt(req.params.firstuser)
    var secondUser = parseInt(req.params.seconduser)
    connection.query('SELECT m.*, f.id as from_id, f.username as from_username, f.last_login as from_last_login, t.id as to_id, t.username as to_username, t.last_login as to_last_login ' + 
    'FROM message m INNER JOIN `user` f ON f.id = m.from_user_id INNER JOIN `user` t ON t.id = m.to_user_id ' +
    'WHERE (m.from_user_id = ? OR m.to_user_id = ?) AND (m.from_user_id = ? OR m.to_user_id = ?) ORDER BY datetime DESC LIMIT 20', 
        [firstUser, firstUser, secondUser, secondUser], (err, results) => {
            console.log(err)
            res.status(200).json(results)
        })
})

app.get('/users/online', (req, res) => {
    connection.query('SELECT * FROM user WHERE last_login <= ?', getFiveLastMinutesDateTime(), (err, results) => {
        res.status(200).json(results)
    })
})

app.listen(8081) //Start HTTP server on 8081 port