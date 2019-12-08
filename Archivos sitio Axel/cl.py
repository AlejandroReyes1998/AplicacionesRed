		busc=[]
		o = input("Ingrese orientacion de palabra (V-VERTICAL,H-HORIZONTAL,D-DIAGONAL)\n")
		if o == 'v' or o == 'V':
			rango = input("Ingrese rango de la palbara encontrada: YTOTAL,XINICIAL,XFINAL\n")
			print(rango)
			rango2 = rango.split(",")
			if((int(rango2[0]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]) or (int(rango2[1]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]) or (int(rango2[2]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14])):
				print("Numero invalido")
			else:
				print(rango[0])
				if int(rango2[1]) > int(rango2[2]):
					rango2[1], rango2[2] = rango2[2], rango2[1]
				for x in range(int(rango2[1]),int(rango2[2])+1):
					busc.append(grid[x][int(rango[0])])
				my_lst_str = ''.join(busc)
				print(my_lst_str)
				if my_lst_str in sol:
					print("Has encontrado una palabra")
					encontrada=encontrada+1
					continue
		if o == 'h' or o == 'H':
			rango = input("Ingrese rango de la palbara encontrada: XTOTAL,YINICIAL,YFINAL\n")
			rango2 = rango.split(",")
			if((int(rango2[0]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]) or (int(rango2[1]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]) or (int(rango2[2]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14])):
				print("Numero invalido")
			else:
				if int(rango2[1]) > int(rango2[2]):
					rango2[1], rango2[2] = rango2[2], rango2[1]
				#for x in range(int(rango2[1]),int(rango2[2])+1):
		if o == 'd' or o == 'D':
			rango = input("Ingrese rango de la palbara encontrada: XINICIAL,YINICIAL,XFINAL,YFINAL\n")
			rango2 = rango.split(",")
			if((int(rango2[0]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]) or (int(rango2[1]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14]) or (int(rango2[2]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14])  or (int(rango2[3]) not in [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14])):
				print("Numero invalido")
			else:
				if (int(rango2[0]) > int(rango2[2])) and (int(rango2[1]) > int(rango2[3])):
					rango2[0], rango2[2] = rango2[2], rango2[0]
					rango2[1], rango2[3] = rango2[3], rango2[1]