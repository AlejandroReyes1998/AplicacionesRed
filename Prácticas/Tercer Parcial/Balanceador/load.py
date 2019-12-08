'''
	POST:
	curl http://localhost:8000 -d "p" 
	HEAD:
	curl -I http://localhost:PUERTO
	GET:
	curl localhost:8080
'''
import socket
from threading import Thread
from time import sleep
import itertools

def recvall(sock):		# Funcion que se encarga de recibir datos a traves de un socket sin importar su tamaño
	BUFF_SIZE = 2**12	# Chunks de 4KB
	data = b''			# data = arreglo de bytes
	while (True):			# loop infinito
		part = sock.recv(BUFF_SIZE)	#recibir de 8KB
		data += part				#anexar los datos
		if (len(part) < BUFF_SIZE): #Terminar cuando sea 0 o ya no hayan archivos
			break						#-->break
	return data

def serv(datos,serv,port):
	s = socket.socket()         # crear un objeto de socket
	try:	s.connect((serv, port))	#Tratar de conectar
	except:	
		print("No se ha podido conectar al servidor!")
		return None
	try:
		s.sendall(datos)
		print("Reenviando datos...")
		sleep(.01)
	except:	print("Error de conexión.")
	while True:
		try:
			data = recvall(s)
			if data:
				try:
					return data
				except: print("[Error con archivo")
				break
			else: #if not data
				raise ValueError('Cliente desconectado')
		except:     #si el cliente cierra la conexión
			print("Desconexión")
			break
		finally:
			s.close()
			print("Cerrando socket")
#Sorteamos los servidores con el roundrobiin y posteriomente asiganmos la peticion a uno de la lista
def balanceador(client,address,servidorDestino):
		while True:
			try:
				data = recvall(client)     
				if data:
					#Enviamos los datos a cualquiera de los servidores disponibles
					print("Conexión detectada de:",address)
					datillos = serv(data,servidorDestino[0],servidorDestino[1])
					try:	
						print("Envio de datos de ",servidorDestino,"hacia",address)
						client.send(datillos)
					except Exception as e:	print("Ha ocurrido un error con el balanceador",e)
				else: #if not data
					raise ValueError('Cliente desconectado')
			except:     #si el cliente cierra la conexión
				print("[E] error de recepción ",client.getpeername(),sep="")
			finally:
				print("Se ha desconectado un cliente: ",client.getpeername(),sep="")
				client.close()                          #cerrar socket
				break                    

def carga():
	#Definicion de los servidores que se encargaran de atender las peticiones
	listaServidores = [('localhost',1234),('localhost',5678),('localhost',9012)]
	round_robin = itertools.cycle(listaServidores)
	host = socket.gethostname()
	port = 6669
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #TCP
	s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1) #REUSAR ADDR
	#Se abre el socket para trabajar con el
	try:	s.bind(('',port))
	except Exception as e:	print("Ha ocurrido un error",e)
	s.listen(port)
	while True:
		try:
			client, address = s.accept()    #aceptar conexiones
			print("Conexion aceptada:",client.getpeername())
			#Se reparte mediante roundrobin
			Thread(target = balanceador, args = (client,address,next(round_robin))).start() #thread
		except Exception as e:	
			print("Ha ocurrido un error",e)
			#client.close()
if __name__ == '__main__':
	try:	
		while True:	carga()
	except Exception as e:	print("Ha ocurrido un error",e)