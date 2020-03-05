import java.util.*;
import javax.crypto.Cipher;
import java.security.*;
import java.awt.*;
import java.awt.event.*;

public class RSA1frame extends Frame implements ActionListener{
    KeyPairGenerator generator;
    //SecretKey key;
    KeyPair kpair;
    String messagept,ciphertext,deciphertext;
    //byte[] iv = {1,2,3,4,5,6,7,10,1,2,3,4,5,6,7,10};
    Label message,mode,encrypttext,decrypttext,modetext,encryptnow,decryptnow,msgchecklabel,iclabel;
    Button encrypt,decrypt;
    TextField textmsg,textencrypt,textdecrypt,textalgo,textic;
    Choice modelist,msgchecklist;
    MessageDigest md;
    String ptdigest,decdigest;
    public RSA1frame(){
    try{
        generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        kpair = generator.generateKeyPair();
        }catch(Exception e){}
        setTitle("RSA algorithm");
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);
		setSize(new Dimension(1100,1000));
		setLayout(new FlowLayout());
        setFont(new Font("Arial",Font.BOLD,20));
        setLayout(new GridLayout(8,2,20,20));
        message=new Label("enter message:");
        add(message);
        textmsg=new TextField(50);
        add(textmsg);
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
           // if(modelist.getSelectedItem()=="ECB"){
            String ciphertext=encrypt(textmsg.getText(),kpair.getPublic());
            textencrypt.setText(ciphertext);
          //  }
           
        }
        if(str.equals("Decrypt")){
            
            //if(modelist.getSelectedItem()=="ECB"){
            String deciphertext = decrypt(textencrypt.getText(),kpair.getPrivate());
            System.out.println("mode deciphertext : "+deciphertext+'\n');
            textdecrypt.setText(deciphertext+"");
           // }
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
    String encrypt(String message,PublicKey publickey) throws Exception{
        //System.out.println('\n'+" Public key : "+publickey);
        Cipher encryptcipher=Cipher.getInstance("RSA");
        encryptcipher.init(Cipher.ENCRYPT_MODE, publickey);
        byte[] ciphertext = encryptcipher.doFinal(message.getBytes());
        //String cipherstring=new String(ciphertext);

        //System.out.println("encrypted text is:"+cipherstring);
        System.out.println('\n'+"Encrypt Algorithm : "+encryptcipher.getAlgorithm()+'\n');
        //System.out.print(encryptcipher.getAlgorithm()+" ");
        textalgo.setText(encryptcipher.getAlgorithm());
        return Base64.getEncoder().encodeToString(ciphertext);

    }
    String decrypt(String cipheredtext,PrivateKey privatekey) throws Exception{
        byte[] bytes = Base64.getDecoder().decode(cipheredtext);
        //System.out.println(" Private key : "+privatekey);
        Cipher decryptcipher = Cipher.getInstance("RSA");
        decryptcipher.init(Cipher.DECRYPT_MODE, privatekey);
        byte[] x=decryptcipher.doFinal(bytes);

        System.out.println("Decrypt Algorithm : "+decryptcipher.getAlgorithm()+'\n');
        System.out.print(decryptcipher.getAlgorithm()+" ");

        return new String(x);
    }
    public static void main(String args[]){
        new RSA1frame();
    }
}