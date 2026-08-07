package io.github.remmerw.buri


import kotlinx.io.Sink

class BEWriter(val sink: Sink){
    fun eof(){
        sink.writeByte(EOF.code.toByte())
    }
   
    fun map(){
        sink.writeByte(MAP_PREFIX.code.toByte())
    }

    fun mapEntry(key: String){
       val keyBytes = key.encodeToByteArray()
      
       // Write key length and delimiter
        sink.write(keyBytes.size.toString().encodeToByteArray())
      sink.writeByte(DELIMITER.code.toByte())
     
      sink.write(keyBytes)
    }
   
    fun list(){
        sink.writeByte(LIST_PREFIX.code.toByte())
    }
    
    fun longValue(value: Long){
       sink.writeByte(INTEGER_PREFIX.code.toByte())
   sink.write(value.toString().encodeToByteArray())
       eof()
    }

    fun dataValue(content: ByteArray){
               sink.write(content.size.toString().encodeToByteArray())
        sink.writeByte(DELIMITER.code.toByte())
        sink.write(content)
    }
 
}

fun Sink.bencode(content: ByteArray){
 this.write(content.size.toString().encodeToByteArray())
    this.writeByte(DELIMITER.code.toByte())
    this.write(content)
}

fun Sink.bencode(value: Long){
   this.writeByte(INTEGER_PREFIX.code.toByte())
   this.write(value.toString().encodeToByteArray())
   this.writeByte(EOF.code.toByte())
}


fun Sink.bencodeEof(){
   this.writeByte(EOF.code.toByte())
}
   
fun Sink.bencodeMap(){
   this.writeByte(MAP_PREFIX.code.toByte())
}


fun Sink.bencodeList(){
   this.writeByte(LIST_PREFIX.code.toByte())
}

fun Sink.bencodeMapEntry(key: String){
   val keyBytes = key.encodeToByteArray()
      
   // Write key length and delimiter
        this.write(keyBytes.size.toString().encodeToByteArray())
      
   this.writeByte(DELIMITER.code.toByte())
   this.write(keyBytes)
}
