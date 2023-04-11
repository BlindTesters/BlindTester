# NodeJS Execution Anylzer

Install dependencies

> npm install

Execute the koa webserver by running `index.js`.

> node index.js

You can then call the route sum with the variables a, b and c in which you can define any number and you'll get the sum result.

> localhost:3000/sum?a=2&b=6&c=6

# TODO
- Extract requires if needed
- Set a real project name in output file (currently test is hardcoded)
- Currently only the calls to functions from API endpoints are detected. Should we work on retrieving information from installed libraries such as koa? I don't think so but TBD.
