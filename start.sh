CLASSPATH="../lib/commons-beanutils.jar:../lib/commons-collections-3.0.jar:../lib/commons-httpclient-2.0.1.jar:../lib/commons-lang-2.0.jar:../lib/commons-logging-1.0.1.jar:../lib/flash.jar:../lib/hsqldb.jar:../lib/itext.jar:../lib/jxl.jar:../lib/log4j-1.2.8.jar:../lib/xercesImpl.jar:../lib/xmlParserAPIs.jar:../lib/barcode4j.jar:../lib/jaxen1.1.1.jar:../lib/activation.jar:../lib/mail.jar:../lib/bsh2.0.jar:../lib/topera.jar:../lib/jatools.jar"

echo
echo "Using JAVA_HOME:       $JAVA_HOME"
echo "Using REPORT_HOME:     $REPORT_HOME"
echo "Using CLASSPATH:       $CLASSPATH"
echo

"$JAVA_HOME"/bin/java -Duser.home=. -classpath "$CLASSPATH" jatools.designer.App
