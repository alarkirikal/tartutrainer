#!/usr/bin/python
# -*- coding: utf-8 -*-

import os
import sys
import sqlite3
path = "/".join(os.getcwd().split('/')[:-2])
if path not in sys.path:
    sys.path.append(path)

def create(cursor):
    try:
        cursor.execute("SELECT * FROM exercises;")
        print "Table \"exercises\" already exists"
    except sqlite3.OperationalError:
        cursor.execute("""
            CREATE TABLE
                exercises (
                    id unique
                    ,name TEXT
                    ,description TEXT
                    ,level INTEGER
                    ,modality INTEGER
                    ,muscles TEXT
                    ,equipment TEXT
                    ,label_1 INTEGER
                    ,label_2 INTEGER
                    ,owned TEXT
                    ,category INTEGER
                );
        """)
        print "Table \"exercises\" made"

    try:
        cursor.execute("SELECT * FROM programs;")
        print "Table \"programs\" already exists"
    except sqlite3.OperationalError:
        cursor.execute("""
            CREATE TABLE
                programs (
                    id unique
                    ,name TEXT
                    ,date TEXT
                    ,author TEXT
                    ,author_email TEXT
                    ,client TEXT
                    ,client_email TEXT
                    ,notes TEXT
                    ,items TEXT
                );
        """)
        print "Table \"programs\" made"


def drop_table(cursor, table):
    try:
        cursor.execute("DROP TABLE %s;" % table)
        print "Table \"%s\" deleted!" % table
    except:
        print "Could not delete \"%s\". It probably doesn't exist" % table

def rebuild_db(cursor):
    drop_table(cursor, "exercises")
    drop_table(cursor, "programs")
    create(cursor)

if __name__ == "__main__":

    if len(sys.argv) == 1:
        print """
Not enough arguments supplied! Please use:
    python db_functions.py rebuild
to clear and rebuild the database file.
            """
        
    else:
        conn = sqlite3.connect(os.getcwd() + "/tartutrainer.db")
        c = conn.cursor()
    
        if sys.argv[1] == "rebuild":
            rebuild_db(c)
            conn.commit()
            print "Tables dropped and remade!"

        if sys.argv[1] == "create":
            create(c)
            conn.commit()

        c.close()
        conn.close()
