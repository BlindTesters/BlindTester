// Inject a wrapper around the function in the class we want to inspect.
const Injector = require('../injector/injector.js');
const lib_name = '@koa/router';
const Router = new Injector(require(lib_name), lib_name, 'get', __filename, 'SSE23-koa').get_library();

const Koa = require('koa');

const { sum, diff } = require('./functions');

const app = new Koa();
const router = new Router();

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
