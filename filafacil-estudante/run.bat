@echo off
REM Compila e executa o FilaFacil (Windows)
cd /d "%~dp0"

echo Compilando...
if exist build rmdir /s /q build
mkdir build
dir /s /b src\main\java\*.java > build\fontes.txt
javac --release 21 -encoding UTF-8 -d build @build\fontes.txt

xcopy /s /e /y src\main\resources\* build\ >nul

echo Iniciando o servidor em http://localhost:8080
java -cp build br.ufma.filafacil.Main
