make:
	javac -sourcepath src src/*/*.java -d bin
run:
	java -classpath bin runtime.Main 0 &
	java -classpath bin runtime.Main 1 &
	java -classpath bin runtime.Main 2 &
#	java -classpath bin runtime.Main 3 &
#	java -classpath bin runtime.Main 4 &
kill:
	pkill -f 'runtime.Main'
leader:
	parallel --verbose --jobs 5 --xapply echo {1} '|' nc -w1 localhost {2} ::: 0 1 2 3 4 ::: 10000 20000 30000 40000 50000
clean:
	rm -rf bin/*
