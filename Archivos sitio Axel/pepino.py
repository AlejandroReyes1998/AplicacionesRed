import pickle
import sys,datetime
import socket
import os
inicio=0
final=0
def recvall(sock,deSerialize=None):	
    BUFF_SIZE = 256    
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
frutas = ["UVA", "PERA", "MANZANA", "NARANJA", "DURAZNO", "BANANA", "KIWI", "TUNA", "MORA", "MANGO", "SANDIA", "COCO", "CEREZA", "LIMON",
      "MELOCOTON","PITAYA","MELON","ANANO","FRESA","POMELO","PASA","LITCHI","HIGO","GROSELLA"]

autos = ["BMW","VOLKSWAGEN","FERRARI","TOYOTA","MAZDA","FORD","LAMBORGHINI","RENAULT","AUDI","SUZUKI","CADILLAC","CHEVROLET","FIAT","NISSAN",
      "EXPLORER","TIDA","SENTRA","MASERATI","JAGUAR","MCLAREN","LEXUS","VOLVO","JEEP","DODGE"]

animales = ["PERRO","GATO","AGUILA","HORMIGA","CARPA","VACA","CERDO","MONO","CABALLO","ABEJA","CONEJO","TIGRE","PANTERA","LAGARTO",
      "TORO","IGUANA","CANARIO","PETIRROJO","AZULEJO","RINOCERO","PONY","ESCARABAJO","TIBURON"]

paises = ["MEXICO","INGLATERRA","AUSTRALIA","JAPON","EGIPTO","CANADA","FRANCIA","TAIWAN","CHINA","PANAMA","EEUU","ALEMANIA","RUSIA","BRASIL",
      "NEPAL","ESCOCIA","VENEZUELA","CUBA","HOLANDA","ZIMBABWE","BELICE","ITALIA","GRECIA","BELGICA"]
#PARA MODALIDAD ANAGRAMA
palabras = ["PLUMA","CEVICHE","JARRA","COMETA","RADIOS","ZOPILOTE","REFRESCO","INTERNET","GOTCHA","LLAVE","IGLU","TORRE","DIAMANTE","NOVELA",
      "AVIONES","RED","HIDALGO","CAUCE","CACAHUATE","TETERA","EMOJI","DIEZMO","FUTBOL","EURO","JUPITER","EXCELSIOR","COMA","REYNA",
      "MILLA","PIZZA","ORDENADOR","FRAUDE","RANCHO","UNITARIO","TAPADERA","TOCAR","PLATON","HUESO","MOTOCICLETA","PAPEL","OGRO","SALSA",
      "UVA", "PERA", "MANZANA", "NARANJA", "DURAZNO", "BANANA", "KIWI", "TUNA", "MORA", "MANGO", "SANDIA", "COCO", "CEREZA", "LIMON",
      "BMW","VOLKSWAGEN","FERRARI","TOYOTA","MAZDA","FORD","LAMBORGHINI","RENAULT","AUDI","SUZUKI","CADILLAC","CHEVROLET","FIAT","NISSAN",
      "PERRO","GATO","AGUILA","HORMIGA","CARPA","VACA","CERDO","MONO","CABALLO","ABEJA","CONEJO","TIGRE","PANTERA","LAGARTO",
      "MEXICO","INGLATERRA","AUSTRALIA","JAPON","EGIPTO","CANADA","FRANCIA","TAIWAN","CHINA","PANAMA","EEUU","ALEMANIA","RUSIA","BRASIL"]
print("Hola")
score=[]
HOST = "localhost"      #Host
PORT = 6772         #Puerto
s = socket.socket()
try:
	s.bind((HOST, PORT))	#Bindear al puerto para socket de servidor
	print("[ok/sendlist] Servidor",HOST,":",PORT,sep="")
except:
	print("[!/sendlist] ERROR! (bind:",PORT,"), ejecute #fuser -k ",PORT,"/tcp",sep="")
	sys.exit(0)
s.listen(5)	#Serializar de nuevo por si acaso	
while True:	#Serializar lista de archivos y directorios en la CarpetaDelServidor	
	try:
		conn, addr = s.accept()
	except:
		print("\n[!/sendlist] KeyboardInterrupt")
		s.close()
		sys.exit(0)
	while(True):
		try:
			data = conn.recv(1024) #512 para que el cliente "toque" al servidor, no se necesita mucha memoria
		except:
			print("[!/sendlist] conexión reiniciada por el cliente")            	
		if(not data): 
			break
		try:
			print(pickle.loads(data))
			totbytes = conn.send(pickle.dumps(str(datetime.datetime.now())))	#Enviar la lista de archivos en el servidor
		except:			#En caso de que el cliente falle al recibir, revivir el socket (connection reset by peer)
			print("[!/sendlist] reviviendo proceso de envío de información")
	conn.close()
'''	while(True):
		try:
			datillos = recvall(conn,True)
		except:
			print("[!/sendlist] conexión reiniciada por el cliente")            	
		if( not datillos ):	break
		elif(datillos=="animales"):
			try:
				datillos=pickle.dumps(animales)
				s.send(datillos)
			except:	
				print("[!/sendlist] Error")
		elif(datillos=="frutas"):
			try:
				datillos=pickle.dumps(frutas)
				s.send(datillos)
			except:	
				print("[!/sendlist] Error")
		elif(datillos=="paises"):
			try:
				datillos=pickle.dumps(paises)
				s.send(datillos)
			except:	
				print("[!/sendlist] Error")
		elif(datillos=="autos"):
			try:
				datillos=pickle.dumps(autos)
				s.send(datillos)
			except:	
				print("[!/sendlist] Error")
		elif(datillos=="inicio"):
			try:
				inicio=timeit.default_timer()
			except:	
				print("[!/sendlist] Error")
		elif(datillos[0]=="fin"):
			try:
				final= timeit.default_timer() - inicio
				score.append([datillos[1],datillos[2],datillos[3],datillos[4],final])
			except:	
				print("[!/sendlist] Error")
		elif(datillos=="test"):
			try:
				print("Se ha realizado una prueba de conexion")
			except:	
				print("[!/sendlist] Error")
		elif(datillos=="puntajes"):
			try:
				datillos=pickle.dumps(score)
				s.send(datillos)
			except:	
				print("[!/sendlist] Error")
	conn.close()'''