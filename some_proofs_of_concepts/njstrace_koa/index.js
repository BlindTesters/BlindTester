// use https://www.npmjs.com/package/njstrace to trace the execution of this nodejs app
require('./njstracer');

const Koa = require('koa');
const Router = require('@koa/router');
const { sum, diff } = require('./functions');

const app = new Koa();

var router = new Router();

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
