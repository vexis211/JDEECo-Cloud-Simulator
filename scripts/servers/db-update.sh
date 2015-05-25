#!/bin/bash

db_structure="20150523_01-Next try.sql"
db_data="test1.sql"

mysql --user=root --password= <"~/db/${db_structure}"
mysql --user=root --password= --database=jdeeco_simulation <"~/db/${db_data}"