
all:	src/VoDKATVChannels.class \
	erl/vodkatv_channels.beam

%.beam: %.erl
	erlc -I ../../include -o erl $< 
	cp $@ ../../ebin

%.class: %.java
	javac -classpath ../../java/:`echo lib/*.jar | tr ' ' ':'` $<
	cp $@ ../../ebin

clean:
	rm -f src/*.class erl/*.beam
