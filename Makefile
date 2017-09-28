JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		BinaryHeap.java \
		decoder.java \
		encoder.java \
		FourCacheHeap.java \
		HuffManEntry.java \
		PairingEntry.java \
		PairingHeap.java \
		PriorityQueueAnalysis.java
		
default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class