require 'socket'                 
server = TCPServer.open(2000)   
loop {
   client = server.accept
   client.puts "aber"
   client.close                  
}