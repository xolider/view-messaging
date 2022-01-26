const express = require('express')
const app = express() //initalize express app
const mysql = require('mysql2')

const bodyParser = require('body-parser')

require('dotenv').config() //load the .env file
app.use(bodyParser.urlencoded({extended: true})) //Allows to parse the POST bodies

let connection = mysql.createConnection({
    host: process.env.MYSQL_HOST,
    port: process.env.MYSQL_PORT,
    database: process.env.MYSQL_DB_NAME,
    user: process.env.MYSQL_USER,
    password: process.env.MYSQL_PASSWORD
})

connection.connect(err => {
    if(err) {
        console.log('Unable to connect to the database. Shutting down')
        process.exit(1)
    }
})

app.post('/user', (req, res) => {
    connection.execute('INSERT INTO user(username) VALUES ?', req.body.username, err => {
        if(err) {
            res.status(500).json({"error": err.message})
        }
    })
    res.status(201).json({"message": "user created"})
})

app.post('/message/:touserid', (req, res) => {
    var fromUser = req.body.userid
    var msg = req.body.msg
    connection.execute('INSERT INTO message(msg, from_user_id, to_user_id, datetime) VALUES ?', [msg, fromUser, req.params.touserid, new Date().toLocaleString()], err => {
        if(err) {
            res.status(500).json({"error": err.message})
        }
    })
    res.status(200).json({"message": "success"})
})

app.get('/messages/:userid', (req, res) => {
    connection.query('SELECT * FROM message WHERE from_user_id = ? AND to_user_id = ? ORDER BY datetime DESC LIMIT 20', (err, results) => {
        if(err) {
            res.status(500).json({"error": err.message})
        }
        else {
            res.status(200).json(results)
        }
    })
})

app.listen(8081) //Start HTTP server on 8081 port

connection.end() //End connection to the database