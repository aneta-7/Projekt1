#!/bin/bash

java -cp \hsql\hsqldb-2.3.3\hsqldb-2.3.3\hsqldb\lib\hsqldb.jar org.hsqldb.server.Server --database.0 mem:mydb --dbname.0 workdb