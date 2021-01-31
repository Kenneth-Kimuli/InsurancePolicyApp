package com.ipa.www.insuracepolicyapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Screen2 extends Activity{
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle) */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
        TextView showCartContent = (TextView)findViewById(R.id.showcart);
        final Controller ct = (Controller)getApplicationContext();
        final int CartSize = ct.getCart().getCartsize();
        String show = "";
        for(int i=0;i<CartSize;i++){
            String pName = ct.getCart().getProducts(i).getProductName();
            int  pPrice = ct.getCart().getProducts(i).getProductPrice();
            String pDisc = ct.getCart().getProducts(i).getProductDesc();
            show += "Product Name:"+pName+" "+"Price : "+pPrice+""+"Discription : "+pDisc+""+    "-----------------------------------";
        }
        showCartContent.setText(show);
    }
}
