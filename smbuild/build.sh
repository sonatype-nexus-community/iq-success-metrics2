#!/bin/bash

version=$1

if [[ -z "${version}" ]];
then
  echo "error: need version number"
	exit -1
fi

cd ..

rm -v ./quickstart.zip
rm -rf ./quickstart

mkdir quickstart
cp -r ./smbuild/quickstart/* ./quickstart

mvn clean package spring-boot:repackage

cp -v target/success-metrics-${version}.jar ./quickstart

echo "java -jar success-metrics-${version}.jar" > ./quickstart/run-app.bat

echo "#!/bin/bash" > quickstart/run-app.sh
echo "java -jar success-metrics-${version}.jar" >> ./quickstart/run-app.sh

chmod +x ./quickstart/*.sh

zip -r -o quickstart.zip ./quickstart

rm -rf ./quickstart

exit 0




