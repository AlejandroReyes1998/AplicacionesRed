'''
	POST:
	curl http://localhost:8000 -d "p" 
	HEAD:
	curl -I http://localhost:PUERTO
	GET:
	curl localhost:8080
	DELETE:
	curl -X "DELETE" localhost:8080/page
'''
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer, test
from SocketServer     import ThreadingMixIn
from concurrent.futures import ThreadPoolExecutor
import mimetypes
import os
from os import curdir, sep, path
from io import BytesIO

class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):
	
	def _set_header(self, status = 200):

		print( 'Path: ', curdir+self.path )
		self.send_response(status)
		if os.path.exists( curdir + self.path ):
			mime = mimetypes.guess_type(self.path)
			if mime[0] != None and os.path.isfile( curdir+self.path ):
				print( 'mime', mime[0] )
				self.send_header('Content-type', mime[0])
			else:
				self.send_header('Content-type', 'text/html')

		self.end_headers()
		#Metodo Head
	def do_HEAD(self):
		self.send_response(200)
		self.send_header("Content-type", "text/html")
		self.end_headers()
		#Metodo Post
	def do_POST(self):
		content_length = int(self.headers['Content-Length'])
		body = self.rfile.read(content_length)

		self.send_response(200)
		self.end_headers()
		response = BytesIO()
		response.write(b'Peticion POST')
		response.write(b'RECIBIDO: \n')
		response.write(body)
		response.write(b'\n')
		self.wfile.write(response.getvalue())

		#Metodo Get
	def do_GET(self):
		print('This is a GET request. ')
		path = curdir+self.path
		if not os.path.isdir( self.path ) :
			if os.path.exists( path ):
				self._set_header()
				#Obtenemos el recurso correspondiente
				print( 'Obteniendo recurso ' + path )
				f = open( path, 'rb+' ) 
				self.wfile.write(f.read())
				f.close()
			else:
				#No se encontro
				print( '404 ERROR!' )
				self.send_error(404, 'File Not Found: %s' % self.path)
		else:
			self.list_directory()


	def list_directory(self):

		self._set_header()
		path = curdir + self.path
		self.wfile.write(b"<html><head><title>Servidor HTTP</title></head>")
		self.wfile.write(b"<body bgcolor='#e0e000'><center><h3>Ejemplo sencillo de un servidor http</h3><h4>Alicaciones para comunicaciones en red</h4>")
		self.wfile.write(b"<img src='shaq.jpg'/>")
		self.wfile.write(b"<p><b>Lista de archivos</b></p><hr>")
		#Enumeracion de archivos en listados
		files = [f for f in os.listdir('.') if os.path.isfile(f)]
		for f in files:
			self.wfile.write(b"<li>")
			self.wfile.write(("<a href=\'%s\''>" % (f)).encode())
			self.wfile.write(("%s" % (f)).encode())
			self.wfile.write(b"</a>")
			self.wfile.write(b"</li>")
		self.write.write(b"</center>")
		self.wfile.write(b"<p><b>Robbie WIlliams</b></p>")	
		self.wfile.write(b"</body></html>")

#Pool de conexiones
class PoolMixIn(ThreadingMixIn):
    def process_request(self, request, client_address):
        self.pool.submit(self.process_request_thread, request, client_address)

def main():
    class PoolHTTPServer(PoolMixIn, HTTPServer):
        pool = ThreadPoolExecutor(max_workers=2)

    test(HandlerClass=SimpleHTTPRequestHandler, ServerClass=PoolHTTPServer)

if __name__=="__main__":
    main()