package com.example.tablebooker;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RestaurantDescActivity extends AppCompatActivity {
    Button makeres;
    Button viewmenu;
    Button contact;
    int in;
    FusedLocationProviderClient fusedLocationProviderClient;
    String sSource;
    String sDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantdesc);


        makeres = (Button) findViewById(R.id.button5);
        makeres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity_Book();
            }
        });

        viewmenu = (Button) findViewById(R.id.buttonemail);
        viewmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity_Menu();
            }
        });

        contact = (Button) findViewById(R.id.contactbutton);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity_Contact();
            }
        });

        Intent intent=getIntent();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        int idx=intent.getIntExtra("index",-1);
        in=idx;

        TextView title = (TextView) findViewById(R.id.contactHeader);
        TextView desc = (TextView) findViewById(R.id.textView2);
        TextView approx=(TextView) findViewById(R.id.textView5);
        RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
        TextView cuisine = (TextView) findViewById(R.id.textView4);
        final TextView addr = (TextView) findViewById(R.id.textView6);
        TextView phone = (TextView) findViewById(R.id.textView7);

        if(idx>-1){
            int pic = getImg(idx);
            ImageView img = (ImageView) findViewById(R.id.imageView3);
            scaleImg(img,pic);
        }

        try {
            InputStream is = getAssets().open("data.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("restaurant");
            Node node=nList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element2 = (Element) node;
                title.setText(getValue("name", element2));
                addr.setText(getValue("addr",element2));
                phone.setText(getValue("phno",element2));
                //desc.setText(desc.getText() + "Cuisine : " + getValue("cuisine", element2) + "\n");
                cuisine.setText(getValue("cuisine",element2));
                rb.setRating(Float.parseFloat(getValue("rating",element2)));
                desc.setText(getValue("desc",element2));
                approx.setText("Approx. ₹"+getValue("approxcost",element2)+" for two people");
            }

            addr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sDestination = addr.getText().toString().trim();
                    if (ActivityCompat.checkSelfPermission(RestaurantDescActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location != null) {
                                    Geocoder geocoder = new Geocoder(RestaurantDescActivity.this, Locale.getDefault());
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        sSource = String.valueOf(Html.fromHtml(addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude()));
                                        DisplayTrack(sSource,sDestination);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                    } else {
                        ActivityCompat.requestPermissions(RestaurantDescActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    }

                }
            });
        } catch (Exception e) {e.printStackTrace();}

    }

    private void DisplayTrack(String sSource, String sDestination) {

        try{
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/"+sSource+"/"+sDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/detail?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void openNewActivity_Book(){
        Intent intent = new Intent(this, BookingPage.class);
        startActivity(intent);
    }

    public void openNewActivity_Menu(){
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("idx",in);
        startActivity(intent);
    }

    public void openNewActivity_Contact(){
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("idx",in);
        startActivity(intent);
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private int getImg (int index){
        switch(index){
            case 0: return R.drawable.orangewok;
            case 1: return R.drawable.sangeetha;
            case 2: return R.drawable.barbeque_nation;
            default: return -1;
        }
    }

    private void scaleImg(ImageView img, int pic){

        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),pic,options);

        int imgWidth = options.outWidth;
        int screenWidth= screen.getWidth();

        if(imgWidth > screenWidth){
            options.inSampleSize = Math.round((float)imgWidth/(float)screenWidth);
        }

        options.inJustDecodeBounds= false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(),pic,options);
        img.setImageBitmap(scaledImg );
    }

}
