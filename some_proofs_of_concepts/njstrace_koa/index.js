const Koa = require('koa');
const Router = require('@koa/router');
const { sum, diff } = require('./functions');

const app = new Koa();
const router = new Router();

// Create an injector and specify the object we want to profile as well as the function to trace.
// In this case, we want to trace the router.get function.
const Injector = require('../injector/injector.js');
const injector = new Injector(router, 'get', __filename, 'SSE23-koa');

router.get('', (ctx, next) => {
    ctx.body = 'Hello world!';
  })
  .get('/sum', (ctx, next) => {
    const a = parseInt(ctx.request.query.a);
    const b = parseInt(ctx.request.query.b);
    const c = parseInt(ctx.request.query.c);
    ctx.body = sum(a, b, c);
  })
  .get('/diff', (ctx, next) => {
    const a = parseInt(ctx.request.query.a);
    const b = parseInt(ctx.request.query.b);
    const c = parseInt(ctx.request.query.c);
    ctx.body = diff(a, b, c);
  });

app
  .use(router.routes())
  .use(router.allowedMethods());

app.listen(3000);
