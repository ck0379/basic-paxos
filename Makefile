make:
	javac -cp lib/gson-2.2.4.jar -sourcepath src src/*/*.java -d bin
run:
	ssh -i ~/.vagrant.d/insecure_private_key -p 2222 vagrant@localhost "cd /vagrant && java -classpath bin:lib/gson-2.2.4.jar runtime.Main 0" &
	ssh -i ~/.vagrant.d/insecure_private_key -p 2200 vagrant@localhost "cd /vagrant && java -classpath bin:lib/gson-2.2.4.jar runtime.Main 1" &
	ssh -i ~/.vagrant.d/insecure_private_key -p 2201 vagrant@localhost "cd /vagrant && java -classpath bin:lib/gson-2.2.4.jar runtime.Main 2" &
proposal:
	ssh -i ~/.vagrant.d/insecure_private_key -p 2222 vagrant@localhost "echo $(VALUE) | nc 11.11.1.101 50007" &
kill:
	ssh -i ~/.vagrant.d/insecure_private_key -p 2222 vagrant@localhost "pkill -f 'runtime.Main'"
	ssh -i ~/.vagrant.d/insecure_private_key -p 2200 vagrant@localhost "pkill -f 'runtime.Main'"
	ssh -i ~/.vagrant.d/insecure_private_key -p 2201 vagrant@localhost "pkill -f 'runtime.Main'"
clean:
	rm -rf bin/*
