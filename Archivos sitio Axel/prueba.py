import random
import copy
import string
import socket,pickle
import sys
import os
import timeit

from random import shuffle
HOST = "localhost"
PORT = 1234
categorias = ["Animales", "Frutas", "Autos", "Paises"]
dificultades = ["Facil", "Intermedia", "Dificil"]
#we retrieve the list and we deserialize it with jsonpickle
def recvall(sock,deSerialize=None):	
    BUFF_SIZE = 1024    
    tiempot = b''          
    while (True):           
        part = sock.recv(BUFF_SIZE)
        tiempot += part               
        if (len(part) < BUFF_SIZE): 
            break                     
    if(deSerialize):
        return pickle.loads(tiempot)
    else: 
        return tiempot
def test():#In order to avoid shutdown-server problems, we test if the server is up first, then we proceed to ask for the user tiempot
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	try:
		s.connect((HOST, PORT))
	except:
		print("Se requiere iniciar el servidor para jugar!")
		sys.exit(0)
	instruccion_server = pickle.dumps("test")
	s.send(instruccion_server)
	s.shutdown(socket.SHUT_WR)
	s.close()
#CONCEPT MODE
def iniciarjuegoconcepto(dificultad,lista,vsauce,name):
	grid_size=15
	grid = [['_' for _ in range(grid_size)] for _ in range(grid_size)]
	found = 0
	remaining = 14
	f=[]
	sol = []
	fail = 0
	part=[]
	repite=False
	grid=generategrid(lista)
	#MODALIDAD CONCEPTO
	if dificultad==0:
		#FACIL
		for z in range(0, 14):
			sol.append(lista[z])		
		print("Lista de palabras completa, encuentre las que esten en la sopa (MAX 14 intentos): ")
		print(vsauce)
	if dificultad==1:
		#MEDIA
		for z in range(0, 14):
			sol.append(lista[z])	
		print("Las palabras se iran desbloqueando conforme vayas intentando")
	if dificultad==2:
		#DIFICIL
		b=lista.copy()
		for z in range(0, 14):
			sol.append(len(lista[z]))		
		print("Longitud de las palabras a encontrar")
		print(sol)	
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	try:
		s.connect((HOST, PORT))
	except:
		print("Servidor caido")
	instruccion_server = pickle.dumps(["inicio"])	
	s.send(instruccion_server)
	s.shutdown(socket.SHUT_WR)
	tiempot = recvall(s) 
	s.close()
	#COPIAMOS PARA IMPRIMIR SOLUCION AL FINAL
	grid_sol=copy.deepcopy(grid)
	##llenamos los caracteres restantes e imprimimos para jugar
	fill_grid(grid)	
	print_grid(grid)
#HASTA QUE ENCUENTRE TODO
	while found!=14:
		print("Palabras encontradas")
		print(f)
		busca = input("Introduzca una palabra que hayas encontrado por primera vez:\n")
		if busca.upper() != "SALIR":
			if dificultad==0:#SET AN ATTEMPT LIMIT IN CASE THAT THE USER WANTS TO INPUT ALL OF THE WORDS FROM THE LIST
				print("Lista de palabras completa, encuentre las que esten en la sopa (MAX 14 intentos): ")
				print(vsauce)
				if busca.upper() in sol:
					if busca.upper() in f:
						print("Ya has encontrado esta palabra anteriormente")
						print("Te faltan: "+str(14-found))
					else:
						print("Has encontrado una palabra")
						found=found+1
						print("Te faltan: "+str(14-found))
						f.append(busca.upper())
				else:	
					fail=fail+1
					print("Intentos restantes: "+str(14-fail))	
					if fail==14:			
						print("Intentos de dificultad facil alcanzados")
						print("Has perdido!")
						break
					print("Intente de nuevo")
				print_grid(grid)
			if dificultad==1:#HELPS THE USER BY SHOWING BRIEFLY A WORD FROM THE LIST
				print("PISTAS DESBLOQUEADAS")
				print(part)	
				if busca.upper() in sol:
					if busca.upper() in f:
						print("Ya has encontrado esta palabra anteriormente")
						print("Te faltan: "+str(14-found))
					else:
						print("Has encontrado una palabra")
						found=found+1
						print("Te faltan: "+str(14-found))
						f.append(busca.upper())
				else:	
					fail=fail+1
					if fail%3==0:
						print("Algo de ayuda")
						pal =  random.choice(sol)
						s = pal[2: 2 + len(pal)]
						print("Fragmento de una palabra desbloqueada")
						part.append(s)
						print(part)
					print("Intente de nuevo")
				print_grid(grid)
			if dificultad==2:#ONLY WORD LENGTHS ARE SHOWN
				print("Longitud de las palabras a encontrar")
				print(sol)		
				if busca.upper() in b:
					if busca.upper() in f:
						print("Ya has encontrado esta palabra anteriormente")
						print("Te faltan: "+str(14-found))
					else:
						print("Has encontrado una palabra")
						found=found+1
						print("Te faltan: "+str(14-found))
						f.append(busca.upper())
				else:	
					print("Intente de nuevo")
				print_grid(grid)
		else:
			break
	print("Solucion")
	print_grid(grid_sol)
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	try:
		s.connect((HOST, PORT))
	except:
		sys.exit(0)
	instruccion_server = pickle.dumps(["fin",name,dificultades[dificultad],str(found),"Conceptos"])
	s.send(instruccion_server)
	s.shutdown(socket.SHUT_WR)
	tiempot = recvall(s) 
	s.close()
	print("Jugador: "+name)
	print("Numero de palabras encontradas: "+str(found))
	print("Tiempo tardado: "+tiempot)
	repe=input("Deseas jugar de nuevo? (S/N)\n")
	if repe.upper()=='S':
		main()
	else:
		print("Hasta luego")
		
