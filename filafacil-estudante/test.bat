@echo off
REM Compila e executa os testes (Windows)
cd /d "%~dp0"

echo Compilando testes...
if exist build-test rmdir /s /q build-test
mkdir build-test
dir /s /b src\main\java\*.java src\test\java\*.java > build-test\fontes.txt
javac --release 21 -encoding UTF-8 -d build-test @build-test\fontes.txt

echo Executando testes...
java -cp build-test br.ufma.filafacil.TesteSenhaService
