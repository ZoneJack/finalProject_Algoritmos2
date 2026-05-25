KOTLIN_FILES:=$(wildcard *.kt)
KOTLIN_COMPILER:=kotlinc

CLASSPATH:=.:lib/jlayer-1.0.1.jar:lib/pausablePlayer.jar

KOTLIN_CLASSES:=$(patsubst %.kt,%Kt.class,$(KOTLIN_FILES))

LIST:=

classes: $(KOTLIN_CLASSES)
	if [ ! -z "$(LIST)" ] ; then \
		$(KOTLIN_COMPILER) -cp $(CLASSPATH) $(LIST) ; \
	fi

$(KOTLIN_CLASSES) : %Kt.class : %.kt
	$(eval LIST+=$$<)

.PHONY: clean

clean :
	rm -rf *Kt.class *~ META-INF