#ANAGRAM MODE
def iniciarjuegoanagrama(lista,name):
	encontradas=0
	found = 0
	remaining = 14
	f=[]
	grid=generategrid(lista)
	b=lista.copy()
	b = [''.join(random.sample(word, len(word))) for word in b]	
	print("Lista de palabras por encontrar (ANAGRAMA): ")
	print(b)
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	try:
		s.connect((HOST, PORT))
	except:
		return False
	instruccion_server = pickle.dumps("inicio")
	s.send(instruccion_server)
	s.shutdown(socket.SHUT_WR)
	palabras = recvall(s,True)
	#COPIAMOS PARA IMPRIMIR SOLUCION AL FINAL
	grid_sol=copy.deepcopy(grid)
	##llenamos los caracteres restantes e imprimimos para jugar
	fill_grid(grid)	
	print_grid(grid)
	#HASTA QUE ENCUENTRE TODO
	while found!=14:
		print("Lista de palabras por encontrar (ANAGRAMA): ")
		print(b)
		print("Palabras encontradas")
		print(f)
		busca = input("Introduzca una palabra que hayas encontrado por primera vez:\n")
		if busca.upper() != "SALIR":
			if busca.upper() in lista:
				if busca.upper() in f:
					print("Ya has encontrado esta palabra anteriormente")
					print("Te faltan: "+str(14-found))
				else:
					print("Has encontrado una palabra")
					found=found+1
					if found==14:
						print("Has ganado!")
					else:
						print("Te faltan: "+str(14-found))
						f.append(busca.upper())				
			else:	
				print("Intente de nuevo")
		else:
			break
		print_grid(grid)
	print("Solucion")
	print_grid(grid_sol)
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	try:
		s.connect((HOST, PORT))
	except:
		sys.exit(0)
	instruccion_server = pickle.dumps(["fin",name,"-",str(found),"Anagramas"])
	s.shutdown(socket.SHUT_WR)
	tiempot = recvall(s) 
	s.close()
	print("Jugador: "+name)
	print("Numero de palabras encontradas: "+str(found))
	print("Tiempo tardado: "+tiempot)
	repe=input("Deseas jugar de nuevo? (S/N)\n")
	if repe.upper()=='S':
		main()
	else:
		print("Hasta luego")
#Prints the grid
def print_grid(grid):
	grid_size=15
	for x in range(grid_size):
		print('\t'*5+' '.join(grid[x]))
#Fills grid with random letters
def fill_grid(gridx):
	grid_size=15
	for x in range(grid_size):
		for y in range(grid_size):
			if(gridx[x][y] == '_'):
				gridx[x][y] = random.choice(string.ascii_uppercase)
#FOR ANAGRAM MODE
def shuffle_word(word):
	word = list(word)
	shuffle(word)
	return ''.join(word)
#Generates grid
def generategrid(words):
	orientations = ['leftright','updown','diagonalup','diagonaldown']
	grid_size=15
	grid = [['_' for _ in range(grid_size)] for _ in range(grid_size)]
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
	return grid
