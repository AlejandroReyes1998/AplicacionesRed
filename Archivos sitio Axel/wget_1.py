 ##!/usr/bin/python3
import re, time, requests, sys
from os import mkdir,chdir,makedirs,system
from os.path import abspath,dirname,basename,realpath
from bs4 import BeautifulSoup
from urllib.parse import urlsplit
from urllib.request import urlopen,urlretrieve

#Identifica el tipo de contenido del sitio a descargar
def CONTENTTYPE(sitio):
	try:
		r = requests.get(sitio)
		return r.headers['content-type']
	except Exception as e:		
		print("Error con el enlace (",sitio,")",sep="")
		return "unknown"

#Revisa la codificación del sitio
def SITECODE(sitio):	
	try:	return requests.get(sitio).encoding	
	except Exception as e:	print("Error de codificación (",e,")",sep="")

#Descarga el sitio (sin recursos, con las subcarpetas organizadas)
def DescargarSitio(sitio,nombre):
	try:
		urlab = urlopen(sitio).url 					
		realp = realpath(__file__)					
		realp = realp.replace(basename(realp),"")	
		chdir(realp)								
		uri = URI_real(sitio)	
		#Obtenemos las subcarpetas del sitio y con esto generamos los directorios			
		novositio = sitio.replace(uri,"")	
		try:	makedirs(novositio)			
		except:	pass
		#Creamos los archivos
		try:	chdir(novositio)					
		except: pass
		urlretrieve(urlab,nombre+".html")	
		#descargar el sitio
		chdir(realp)					
		return nombre + ".html [ok]"
	except Exception as e:
		print("Error con la descarga del sitio(",e,")",sep="")	
		return False

#Lo mismo de arriba, pero con los recursos con los que funciona el sitio
def downloadfile(sitio):
	try:
		urlab = urlopen(sitio).url 					
		realp = realpath(__file__)					
		realp = realp.replace(basename(realp),"")	
		chdir(realp)								
		uri = URI_real(sitio)				
		nomArch = basename(urlab)			
		novositio = sitio.replace(uri,"").replace(nomArch,"")	
		try:	makedirs(novositio)			
		except:	pass
		chdir(novositio)					
		urlretrieve(urlab,nomArch)			
		chdir(realp)						
		return nomArch+" [OK]"				
	except Exception as e:
		print("Error con la descarga del recurso (",e,")",sep="")	
		return False

def urlisvalid(sitio):	
#Validar que la url empiece con http(s) y que sea válida también
	regex = re.compile(
		r'^(?:http|ftp)s?://' #http:// o https://
		r'(?:(?:[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?\.)+(?:[A-Z]{2,6}\.?|[A-Z0-9-]{2,}\.?)|' #Dominios
		r'localhost|' #localhost
		r'\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3})' #Dirección ip
		r'(?::\d+)?' #Puertos
		r'(?:/?|[/?]\S+)$', re.IGNORECASE)
	if(re.match(regex, sitio)):	return True	#la url es correcta
	else:	return False	#pues no :v

#Abre el sitio para que siga descargando
def AbrirSitio(sitio):
	if(sitio.startswith("ftp://")):
		print("Sitios FTP no soportados")
		return None
	try:	website = urlopen(sitio)	#Tratar de abrir el sitio
	except Exception as e:
		print("Error al abrir el sitio! (",e,")",sep="")
		return None
	try:
		return website.read().decode(SITECODE(sitio))	#Leer el sitio y decodificarlo acorde a su codificación
	except TypeError as e:	print("Sitio sin codificación detectado (",sitio,")",sep="")

#Obtiene el directorio 'PADRE'
def URI_real(sitio):	return "{0.scheme}://{0.netloc}/".format(urlsplit(sitio))#Esta función se encarga de sacar el URI real, por ejemplo

#Establece que el enlace sea correcto y los arregla en caso de que no sea así
def fixEnlaces(listaSitios):	
	nuevo = []					
	for item in  listaSitios:	
		if(item.startswith("//")):	nuevo.append(item.replace('//',''))
		elif(item in ["#","https://","ftp://","http://","/"] or item.startswith("{{")):	pass
		else: nuevo.append(item)
	return nuevo

#Agrega los prefijos http, https o data para los sitios que carezcan de ello
def agregarPrefijo(listaSitios):	
	nuevo = []
	for item in listaSitios:
		#Sea normal
		if(item.startswith("http")):	nuevo.append(item)
		#Se omite
		elif(item.startswith("data:")):	pass 	
		#Se agrega el sitio			
		else:	nuevo.append("https://"+item)				
	return nuevo
