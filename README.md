# MRZ

Machine-Readable Zone (MRZ, see http://en.wikipedia.org/wiki/Machine-readable_passport ) parser for Java, as defined by ICAO: http://www.icao.int/

## Simple usage:

* run `java -jar mrz-java-VERSION-jar-with-dependencies.jar "IDFRAPETE<<<<<<<<<<<<<<<<<<<<<952042\n0509952018746NICOLAS<<PAUL<8206152M3"`

* Output: {"documentCode": "ID","issuingState": "FRA","documentNumber": "050995201874","lastName": "PETE","firstName": "NICOLAS, PAUL","birthDate": "820615","sex": "MALE","expirationDate": "null","nationality": "FRA"}

* Date Format: yymmdd

## Build and run demo

If you wish to build the project you will need [Apache Maven](https://maven.apache.org/) installed. Minimum requirements are `Maven 3.3.9` and `Java 8`.

Follow these commands to fetch the source, build and run:

* git clone https://github.com/bordertech/mrz-java.git (first time only)
* cd mrz-java
* mvn install

Run standalone jar:

* cd target
* java -jar mrz-java-VERSION-jar-with-dependencies.jar

Run in IDE:

* Run main class `com.innovatrics.mrz.Demo`

## Branches

Branch "master" is for hotfixes only. For enhancements and minor bugfixes, please use branch "development".

## License
  Java parser for the MRZ records, as specified by the ICAO organization.
  Copyright (C) 2011 Innovatrics s.r.o.

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public
  License along with this library; if not, write to the Free Software
  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Original maintainer (till 2017-05-28): Martin Vysny
