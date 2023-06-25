<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página inicial</title>
    <link rel="stylesheet" href="static/css/main.css">
</head>
<body>
    <div id="main">
        <form action="/myBank/cadUsuario" method="post">
            <table>
                <tr>
                    <td><label for="nome">Nome:</label></td>
                    <td><input type="text" name="nome" id=""></td>
                </tr>
                <tr>
                    <td><label for="numero">Número:</label></td>
                    <td><input type="text" name="numero" id=""></td>
                </tr>
                <tr><td colspan="2"><button type="submit">Enviar</button></td></tr>
            </table>
        </form>
    </div>
</body>
</html>