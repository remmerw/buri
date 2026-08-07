package io.github.remmerw.buri


import kotlinx.io.Sink

class BEWriter(val sink: Sink){
    fun eof(){
        sink.writeByte(EOF.code.toByte())
    }
   
    fun map(){
        sink.writeByte(MAP_PREFIX.code.toByte())
    }

    fun mapEntry(val key: String){
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
