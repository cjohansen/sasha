target:
	mkdir -p target

target/js/compiled/sasha.js: target
	clojure -A:dev -M -m figwheel.main -bo prod

target/index.html: target/js/compiled/sasha.js dev-resources/public/index.html resources/public/css/*.css
	clojure -A:dev:build -M -m sasha.build
