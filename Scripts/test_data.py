from models import Exercise
print "Starting the import..\n"


def getIds():
    return ("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")

def getNames():
    return ("Crunch", "Resisted Crunch", "Side plank", "High Leg Pull-In", 
        "Row", "Kneeling Row", "Back Fly", "Pull-up", "Lateral Pulldown",
        "Ghost Press", "Pull Over With Squat", "Leg Curl", "Front Lunge",
        "Wide Squat", "Hip Abduction")

def getDescriptions():
    return ("This is my crunch description", "this is a desc for resisted crunch", 
    "side plant desc", "desc for high leg pull-in", "i am a description!",
    "this is kneeling rowwww", "back fly descriptiooooon", "pull-up deeeesc",
    "lateral pulldown description", "pc master race ghost press", "squat and pull over!",
    "leg curl, curl my leg", "this is a front lunge!!!", "wide squat description",
    "hip abduction descccccc")

def getLevels():
    return (1, 1, 1, 1, 1, 2, 2, 3, 2, 3, 2, 1, 2, 3, 3)

def getModalities():
    return (1, 2, 3, 11, 3, 4, 5, 6, 3, 8, 8, 9, 7, 2, 10)

def getMuscles():
    return ("1;10;5", "1;2;3", "4;5;6", "7;6;4", "9;8;7", "1;5;7", "7;3;2", "1;6;3",
    "3;2;1;5", "9;10;11", "12;11;10", "1;5;8", "3;1", "4;6;3", "9;7;2")

def getEquip():
    return ("BenchOne", "BenchTwo", "BenchThree", "BenchFour", "BenchFive", 
    "BenchSix", "BenchSeven", "BenchEight", "BenchNine", "BenchTENTENTENTEN",
    "Bencheleveennn", "BenchTwelve", "Bench", "Bench", "Bench")

def getLabelOne():
    return (1, 4, 2, 3, 4, 5, 1, 2, 4, 2, 3, 1, 2, 3, 5)

def getLabelTwo():
    return (3, 2, 4, 3, 1, 5, 2, 4, 3, 2, 1, 4, 2, 1, 2)

def getOwned():
    return ("true", "true", "true", "false", "false", "true", "true", "false",
    "true", "true", "true", "true", "true", "true", "true")

def getCategory():
    return (2, 3, 1, 2, 5, 4, 3, 2, 1, 5, 6, 3, 4, 2, 1)


###########
#   RUN   #
###########

id_array = getIds()
name_array = getNames()
desc_array = getDescriptions()
level_array = getLevels()
modality_array = getModalities()
muscle_array = getMuscles()
equip_array = getEquip()
labelone_array = getLabelOne()
labeltwo_array = getLabelTwo()
owned_array = getOwned()
category_array = getCategory()

exercises_added = 0
not_added = 0
for x in range(0, 15):
    exercise = Exercise()
    exercise.id = id_array[x]
    exercise.name = name_array[x]
    exercise.description = desc_array[x]
    exercise.level = level_array[x]
    exercise.modality = modality_array[x]
    exercise.muscles = muscle_array[x]
    exercise.equipment = equip_array[x]
    exercise.label_1 = labelone_array[x]
    exercise.label_2 = labeltwo_array[x]
    exercise.owned = owned_array[x]
    exercise.category = category_array[x]

    #exercise.printExercise() 
    status = exercise.addToDb()
    if status == "not_added":
        not_added += 1
    else:
        exercises_added += 1

print "------------------------------\n"
print "Exercises not added : " + str(not_added)
print "Exercises added     : " + str(exercises_added)
