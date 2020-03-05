import javax.crypto.*;
import java.util.Scanner;
import javax.crypto.spec.IvParameterSpec;
import java.util.concurrent.ExecutionException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.awt.*;
import java.awt.event.*;

public class AES1frame extends Frame implements ActionListener{
    //KeyGenerator: used to generate new secret keys for use with a specified algorithm.
    //SecretKey: an interface
    //Cipher: initialized with keys, these are used for encrypting/decrypting data. 
    KeyGenerator keyGenerator;
    SecretKey key;
    String messagept,ciphertext,deciphertext;
    byte[] iv = {1,2,3,4,5,6,7,10,1,2,3,4,5,6,7,10};
    Label message,mode,encrypttext,decrypttext,modetext,encryptnow,decryptnow,msgchecklabel,iclabel;
    Button encrypt,decrypt;
    TextField textmsg,textencrypt,textdecrypt,textalgo,textic;
    Choice modelist,msgchecklist;
    MessageDigest md;
    String ptdigest,decdigest;
    public AES1frame(){
        try{
        keyGenerator = KeyGenerator.getInstance("AES");
        key=keyGenerator.generateKey();
        }catch(Exception e){}
        setTitle("AES encryption");
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);
		setSize(new Dimension(1000,1000));
		setLayout(new FlowLayout());
        setFont(new Font("Arial",Font.BOLD,20));
        setLayout(new GridLayout(9,2,20,20));
        message=new Label("enter message:");
        add(message);
        textmsg=new TextField(50);
        add(textmsg);
        mode=new Label("Select mode:");
        modelist=new Choice();
        modelist.addItem("ECB");
        modelist.addItem("CBC");
        modelist.addItem("CFB");
        modelist.addItem("OFB");
        add(mode);
        add(modelist);
        msgchecklabel=new Label("Message Integrity check:");
        msgchecklist=new Choice();
        msgchecklist.addItem("SHA");
        msgchecklist.addItem("MD5");
        add(msgchecklabel);
        add(msgchecklist);
        encryptnow=new Label("Encrypt:");
        add(encryptnow);
        encrypt=new Button("Encrypt");
        add(encrypt);
        encrypttext=new Label("Cipher text:");
        add(encrypttext);
        textencrypt=new TextField(50);
        add(textencrypt);
        modetext=new Label("Algorithm:");
        add(modetext);
        textalgo=new TextField(50);
        add(textalgo);
        decryptnow=new Label("Dencrypt:");
        add(decryptnow);
        decrypt=new Button("Decrypt");
        add(decrypt);
        decrypttext=new Label("Decipher text");
        add(decrypttext);
        textdecrypt=new TextField(50);
        add(textdecrypt);
        iclabel=new Label("Integrity check:");
        add(iclabel);
        textic=new TextField(50);
        add(textic);
        encrypt.addActionListener(this);
        decrypt.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae){
        try{
        String str=ae.getActionCommand();
        if(str.equals("Encrypt")){
            if(msgchecklist.getSelectedItem()=="SHA"){
                md = MessageDigest.getInstance("SHA-256");
                md.update(textmsg.getText().getBytes());
                byte[] pdigest = md.digest();  
                ptdigest=new String(pdigest);
            }
            else{
                md = MessageDigest.getInstance("MD5");
                md.update(textmsg.getText().getBytes());
                byte[] pdigest = md.digest();  
                ptdigest=new String(pdigest);
            }
            if(modelist.getSelectedItem()=="ECB"){
            byte[] encryptbyte=encryptECB(textmsg.getText(),key);
            ciphertext=new String(encryptbyte);
            System.out.println("mode ciphertext:"+ciphertext+'\n');
            textencrypt.setText(ciphertext);
            }
            if(modelist.getSelectedItem()=="CBC"){
            byte[] encryptbyte=encryptCBC(textmsg.getText(),key);
            ciphertext=new String(encryptbyte);
            System.out.println("mode ciphertext:"+ciphertext+'\n');
            textencrypt.setText(ciphertext);
            }
            if(modelist.getSelectedItem()=="CFB"){
            byte[] encryptbyte=encryptCFB(textmsg.getText(),key);
            ciphertext=new String(encryptbyte);
            System.out.println("mode ciphertext:"+ciphertext+'\n');
            textencrypt.setText(ciphertext);
            }
            if(modelist.getSelectedItem()=="OFB"){
            byte[] encryptbyte=encryptOFB(textmsg.getText(),key);
            ciphertext=new String(encryptbyte);
            System.out.println("mode ciphertext:"+ciphertext+'\n');
            textencrypt.setText(ciphertext);
            }
        }
        if(str.equals("Decrypt")){
            
            if(modelist.getSelectedItem()=="ECB"){
            byte[] decryptbyte=decryptECB(ciphertext,key);
            deciphertext=new String(decryptbyte);
            System.out.println("mode deciphertext : "+deciphertext+'\n');
            textdecrypt.setText(deciphertext+"");
            }
            if(modelist.getSelectedItem()=="CBC"){
            byte[] decryptbyte=decryptCBC(ciphertext,key);
            deciphertext=new String(decryptbyte);
            System.out.println("mode deciphertext : "+deciphertext+'\n');
            textdecrypt.setText(deciphertext+"");
            }
            if(modelist.getSelectedItem()=="CFB"){
            byte[] decryptbyte=decryptCFB(ciphertext,key);
            deciphertext=new String(decryptbyte);
            System.out.println("mode deciphertext : "+deciphertext+'\n');
            textdecrypt.setText(deciphertext+"");
            }
            if(modelist.getSelectedItem()=="OFB"){
            byte[] decryptbyte=decryptOFB(ciphertext,key);
            deciphertext=new String(decryptbyte);
            System.out.println("mode deciphertext : "+deciphertext+'\n');
            textdecrypt.setText(deciphertext+"");
            }
            if(msgchecklist.getSelectedItem()=="SHA"){
                md.update(deciphertext.getBytes());
                byte[] ddigest = md.digest();  
                decdigest=new String(ddigest);
                textic.setText((decdigest.equals(ptdigest))+"");
                if((decdigest.equals(ptdigest))){textic.setBackground(Color.GREEN);}
            }
            if(msgchecklist.getSelectedItem()=="MD5"){
                md.update(deciphertext.getBytes());
                byte[] ddigest = md.digest();  
                decdigest=new String(ddigest);
                textic.setText((decdigest.equals(ptdigest))+"");
                if((decdigest.equals(ptdigest))){textic.setBackground(Color.GREEN);}
            }
        }
        
        }catch(Exception e){}
    }
    public Insets getInsets(){
        return new Insets(50,50,50,50);
    }
    byte[] encryptECB(String message,SecretKey key){
        try{
            Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] ciphertextbyte = encryptCipher.doFinal(message.getBytes());
            System.out.println('\n'+"Algorithm : "+encryptCipher.getAlgorithm()+'\n');
            textalgo.setText(encryptCipher.getAlgorithm());
            //System.out.println('\n'+"private key:"+PRIVATE_KEY+'\n');
            //System.out.println('\n'+"secret key:"+encryptCipher.SECRET_KEY+'\n');
            //System.out.println('\n'+"block size:"+encryptCipher.getBlockSize()+'\n');
            //System.out.println('\n'+"output size:"+encryptCipher.getOutputSize()+'\n');
            System.out.print(encryptCipher.getAlgorithm()+" ");
            return ciphertextbyte;         
        }catch (Exception e) {
               e.printStackTrace();
            }
            return null;
    }
    byte[] decryptECB(String ct,SecretKey key)throws Exception{
        try{               
            Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] deciphertextbyte = decryptCipher.doFinal(ct.getBytes());
            System.out.println('\n'+"Algorithm : "+decryptCipher.getAlgorithm()+'\n');
            System.out.print(decryptCipher.getAlgorithm()+" ");
            return deciphertextbyte;         
        }catch (Exception e) {
                 e.printStackTrace();
            }
            return null;
    }
    byte[] encryptCBC(String message,SecretKey key){
        try{
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);   
            Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key,paramSpec);
            byte[] ciphertextbyte = encryptCipher.doFinal(message.getBytes());
            System.out.println('\n'+"Algorithm : "+encryptCipher.getAlgorithm()+'\n');
            System.out.print(encryptCipher.getAlgorithm()+" ");
            textalgo.setText(encryptCipher.getAlgorithm());
            return ciphertextbyte; 
            }catch (Exception e) {
               e.printStackTrace();
            }
            return null;
    }
    byte[] decryptCBC(String ct,SecretKey key)throws Exception{
        try{
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); 
            Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
            byte[] deciphertextbyte = decryptCipher.doFinal(ct.getBytes());
            System.out.println('\n'+"Algorithm : "+decryptCipher.getAlgorithm()+'\n');
            System.out.print(decryptCipher.getAlgorithm()+" ");
            return deciphertextbyte;        
            }catch (Exception e) {
                 e.printStackTrace();
            }
            return null;
    }
    byte[] encryptCFB(String message,SecretKey key){
        try{
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);   
            Cipher encryptCipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key,paramSpec);
            byte[] ciphertextbyte = encryptCipher.doFinal(message.getBytes());
            System.out.println('\n'+"Algorithm : "+encryptCipher.getAlgorithm()+'\n');
            System.out.print(encryptCipher.getAlgorithm()+" ");
            textalgo.setText(encryptCipher.getAlgorithm());
            return ciphertextbyte; 
            }catch (Exception e) {
               e.printStackTrace();
            }
            return null;
    }
    byte[] decryptCFB(String ct,SecretKey key)throws Exception{
        try{
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); 
            Cipher decryptCipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
            byte[] deciphertextbyte = decryptCipher.doFinal(ct.getBytes());
            System.out.println('\n'+"Algorithm : "+decryptCipher.getAlgorithm()+'\n');
            System.out.print(decryptCipher.getAlgorithm()+" ");

            return deciphertextbyte;        
            }catch (Exception e) {
                 e.printStackTrace();
            }
            return null;
    }
    byte[] encryptOFB(String message,SecretKey key){
        try{
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);   
            Cipher encryptCipher = Cipher.getInstance("AES/OFB/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key,paramSpec);
            byte[] ciphertextbyte = encryptCipher.doFinal(message.getBytes());
            System.out.println('\n'+"Algorithm : "+encryptCipher.getAlgorithm()+'\n');
            System.out.print(encryptCipher.getAlgorithm()+" ");
            textalgo.setText(encryptCipher.getAlgorithm());
            return ciphertextbyte; 
            }catch (Exception e) {
               e.printStackTrace();
            }
            return null;
    }
    byte[] decryptOFB(String ct,SecretKey key)throws Exception{
        try{
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); 
            Cipher decryptCipher = Cipher.getInstance("AES/OFB/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
            byte[] deciphertextbyte = decryptCipher.doFinal(ct.getBytes());
            System.out.println('\n'+"Algorithm : "+decryptCipher.getAlgorithm()+'\n');
            System.out.print(decryptCipher.getAlgorithm()+" ");
            return deciphertextbyte;        
            }catch (Exception e) {
                 e.printStackTrace();
            }
            return null;
    }
    public static void main(String args[]){
         new AES1frame();
    }
}