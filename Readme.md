# Sasha - Stateless UI Components

A library of stateless UI components built with
[dumdom](https://github.com/cjohansen/dumdom). Use them with ClojureScript to
build an SPA, or generate static HTML from them with Clojure.

This library is in its infancy, and is not intended for widespread use. The
components were originally written for a [ClojureScript
talk](https://www.youtube.com/watch?v=yFVk3D76wQw), and currently the main
purpose of this library is to demonstrate the use of
[Portfolio](https://github.com/cjohansen/portfolio).

## Run with figwheel-main

You can run a figwheel-main REPL with:

```sh
clojure -A:dev -m figwheel.main -b dev -r
```

Or with `cider-jack-in-cljs` in Emacs.

## Run with shadow-cljs

To run this project with shadow:

```sh
npx shadow-cljs watch portfolio
```
