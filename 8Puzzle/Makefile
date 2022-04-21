KC=	kotlinc
KFLAG=	-cp
GLIB=	ve/usb/libGrafo

all:	libGrafoKt.jar

libGrafoKt.jar: $(GLIB)/Lado.class\
				$(GLIB)/Arco.class\
				$(GLIB)/ArcoCosto.class\
				$(GLIB)/Grafo.class\
				$(GLIB)/GrafoDirigidoCosto.class\
				EightPuzzle.class\
				
		
	jar -cvf $@ $(GLIB)/*.class 

$(GLIB)/Lado.class: $(GLIB)/Lado.kt
	$(KC) $(GLIB)/Lado.kt

$(GLIB)/Arco.class: $(GLIB)/Arco.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Arco.kt

$(GLIB)/ArcoCosto.class: $(GLIB)/ArcoCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/ArcoCosto.kt

$(GLIB)/Grafo.class: $(GLIB)/Grafo.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/Grafo.kt

$(GLIB)/GrafoDirigidoCosto.class: $(GLIB)/GrafoDirigidoCosto.kt
	$(KC) $(KFLAG) $(GLIB): $(GLIB)/GrafoDirigidoCosto.kt

EightPuzzle.class: EightPuzzle.kt 
	$(KC) $(KFLAG) :$(GLIB) EightPuzzle.kt

#$(GLIB)/AEstrella.class: $(GLIB)/AEstrella.kt
#	$(KC) $(KFLAG) $(GLIB): $(GLIB)/AEstrella.kt
	
clean:
	rm -rf libGrafoKt.jar META-INF $(GLIB)/*.class