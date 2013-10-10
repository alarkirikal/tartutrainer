import sqlite3
import os

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
