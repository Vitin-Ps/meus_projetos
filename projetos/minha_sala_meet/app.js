const express = require("express");
const http = require("http");

const PORT = process.env.PORT || 3001;

const app = express();
const server = http.createServer(app);
const io = require("socket.io")(server);

app.use(express.static("public"));

app.get("/", (req, res) => {
  res.sendFile(__dirname + "public/index.html");
});

server.listen(PORT, () => {
  console.log(
    `Server iniciado na porta ${PORT}\nPara acessar a aplicação, clique em http://localhost:${PORT}`,
  );
});
