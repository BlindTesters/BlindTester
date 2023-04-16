const express = require('express');

const app = express();

// This is the express handler.
app.get('/', function (req, res) {
    res.send('Homepage');
    console.log('GET /');
})

app.get('/hello', function (req, res) {
    res.send('Hello');
    console.log('GET /');
})

// Start the express server.
const server = app.listen(3000, "0.0.0.0", () => {
    const host = server.address().address;
    const port = server.address().port;
    console.log(`Listening at http://${host}:${port}`);
});
