package com.example.findhospital.activity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.findhospital.R
import org.bouncycastle.util.*//io.pem.PemReader
import org.bouncycastle.util.io.pem.PemReader
import java.io.StringReader
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class RSAactivity : AppCompatActivity() {
    lateinit var generator : KeyPairGenerator;
    lateinit var privateKey : PrivateKey
    lateinit var publicKey : PublicKey
    //KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

    fun encrypt(message : String){
        Log.e("TAG", "To encrypt: " + message)

        //put PUBLIC key here key generated from: https://8gwifi.org/rsafunctions.jsp
        val publicKeyRaw =
            "-----BEGIN PUBLIC KEY-----\n" +
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAW4WQxF2/qzqYwoQlwkkQIjQJ\n" +
                    "hCm2Hjl00QGkxeO12Py+jytTNYAopHCPpR4SbhE1cFdYx1qjEnFbgeJBxFENyqDg\n" +
                    "GvBhlwrWQXfI9LdA2M3xbr/4wur7ph1c+aQxOpImzslCtHJ5df7cyFrOTnkY+XYY\n" +
                    "yGK2Fsnu67FKWjgVvQIDAQAB\n" +
                    "-----END PUBLIC KEY-----"
        val reader = PemReader(StringReader(publicKeyRaw))
        val pemObject = reader.readPemObject()
        val keyBytes: ByteArray = pemObject.content
        val keySpec: EncodedKeySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val key = keyFactory.generatePublic(keySpec)

        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherData: ByteArray = cipher.doFinal(message.toByteArray())
        val encrypted = Base64.encodeToString(cipherData, Base64.DEFAULT)

        Log.e("TAG", "encrypted: $encrypted")
        /* use the encrypted text and place in https://8gwifi.org/rsafunctions.jsp
        and try to decrypt with the PRIVATE key generated*/
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(1024)
        //privateKey = pair.getPrivate()
        val toEncrypt = "secret message"
        Log.e("TAG", "To encrypt: " + toEncrypt)

        //put PUBLIC key here key generated from: https://8gwifi.org/rsafunctions.jsp
        val publicKeyRaw =
            "-----BEGIN PUBLIC KEY-----\n" +
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAW4WQxF2/qzqYwoQlwkkQIjQJ\n" +
                    "hCm2Hjl00QGkxeO12Py+jytTNYAopHCPpR4SbhE1cFdYx1qjEnFbgeJBxFENyqDg\n" +
                    "GvBhlwrWQXfI9LdA2M3xbr/4wur7ph1c+aQxOpImzslCtHJ5df7cyFrOTnkY+XYY\n" +
                    "yGK2Fsnu67FKWjgVvQIDAQAB\n" +
                    "-----END PUBLIC KEY-----"
        val reader = PemReader(StringReader(publicKeyRaw))
        val pemObject = reader.readPemObject()
        val keyBytes: ByteArray = pemObject.content
        val keySpec: EncodedKeySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val key = keyFactory.generatePublic(keySpec)

        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherData: ByteArray = cipher.doFinal(toEncrypt.toByteArray())
        val encrypted = Base64.encodeToString(cipherData, Base64.DEFAULT)

        Log.e("TAG", "encrypted: $encrypted")
        /* use the encrypted text and place in https://8gwifi.org/rsafunctions.jsp
        and try to decrypt with the PRIVATE key generated*/
    }







    /*
       val publicKey = publicKeyRaw.replace("\n", "")
           .replace("\\n", "")
           .replace("-----BEGIN PUBLIC KEY-----", "")
           .replace("-----END PUBLIC KEY-----", "")

        */

    /*
       val pemParser = PEMParser(StringReader(publicKeyRaw))
       val pemKeyPair : PEMKeyPair = pemParser.readObject() as PEMKeyPair
       val key = JcaPEMKeyConverter().getPublicKey(pemKeyPair.publicKeyInfo)
       */
/*
   val keyFactory = KeyFactory.getInstance("RSA")
   val keyBytes: ByteArray = Base64.decode(publicKey.toByteArray(), Base64.DEFAULT)
   val spec = X509EncodedKeySpec(keyBytes)
   val fileGeneratedPublicKey = keyFactory.generatePublic(spec)
   val rsaPub: RSAPublicKey = fileGeneratedPublicKey as RSAPublicKey
   val publicKeyModulus: BigInteger = rsaPub.modulus
   val publicKeyExponent: BigInteger = rsaPub.publicExponent


   val keyFactoryAlt = KeyFactory.getInstance("RSA")
   val pubKeySpec = RSAPublicKeySpec(publicKeyModulus, publicKeyExponent)
   val key = keyFactoryAlt.generatePublic(pubKeySpec) as RSAPublicKey
*/

/*
    val reader = PemReader(StringReader(publicKeyRaw))
    val pemObject = reader.readPemObject()
    val keyBytes: ByteArray = pemObject.content
    val keySpec: EncodedKeySpec = X509EncodedKeySpec(keyBytes)
    val keyFactory = KeyFactory.getInstance("RSA")
    val key = keyFactory.generatePublic(keySpec)
    */
/*
    val keyFactory = KeyFactory.getInstance("RSA")
    val keyBytes: ByteArray = Base64.decode(publicKey.toByteArray(), Base64.DEFAULT)
    val spec = X509EncodedKeySpec(keyBytes)
    val fileGeneratedPublicKey = keyFactory.generatePublic(spec)
    val rsaPub: RSAPublicKey = fileGeneratedPublicKey as RSAPublicKey
    val publicKeyModulus: BigInteger = rsaPub.modulus
    val publicKeyExponent: BigInteger = rsaPub.publicExponent
    */

/*
val pemParser = PEMParser(StringReader(publicKey))
val pemKeyPair : PEMKeyPair = pemParser.readObject() as PEMKeyPair
val encoded : ByteArray = pemKeyPair.publicKeyInfo.encoded
val keyFactory = KeyFactory.getInstance("RSA")
val key = keyFactory.generatePublic(PKCS8EncodedKeySpec(encoded))
 */
}