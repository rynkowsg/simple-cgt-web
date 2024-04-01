.PHONY: run-lein uberjar run-uberjar native run-native

# No compilation
# Having a Leiningen or deps.edn project, you can run the main without compilation

run-lein:
	lein run

run-clj:
	clj -M -m simplecgt.web.server.main

# Uberjar
# Another option is to compile the project to an uberjar and run it

uberjar:
	lein with-profiles "+jvm" classpath | tr ':' '\n' | sort
	@echo
	lein with-profiles "+jvm" deps :tree
	@echo
	lein with-profiles "+jvm" do clean, uberjar

run-uberjar:
	java -jar target/example-standalone.jar

# GraalVM Native Image
# Yet another, is to build a native image with GraalVM and run binary it produces

native:
	lein with-profiles "+native" classpath | tr ':' '\n' | sort
	@echo
	lein with-profiles "+native" deps :tree
	@echo
	lein with-profiles "+native" do clean, uberjar
	./scripts/compile-native.bash target/simplecgt-web-standalone.jar target/simplecgt-web-standalone

run-native:
	./target/example-standalone
