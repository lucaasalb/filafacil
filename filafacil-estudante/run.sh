#!/usr/bin/env bash
# Compila e executa o FilaFacil (Linux/macOS)
set -e
cd "$(dirname "$0")"

echo "Compilando..."
rm -rf build
mkdir -p build
find src/main/java -name "*.java" > build/fontes.txt
javac --release 21 -encoding UTF-8 -d build @build/fontes.txt

# copia os arquivos da interface para o classpath
cp -r src/main/resources/* build/

echo "Iniciando o servidor em http://localhost:8080"
java -cp build br.ufma.filafacil.Main
