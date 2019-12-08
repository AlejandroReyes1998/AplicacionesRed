require 'socket'
require 'json'
#PARA GUARDAR LOS TIEMPOS
$start = 0
$total = 0
$end= 0
#MODALIDAD CONCEPTOS
$frutas = ["UVA", "PERA", "MANZANA", "NARANJA", "DURAZNO", "BANANA", "KIWI", "TUNA", "MORA", "MANGO", "SANDIA", "COCO", "CEREZA", "LIMON",
      "MELOCOTON","PITAYA","MELON","ANANO","FRESA","POMELO","PASA","LITCHI","HIGO","GROSELLA"]

$autos = ["BMW","VOLKSWAGEN","FERRARI","TOYOTA","MAZDA","FORD","LAMBORGHINI","RENAULT","AUDI","SUZUKI","CADILLAC","CHEVROLET","FIAT","NISSAN",
      "EXPLORER","TIDA","SENTRA","MASERATI","JAGUAR","MCLAREN","LEXUS","VOLVO","JEEP","DODGE"]

$animales = ["PERRO","GATO","AGUILA","HORMIGA","CARPA","VACA","CERDO","MONO","CABALLO","ABEJA","CONEJO","TIGRE","PANTERA","LAGARTO",
      "TORO","IGUANA","CANARIO","PETIRROJO","AZULEJO","RINOCERO","PONY","ESCARABAJO","TIBURON"]

$paises = ["MEXICO","INGLATERRA","AUSTRALIA","JAPON","EGIPTO","CANADA","FRANCIA","TAIWAN","CHINA","PANAMA","EEUU","ALEMANIA","RUSIA","BRASIL",
      "NEPAL","ESCOCIA","VENEZUELA","CUBA","HOLANDA","ZIMBABWE","BELICE","ITALIA","GRECIA","BELGICA"]
#PARA MODALIDAD ANAGRAMA
$palabras = ["PLUMA","CEVICHE","JARRA","COMETA","RADIOS","ZOPILOTE","REFRESCO","INTERNET","GOTCHA","LLAVE","IGLU","TORRE","DIAMANTE","NOVELA",
      "AVIONES","RED","HIDALGO","CAUCE","CACAHUATE","TETERA","EMOJI","DIEZMO","FUTBOL","EURO","JUPITER","EXCELSIOR","COMA","REYNA",
      "MILLA","PIZZA","ORDENADOR","FRAUDE","RANCHO","UNITARIO","TAPADERA","TOCAR","PLATON","HUESO","MOTOCICLETA","PAPEL","OGRO","SALSA",
      "UVA", "PERA", "MANZANA", "NARANJA", "DURAZNO", "BANANA", "KIWI", "TUNA", "MORA", "MANGO", "SANDIA", "COCO", "CEREZA", "LIMON",
      "BMW","VOLKSWAGEN","FERRARI","TOYOTA","MAZDA","FORD","LAMBORGHINI","RENAULT","AUDI","SUZUKI","CADILLAC","CHEVROLET","FIAT","NISSAN",
      "PERRO","GATO","AGUILA","HORMIGA","CARPA","VACA","CERDO","MONO","CABALLO","ABEJA","CONEJO","TIGRE","PANTERA","LAGARTO",
      "MEXICO","INGLATERRA","AUSTRALIA","JAPON","EGIPTO","CANADA","FRANCIA","TAIWAN","CHINA","PANAMA","EEUU","ALEMANIA","RUSIA","BRASIL"]
$score = []
Socket.tcp_server_loop(1234) do |sock,addr|
   Thread.new do
    puts "CONEXION REALIZADA"
    begin
      loop do#Con base a las peticiones, nosotros le mandaremos las listas o lo que sea que debamos de hacer
        line = sock.readline
        comando = JSON.parse(line)
        if comando[0]=="inicio"
          $start = Time.now
          puts "JUEGO EMPEZADO"
          sock.puts("Corre tu tiempo!")
        #MODO CONCEPTOS       
        elsif comando[0]=="animales"
          sock.puts($animales.to_json)
        elsif comando[0]=="frutas"
          sock.puts($frutas.to_json)
        elsif comando[0]=="autos"
          sock.puts($autos.to_json)
        elsif comando[0]=="paises"
          sock.puts($paises.to_json)
        #MODO ANAGRAMA
        elsif comando[0]=="palabras"
          sock.puts($palabras.to_json)
        elsif comando[0]=="fin"
          $end = Time.now
          total = $end - $start
          #Agregamos a los puntajes los datos del juego
          $score.push([comando[1],comando[2],comando[3],comando[4],total.to_s])
          puts "JUEGO TERMINADO"
          sock.puts(total.to_json)
        #IMPRIMIMOS LOS PUNTAJES ALTOS
        elsif comando[0]=="puntajes"
          sock.puts($score.to_json)
        end
      end
    rescue EOFError
      sock.close
      puts "CONEXION FINALIZADA"
    end
  end
end
