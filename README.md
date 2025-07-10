<div>
    <div>
        <img src="https://img.shields.io/maven-central/v/io.github.remmerw/buri" alt="Kotlin Maven Version" />
        <img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android" alt="Badge Android" />
        <img src="https://img.shields.io/badge/Platform-iOS%20%2F%20macOS-lightgrey.svg?logo=apple" alt="Badge iOS" />
        <img src="https://img.shields.io/badge/Platform-JVM-8A2BE2.svg?logo=openjdk" alt="Badge JVM" />
    </div>
</div>

## Buri
Bencode Library

The bencoding format is described in https://www.bittorrent.org/beps/bep_0003.html


## Integration

```
    
kotlin {
    sourceSets {
        commonMain.dependencies {
            ...
            implementation("io.github.remmerw:buri:0.0.1")
        }
        ...
    }
}
    
```

## API

```
    @Test
    fun examplesList() {

        // prepare data
        val value: List<BEObject> = listOf(
            555L.bencode(), "hello".bencode()
        )
        val buffer = Buffer()
        
        // encode
        value.encodeBencodeTo(buffer)

        // decode
        val list = (buffer.decodeBencode() as BEList).toList()
        
        // testing
        assertEquals(value.size, list.size)
        val a = value.first() as BEInteger
        assertEquals(a.toInt(), 555)
        val b = value.last() as BEString
        assertContentEquals(b.toByteArray(), "hello".encodeToByteArray())
    }
```






