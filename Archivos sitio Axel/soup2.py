# coding=utf-8
import string
import random
tam = 15
grid = [['_' for _ in range(0,15)]for _ in range(0,15)]
orie = ['leftright','updown','diagonalup','diagonaldown']
def imprimesopa():
    for x in range(0, 15):
        print('\t'*5+' '.join(grid[x]))
def generategrid(words):
    for word in words:
        word_length = len(word)
        placed = False
        while not placed:
            orientation = random.choice(orientations)
            #Sets orientation given by a random number
            if orientation == 'leftright':
                step_x=1
                step_y=0
            if orientation == 'updown':
                step_x = 0
                step_y = 1
            if orientation == 'diagonalup':
                step_x = 1
                step_y = 1
            if orientation == 'diagonaldown':
                step_x = 1
                step_y = -1

            #We generate a random starting point, then we calculate the ending point and if it exceeds the limit, we calculate the number again
            x_position = random.randrange(grid_size)
            y_position = random.randrange(grid_size)

            ending_x = x_position + word_length*step_x
            ending_y = y_position + word_length*step_y

            if ending_x < 0 or ending_x >= grid_size: continue
            if ending_y < 0 or ending_y >= grid_size: continue
            
            failed = False
            
            #we set the word on the previously given position
            for i in range(word_length):
                character = word[i]

                new_position_x = x_position + i*step_x
                new_position_y = y_position + i*step_y
                
                character_at_new_position = grid[new_position_x][new_position_y]
                #if there is some character that could be used to form the word
                if character_at_new_position != '_':
                    if character_at_new_position == character:
                        continue
                    else:
                        failed = True
                        break
            if failed:
            #We do the process from above again until we can put the word on the grid 
                continue
            else:
                #Everything worked perfectly and the word was placed without problems
                for i in range(word_length):
                    character = word[i]

                    new_position_x = x_position + i*step_x
                    new_position_y = y_position + i*step_y
                    
                    grid[new_position_x][new_position_y] = character
                
                    placed = True
frutas = ["UVA", "PERA", "MANZANA", "NARANJA", "DURAZNO", "BANANA", "KIWI", "TUNA", "MORA", "MANGO", "SANDIA", "COCO", "CEREZA", "LIMON"]
test = ["LEMON","GRAPE","HOLA","VASO"]
categorias = ["Animales", "Frutas", "Autos", "Paises"]
dificultades = ["Facil", "Intermedia", "Dificil"]

"""print("SOPA DE LETRAS")
for z in range(0, 3):
    print(str(z + 1) + " )" + dificultades[z])
xString = input('Seleccione una opción\n')
x = int(xString)
while x > 3 or x < 1:
    print("Entrada invalida")
    xString = input('Seleccione una opción\n')
    x = int(xString)
if x == 1:
    for z in range(0, 4):
        print(str(z) + " )" + categorias[z])
    yString = input('Seleccione su categoria\n')
    y = int(yString)
    while y > 3 or y < 0:
        print("Entrada invalida")
        yString = input('Seleccione su categoria\n')
        y = int(xString)
else:
    if x == 2:
        for z in range(0, 4):
            print(str(z) + " )" + categorias[z])
        yString = input('Seleccione su categoria\n')
        y = int(yString)
        while y > 3 or y < 0:
            print("Entrada invalida")
            yString = input('Seleccione su categoria\n')
            y = int(xString)
    else:
        for z in range(0, 4):
            print(str(z) + " )" + categorias[z])
        yString = input('Seleccione su categoria\n')
        y = int(xString)
        while y > 3 or y < 0:
            print("Entrada invalida")
            yString = input('Seleccione su categoria\n')
            y = int(xString)
print("Seleccionaste la sopa de " + categorias[y])
print("Generando sopa en dificultad " + dificultades[x])"""

generador(categorias)
imprimesopa()