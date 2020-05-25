// server.js
var express = require('express');
var path = require('path');
//var serveStatic = require('serve-static');
var history = require('connect-history-api-fallback');
app = express();

const middleware = express.static(path.join(__dirname, 'dist'))

app.use(middleware);
app.use(history({
  disableDotRule: true,
  verbose: true
}));
app.use(middleware);

var port = process.env.PORT || 5000;
app.listen(port);

console.log('server started '+ port);