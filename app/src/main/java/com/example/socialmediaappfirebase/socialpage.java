package com.example.socialmediaappfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class socialpage extends AppCompatActivity implements AdapterView.OnItemClickListener,addingcdata{
    private FirebaseAuth mAuth;
    private EditText ed1;
    private Button b1,b2up;
    private ImageView img1;
    private ListView la1;
    public static List<HashMap<String,String>> adding;
    public static final int PICK_IMAGE = 1;
    boolean isimagefittoscreen;
    String imageidentifier;
    public ArrayList<String> ke,save;
    String datalink;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialpage);
        //intialization
        mAuth=FirebaseAuth.getInstance();//firebase object
        ed1=(EditText)findViewById(R.id.descripted);
        b1=(Button)findViewById(R.id.contentcreator);
        img1=(ImageView)findViewById(R.id.imageView2);
        la1=(ListView)findViewById(R.id.listitems);
        img1.setVisibility(View.GONE);
        b2up=(Button)findViewById(R.id.uploading);
        b2up.setVisibility(View.GONE);
        adding= new ArrayList<HashMap<String, String>>();
        ke=new ArrayList<>();
        save=new ArrayList<>();
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,ke);
        la1.setAdapter(adapter);
la1.setOnItemClickListener( this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this code open gallery to collect image
                Intent i1=new Intent();
                i1.setType("image/*");
                i1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i1,PICK_IMAGE);
                img1.setVisibility(View.VISIBLE);
                b2up.setVisibility(View.VISIBLE);
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isimagefittoscreen){
                            isimagefittoscreen=false;
                            img1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,200));
                    img1.setAdjustViewBounds(true);

                }
                else{
                    isimagefittoscreen=true;
                    img1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                    img1.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });
        b2up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimagetoserver();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:

                mAuth.signOut();
                finish();
                break;
            case R.id.viewing:
                Intent i2=new Intent(socialpage.this,recyclerpage.class);
                startActivity(i2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mynenu,menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    //its run when we select our image
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            //uri stands for universal resourse identifier
            Uri fullPhotoUri = data.getData();
            img1.setImageURI(fullPhotoUri);
        }
    }
//this is code for uploading image to storage firebase
    private void uploadimagetoserver(){

// Get the data from an ImageView as bytes
        img1.setDrawingCacheEnabled(true);
        img1.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img1.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();


        imageidentifier= UUID.randomUUID()+".png";


        UploadTask uploadTask = FirebaseStorage.getInstance().getReference().child("my_images").child(imageidentifier).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(socialpage.this,"failure occure",Toast.LENGTH_LONG).show();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
             //   datalink=taskSnapshot.getStorage().getDownloadUrl().toString();

                Toast.makeText(socialpage.this,"Successfully uploading done",Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference().child("USERS").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        save.add(snapshot.getKey());
                        String  username= Objects.requireNonNull(snapshot.child("username").getValue()).toString();
                        ke.add(username);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            datalink=task.getResult().toString();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //here this 4 line of code save all the data like photo link and all this
        HashMap<String,String> hs1=new HashMap<>();
        hs1.put("from whom", ke.get(position));
        hs1.put("IMAGE LINK",datalink);
        hs1.put("des",ed1.getText().toString());
        //here using this line of code we save the hs1 data to database and using push command we create random id

        FirebaseDatabase.getInstance().getReference().child("RETRIVEDDATA").child("RECEIVED POST").push().setValue(hs1);

        adding.add(hs1);


    }



    @Override
    public List<HashMap<String, String>> store() {
        return adding;
    }
}