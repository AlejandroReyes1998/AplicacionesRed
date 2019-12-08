import socket,pickle,sys,datetime
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

def getRemoteListOfFiles(hostx,portx):
    s = socket.socket()
    try:
        s.connect((hostx, portx))
    except:
        print("[!] Error fatal al obtener lista de archivos...")
        sys.exit(0)
    cmd = str(datetime.datetime.now())
    cmd = pickle.dumps(cmd)
    s.send(cmd)		
    stan = recvall(s)		
    stan = pickle.loads(stan)
    print("server:",stan)
    s.close()		

getRemoteListOfFiles("localhost",6772)