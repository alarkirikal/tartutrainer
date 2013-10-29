import sqlite3
import os

class Program:
    id = str()
    name = str()
    date = str()
    author = str()
    author_email = str()
    client = str()
    client_email = str()
    notes = str()
    items = str()
    owned = str()
	
    def printProgram(self):
	print "ID	    	:" + self.id
	print "Name 		:" + self.name
	print "Date		:" + self.date
	print "Author		:" + self.author
	print "Author mail	:" + self.author_email
	print "Client		:" + self.client
	print "Client mail	:" + self.client_email
	print "Notes		:" + self.notes
	print "Items		:" + self.items
	print "Owned		:" + self.owned
		
    def addToDb(self):
	conn = sqlite3.connect(os.getcwd() + "/tartutrainer.db")
	c = conn.cursor()
			
	try:
	    c.execute("""
		INSERT INTO
               	    programs(id, name, date, author, author_email, client, client_email, notes, items, owned)
		VALUES
		    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
	    """, (self.id
		  ,self.name
		  ,self.date
	    	  ,self.author
    	    	  ,self.author_email
                  ,self.client
	      	  ,self.client_email
    	    	  ,self.notes
                  ,self.items
		  ,self.owned))

        except:
            return "not_added"
			
	c.close()
    	conn.commit()
        conn.close()
    	return None

class Exercise:
    id = str()
    name = str()
    description = str()
    level = int()
    modality = int()
    muscles = str()
    equipment = str()
    label_1 = str()
    label_2 = str()
    owned = str()
    category = int()

    def printExercise(self):
        print "ID          :" + self.id
        print "Name        :" + self.name 
        print "Description :" + self.description
        print "Level       :" + str(self.level)
        print "Modality    :" + str(self.modality)
        print "Muscle      :" + self.muscles
        print "Equipment   :" + self.equipment
        print "Label One   :" + str(self.label_1)
        print "Label Two   :" + str(self.label_2)
        print "Owned       :" + self.owned
        print "Category    :" + str(self.category) 

    def addToDb(self):
        conn = sqlite3.connect(os.getcwd() + "/tartutrainer.db")
        c = conn.cursor()

        try:
            c.execute("""
                INSERT INTO
                    exercises (id, name, description, level, modality, muscles, equipment, label_1, label_2, owned, category)
                VALUES
                    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """, (self.id
                  ,self.name
                  ,self.description
                  ,self.level
                  ,self.modality
                  ,self.muscles
                  ,self.equipment
                  ,self.label_1
                  ,self.label_2
                  ,self.owned
                  ,self.category))
        except:
            return "not_added"

        c.close()
        conn.commit()
        conn.close()
        return None
