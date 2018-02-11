echo off
for %%i in ("../lib/*.*") do call "lcp.bat" ../lib/%%i
java -Xms64m -Xmx640m  -Duser.home=. -classpath "%LOCALCLASSPATH%" jatools.designer.App 