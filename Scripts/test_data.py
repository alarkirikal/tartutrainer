from models import Exercise, Program
print "Starting the import..\n"


# Creating the test data for EXERCISES table
def getExerciseIds():
    return ("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")

def getExerciseNames():
    return ("Crunch", "Resisted Crunch", "Side plank", "High Leg Pull-In", 
        "Row", "Kneeling Row", "Back Fly", "Pull-up", "Lateral Pulldown",
        "Ghost Press", "Pull Over With Squat", "Leg Curl", "Front Lunge",
        "Wide Squat", "Hip Abduction")

def getExerciseDescriptions():
    return ("This is my crunch description", "this is a desc for resisted crunch", 
    "side plant desc", "desc for high leg pull-in", "i am a description!",
    "this is kneeling rowwww", "back fly descriptiooooon", "pull-up deeeesc",
    "lateral pulldown description", "pc master race ghost press", "squat and pull over!",
    "leg curl, curl my leg", "this is a front lunge!!!", "wide squat description",
    "hip abduction descccccc")

def getExerciseLevels():
    return (1, 1, 1, 1, 1, 2, 2, 3, 2, 3, 2, 1, 2, 3, 3)

def getExerciseModalities():
    return (1, 2, 3, 11, 3, 4, 5, 6, 3, 8, 8, 9, 7, 2, 10)

def getExerciseMuscles():
    return ("1;10;5", "1;2;3", "4;5;6", "7;6;4", "9;8;7", "1;5;7", "7;3;2", "1;6;3",
    "3;2;1;5", "9;10;11", "12;11;10", "1;5;8", "3;1", "4;6;3", "9;7;2")

def getExerciseEquip():
    return ("BenchOne", "BenchTwo", "BenchThree", "BenchFour", "BenchFive", 
    "BenchSix", "BenchSeven", "BenchEight", "BenchNine", "BenchTENTENTENTEN",
    "Bencheleveennn", "BenchTwelve", "Bench", "Bench", "Bench")

def getExerciseLabelOne():
    return (1, 4, 2, 3, 4, 5, 1, 2, 4, 2, 3, 1, 2, 3, 5)

def getExerciseLabelTwo():
    return (3, 2, 4, 3, 1, 5, 2, 4, 3, 2, 1, 4, 2, 1, 2)

def getExerciseOwned():
    return ("true", "true", "true", "false", "false", "true", "true", "false",
    "true", "true", "true", "true", "true", "true", "true")

def getExerciseCategory():
    return (2, 3, 1, 2, 5, 4, 3, 2, 1, 5, 6, 3, 4, 2, 1)

# Creating the test data for PROGRAMS table
def getProgramIds():
	return ("1", "2", "3")

def getProgramNames():
	return ("Alari esimene", "Alari teine", "Helina esimene")

def getProgramDates():
	return ("01-01-2013", "01-02-2013", "01-03-2013")

def getProgramAuthors():
	return ("Alar Kirikal", "Alar Kirikal", "Helina Ziugand")

def getProgramAuthorEmails():
	return ("alar.kirikal@gmail.com", "alar.kirikal@gmail.com", "helina.zuigand@gmail.com")

def getProgramClients():
	return ("Sirle Sagur", "Este-Liin Margens", "Este-Liin Margens")

def getProgramClientEmails():
	return ("sirle.sagur@gmail.com", "este.margens@gmail.com", "este.margens@gmail.com")

def getProgramsNotes():
	return ("These are the first program notes for 'alari esimene' program. you should do this and that",
			"This is a program notes element for 'alari teine' progoram, blalbalblalblablallbalbaba",
			"Third program in the database, made by helina ziugand")

def getProgramItems():
	return ("1;this is an exercise note;10;10;;;20;20;;;30;30;;;40;40;;;50;50;;:" + 
                "2;this is a second exercise note;11;11;;;22;22;;;33;33;;;44;44;;;55;55;;"
                
                ,"3;another exercise note;11;11;;;21;21;;;31;31;;;41;41;;;51;51;;:" + 
                "4;yet another exc note;12;12;;;22;22;;;32;32;;;42;42;;;52;52;;"

                ,"5;third program note one;60;60;;;70;70;;;80;80;;;90;90;;;100;100;;:" + 
                "6;third program note two;60;60;;;70;70;;;80;80;;;90;90;;;100;100;;:" + 
                "7;third program note three;12;34;;;56;78;;;90;12;;;34;56;;;78;91;;"
                )

def getProgramOwned():
	return ("true", "true", "true")

###########
#   RUN   #
###########

# Exercise arrays
exc_id_array = getExerciseIds()
exc_name_array = getExerciseNames()
exc_desc_array = getExerciseDescriptions()
exc_level_array = getExerciseLevels()
exc_modality_array = getExerciseModalities()
exc_muscle_array = getExerciseMuscles()
exc_equip_array = getExerciseEquip()
exc_labelone_array = getExerciseLabelOne()
exc_labeltwo_array = getExerciseLabelTwo()
exc_owned_array = getExerciseOwned()
exc_category_array = getExerciseCategory()

exercises_added = 0
not_added = 0
for x in range(0, 15):
    exercise = Exercise()
    exercise.id = exc_id_array[x]
    exercise.name = exc_name_array[x]
    exercise.description = exc_desc_array[x]
    exercise.level = exc_level_array[x]
    exercise.modality = exc_modality_array[x]
    exercise.muscles = exc_muscle_array[x]
    exercise.equipment = exc_equip_array[x]
    exercise.label_1 = exc_labelone_array[x]
    exercise.label_2 = exc_labeltwo_array[x]
    exercise.owned = exc_owned_array[x]
    exercise.category = exc_category_array[x]

    #exercise.printExercise() 
    status = exercise.addToDb()
    if status == "not_added":
        not_added += 1
    else:
        exercises_added += 1
		
		
print "------------------------------\n"
print "Exercises not added : " + str(not_added)
print "Exercises added     : " + str(exercises_added)

# Program arrays
pgr_id_array = getProgramIds()
pgr_name_array = getProgramNames()
pgr_date_array = getProgramDates()
pgr_author_array = getProgramAuthors()
pgr_authoremail_array = getProgramAuthorEmails()
pgr_client_array = getProgramClients()
pgr_clientemail_array = getProgramClientEmails()
pgr_notes_array = getProgramsNotes()
pgr_items_array = getProgramItems()
pgr_owned_array = getProgramOwned()

programs_added = 0
not_added = 0
for y in range(0, 3):
    program = Program()
    program.id = pgr_id_array[y]
    program.name = pgr_name_array[y]
    program.date = pgr_date_array[y]
    program.author = pgr_author_array[y]
    program.author_email = pgr_authoremail_array[y]
    program.client = pgr_client_array[y]
    program.client_email = pgr_clientemail_array[y]
    program.notes = pgr_notes_array[y]
    program.items = pgr_items_array[y]
    program.owned = pgr_owned_array[y]

    #program.printProgram()
    status = program.addToDb()
    if status == "not_added":
        not_added += 1
    else:
        programs_added += 1

print "------------------------------\n"
print "Programs not added   : " + str(not_added)
print "Programs added       : " + str(programs_added)
