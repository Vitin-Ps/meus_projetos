const app = require('express')()
const server = require('http').createServer(app)
const io = require('socket.io')(server, {cors: {origin: 'http//localhost:4200'}})

const port = 3001

server.listen(port, () => console.log("Servidor rodando...."))