#------MAIN PROGRAM--------------
def main():
	words=[]
	i=0
	print("Comprobando servidor...")
	test()
	print("SOPA DE LETRAS")
	name = input('Ingrese su nombre\n')
	print("1)Modalidad por concepto")
	print("2)Modalidad anagrama")
	print("3)Revisar puntajes")
	sString = input('Seleccione opcion\n')
	s = int(sString)
	while s > 3 or s < 1:
		print("Entrada invalida")
		sString = input('Seleccione una opción\n')
		s = int(xString)
	if s == 1:
		#MODALIDAD POR CONCEPTOS
		print("DIFICULTAD")
		an = False
		for z in range(0, 3):
			print(str(z) + ")" + dificultades[z])
		xString = input('Seleccione una opción\n')
		x = int(xString)
		while x > 2 or x < 0:
			print("Entrada invalida")
			xString = input('Seleccione una opción\n')
			x = int(xString)
		print("Seleccionaste la dificultad:" + dificultades[x])
		print("CONCEPTOS")
		for z in range(0, 4):
			print(str(z) + ")" + categorias[z])
		yString = input('Seleccione su categoria\n')
		y = int(yString)
		while y > 3 or y < 0:
			print("Entrada invalida")
			yString = input('Seleccione su categoria\n')
			y = int(yString)
		print("Seleccionaste la sopa de " + categorias[y])
		if y==0:#ANIMALES
			print("hOLA")
			s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			try:
				s.connect((HOST, PORT))
			except:
				print("Muerto")
			instruccion_server = pickle.dumps("animales")
			s.send(instruccion_server)
			s.shutdown(socket.SHUT_WR)
			animales = recvall(s,True)
			s.close()
			while i != 14:
				pal =  random.choice(animales)
				if pal in words:
					continue
				else: 
					words.append(pal)
					i=i+1
			iniciarjuegoconcepto(x,words,animales,name)
		if y==1:#FRUTAS
			s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			try:
				s.connect((HOST, PORT))
			except:
				print("Muerto")
			instruccion_server = pickle.dumps("frutas")
			s.send(instruccion_server)
			s.shutdown(socket.SHUT_WR)
			frutas = recvall(s,True)
			s.close()
			while i != 14:
				pal =  random.choice(frutas)
				if pal in words:
					continue
				else: 
					words.append(pal)
					i=i+1
			iniciarjuegoconcepto(x,words,frutas,name)
		if y==2:#AUTOS
			s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			try:
				s.connect((HOST, PORT))
			except:
				return False
			instruccion_server = pickle.dumps("autos")
			s.send(instruccion_server)
			s.shutdown(socket.SHUT_WR)
			autos = recvall(s,True)
			s.close()
			while i != 14:
				pal =  random.choice(autos)
				if pal in words:
					continue
				else: 
					words.append(pal)
					i=i+1
			iniciarjuegoconcepto(x,words,autos,name)
		if y==3:#PAISES
			s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			try:
				s.connect((HOST, PORT))
			except:
				return False
			instruccion_server = pickle.dumps("paises")
			s.send(instruccion_server)
			s.shutdown(socket.SHUT_WR)
			palabras = recvall(s,True)
			s.close()
			#NO REPETITIONS
			while i != 14:
				pal =  random.choice(paises)
				if pal in words:
					continue
				else: 
					words.append(pal)
					i=i+1
			iniciarjuegoconcepto(x,words,paises,name)
	if s==2:
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		try:
			s.connect((HOST, PORT))
		except:
			return False
		instruccion_server = pickle.dumps("palabras")
		s.send(instruccion_server)
		s.shutdown(socket.SHUT_WR)
		palabras = recvall(s,True)
		s.close()
		#NO REPETITIONS
		while i != 14:
			pal =  random.choice(palabras)
			if pal in words:
				continue
			else: 
				words.append(pal)
				i=i+1
		iniciarjuegoanagrama(words,name)
	else:
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		try:
			s.connect((HOST, PORT))
		except:
			return False
		instruccion_server = pickle.dumps("puntajes")
		s.send(instruccion_server)
		s.shutdown(socket.SHUT_WR)
		puntajes = recvall(s,True)
		s.close()
		print("[Nombre,Dificultad,Palabras Encontradas,Modalidad,Tiempo]")
		for i in puntajes:
			print(puntajes)
		main()
main()