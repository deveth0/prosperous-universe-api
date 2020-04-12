# React Frontend for the PrUn API

## Installation

```sh 
npm install
``` 

## Run

### dev
During development, you can use the `dev` task to transpile and bundle your code. Furthermore a `webpack-dev-server` on port `8616` is started. You can then access `http://localhost:8616` and see the app.

```sh 
npm run dev
```

### prod
To create a production bundle, the `prod` task is used. It will transpile and output all your code via `webpack` and write them to the `target` folder.

```sh 
npm run prod
```

## ESLint 

This project also contains a `eslint` configuration you can use. To manually run `eslint` on a file, use the following command:

```sh
/node_modules/.bin/eslint src/js/app.tsx
```

By default `eslint` does not support TypeScript, therefor we utilize `@typescript-eslint/eslint-plugin` and `@typescript-eslint/parser`.