#!/usr/bin/env bash
# Compila e executa os testes (Linux/macOS)
set -e
cd "$(dirname "$0")"

echo "Compilando testes..."
rm -rf build-test
mkdir -p build-test
find src/main/java src/test/java -name "*.java" > build-test/fontes.txt
javac --release 21 -encoding UTF-8 -d build-test @build-test/fontes.txt

echo "Executando testes..."
java -cp build-test br.ufma.filafacil.TesteSenhaService
