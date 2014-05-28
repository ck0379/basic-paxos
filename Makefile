make:
	javac -sourcepath src src/*/*.java -d bin
run:
	java -classpath bin runtime.Main 0 h &
	java -classpath bin runtime.Main 1 e &
	java -classpath bin runtime.Main 2 l &
	java -classpath bin runtime.Main 3 l &
	java -classpath bin runtime.Main 4 o &
kill:
	pkill -f 'runtime.Main'
clean:
	rm -rf bin/*