#Esta función elimina el "/" que algunos sitios utilizan para recursos y les pega el URI al principio para que funcionen correctamente al descargar.
def eliminardiagonales(listaSitios,uri):	return	[(uri+item[1:]) if(item.startswith("/")) else item for item in listaSitios]

#Solo descarga el sitio padre, se omiten anuncios u otros aspectos externos
def SitioPadreSolo(listaSitios,uri):	return [item for item in listaSitios if(item.startswith(uri))]

#Regresa el nombre de la página
def NombrePagina(sopa):	return sopa.title.string.strip()	#Título de la página

#Sitios que no comienzan con /
def diagonales(listaSitios,uri):	
	nuevo = []					
	for item in listaSitios:
		if(urlisvalid(item)):	nuevo.append(item)	
		else:	
			if("?" in item):	pass 	#Uso de parámetros en la ágina
			elif(uri.endswith("/") or item.startswith("/")):	nuevo.append(uri+item)	#el uri contiene /
			else:	nuevo.append(uri+"/"+item)										#En caso de que no lo tenga
	return nuevo

#Función principal
def wpyget(sitio):
	global descargados
	global flag
	if(urlisvalid(sitio)):			#La URL es válida
		if(sitio not in descargados):
			descargados.append(sitio)
			try:	html = AbrirSitio(sitio)	#Código html (texto plano)
			except:	html = None
			print("===>",sitio)
			if(html is None):			#Cuando no hay cuerpo de html, se trata de un archivo/recurso
				descargados.append(sitio)
				print("Descargando archivo",downloadfile(sitio))
				return
			uri = URI_real(sitio)			#Obtener URL real
			try:
				sopa = BeautifulSoup(html,features="html5lib")	#Convertir el html a objeto de BeautifulSoup para poder leer
			except Exception as e:	print("Error con BeautifulSoup: ",e)
			finally:	
				if(sopa):	pass 	#En caso de que se haya podido abrir, no hacer nada
				else:				#Si no se pudo abrir, intentar abrir con cualquier motor disponible en el sistema
					sopa = BeautifulSoup(html)
			tituloPagina = re.sub('\W+','', NombrePagina(sopa))	#Eliminar símbolos 'raros' de una cadena
			if(not flag):
				try:
					flag = True
					mkdir(tituloPagina)
				except:	pass
				chdir(tituloPagina)

			print("Descargando enlace",DescargarSitio(sitio,tituloPagina))
			links = [a.get('href') for a in sopa.find_all('a', href=True)]	
			#Obtiene los subenlaces de la página son beautiful soup
			alinks = [a for a in sopa.find_all(src=True)]	
			#Obtiene los recursos de la página con beautiful soup
			recursos =  SitioPadreSolo(eliminardiagonales(fixEnlaces(re.findall('src="([^"]+)"',str(html))),uri),uri) 
			#SRC=
			nuevosRecursos = [str(i) for i in alinks]	
			#Convertir a cadena todos los recursos que se sacaron con BeautifulSoup
			recursos2 = re.findall('src="([^"]+)"',str(nuevosRecursos))		
			#Sacar todos los enlaces que se encontraron con BeautifulSoup 
			linksexternos =  SitioPadreSolo(diagonales(eliminardiagonales(fixEnlaces(links),uri),sitio),uri)	
			#Quitar basura que pueda estar en el html, y arreglar subenlaces
			recursos = agregarPrefijo(recursos)
			recursos2 = SitioPadreSolo( eliminardiagonales( fixEnlaces(recursos2),uri),uri)  
			resources = set(recursos + recursos2)		
			for recurso in resources:
				if(recurso not in descargados):
					descargados.append(recurso)
					print("Recurso de SRC Detectado!",recurso,downloadfile(recurso))
					#Estos son los recursos presentes en la página (aquellos que apuntan a SRC)	
				else:	print("SRC no disponible, omitiendo",recurso)
			for i in linksexternos:
				if("application" in CONTENTTYPE(i)):		
					print("Enlace descargado",i,downloadfile(i))	
				else: 	
					print("Enlace",i)									
					try:	wpyget(i)
					except:	pass
		else:	return
		#Sitio no válido
	else:	print("error, intente con otro enlace")

if __name__ == "__main__":
	try:
		axel = "http://148.204.58.221/axel/aplicaciones/"
		descargados = []
		flag = False
		if(len(sys.argv)>1):	wpyget(sys.argv[1])
		else:	wpyget(axel)	
	except:	pass