#!/usr/bin/env bash

readonly jar_file="car.jar"
readonly manifest_file="Manifest.txt"

rm $jar_file
rm $(find | grep .class)

javac $(find | grep .java)
jar cfm $jar_file $manifest_file *