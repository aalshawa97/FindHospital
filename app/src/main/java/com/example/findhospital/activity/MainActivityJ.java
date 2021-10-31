package com.example.findhospital.activity;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
//import org.apache.commons.codec.binary.Base64;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import java.util.Base64;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.io.*;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findhospital.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.findhospital.activity.RSAactivity;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.jetbrains.annotations.NotNull;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
//import com.google.firebase.perf.util.Timer;

public class MainActivityJ extends AppCompatActivity {

    private static final String PUBLIC_PATH = " ";
    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    private final int interval = 1000; // 1 Second

    public MainActivityJ() throws NoSuchAlgorithmException {
    }

    public void waitThread()
    {
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            public void run() {
                Toast.makeText(MainActivityJ.this, "Encryption with RSA!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivityJ.this, RSAactivity.class);
                startActivity(intent);
            }
        };
    }

    String[] values = new String[] { "begin_button", "add_friend", "sign_out_button",
    };
    //Get reference to the database
    //private Firebase firebase = new Firebase("https://encryptedmessanger.firebaseio.com/");


    RelativeLayout activity_main;
    FloatingActionButton fab;
    Button DecryptButton;
    FloatingActionButton SendButton;
    Toast decryptToast;
    Context context;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_sign_out )
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(findViewById(android.R.id.content),"You have been signed out.",Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            });
        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    /*
    protected void onListItemClick(ListView l, View v, int position, long id) {
        try
        {
            String val = values[position];
            Class ourClass  = Class.forName("com.example.listview."+val);
            Intent intent = new Intent(MainActivity.this,ourClass);
            startActivity(intent);
        }catch(Exception e){
            e.prinStacktrace();
        }
    }*/



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Snackbar.make(findViewById(android.R.id.content),"Successfully signed in. Welcome ",Snackbar.LENGTH_SHORT).show();
                String aKey = null;
                displayChatMessage(aKey);
            }
            else
            {
                Snackbar.make(findViewById(android.R.id.content),"Unable to sign in, please try again",Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        kpg.initialize(2048);

        activity_main = (RelativeLayout)findViewById(R.id.activity_main);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        /*
        fab.setOnClickListener(v -> {
            EditText input = (EditText)findViewById(R.id.input);
            FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()));
            input.setText("");
        });
        */

        //Check if not sign-in then navigate Signin page
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
        else
        {
            Snackbar.make(findViewById(android.R.id.content),"Welcome "+ FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
            //Load content
            String aKey = null;
            displayChatMessage(aKey);
        }

        //Trying to see if I can detect button clicks on the decrypt button
        DecryptButton = (Button)findViewById(R.id.button_decrypt);

        if(DecryptButton != null)
        {
            DecryptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                //This method runs everytime the decrypt button is clicked
                public void onClick(View v) {
                    //Log statement to assure us that we have gotten here
                    Log.d("Ran","Decrypting");

                    displayDecryptedChatMessageCaesar();
                    //Now prompt the user for the key to use for decrypting the message
                    //DecryptionPopup decryptionPopup = new DecryptionPopup();
                    //startActivity(new Intent(MainActivityJ.this,DecryptionPopup.class));
                }

            });
        }

        //Send message
        SendButton = findViewById(R.id.fab);
        if(SendButton != null)
        {
            SendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                //This method runs everytime the decrypt button is clicked
                public void onClick(View v) {
                    //Log statement to assure us that we have gotten here
                    Log.d("Ran","Sending");
                    //Toast.makeText(this, "Sending text..." + findViewById(R.id.input).toString(), Toast.LENGTH_SHORT).show();
                    EditText input = (EditText)findViewById(R.id.input);
                    FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                    input.setText("");
                }

            });
        }
    }

    public void sendMessage(final View view)
    {
        Toast.makeText(this, "Sending text..." + findViewById(R.id.input).toString(), Toast.LENGTH_SHORT).show();
        EditText input = (EditText)findViewById(R.id.input);
        FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        input.setText("");
    }

    public void decryptMessage(final View view)
    {
        //Trying to see if I can detect button clicks on the decrypt button
        DecryptButton = (Button)view;

        if(DecryptButton != null)
        {
            DecryptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                //This method runs everytime the decrypt button is clicked
                public void onClick(View v) {
                    //Log statement to assure us that we have gotten here
                    Log.d("Ran","Decrypting");

                    displayDecryptedChatMessageCaesar();
                    //Now prompt the user for the key to use for decrypting the message
                    //DecryptionPopup decryptionPopup = new DecryptionPopup();
                    //startActivity(new Intent(MainActivityJ.this,DecryptionPopup.class));
                }

            });
        }
    }

    public void displayDecryptedChatMessageCaesar()
    {
        Toast.makeText(this, "Decrypting", Toast.LENGTH_LONG).show();

        ListView listOfMessage = (ListView)findViewById(R.id.list_of_message);
        adapter = new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                //Get references to the views of list_item.xml
                TextView messageText,messageUser,messageTime;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                //Decrypt messages with caesar

                StringBuilder tempMessageText = new StringBuilder(model.getMessageText());
                int ascii = 0;

                //Key for decryption
                int key = 0;
                char ch = ' ';

                /*
                for (int i = 0; i<tempMessageText.length(); i++)
                {
                    tempMessageText.setCharAt(i,toLowerCase(tempMessageText.charAt(i)));
                    ascii = ((((int)tempMessageText.charAt(i)-65 - key) %26) + 65);
                    tempMessageText.setCharAt(i,(char)ascii);
                }
                */

                String decryptedMessage = "";
                for(int i = 0; i < tempMessageText.length(); ++i){
                    ch = tempMessageText.charAt(i);
                    if(ch >= 'a' && ch <= 'z'){
                        ch = (char)(ch - key);

                        if(ch < 'a'){
                            ch = (char)(ch + 'z' - 'a' + 1);
                        }

                        decryptedMessage += ch;
                    }
                    else if(ch >= 'A' && ch <= 'Z'){
                        ch = (char)(ch - key);

                        if(ch < 'A'){
                            ch = (char)(ch + 'Z' - 'A' + 1);
                        }

                        decryptedMessage += ch;
                    }
                    else {
                        decryptedMessage += ch;
                    }
                }
                System.out.println("Decrypted Message = " + decryptedMessage);


                //Here is where I am trying to convert a message to a number so that I can encrypt it with my RSA functions

                long plainText = 100;

                //Get the plaintext from the textview

                //Toast.makeText(getApplicationContext(),"This is an RSA tutorial, please enter numbers only!", Toast.LENGTH_LONG).show();

                StringBuilder tempPlainText = new StringBuilder(model.getMessageText());

                Log.d("Original plaintext: ", (tempPlainText.toString()));

                try
                {
                    plainText = Long.parseLong(stringToLong(tempPlainText.toString()));
                }
                catch (NumberFormatException e)
                {
                    Log.d("Decyrption", "populateView: " + e.toString());
                }

                Log.d("Decrypting plaintext: ", String.valueOf(tempMessageText));


                //Encrypt messages with RSA
                //RSAencryption rsAencryption = new RSAencryption();
                //plainText = rsAencryption.KeyGeneration(plainText);

                messageText.setText(String.valueOf(decryptedMessage));
                //messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
            }
        };

        listOfMessage.setAdapter(adapter);
        waitThread();
    }

    private static String stringToLong(String string) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < string.length(); ++i) {
            int ch = (int) string.charAt(i);
            if (ch < 100) {
                if(ch<10)
                {
                    sb.append('0');
                }
                sb.append('0').append(ch);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public void displayChatMessage(final String aKey)
    {
        Toast.makeText(this, "Encrypting", Toast.LENGTH_LONG).show();

        ListView listOfMessage = (ListView)findViewById(R.id.list_of_message);
        adapter = new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                //Get references to the views of list_item.xml
                TextView messageText,messageUser,messageTime;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);
                StringBuilder tempMessageText = new StringBuilder(model.getMessageText());
                String rsaEncryption = "";
                final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

                //Encrypt messages with RSA
                try {
                    rsaEncryption = encrypt(messageText.toString());
                    RSAEncrypt(messageText.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
                Log.d("RSA", "populateView RSA: " + rsaEncryption);
                //Encrypt messages with caesar

                /*
                int ascii = 0;

                    //Key for encryption
                    int key = 1;

                    for (int i = 0; i<tempMessageText.length(); i++)
                    {
                        tempMessageText.setCharAt(i,toUpperCase(tempMessageText.charAt(i)));
                        ascii = ((((int)tempMessageText.charAt(i)-65 + key) %26) + 65);
                        tempMessageText.setCharAt(i,(char)ascii);
                    }

                //Here is where I am trying to convert a message to a number so that I can encrypt it with my RSA functions

                 long plainText = 100;

                //Get the plaintext from the textview

                //Toast.makeText(getApplicationContext(),"This is an RSA tutorial, please enter numbers only!", Toast.LENGTH_LONG).show();

                StringBuilder tempPlainText = new StringBuilder(model.getMessageText());

                Log.d("Original plaintext: ", (tempPlainText.toString()));

                try
                {
                    plainText = Long.parseLong(stringToLong(tempPlainText.toString()));
                }
                catch (NumberFormatException e)
                {
                    Log.d("Encyrption", "populateView: " + e.toString());
                }

                Log.d("Encrypted plaintext: ", String.valueOf(tempMessageText));
                */
                //Encrypt messages with RSA
                //RSAencryption rsAencryption = new RSAencryption();
                //plainText = rsAencryption.KeyGeneration(plainText);

                try {
                    messageText.setText(Arrays.toString(RSAEncrypt(model.getMessageText())));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
                //messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
            }
        };

        listOfMessage.setAdapter(adapter);
    }
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    KeyPair kp = kpg.genKeyPair();
    PublicKey publicKey = kp.getPublic();
    PrivateKey privateKey = kp.getPrivate();


    public byte[] RSAEncrypt(final String plain) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {


        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plain.getBytes());
        System.out.println("Encrypted" + new String(bytesToHex(encryptedBytes)));
        return encryptedBytes;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String RSADecrypt(final byte[] encryptedBytes) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher1 = Cipher.getInstance("RSA");
        Key privateKey = null;
        cipher1.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher1.doFinal(encryptedBytes);
        String decrypted = new String(decryptedBytes);
        System.out.println("Decrypted" + decrypted);
        return decrypted;
    }

    public final String encrypt(@NotNull String message) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Intrinsics.checkNotNullParameter(message, "message");
        Log.e("TAG", "To encrypt: " + message);
        String publicKeyRaw = "-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAW4WQxF2/qzqYwoQlwkkQIjQJ\nhCm2Hjl00QGkxeO12Py+jytTNYAopHCPpR4SbhE1cFdYx1qjEnFbgeJBxFENyqDg\nGvBhlwrWQXfI9LdA2M3xbr/4wur7ph1c+aQxOpImzslCtHJ5df7cyFrOTnkY+XYY\nyGK2Fsnu67FKWjgVvQIDAQAB\n-----END PUBLIC KEY-----";
        PemReader reader = new PemReader((Reader)(new StringReader(publicKeyRaw)));
        PemObject pemObject = reader.readPemObject();
        Intrinsics.checkNotNullExpressionValue(pemObject, "pemObject");
        byte[] var10000 = pemObject.getContent();
        Intrinsics.checkNotNullExpressionValue(var10000, "pemObject.content");
        byte[] keyBytes = var10000;
        EncodedKeySpec keySpec = (EncodedKeySpec)(new X509EncodedKeySpec(keyBytes));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic((KeySpec)keySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, (Key)key);
        Charset var12 = Charsets.UTF_8;
        boolean var13 = false;
        byte[] var10001 = message.getBytes(var12);
        Intrinsics.checkNotNullExpressionValue(var10001, "(this as java.lang.String).getBytes(charset)");
        var10000 = cipher.doFinal(var10001);
        Intrinsics.checkNotNullExpressionValue(var10000, "cipher.doFinal(message.toByteArray())");
        byte[] cipherData = var10000;
        //String encrypted = Base64.encodeToString(cipherData, 0);
        //Log.e("TAG", "encrypted: " + encrypted);
        return ""; //encrypted;
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return "";//decrypt(Base64.decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    private static String decrypt(byte[] buffer) {
        try {
            Cipher rsa;
            rsa = Cipher.getInstance("RSA");
            rsa.init(Cipher.DECRYPT_MODE, getPrivateKey(PUBLIC_PATH));
            byte[] utf8 = rsa.doFinal(buffer);
            return new String(utf8, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        //PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        /*
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }*/
        return privateKey;
    }



    public void displayDecryptedChatMessage(String valueKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String inputString = valueKey;
        byte[] byteArrray = inputString.getBytes();
        RSADecrypt(byteArrray);
        valueKey = "1";
        Log.d("Decryption", "displayDecryptedChatMessage: " + valueKey.toString());

        //Trying new method for decryption with caesar cipher
        final TextView messageText,messageUser,messageTime;
        View v = new View(getApplicationContext());
        messageText = (TextView) v.findViewById(R.id.message_text);

        Log.d("Key: ", valueKey);

        //Decrypt the message
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                StringBuilder tempMessageText = new StringBuilder(messageText.getText());

                //Key for encryption
                /*
                int key = 1;
                int ascii = 0;


                for (int i = 0; i<messageText.getText().length(); i++)
                {
                    tempMessageText.setCharAt(i,toUpperCase(tempMessageText.charAt(i)));
                    ascii = ((((int)tempMessageText.charAt(i)+65 + key) %26) + 65);
                    tempMessageText.setCharAt(i,(char)ascii);
                }
                */
                String data = dataSnapshot.getValue(String.class);
                CharSequence seq = null;
                Charset charset = null;
                byte[] bytes = seq.toString().getBytes(charset);
                try {
                    messageText.setText(RSADecrypt(bytes));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
                Log.d("Decryption", "Decrypted chat message: " + messageText.getText());
            }

            /**
             * This method will be triggered in the event that this listener either failed at the server, or
             * is removed as a result of the security and Firebase Database rules. For more information on
             * securing your data, see: <a
             * href="https://firebase.google.com/docs/database/security/quickstart" target="_blank"> Security
             * Quickstart</a>
             *
             * @param error A description of the error that occurred
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        //Decrypt Message

        // ListView listOfMessage = (ListView)findViewById(R.id.list_of_message);



        /*
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                adapter.getRef(0).child("-KeVKcb1pqDchA75i-Jd").setValue("I can update this");

            }
        };
        */

        //Update the message with the decrypted text

        //Decrypt messages
                /*
                        StringBuilder decryptedMessage = new StringBuilder(messageText.toString());
                        int ascii = 0;

                        for (int i = 0; i<decryptedMessage.length(); i++)
                        {

                            decryptedMessage.setCharAt(i,toLowerCase(decryptedMessage.charAt(i)));

                            ascii = ((((int)decryptedMessage.charAt(i) - 97 - (int) valueKey.charAt(0) + 26) %26) + 97);
                            decryptedMessage.setCharAt(i,(char)ascii);



                        }
                        */


    }


}