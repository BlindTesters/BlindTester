const Router = require('@koa/router');

// other imports
const Koa = require('koa');
const { sum, diff } = require('../test_mymath/my_math');

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
